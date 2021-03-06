package com.example.EAIGroupWebSite.payload.request;

import java.util.List;
import java.util.Set;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserRequest {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private List<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String country;

    private Boolean isAdmin;

    private String description;
}