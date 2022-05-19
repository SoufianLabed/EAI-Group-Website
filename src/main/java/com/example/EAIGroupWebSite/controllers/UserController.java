package com.example.EAIGroupWebSite.controllers;
import com.example.EAIGroupWebSite.models.Role;
import com.example.EAIGroupWebSite.models.User;
import com.example.EAIGroupWebSite.payload.request.UserRequest;
import com.example.EAIGroupWebSite.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRequest signUpRequest) {
        try{
            return new ResponseEntity<>(userService.createOrUpdateUser(signUpRequest),HttpStatus.CREATED);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"USER NOT FOUND",e);
        }

    }
    @PostMapping("/role")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<List<Role>> getRoles() {
        try{
            return ResponseEntity.ok(userService.getRoles());
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/user/{username}")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"USER NOT FOUND",e);
        }
    }
    @PutMapping("/user")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequest user) {
        try{
            return ResponseEntity.ok(userService.createOrUpdateUser(user));
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ERROR UPDATING USER",e);
        }


    }
    @DeleteMapping("/user")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<String> deleteUser(@Valid @RequestBody User user) {
        try{
            return ResponseEntity.ok(userService.deleteUser(user));
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"USER NOT FOUND",e);
        }
    }
}
