package com.timcastelijns.room15bot.usecases

import com.timcastelijns.room15bot.bot.usecases.AhmadUseCase
import org.junit.Test
import kotlin.test.assertTrue

class AhmadUseCaseTest {
    private val internships = listOf(
            "I dunno man, I'm just an intern @ Aflac.",
            "I dunno man, I'm just an intern @ Caterpillar.",
            "I dunno man, I'm just an intern @ Quantum Solutions Corporation.",
            "I dunno man, I'm just an intern @ Roland Machinery Co..",
            "I dunno man, I'm just an intern @ State of Illinois.",
            "I dunno man, I'm just an intern @ Microsoft.",
            "I dunno man, I'm just an intern @ OkCupid.",
            "I dunno man, I'm just an intern @ Tinder.",
            "I dunno man, I'm just an intern @ Uber.",
            "I dunno man, I'm just an intern @ Lyft.",
            "I dunno man, I'm just an intern @ Bank of America.",
            "I dunno man, I'm just an intern @ Google.",
            "I dunno man, I'm just an intern @ Kayak.",
            "I dunno man, I'm just an intern @ Cisco.",
            "I dunno man, I'm just an intern @ Ubiquity Networks.",
            "I dunno man, I'm just an intern @ Snapchat.",
            "I dunno man, I'm just an intern @ Facebook.",
            "I dunno man, I'm just an intern @ Instagram https://www.instagram.com/rashiq.z/.",
            "I dunno man, I'm just an intern @ Twitter.",
            "I dunno man, I'm just an intern @ Coinbase.",
            "I dunno man, I'm just an intern @ Dell.",
            "I dunno man, I'm just an intern @ Deutsche Bank.",
            "I dunno man, I'm just an intern @ General Electric.",
            "I dunno man, I'm just an intern @ Groupon.",
            "I dunno man, I'm just an intern @ Hilton.",
            "I dunno man, I'm just an intern @ Honda.",
            "I dunno man, I'm just an intern @ HP, Inc..",
            "I dunno man, I'm just an intern @ IBM.",
            "I dunno man, I'm just an intern @ JetBlue Airways.",
            "I dunno man, I'm just an intern @ Johnson & Johnson.",
            "I dunno man, I'm just an intern @ Kroger.",
            "I dunno man, I'm just an intern @ L’Oréal.",
            "I dunno man, I'm just an intern @ Macy's, Inc..",
            "I dunno man, I'm just an intern @ MetLife.",
            "I dunno man, I'm just an intern @ Morgan Stanley.",
            "I dunno man, I'm just an intern @ Motorola Solutions, Inc..",
            "I dunno man, I'm just an intern @ NASA.",
            "I dunno man, I'm just an intern @ Nascar.",
            "I dunno man, I'm just an intern @ Nike.",
            "I dunno man, I'm just an intern @ Red Bull.",
            "I dunno man, I'm just an intern @ Samsung.",
            "I dunno man, I'm just an intern @ Spotify.",
            "I dunno man, I'm just an intern @ T-Mobile.",
            "I dunno man, I'm just an intern @ The Nature's Bounty Co..",
            "I dunno man, I'm just an intern @ Thomson Reuters.",
            "I dunno man, I'm just an intern @ Toyota Motors.",
            "I dunno man, I'm just an intern @ Unilever.",
            "I dunno man, I'm just an intern @ Warner Music Group.",
            "I dunno man, I'm just an intern @ SAP SE.",
            "I dunno man, I'm just an intern @ Lenovo.",
            "I dunno man, I'm just an intern @ Xiaomi.",
            "I dunno man, I'm just an intern @ Sony.",
            "I dunno man, I'm just an intern @ Apple.",
            "I dunno man, I'm just an intern @ Acer.",
            "I dunno man, I'm just an intern @ LG Electronics.",
            "I dunno man, I'm just an intern @ Adidas.",
            "I dunno man, I'm just an intern @ American Express.",
            "I dunno man, I'm just an intern @ Visa.",
            "I dunno man, I'm just an intern @ Mastercard.",
            "I dunno man, I'm just an intern @ Adobe Systems.",
            "I dunno man, I'm just an intern @ The Cheesecake Factory.",
            "I dunno man, I'm just an intern @ NVIDIA.",
            "I dunno man, I'm just an intern @ Intel.",
            "I dunno man, I'm just an intern @ AMD.",
            "I dunno man, I'm just an intern @ McDonald's.",
            "I dunno man, I'm just an intern @ Burger King.",
            "I dunno man, I'm just an intern @ Dropbox.",
            "I dunno man, I'm just an intern @ Pagseguro.",
            "I dunno man, I'm just an intern @ SpaceX.",
            "I dunno man, I'm just an intern @ Nokia.",
            "I dunno man, I'm just an intern @ FedEx.",
            "I dunno man, I'm just an intern @ AT&T.",
            "I dunno man, I'm just an intern @ Siemens.",
            "I dunno man, I'm just an intern @ Delta Airlines.",
            "I dunno man, I'm just an intern @ American airlines.",
            "I dunno man, I'm just an intern @ Kia motors.",
            "I dunno man, I'm just an intern @ Ford motors.",
            "I dunno man, I'm just an intern @ General Motors.",
            "I dunno man, I'm just an intern @ Amazon.",
            "I dunno man, I'm just an intern @ Magneto IT Solutions.",
            "I dunno man, I'm just an intern @ My Apps Development .",
            "I dunno man, I'm just an intern @ QBurst.",
            "I dunno man, I'm just an intern @ Appentus.",
            "I dunno man, I'm just an intern @ Trigent.",
            "I dunno man, I'm just an intern @ Confianz Global.",
            "I dunno man, I'm just an intern @ Endive Software.",
            "I dunno man, I'm just an intern @ Wildnet Technologies.",
            "I dunno man, I'm just an intern @ Tata Consultancy Services Ltd..",
            "I dunno man, I'm just an intern @ Infosys.",
            "I dunno man, I'm just an intern @ ConCur.",
            "I dunno man, I'm just an intern @ VMWare.",
            "I dunno man, I'm just an intern @ Evernote.",
            "I dunno man, I'm just an intern @ PayPal.",
            "I dunno man, I'm just an intern @ Blizzard Entertainment.",
            "I dunno man, I'm just an intern @ LinkedIn.",
            "I dunno man, I'm just an intern @ Oracle.",
            "I dunno man, I'm just an intern @ Tata Consultancy Services.",
            "I dunno man, I'm just an intern @ Slack Technologies, Inc..",
            "I dunno man, I'm just an intern @ Mailchimp.",
            "I dunno man, I'm just an intern @ Namecheap.",
            "I dunno man, I'm just an intern @ Square.",
            "I dunno man, I'm just an intern @ TeamViewer.",
            "I dunno man, I'm just an intern @ Atlassian.",
            "I dunno man, I'm just an intern @ Zendesk.",
            "I dunno man, I'm just an intern @ MuleSoft.",
            "I dunno man, I'm just an intern @ Squarespace.",
            "I dunno man, I'm just an intern @ MathWorks.",
            "I dunno man, I'm just an intern @ LiveChat.",
            "I dunno man, I'm just an intern @ Kahoot!.",
            "I dunno man, I'm just an intern @ Deputy.",
            "I dunno man, I'm just an intern @ Telegram Messenger.",
            "I dunno man, I'm just an intern @ GitHub.",
            "I dunno man, I'm just an intern @ JetBrains.",
            "I dunno man, I'm just an intern @ Balsamiq.",
            "I dunno man, I'm just an intern @ CircleCI.",
            "I dunno man, I'm just an intern @ Wondershare.",
            "I dunno man, I'm just an intern @ Acronis.",
            "I dunno man, I'm just an intern @ Notepad++.",
            "I dunno man, I'm just an intern @ Trimble.",
            "I dunno man, I'm just an intern @ PayScale.",
            "I dunno man, I'm just an intern @ Stack Exchange.",
            "I dunno man, I'm just an intern @ Reddit.",
            "I dunno man, I'm just an intern @ Foxconn.",
            "I dunno man, I'm just an intern @ Alphabet Inc..",
            "I dunno man, I'm just an intern @ Huawei.",
            "I dunno man, I'm just an intern @ Hitachi.",
            "I dunno man, I'm just an intern @ Panasonic.",
            "I dunno man, I'm just an intern @ Accenture.",
            "I dunno man, I'm just an intern @ Taiwan Semiconductor Manufacturing.",
            "I dunno man, I'm just an intern @ Texas Instruments.",
            "I dunno man, I'm just an intern @ Qualcomm.",
            "I dunno man, I'm just an intern @ Micron Technology.",
            "I dunno man, I'm just an intern @ Nintendo.",
            "I dunno man, I'm just an intern @ Broadcom.",
            "I dunno man, I'm just an intern @ ASML.",
            "I dunno man, I'm just an intern @ Applied Materials.",
            "I dunno man, I'm just an intern @ Salesforce.",
            "I dunno man, I'm just an intern @ Raspberry Pi Foundation.")

    @Test
    fun testRandomAhmadReply() {
        assertTrue { internships.contains(AhmadUseCase().execute(Unit)) }
        assertTrue { internships.contains(AhmadUseCase().execute(Unit)) }
        assertTrue { internships.contains(AhmadUseCase().execute(Unit)) }
        assertTrue { internships.contains(AhmadUseCase().execute(Unit)) }
        assertTrue { internships.contains(AhmadUseCase().execute(Unit)) }
    }
}
