package bot

import com.timcastelijns.chatexchange.chat.*
import bot.commands.*
import bot.monitors.ReminderMonitor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import util.MessageFormatter
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Bot(
        private val getUserStatsCommand: GetUserStatsCommand,
        private val syncStarsDataCommand: SyncStarsDataCommand,
        private val getStarsDataCommand: GetStarsDataCommand,
        private val setReminderCommand: SetReminderCommand,
        private val reminderMonitor: ReminderMonitor,
        private val messageFormatter: MessageFormatter
) {

    private val aliveSubject = BehaviorSubject.create<Boolean>()

    private lateinit var room: Room

    private val disposables = CompositeDisposable()

    fun observeLife(): Observable<Boolean> {
        return aliveSubject.hide()
    }

    fun boot(client: StackExchangeClient, roomId: Int) {
        aliveSubject.onNext(true)
        joinRoom(client, roomId)
    }

    private fun die(killer: User) {
        disposables.clear()

        aliveSubject.onNext(false)
        aliveSubject.onComplete()

        println("Died. Killed by ${killer.name}")
    }

    private fun joinRoom(client: StackExchangeClient, roomId: Int) {
        room = client.joinRoom(ChatHost.STACK_OVERFLOW, roomId)
        println("Joined room #$roomId")
    }

    fun start() {
        room.accessLevelChangedEventListener = {
            if (it.accessLevel == AccessLevel.REQUEST) {
                processUserRequestedAccess(it.targetUser)
            }
        }

        room.messagePostedEventListener = {
            println("${it.userName}: ${it.message.content}")

            if (it.message.content!!.startsWith("!")) {
                processMessage(it.message)
            }
        }

        room.messageRepliedToEventListener = {
            println("${it.userName}: ${it.parentMessageId} <- ${it.message.content}")
            if (it.message.content!!.split(" ")[1].startsWith("!")) {
                processReply(it.message)
            }
        }

        monitorReminders()
    }

    private fun processUserRequestedAccess(user: User) {
        disposables.add(getUserStatsCommand.execute(user)
                .subscribe { it ->
                    room.send("${user.name} requested access. $it")
                })
    }

    private fun processMessage(message: Message) {
        val command = message.content!!.substring(1)
        if (command == "shoo" && message.user!!.id == 1843331L) {
            die(killer = message.user!!)
        } else if (command.startsWith("stats")) {
            val userId = command.split(" ").last().toLongOrNull()

            val user = if (userId == null) {
                message.user!!
            } else {
                room.getUser(userId)
            }

            processShowStatsCommand(user)
        } else if (command.startsWith("sync stars")) {
            processSyncStarsCommand()
        } else if (command.startsWith("stars")) {
            val username = if (command.split(" ").size > 1) {
                command.split(" ").last()
            } else null

            processShowStarsCommand(username)
        }
    }

    private fun processReply(message: Message) {
        val command = message.content!!.substring(message.content!!.indexOf(" ") + 2)
        if (command.startsWith("remindme")) {
            processRemindMeCommand(message.id, command)
        }
    }

    private fun processShowStatsCommand(user: User) {
        disposables.add(getUserStatsCommand.execute(user)
                .subscribe { it ->
                    room.send("Stats for ${user.name} -- $it")
                })
    }

    private fun processSyncStarsCommand() {
        disposables.add(syncStarsDataCommand.execute(Unit)
                .subscribe {
                    room.send("Done.")
                })
    }

    private fun processShowStarsCommand(username: String?) {
        disposables.add(getStarsDataCommand.execute(username)
                .subscribe { data ->
                    room.send(messageFormatter.asTableString(data))
                })
    }

    private fun processRemindMeCommand(messageId: Long, command: String) {
        val params = SetReminderCommandParams(messageId,
                command.substring("remindme".length + 1))

        disposables.add(setReminderCommand.execute(params)
                .observeOn(Schedulers.io())
                .subscribe({ triggerDate ->
                    val dtf = DateTimeFormatter.ofPattern("'at' HH:mm 'on' dd MMMM yyyy")
                            .withZone(ZoneOffset.UTC)
                    room.send("Ok, I will remind you ${dtf.format(triggerDate)} (UTC)")
                }, {
                    it.printStackTrace()
                    it.message?.let {
                        room.send(it)
                    }
                }))
    }

    private fun monitorReminders() = disposables.add(reminderMonitor.start(room))

}
