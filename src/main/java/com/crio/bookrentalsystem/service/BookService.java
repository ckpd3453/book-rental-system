package com.crio.bookrentalsystem.service;

import com.crio.bookrentalsystem.dto.BookDto;
import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.model.Book;
import com.crio.bookrentalsystem.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepo bookRepo;

    @Override
    public ResponseDto addBook(BookDto bookDto) {
        if (bookRepo.existsByTitle(bookDto.getTitle())) {
            return new ResponseDto(HttpStatus.BAD_REQUEST, null, "Book title already exists!");
        }

        Book book = new Book(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getGenre(), bookDto.getAvailabilityStatus());
        Book savedBook = bookRepo.save(book);

        return new ResponseDto(HttpStatus.CREATED, savedBook, "Book Added Successfully!");
    }

    @Override
    public ResponseDto getAllBooks() {
        try{
            List<Book> allBook = bookRepo.findAll();
            System.out.println("Printing all books :" + allBook);

            if(allBook.isEmpty()){
                return new ResponseDto(HttpStatus.OK, allBook, "Books list is empty");
            }
            return new ResponseDto(HttpStatus.OK, allBook, "Books fetched successful!");
        }catch (Exception ex){
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, new String(ex.getMessage()), "Something Went Wrong");
        }
    }

    @Override
    public ResponseDto getBookByBookId(int bookId) {
        try {
            Optional<Book> book = bookRepo.findById(bookId);

            if(book.isEmpty()){
                return new ResponseDto(HttpStatus.BAD_REQUEST, book, "Book not present with this id");
            }
            return new ResponseDto(HttpStatus.OK, book, "Books fetched successful!");
        }catch (Exception e) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, new String(e.getMessage()), "Something went wrong in service layer");
        }
    }

    @Override
    public ResponseDto updateBookById(int bookId, BookDto bookDto) {
        try {

            Optional<Book> updatedBook = bookRepo.findById(bookId).map(book -> {
                if (bookDto.getTitle() != null) book.setTitle(bookDto.getTitle());
                if (bookDto.getAuthor() != null) book.setAuthor(bookDto.getAuthor());
                if (bookDto.getGenre() != null) book.setGenre(bookDto.getGenre());
                if (bookDto.getAvailabilityStatus() != null)
                    book.setAvailabilityStatus(bookDto.getAvailabilityStatus());

                bookRepo.save(book);
                return book;
            });

            if(updatedBook.isEmpty()){
                return new ResponseDto(HttpStatus.BAD_REQUEST, updatedBook, "Book not present with this id");
            }


            return new ResponseDto(HttpStatus.OK, updatedBook, "Books updated successful!");
        }catch (Exception e) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, new String(e.getMessage()), "Something went wrong in service layer");
        }
    }

    @Override
    public ResponseDto deleteBook(int bookId) {
        try {
            Book book = bookRepo.findById(bookId).get();

            if(book == null){
                return new ResponseDto(HttpStatus.BAD_REQUEST, book, "Book not present with this id");
            }

            bookRepo.delete(book);
            return new ResponseDto(HttpStatus.OK, null, "Books removed successful!");
        }catch (Exception e) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, new String(e.getMessage()), "Something went wrong in service layer");
        }
    }
}
