package org.xzframewordk.wx.oa.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface TemplateMessage extends Serializable {
    static TemplateMessageBuilder newMessage() {
        return new TemplateMessageBuilder();
    }

    String touser();

    @JsonGetter("template_id")
    String templateId();

    String url();

    TemplateMessageMiniprogram miniprogram();

    @JsonGetter("client_msg_id")
    String clientMsgId();

    Map<String, TemplateMessageData> data();

    class TemplateMessageBuilder {
        private final Map<String, TemplateMessageData> data = new HashMap<>();
        private String touser;
        private String templateId;
        private String url;
        private TemplateMessageMiniprogram miniprogram;
        private String clientMsgId;

        TemplateMessageBuilder() {
        }

        public TemplateMessageBuilder touser(String touser) {
            this.touser = touser;
            return this;
        }

        public TemplateMessageBuilder templateId(String templateId) {
            this.templateId = templateId;
            return this;

        }

        public TemplateMessageBuilder url(String url) {
            this.url = url;
            return this;
        }

        public TemplateMessageBuilder miniprogram(String miniprogram, String pagepath) {
            this.miniprogram = new DefaultTemplateMessageMiniprogram(miniprogram, pagepath);
            return this;
        }

        public TemplateMessageBuilder clientMsgId(String clientMsgId) {
            this.clientMsgId = clientMsgId;
            return this;
        }

        public TemplateMessageBuilder addData(String key, String value) {
            this.data.put(key, TemplateMessageData.newData(value));
            return this;
        }

        public TemplateMessage build() {
            return new DefaultTemplateMessage(
                    touser,
                    templateId,
                    url,
                    miniprogram,
                    clientMsgId,
                    data
            );
        }
    }


}

record DefaultTemplateMessage(
        String touser,
        @JsonGetter("template_id")
        String templateId,
        String url,
        TemplateMessageMiniprogram miniprogram,
        @JsonGetter("client_msg_id")
        String clientMsgId,
        Map<String, TemplateMessageData> data
) implements TemplateMessage {
    @Serial
    private static final long serialVersionUID = 6810743128710292145L;

    DefaultTemplateMessage(
            String touser,
            String templateId,
            String url,
            TemplateMessageMiniprogram miniprogram,
            String clientMsgId,
            Map<String, TemplateMessageData> data
    ) {
        this.touser = touser;
        this.templateId = templateId;
        this.url = url;
        this.miniprogram = miniprogram;
        this.clientMsgId = clientMsgId;
        this.data = Map.copyOf(data);
    }
}

