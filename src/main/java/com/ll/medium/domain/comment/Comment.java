package com.ll.medium.domain.comment;

import com.ll.medium.domain.member.SiteMember;
import com.ll.medium.domain.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private Post post;

    @ManyToOne
    private SiteMember author;

    @ManyToMany
    Set<SiteMember> voter;


}
