package com.example.EAIGroupWebSite.controllers;
import com.example.EAIGroupWebSite.models.User;
import com.example.EAIGroupWebSite.payload.request.UserRequest;
import com.example.EAIGroupWebSite.payload.response.MessageResponse;
import com.example.EAIGroupWebSite.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest signUpRequest) {
        return ResponseEntity.ok(new MessageResponse(userService.createUser(signUpRequest)));
    }
    @GetMapping("/getUser/{username}")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<?> getUserByUsername(@PathVariable String username)  {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @PutMapping("/updateUser")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest user) {
        return ResponseEntity.ok(new MessageResponse(userService.updateUser(user)));
    }
    @DeleteMapping("/deleteUser")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 401, message = "Not authorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal sever error")
    })
    public ResponseEntity<?> deleteUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(new MessageResponse(userService.deleteUser(user)));
    }
}
