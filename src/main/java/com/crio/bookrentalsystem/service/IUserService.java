package com.crio.bookrentalsystem.service;


import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.dto.UserDto;

public interface IUserService {
    public ResponseDto createUser(UserDto userDto);

    public ResponseDto getAllUsers();

    ResponseDto loginDto(UserDto userDto);
}
