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
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
    public User createOrUpdateUser(UserRequest userRequest) {
                List<String> strRoles = userRequest.getRoles();
                Set<Role> roles = new HashSet<>();
                User user;
                if ((userRequest.getEmail() == null) || (userRequest.getPassword() == null) || (userRequest.getUsername() == null)) {
                    throw new RuntimeException("Error : email, password and username can not be null");
                }
                if (userRepository.existsByUsername(userRequest.getUsername())) {
                    throw new RuntimeException("Error : Username is already taken!");
                }
                if (!Utils.emailCheck(userRequest.getEmail())) {
                    throw new RuntimeException("Error: Email is not accepted !");
                }
                if (userRepository.existsByEmail(userRequest.getEmail())) {
                    throw new RuntimeException("Error: Email is already use!");
                }
                if(userRequest.getId()== null){
                    user = User.builder()
                            .username(userRequest.getUsername())
                            .email(userRequest.getEmail())
                            .password(encoder.encode(userRequest.getPassword()))
                            .country(userRequest.getCountry())
                            .description(userRequest.getDescription())
                            .build();
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    // set Admin role if isAdmin is true and the user is from France.
                    if(userRequest.getCountry().equalsIgnoreCase("france") && userRequest.getIsAdmin()){
                        roles.add(adminRole);
                    }
                }else{
                    if(!userRepository.existsById(userRequest.getId())){
                        throw new RuntimeException("Error: User not FOUND !");
                    }
                    user = User.builder()
                            .id(userRequest.getId())
                            .username(userRequest.getUsername())
                            .email(userRequest.getEmail())
                            .password(encoder.encode(userRequest.getPassword()))
                            .country(userRequest.getCountry())
                            .description(userRequest.getDescription())
                            .build();
                }
                if (strRoles == null) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                } else {
                   roles =  strRoles.stream().map(role ->{
                        switch (role) {
                            case "admin" -> {
                                return roleRepository.findByName(ERole.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            }
                            case "cm" -> {
                                return roleRepository.findByName(ERole.ROLE_COMMUNITY_MANAGER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            }
                            case "supplier" -> {
                                return roleRepository.findByName(ERole.ROLE_SUPPLIER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            }
                            default -> {
                                return roleRepository.findByName(ERole.ROLE_USER)
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            }
                        }
                    }).collect(Collectors.toSet());
                }
                user.setRoles(roles);
        return (userRepository.save(user));
    }
    public String deleteUser(User user){
            User userToDelete = userRepository.findByUsername(user.getUsername()).orElseThrow(()->new RuntimeException("Error: User is not found."));
            userRepository.deleteById(userToDelete.getId());
            return "User with id : "+userToDelete.getId()+" deleted !";
    }
}
