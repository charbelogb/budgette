package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private List<Account> accounts = new ArrayList<>();

    public User(String id, String email, String passwordHash, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
