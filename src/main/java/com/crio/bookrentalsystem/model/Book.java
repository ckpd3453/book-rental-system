package com.crio.bookrentalsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Column(unique = true, nullable = false) // Ensuring unique title
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 3, max = 50, message = "Author must be between 3 and 50 characters")
    private String author;

    private String genre;

    @Pattern(regexp = "true|false", message = "Availability status must be true or false")
    private String availabilityStatus = "true"; // Default to true

    public Book() {
    }

    public Book(String title, String author, String genre, String availabilityStatus) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availabilityStatus = availabilityStatus;
    }

    @PrePersist
    public void setDefaultAvailability() {
        if (availabilityStatus == null || availabilityStatus.isBlank()) {
            this.availabilityStatus = "true";
        }
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
