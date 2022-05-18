package com.example.EAIGroupWebSite.controllers;
import com.example.EAIGroupWebSite.models.User;
import com.example.EAIGroupWebSite.payload.request.UserRequest;
import com.example.EAIGroupWebSite.payload.response.MessageResponse;
import com.example.EAIGroupWebSite.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/signup")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRequest signUpRequest) {
        return ResponseEntity.ok(userService.createOrUpdateUser(signUpRequest));
    }
    @GetMapping("/getUser/{username}")
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
    @PutMapping("/updateUser")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequest user) {
            return ResponseEntity.ok(userService.createOrUpdateUser(user));
    }
    @DeleteMapping("/deleteUser")
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
