package com.test.demo.api;

import com.test.demo.dao.UserRepository;
import com.test.demo.domain.User;
import com.test.demo.service.PasswordService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PasswordService passwordService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @TestConfiguration
    static class Config {
        // Other beans
        @Bean
        public PasswordService passwordService() {
            return mock(PasswordService.class);
        }

    }

    @SneakyThrows
    @Test
    void testPerformSuccessfulSave(){
        String fakeUser = "fakeUser";
        String fakePassword = "fakePassword";
        String fakeEncodedPassword = "fakeEncodedPassword";
        when(passwordService.encodeUserPassword(fakeUser, fakePassword)).thenReturn(fakeEncodedPassword);
        User fakeUserObject = new User();
        fakeUserObject.setName("SavedUserName");
        fakeUserObject.setPassword("SavedUserPassword");
        fakeUserObject.setCreatedAt(new Date());
        when(userRepository.save(Mockito.any())).thenReturn(fakeUserObject);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);


        this.mockMvc.perform(get("/users?name=fakeUser&password=fakePassword"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().json("{name: \"" + fakeUser + "\", hashedPassword: \"" + fakeEncodedPassword + "\"}")
                );

        verify(userRepository).save(argument.capture());
        assertEquals(fakeUser, argument.getValue().getName());
        assertEquals(fakeEncodedPassword, argument.getValue().getPassword());
    }
}