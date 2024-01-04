package com.ll.medium.domain.member;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    @Column(unique = true)
    private String email;

    @Column(name = "isPaid")
    private boolean isPaid;
    // 추가: 권한 열거형
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Member() {
        this.isPaid = false; // 기본값은 false
        this.role = MemberRole.USER; // 기본 권한은 USER
    }
}