package com.test.demo.service.impl;

import com.test.demo.service.PasswordService;
import com.test.demo.service.impl.exception.PasswordEncodeException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest
class SHA256PasswordServiceImplTest {

    @Autowired
    PasswordService passwordService;

    @Test
    public void testEncodeMissingUsername() {
        try {
            passwordService.encodeUserPassword(null, "fakePass");
            fail();
        } catch (PasswordEncodeException e) {
            assertEquals("Missing username.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncodeEmptyUsername() {
        try {
            passwordService.encodeUserPassword("  ", "fakePass");
            fail();
        } catch (PasswordEncodeException e) {
            assertEquals("Missing username.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncodeMissingPassword() {
        try {
            passwordService.encodeUserPassword("fakeUser", null);
            fail();
        } catch (PasswordEncodeException e) {
            assertEquals("Missing password.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testEncodeEmptyPassword() {
        try {
            passwordService.encodeUserPassword("fakeUser", "  ");
            fail();
        } catch (PasswordEncodeException e) {
            assertEquals("Missing password.", e.getMessage());
        } catch (Exception e) {
            fail();
        }
    }
}