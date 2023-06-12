package org.xzframewordk.wx.mp.domain

class MiniprogramDsl {
    var appid: String? = null
    var pagepath: String? = null
}

class TemplateMessageDsl {
    var toUser: String? = null
    var templateId: String? = null
    var url: String? = null
    var miniprogram: MiniprogramDsl.() -> Unit = {}
    var clientMsgId: String? = null
    var data: Map<String, String> = mapOf()
}

fun TemplateMessage(config: TemplateMessageDsl.() -> Unit): TemplateMessage {
    val dsl = TemplateMessageDsl().apply(config)
    val c = MiniprogramDsl().apply(dsl.miniprogram)
    val builder = TemplateMessage.newMessage()
        .templateId(dsl.templateId)
        .touser(dsl.toUser)
        .url(dsl.url)
        .miniprogram(c.appid, c.pagepath)
        .clientMsgId(dsl.clientMsgId)
    dsl.data.forEach { (t, u) -> builder.addData(t, u) }
    return builder.build()
}
