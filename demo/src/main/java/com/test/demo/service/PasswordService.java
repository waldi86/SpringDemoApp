package com.test.demo.service;

import com.test.demo.service.impl.exception.PasswordEncodeException;

public interface PasswordService {
    String encodeUserPassword(String username, String password) throws PasswordEncodeException;
}
