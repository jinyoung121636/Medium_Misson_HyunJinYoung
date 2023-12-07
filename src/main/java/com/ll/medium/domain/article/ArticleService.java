package com.ll.medium.domain.article;

import com.ll.medium.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    //article 리스트
    public List<Article> getList(){
        return this.articleRepository.findAll();
    }

    //해당 id article 가져오기
    public Article getArticle(Integer id){
        Optional<Article> article = this.articleRepository.findById(id);
        if(article.isPresent()){
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }

    // article 생성
    public void create(String subject, String content){
        Article article = new Article();
        article.setSubject(subject);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }
}
