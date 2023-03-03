package com.iprody.user.profile.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
public class UserDetails {
    /**
     * ID of the user details is a primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * Telegram ID to link with a client profile in [telegram.org](https://telegram.org/).
     */
    @Column(name = "telegram_id", nullable = false)
    private String telegramId;
    /**
     * A personal mobile phone number of the user.
     */
    @Column(name = "mobile_phone", length = 30)
    private String mobilePhone;
    /**
     * A user that holds this data.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
