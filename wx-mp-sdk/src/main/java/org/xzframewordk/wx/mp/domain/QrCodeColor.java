package org.xzframewordk.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

public record QrCodeColor(
        @JsonGetter("r")
        Integer red,
        @JsonGetter("g")
        Integer green,
        @JsonGetter("b")
        Integer blue
) {
}
