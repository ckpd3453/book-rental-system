package com.crio.bookrentalsystem.controller;

import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.dto.UserDto;
import com.crio.bookrentalsystem.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@RequestBody UserDto userDto){
        ResponseDto responseDto = userService.createUser(userDto);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUsers(){
        ResponseDto allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, allUsers.getCode());
    }
}
