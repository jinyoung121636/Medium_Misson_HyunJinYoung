package com.ll.medium.domain.post;

import com.ll.medium.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void create(String subject, String content){
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    // post 전체 페이징
    public Page<Post> getList(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return this.postRepository.findAll(pageable);
    }

    // post 최신글 30개 가져오기
//    public Post getNewList(LocalDateTime){
//        Pageable pageable = PageRequest.of(page, 30);
//        return this.postRepository.findAll(pageable);
//    }
}
