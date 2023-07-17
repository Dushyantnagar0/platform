package com.org.platform.utils;

import lombok.experimental.UtilityClass;

import static org.apache.commons.codec.digest.DigestUtils.sha512Hex;

@UtilityClass
public class HashUtils {

    public String hash(String str) {
        return sha512Hex(str);
    }

}
