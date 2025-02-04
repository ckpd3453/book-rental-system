package com.crio.bookrentalsystem.service;

import com.crio.bookrentalsystem.appConfig.SecurityConfig;
import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.dto.UserDto;
import com.crio.bookrentalsystem.model.User;
import com.crio.bookrentalsystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public ResponseDto createUser(UserDto userDto) {
        try {
            // Check if email already exists
            if (userRepo.existsByEmail(userDto.getEmail())) {
                return new ResponseDto(HttpStatus.BAD_REQUEST, null, "Email already exists!");
            }

            // Encode password using BCrypt
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());

            // Create a new User object
            User user = new User(userDto.getUserName(), userDto.getEmail(), encodedPassword, userDto.getRole());

            // Save user
            User savedUser = userRepo.save(user);

            return new ResponseDto(HttpStatus.CREATED, savedUser, "User Registered Successfully!");
        } catch (DataIntegrityViolationException ex) {
            return new ResponseDto(HttpStatus.BAD_REQUEST, null, "Email is already in use!");
        } catch (Exception ex) {
            ex.printStackTrace(); // Log full stack trace
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, null, "An error occurred while creating the user.");
        }
    }

    @Override
    public ResponseDto getAllUsers() {
        try{
            List<User> allUsers = userRepo.findAll();

            return new ResponseDto(HttpStatus.OK, allUsers, "All users fetched successfully!");
        }catch (Exception ex){
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, new String(ex.getMessage()), "An error occurred while fetching users!");
        }
    }
}
