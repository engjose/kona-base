package com.kona.base.security.sign;
import com.kona.base.security.base.BaseCoder;
import com.kona.base.security.base.RSACoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author : Yuan.Pan 2019/6/30 12:20 AM
 */
public class SignatureUtil {

    /**
     * sign by key
     *
     * @param param {param-0: request param}
     * @param secretKey {param-1: key}
     * @param signType {param-2: sign type, support md5,rsa}
     * @return {return sign string}
     */
    public static String sign(Map<String, String> param, String secretKey, String signType) {
        String signDate;

        String context = BaseCoder.getStringContext(param, BaseCoder.KEY_MD5.equals(signType) ? secretKey : null);
        switch (signType) {
            case BaseCoder.KEY_MD5:
                signDate = DigestUtils.md5Hex(context);
                break;
            case BaseCoder.KEY_RSA:
                signDate = RSACoder.sign(context, secretKey);
                break;
            default:
                throw new IllegalArgumentException("not support sign type");
        }

        return signDate;
    }

    /**
     * verify sign
     *
     * @param param {param-0: request param}
     * @param secretKey {param-1: key}
     * @param signType {param-2: sign type, support md5,rsa}
     * @return {return: verify ok-->true, else-->false}
     */
    public static Boolean verifySign(Map<String, String> param, String secretKey, String signType) {
        String requestSignature = param.get(BaseCoder.SIGNATURE);
        if (StringUtils.isBlank(requestSignature)) {
            throw new IllegalArgumentException("verify sign can not be null");
        }

        String context = BaseCoder.getStringContext(param, BaseCoder.KEY_MD5.equals(signType) ? secretKey : null);
        switch (signType) {
            case BaseCoder.KEY_MD5:
                return requestSignature.equals(DigestUtils.md5Hex(context));

            case BaseCoder.KEY_RSA:
                return RSACoder.verify(context, secretKey, requestSignature);

            default:
                throw new IllegalArgumentException("not support verifySign");
        }
    }
}
