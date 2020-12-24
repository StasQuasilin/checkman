package utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class Crypto {

    public String md5(String str) {
        return DigestUtils.md5Hex(str);
    }
}
