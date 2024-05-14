package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public record UnlimitedQRCodeRequest(
        String scene,

        String page,

        @JsonGetter("check_path")
        Boolean checkPath,

        int width,

        @JsonGetter("auto_color")
        Boolean autoColor,

        @JsonGetter("line_color")
        QrCodeColor lineColor,

        @JsonGetter("is_hyaline")
        Boolean hyaline,

        @JsonGetter("env_version")
        String envVersion
) {


    public UnlimitedQRCodeRequest {
        page = "";
        checkPath = false;
        width = 140;
        autoColor = false;
        lineColor = null;
        hyaline = false;
        envVersion = "release";
    }
}
