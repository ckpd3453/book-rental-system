package com.crio.bookrentalsystem.controller;

import com.crio.bookrentalsystem.dto.BookDto;
import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @PostMapping
    public ResponseEntity<ResponseDto> addBook(@RequestBody BookDto bookDto){
        ResponseDto responseDto = bookService.addBook(bookDto);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @GetMapping
    public  ResponseEntity<ResponseDto> getAllBook(){
        ResponseDto allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, allBooks.getCode());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseDto> getBookById(@PathVariable int bookId){
        ResponseDto bookByBookId = bookService.getBookByBookId(bookId);
        return new ResponseEntity<>(bookByBookId, bookByBookId.getCode());
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ResponseDto> updateBook(@PathVariable int bookId, @RequestBody BookDto bookDto){
        ResponseDto responseDto = bookService.updateBookById(bookId, bookDto);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ResponseDto> deleteBook(@PathVariable int bookId){
        ResponseDto responseDto = bookService.deleteBook(bookId);
        return new ResponseEntity<>(responseDto, responseDto.getCode());
    }
}
