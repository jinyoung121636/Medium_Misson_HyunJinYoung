package com.ll.medium.domain.post;

import com.ll.medium.DataNotFoundException;
import com.ll.medium.domain.member.SiteMember;
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
    public void create(String subject, String content, SiteMember member){
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setAuthor(member);
        this.postRepository.save(post);
    }
//    //공개된 글
//    public Page<Post> getPublishedPosts(Pageable pageable){
//        return postRepository.findByIsPublishedTrue(pageable);
//    }

    // post 전체 페이징
    public Page<Post> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

//     post 최신글 30개 가져오기
    public List<Post> getNewList(){return postRepository.findTop30ByOrderByCreateDateDesc();}

    // post 수정
    public void modify(Post post, String subject, String content){
        post.setSubject(subject);
        post.setContent(content);
        this.postRepository.save(post);
    }

    //post 삭제
    public void delete(Post post){
        this.postRepository.delete(post);
    }

    // mylist
    public List<Post> getMylist(String membername){

        return postRepository.findByAuthorMembername(membername);}

    //조회수
    @Transactional
    public void increaseViewCount(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        post.increaseViewCount();
        postRepository.save(post);
    }

    //좋아요
    @Transactional
    public void increaseLikeCount(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        post.increaseLikeCount();
        postRepository.save(post);
    }

    public void vote(Post post, SiteMember siteMember){
        post.getVoter().add(siteMember);
        this.postRepository.save(post);
    }
}
