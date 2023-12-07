package com.ll.medium.domain.comment;

import com.ll.medium.domain.article.Article;
import com.ll.medium.domain.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/comment")
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            BindingResult bindingResult)
    {
        Article article = this.articleService.getArticle(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("article",article);
            return "article_detail";
        }
        this.commentService.create(article, commentForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }
}
