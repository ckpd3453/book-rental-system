package com.crio.bookrentalsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rentalId;

    @ManyToMany
    @JoinTable(
            name = "rental_books",
            joinColumns = @JoinColumn(name = "rental_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @NotEmpty(message = "Rental must include at least one book")
    private Set<Book> bookList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required for rental")
    private User user;

    @NotBlank(message = "Rental date is required")
    private String rentalDate;

    private String returnDate;

    private boolean returned = false; // New field to track active rentals

    // Constructors
    public Rental() {}

    public Rental(Set<Book> bookList, User user, String rentalDate) {
        this.bookList = bookList;
        this.user = user;
        this.rentalDate = rentalDate;
        this.returned = false; // Default to false (book not yet returned)
    }

    // Getters and Setters
    public int getRentalId() { return rentalId; }

    public Set<Book> getBookList() { return bookList; }

    public void setBookList(Set<Book> bookList) { this.bookList = bookList; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getRentalDate() { return rentalDate; }

    public void setRentalDate(String rentalDate) { this.rentalDate = rentalDate; }

    public String getReturnDate() { return returnDate; }

    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() { return returned; }

    public void setReturned(boolean returned) { this.returned = returned; }
}
