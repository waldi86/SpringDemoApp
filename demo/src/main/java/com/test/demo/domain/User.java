package com.test.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {

    private Date createdAt;

    @Id
    private String name;

    private String password;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}

