package com.crio.bookrentalsystem.controller;

import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.dto.UserDto;
import com.crio.bookrentalsystem.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto){
        ResponseDto responseDto = userService.createUser(userDto);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @PutMapping
    public ResponseEntity<ResponseDto> userLogin(@Valid @RequestBody UserDto userDto){
        ResponseDto responseDto = userService.loginDto(userDto);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllUsers(){
        ResponseDto allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, allUsers.getCode());
    }
}
