package com.zb.cms.user.domain.model;

import com.zb.cms.user.domain.BaseEntity;
import com.zb.cms.user.domain.SignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@AuditOverride(forClass = BaseEntity.class)
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = true)
    private String email;

    private String name;

    private String password;

    private String phone;

    private LocalDate birth;

    @Column(columnDefinition = "int default 0")
    private Integer balance;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(form.getPassword())
                .name(form.getName())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .verify(false)
                .build();
    }
}
