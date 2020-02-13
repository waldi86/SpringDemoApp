package com.test.demo.service.impl;

import com.test.demo.service.PasswordService;
import com.test.demo.service.impl.exception.PasswordEncodeException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SHA256PasswordServiceImpl implements PasswordService {

    @Override
    public String encodeUserPassword(String username, String password) throws PasswordEncodeException {
        try {
            validateEncodeParameters(username, password);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String originalString = username + password;
            byte[] hash = digest.digest(
                    originalString.getBytes(StandardCharsets.UTF_8));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new PasswordEncodeException("Missing algorithm definition.", e);
        }
    }

    private void validateEncodeParameters(String username, String password) throws PasswordEncodeException{
        if (username == null || username.trim().isEmpty())
            throw new PasswordEncodeException("Missing username.");
        if (password == null || password.trim().isEmpty())
            throw new PasswordEncodeException("Missing password.");
    }

}