package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public record QrCodeRequest(

        String path,

        Integer width,

        @JsonGetter("auto_color")
        Boolean auto_color,

        @JsonGetter("line_color")
        QrCodeColor line_color,

        @JsonGetter("is_hyaline")
        Boolean hyaline,

        @JsonGetter("env_version")
        String env_version
) {
    public QrCodeRequest() {
        this(
                "",
                140,
                false,
                null,
                false,
                "release"
        );
    }
}
