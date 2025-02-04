package com.crio.bookrentalsystem.service;


import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.dto.UserDto;
import org.springframework.stereotype.Service;

public interface IUserService {
    public ResponseDto createUser(UserDto userDto);

    public ResponseDto getAllUsers();
}
