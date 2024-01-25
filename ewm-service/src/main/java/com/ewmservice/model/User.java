package com.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private   Integer id;
    @Column(name = "name")
    @NotBlank
    @Length(min = 2, max = 250)
    private   String name;
    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    @Column(name = "email")
    private    String email;
}
