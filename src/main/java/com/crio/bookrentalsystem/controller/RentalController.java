package com.crio.bookrentalsystem.controller;

import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.service.IRentalService;
import com.crio.bookrentalsystem.utility.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private IRentalService rentalService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/{bookId}")
    public ResponseEntity<ResponseDto> rentBook(@PathVariable int bookId, @RequestHeader("Authorization") String token){
        int userIdFromToken = jwtService.getUserIdFromToken(token);

        ResponseDto responseDto = rentalService.rentBook(userIdFromToken, bookId);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseDto> returnBook(@PathVariable int bookId, @RequestHeader("Authorization") String token){
        int userIdFromToken = jwtService.getUserIdFromToken(token);

        ResponseDto responseDto = rentalService.returnBook(userIdFromToken, bookId);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllRentalBooks(@RequestHeader("Authorization") String token){
        int userIdFromToken = jwtService.getUserIdFromToken(token);
        ResponseDto allRentedBooksByUser = rentalService.getAllRentedBooksByUser(userIdFromToken);
        return new ResponseEntity<>(allRentedBooksByUser, allRentedBooksByUser.getCode());
    }
}
