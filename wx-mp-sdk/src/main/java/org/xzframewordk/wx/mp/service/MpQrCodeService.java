package org.xzframewordk.wx.mp.service;

import org.xzframewordk.wx.mp.domain.QrCodeRequest;
import org.xzframewordk.wx.mp.domain.UnlimitedQRCodeRequest;

public interface MpQrCodeService {
    byte[] getQRCode(String appid, QrCodeRequest request);

    byte[] getUnlimitedQRCode(String appid, UnlimitedQRCodeRequest request);
}
