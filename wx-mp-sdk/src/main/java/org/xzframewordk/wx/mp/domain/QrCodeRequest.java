package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public record QrCodeRequest(

        String path,

        Integer width,

        @JsonGetter("auto_color")
        Boolean autoColor,

        @JsonGetter("line_color")
        QrCodeColor lineColor,

        @JsonGetter("is_hyaline")
        Boolean hyaline,

        @JsonGetter("env_version")
        String envVersion
) {
    public QrCodeRequest {
        path = "";
        width = 140;
        autoColor = false;
        hyaline = false;
        envVersion = "release";
    }
}
