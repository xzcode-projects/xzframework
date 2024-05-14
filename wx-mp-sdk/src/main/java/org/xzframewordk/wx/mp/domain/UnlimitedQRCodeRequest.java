package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public record UnlimitedQRCodeRequest(
        String scene,

        String page,

        @JsonGetter("check_path")
        Boolean check_path,

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

    public UnlimitedQRCodeRequest() {
        this("", "", false, 140, false, null, false, "release");
    }
}
