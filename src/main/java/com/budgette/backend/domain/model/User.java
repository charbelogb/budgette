package com.budgette.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String id;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    @Builder.Default
    private List<Account> accounts = new ArrayList<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
