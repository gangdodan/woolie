package com.woolie.user.domain;

import com.woolie.terms.domain.TermsHistory;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static com.woolie.user.domain.enums.RoleName.ROLE_USER;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    private String username;
    private String email;
    private long birth;
    private String password;
    @Setter
    private String profileImageUrl;

    @Embedded
    @Builder.Default
    private Role role = new Role(List.of(ROLE_USER));

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<TermsHistory> termsHistories;

    /**
     *
     * */
}
