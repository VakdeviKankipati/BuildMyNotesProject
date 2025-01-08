package com.vakya.userrservice.util;

import java.util.Base64;

public class KeyEncoder {
    public static void main(String[] args) {
        String rawKey = "ymyS3cr3tK3yW1th$pec!@lCharsAnd123Numbers";
        String base64Key = Base64.getEncoder().encodeToString(rawKey.getBytes());
        System.out.println("Base64 Encoded Key: " + base64Key);
    }
}

