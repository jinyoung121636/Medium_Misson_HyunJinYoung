package com.ll.medium.domain.post;

import com.ll.medium.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    //article 리스트
    public List<Post> getList(){
        return this.postRepository.findAll();
    }

    //해당 id article 가져오기
    public Post getPost(Integer id){
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    // article 생성
    public void create(String subject, String content){
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }
}
