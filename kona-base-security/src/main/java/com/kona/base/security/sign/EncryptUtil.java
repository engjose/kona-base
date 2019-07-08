package com.kona.base.security.sign;

import com.alibaba.fastjson.JSON;
import com.kona.base.security.base.BaseCoder;
import com.kona.base.security.base.RSACoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author : Yuan.Pan 2019/6/30 12:20 AM
 */
public class EncryptUtil {

    /**
     * md5
     *
     * @param param {param-0: source param}
     * @param secretKey {param-1: salt}
     * @return {return md5 str}
     */
    public static String md5Encrypt(Map<String, String> param, String secretKey) {
        String context = BaseCoder.getStringContext(param, secretKey);
        return DigestUtils.md5Hex(context);
    }

    /**
     * md5
     *
     * @param param {param-0: source param}
     * @param secretKey {param-1: salt}
     * @return {return md5 str}
     */
    public static String md5Encrypt(String param, String secretKey) {
        return DigestUtils.md5Hex(param + secretKey);
    }

    /**
     * rsa encrypt
     *
     * @param param {param-0: source param}
     * @param secretKey {param-1: private key}
     * @return {return encrypt str}
     */
    public static String rsaEncrypt(Map<String, String> param, String secretKey) {
        String context = BaseCoder.getJsonContext(param, null);
        return RSACoder.encrypt(context, secretKey);
    }

    /**
     * rsa decrypt
     *
     * @param data {param-0: source param}
     * @param secretKey {param-1: public key}
     * @return {return encrypt str}
     */
    public static <T> T rsaDecrypt(String data, String secretKey, Class<T> clazz) {
        String decrypt = RSACoder.decrypt(data, secretKey);
        return (clazz == String.class) ? (T) decrypt : JSON.parseObject(decrypt, clazz);
    }

    /**
     * base64 encode
     *
     * @param param {param-0: source param}
     * @return {return base64 result}
     */
    public static String base64Encrypt(String param) {
        return BaseCoder.encryptBASE64(param.getBytes());
    }

    /**
     * base64 decode
     *
     * @param param {param-0: source param}
     * @return {return base64 decode}
     */
    public static String base64Decrypt(String param) {
        try {
            byte[] bytes = BaseCoder.decryptBASE64(param);
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException("decrypt base64 err");
        }
    }

    /**
     * url encode
     *
     * @param source {param-0: source str}
     * @return {return encode str}
     */
    public static String encode(String source) {
        if (StringUtils.isNotEmpty(source)) {
            try {
                return URLEncoder.encode(source, BaseCoder.ENCODE_CHARSET_UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * url decode
     *
     * @param source {param-0: source str}
     * @return {return decode str}
     */
    public static String decode(String source) {
        if (StringUtils.isNotEmpty(source)) {
            try {
                return URLDecoder.decode(source, BaseCoder.ENCODE_CHARSET_UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
