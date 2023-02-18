package com.dosi.ac.demo.repositories;

import com.dosi.ac.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * The interface User repository.
 */
@RepositoryRestResource
@CrossOrigin(origins = "*")
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by first name and last name optional.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the optional
     */
    @Query("SELECT u FROM User u WHERE u.first_name = :firstName AND u.last_name = :lastName")
    Optional<User> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
