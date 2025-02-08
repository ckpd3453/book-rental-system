package com.crio.bookrentalsystem.repository;

import com.crio.bookrentalsystem.model.Book;
import com.crio.bookrentalsystem.model.Rental;
import com.crio.bookrentalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepo extends JpaRepository<Rental, Integer> {

    Optional<Rental> findByUserAndReturnedFalse(User user);
}
