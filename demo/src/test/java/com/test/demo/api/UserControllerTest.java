package com.test.demo.api;

import com.test.demo.dao.UserRepository;
import com.test.demo.domain.User;
import com.test.demo.dto.UserDTO;
import com.test.demo.service.PasswordService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;


    @Test
    void testMissingUsername(){
        try {
            userController.save(null, "fakePass");
            fail();
        } catch (ConstraintViolationException e){
            assertTrue(e.getMessage().contains("save.name: must not be blank"));
            assertTrue(e.getMessage().contains("save.name: must not be null"));
        }
    }

    @Test
    void testEmptyUsername(){
        try {
            userController.save("", "fakePass");
            fail();
        } catch (ConstraintViolationException e){
            assertTrue(e.getMessage().contains("save.name: must not be blank"));
            assertTrue(e.getMessage().contains("save.name: size must be between 1 and 2147483647"));
        }
    }

    @Test
    void testUsernameWithEmptyCharsUsername(){
        try {
            userController.save("  ", "fakePass");
            fail();
        } catch (ConstraintViolationException e){
            assertEquals("save.name: must not be blank", e.getMessage());
        }
    }

    @Test
    void testMissingPassword(){
        try {
            userController.save("fakeUser", null);
            fail();
        } catch (ConstraintViolationException e){
            assertTrue(e.getMessage().contains("save.password: must not be blank"));
            assertTrue(e.getMessage().contains("save.password: must not be null"));
        }
    }

    @Test
    void testEmptyPassword(){
        try {
            userController.save("fakeUser", "");
            fail();
        } catch (ConstraintViolationException e){
            assertTrue(e.getMessage().contains("save.password: must not be blank"));
            assertTrue(e.getMessage().contains("save.password: size must be between 3 and 2147483647"));
        }
    }

    @Test
    void testPasswordWithEmptyCharsUsername(){
        try {
            userController.save("fakeUser", "   ");
            fail();
        } catch (ConstraintViolationException e){
            assertEquals("save.password: must not be blank", e.getMessage());
        }
    }

}