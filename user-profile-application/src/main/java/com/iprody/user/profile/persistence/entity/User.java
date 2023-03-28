package com.iprody.user.profile.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    /**
     * ID of the user is a primary key for the table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "seq_users")
    private long id;
    /**
     * First name of the user.
     */
    @Column(nullable = false, length = 30)
    private String firstName;
    /**
     * Last name of the user.
     */
    @Column(nullable = false, length = 30)
    private String lastName;
    /**
     * Email of the user.
     */
    @Email
    @Column(unique = true, length = 50)
    private String email;
    /**
     * Nested object with details of the user.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id", referencedColumnName = "id")
    private UserDetails userDetails;
}
