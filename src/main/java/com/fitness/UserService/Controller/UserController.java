package com.fitness.UserService.Controller;

import com.fitness.UserService.Services.UserService;
import com.fitness.UserService.dto.RegisterRequest;
import com.fitness.UserService.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{userID}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userID){
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }




    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request ){
        return ResponseEntity.ok(userService.register(request));

    }
}

