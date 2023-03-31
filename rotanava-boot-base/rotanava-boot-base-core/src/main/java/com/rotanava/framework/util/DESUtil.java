package com.rotanava.framework.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class DESUtil {
//    private static final SymmetricCrypto des;
//    static {
//        // sgEsnN6QWq8W7j5H01020304即为密钥明文长24位，
//        // 不够则会随机补足24位
//        des = new SymmetricCrypto(SymmetricAlgorithm.DES, "sgEsnN6QWq8W7j5H01020304".getBytes());
//    }

    /**
     * des解密
     * @param content 密文
     * @return 解密后的明文
     */
    public static String Decrypt(String content,String key) {
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES,key.getBytes());
        return des.decryptStr(content);
    }

    /**
     * des加密
     * @param content 明文
     * @return 加密后的密文
     */
    public static String Encrypt(String content,String key) {

        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES,key.getBytes());
        return des.encryptHex(content);
    }
}
