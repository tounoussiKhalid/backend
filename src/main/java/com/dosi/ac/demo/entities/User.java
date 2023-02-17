package com.dosi.ac.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder @Data
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "first_name", "last_name" }) })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "users_seq", allocationSize = 1, name = "USER_SEQ")
    private Long id;
    private String first_name;
    private String last_name;
    private Date birthdate;
    private String city;
}
