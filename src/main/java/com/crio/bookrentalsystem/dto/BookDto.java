package com.crio.bookrentalsystem.dto;

import jakarta.validation.constraints.*;

public class BookDto {

    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(min = 3, max = 50, message = "Author must be between 3 and 50 characters")
    private String author;

    @Size(max = 50, message = "Genre must be at most 50 characters")
    private String genre;

    @Pattern(regexp = "true|false", message = "Availability status must be true or false")
    private String availabilityStatus;

    // Getters and Setters
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
