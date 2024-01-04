package com.ll.medium.domain.post;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.member.Member;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    //post 리스트
    public List<Post> getList(){
        return this.postRepository.findAll();
    }

    //해당 id post 가져오기
    public Post getPost(Integer id){
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    // post 생성
    public void create(String subject, String content, Member member, boolean isPublished, boolean isPaid){
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(member);
        post.setPublished(isPublished);
        member.setPaid(isPaid);
        post.synchronizeIsPaid();
        this.postRepository.save(post);
    }

    // post 전체 페이징
    public Page<Post> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }


    // post 수정
    public void modify(Post post, String subject, String content, boolean isPublished){
        post.setSubject(subject);
        post.setContent(content);
        post.setPublished(isPublished);
        post.synchronizeIsPaid();
        this.postRepository.save(post);
    }

    //post 삭제
    public void delete(Post post){
        this.postRepository.delete(post);
    }

    // mylist
    public Page<Post> getMyList(String username, int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findByAuthorUsername(username, pageable);
    }

    // newlist
    public List<Post> getNewList(){
        return this.postRepository.findTop30ByOrderByCreateDateDesc();
    }

    //조회수
    @Transactional
    public void increaseViewCount(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        post.increaseViewCount();
        postRepository.save(post);
    }

    public void vote(Post post, Member member){
        post.getVoter().add(member);
        this.postRepository.save(post);
    }

    public void voteCancle(Post post, Member member){
        post.getVoter().remove(member);
        this.postRepository.save(post);
    }

}
