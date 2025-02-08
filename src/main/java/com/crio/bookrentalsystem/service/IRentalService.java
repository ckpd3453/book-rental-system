package com.crio.bookrentalsystem.service;


import com.crio.bookrentalsystem.dto.ResponseDto;

public interface IRentalService {

    ResponseDto rentBook(int userId, int bookId);

    ResponseDto returnBook(int userId, int bookId);

    ResponseDto getAllRentedBooksByUser(int userId);
}
