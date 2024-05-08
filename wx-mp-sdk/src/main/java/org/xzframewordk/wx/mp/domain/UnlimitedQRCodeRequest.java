package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.web.bind.annotation.RequestParam;

public record UnlimitedQRCodeRequest(
        @RequestParam(value = "scene", required = false)
        String scene,

        @RequestParam(value = "page", required = false)
        String page,

        @JsonGetter("check_path")
        @RequestParam(value = "check_path", defaultValue = "false", required = false)
        Boolean checkPath,

        @RequestParam(value = "width", defaultValue = "140", required = false)
        int width,

        @JsonGetter("auto_color")
        @RequestParam(value = "auto_color", defaultValue = "false", required = false)
        Boolean autoColor,

        @JsonGetter("line_color")
        QrCodeColor lineColor,

        @JsonGetter("is_hyaline")
        @RequestParam(value = "is_hyaline", defaultValue = "false", required = false)
        Boolean hyaline,

        @JsonGetter("env_version")
        @RequestParam(value = "env_version", defaultValue = "release", required = false)
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
