package com.example.EAIGroupWebSite.services;

import com.example.EAIGroupWebSite.Utils.Utils;
import com.example.EAIGroupWebSite.models.ERole;
import com.example.EAIGroupWebSite.models.Role;
import com.example.EAIGroupWebSite.models.User;
import com.example.EAIGroupWebSite.payload.request.UserRequest;
import com.example.EAIGroupWebSite.repository.RoleRepository;
import com.example.EAIGroupWebSite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public String getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get().toString();
        }else{
            return "user not found";
        }
    }
    public String createUser(UserRequest userRequest){
        // Check if username or email are not taken and if the new email is valid.
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return "Error: Username is already taken!";
        }
        if(!Utils.emailCheck(userRequest.getEmail())){
            return "Error: Email is not accepted !";
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return "Error: Email is already in use!";
        }
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .country(userRequest.getCountry())
                .description(userRequest.getDescription())
                .build();
        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "cm" -> {
                        Role cmRole = roleRepository.findByName(ERole.ROLE_COMMUNITY_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(cmRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        // set Admin role if isAdmin is true and the user is from France.
        if(!roles.contains(adminRole) && userRequest.getCountry().equalsIgnoreCase("france") && userRequest.getIsAdmin()){
            roles.add(adminRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully !";
    }
    public String updateUser(UserRequest userToUpdate){
        // Check changes validity
        if (userRepository.existsById(userToUpdate.getId())){
            if (userRepository.existsByUsername(userToUpdate.getUsername())) {
                return "Error : Username is already taken!";
            }
            if((userToUpdate.getEmail() == null) || (userToUpdate.getPassword() == null) || (userToUpdate.getUsername() == null)){
                return "Error : email, password and username can not be null";
            }
            if(!Utils.emailCheck(userToUpdate.getEmail())) {
                return "Error: Email is not accepted !";
            }
            if (userRepository.existsByEmail(userToUpdate.getEmail())) {
                return "Error: Email is already in suse!";
            }
            User user = User.builder()
                    .id(userToUpdate.getId())
                    .username(userToUpdate.getUsername())
                    .email(userToUpdate.getEmail())
                    .password(encoder.encode(userToUpdate.getPassword()))
                    .country(userToUpdate.getCountry())
                    .description(userToUpdate.getDescription())
                    .build();
            Set<String> strRoles = userToUpdate.getRole();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin" -> {
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        }
                        case "cm" -> {
                            Role cmRole = roleRepository.findByName(ERole.ROLE_COMMUNITY_MANAGER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(cmRole);
                        }
                        case "supplier" -> {
                            Role supplierRole = roleRepository.findByName(ERole.ROLE_SUPPLIER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(supplierRole);
                        }
                        default -> {
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                        }
                    }
                });
            }
            user.setRoles(roles);
            User userUpdated = userRepository.save(user);
            return (userUpdated.toString());
        }
        return "Not existing User !";
    }
    public String deleteUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            User userToDelete = userRepository.findByUsername(user.getUsername()).orElseThrow(()->new RuntimeException("Error: User is not found."));
            userRepository.deleteById(userToDelete.getId());
            return "User with id : "+userToDelete.getId()+" deleted !";
        }
        return "Not existing User !";
    }
}
