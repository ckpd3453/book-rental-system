package com.crio.bookrentalsystem.service;

import com.crio.bookrentalsystem.dto.ResponseDto;
import com.crio.bookrentalsystem.model.Book;
import com.crio.bookrentalsystem.model.Rental;
import com.crio.bookrentalsystem.model.User;
import com.crio.bookrentalsystem.repository.BookRepo;
import com.crio.bookrentalsystem.repository.RentalRepo;
import com.crio.bookrentalsystem.repository.UserRepo;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RentalService implements IRentalService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BookRepo bookRepo;
    
    @Autowired
    private RentalRepo rentalRepo;

    @Override
    public ResponseDto rentBook(int userId, int bookId) {
        try {
            Optional<User> userOpt = userRepo.findById(userId);
            Optional<Book> bookOpt = bookRepo.findById(bookId);

            if (userOpt.isEmpty()) {
                throw new IllegalStateException("User not found");
            }

            if (bookOpt.isEmpty()) {
                throw new BadRequestException("Book not found");
            }

            User user = userOpt.get();
            Book book = bookOpt.get();

            // Check if user already has a rental entry
            Optional<Rental> existingRentalOpt = rentalRepo.findByUserAndReturnedFalse(user);

            Rental rental;
            if (existingRentalOpt.isPresent()) {
                rental = existingRentalOpt.get();

                System.out.println("Rental List ::::"+ rental.getBookList().size());
                // Check if already has 2 books rented
                if (rental.getBookList().size() >= 2) {
                    throw new IllegalStateException("User already has 2 active rentals.");
                }

                rental.getBookList().add(book);
                System.out.println("Rental List after adding books::::"+ rental.getBookList().size());
            } else {
                // Create a new rental entry
                Set<Book> books = new HashSet<>();
                books.add(book);
                rental = new Rental(books, user, LocalDate.now().toString());
            }

            rentalRepo.save(rental);
            return new ResponseDto(HttpStatus.ACCEPTED, rental, "Book rented successfully");

        } catch (Exception ex) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, null, "Error: " + ex.getMessage());
        }
    }

    @Override
    public ResponseDto returnBook(int userId, int bookId) {
        try {
            Optional<User> userOpt = userRepo.findById(userId);
            Optional<Book> bookOpt = bookRepo.findById(bookId);

            if (userOpt.isEmpty()) {
                throw new IllegalStateException("User not found");
            }

            if (bookOpt.isEmpty()) {
                throw new BadRequestException("Book not found");
            }

            User user = userOpt.get();
            Book book = bookOpt.get();

            // Find the rental entry where this user has an active rental
            Optional<Rental> rentalOpt = rentalRepo.findByUserAndReturnedFalse(user);

            if (rentalOpt.isEmpty()) {
                throw new IllegalStateException("No active rental found for this user.");
            }

            Rental rental = rentalOpt.get();

            // Check if the book is part of the rental
            if (!rental.getBookList().contains(book)) {
                throw new IllegalStateException("This book was not rented by the user.");
            }

            // Remove the book from the rental list
            rental.getBookList().remove(book);

            // If all books are returned, mark the rental as returned
            if (rental.getBookList().isEmpty()) {
                rental.setReturned(true);
                rental.setReturnDate(LocalDate.now().toString());
            }

            Rental updatedRental = rentalRepo.save(rental);

            return new ResponseDto(HttpStatus.ACCEPTED, updatedRental, "Book returned successfully");
        } catch (Exception ex) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, false, "Error: " + ex.getMessage());
        }
    }


    @Override
    public ResponseDto getAllRentedBooksByUser(int userId) {
        try {
            Optional<User> userOpt = userRepo.findById(userId);
            if (userOpt.isEmpty()) {
                throw new IllegalStateException("User not found");
            }

            Optional<Rental> userRentalOpt = rentalRepo.findByUserAndReturnedFalse(userOpt.get());

            if (userRentalOpt.isEmpty()) {
                return new ResponseDto(HttpStatus.OK, new ArrayList<>(), "No active rentals found");
            }

            return new ResponseDto(HttpStatus.OK, userRentalOpt.get(), "Rental fetched successfully!");
        } catch (Exception ex) {
            return new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, null, "Something went wrong: " + ex.getMessage());
        }
    }

}
