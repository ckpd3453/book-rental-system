package com.crio.bookrentalsystem.service;

import com.crio.bookrentalsystem.dto.BookDto;
import com.crio.bookrentalsystem.dto.ResponseDto;

public interface IBookService {

    ResponseDto addBook(BookDto bookDto);
    ResponseDto getAllBooks();
    ResponseDto getBookByBookId(int bookId);
    ResponseDto updateBookById(int bookId, BookDto bookDto);
    ResponseDto deleteBook(int bookId);
}
