package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.web.bind.annotation.RequestParam;

public record QrCodeRequest(

        @RequestParam(value = "path", required = false)
        String path,

        @RequestParam(value = "width", required = false, defaultValue = "140")
        Integer width,

        @JsonGetter("auto_color")
        @RequestParam(value = "auto_color", required = false, defaultValue = "false")
        Boolean autoColor,

        @JsonGetter("line_color")
        @RequestParam(value = "line_color", required = false)
        QrCodeColor lineColor,

        @JsonGetter("is_hyaline")
        @RequestParam(value = "is_hyaline", required = false, defaultValue = "false")
        Boolean hyaline,

        @JsonGetter("env_version")
        @RequestParam(value = "env_version", required = false, defaultValue = "release")
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
