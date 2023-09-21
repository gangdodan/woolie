package com.woolie.terms.domain;

import com.woolie.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TermsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isAccept;
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
