package com.test.demo.api;

import com.test.demo.domain.User;
import com.test.demo.dao.UserRepository;
import com.test.demo.dto.UserDTO;
import com.test.demo.service.PasswordService;
import com.test.demo.service.impl.exception.PasswordEncodeException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@Validated
public class UserController {

    @Autowired
    PasswordService passwordService;

    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @GetMapping
    public UserDTO save(@RequestParam("name") @Size(min = 1) @NotNull @NotBlank String name,
                        @RequestParam("password") @Size(min = 3) @NotNull @NotBlank String password) {
        User user = new User();
        user.setName(name);
        try {
            user.setPassword(passwordService.encodeUserPassword(name, password));
            userRepository.save(user);
            return convertToDTO(user);
        } catch (PasswordEncodeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setHashedPassword(user.getPassword());
        return userDTO;
    }
}
