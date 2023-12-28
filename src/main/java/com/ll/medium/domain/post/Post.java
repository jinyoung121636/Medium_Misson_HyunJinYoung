package com.ll.medium.domain.post;

import com.ll.medium.domain.comment.Comment;
import com.ll.medium.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    private Member author;

    //조회수
    @Column(name = "view_count")
    private Integer viewCount;

    public Post(){
        this.viewCount = 0;
    }

    public void increaseViewCount() {
        this.viewCount = (this.viewCount == null) ? 1 : this.viewCount+1;
    }

    @ManyToMany
    Set<Member> voter;

//    @Column(name = "IS_PUBLISHED", nullable = true)
//    private boolean isPublished;
//
//    public Boolean getIsPublished(){
//        return isPublished;
//    }
//
//    public void setIsPublished(Boolean isPublished){
//        this.isPublished = isPublished;
//    }

}
