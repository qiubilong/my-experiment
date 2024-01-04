package org.example.哈希算法;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import java.nio.charset.StandardCharsets;

/**
 * @author chenxuegui
 * @since 2023/11/9
 */
public class 哈希算法应用 {
    public static void main(String[] args) {
        System.out.println(md5("1"));
        System.out.println(md5("sdsdfsdfsdhgiohfiohjfa[2u0u9-uiabndiau20-euhfnjbckabf013urufbdbfa20u4-thbnvds"));

        System.out.println(DigestUtils.sha256Hex("1"));
    }


    public static String md5(String data){
        return DigestUtils.md5Hex(data);
    }
}
