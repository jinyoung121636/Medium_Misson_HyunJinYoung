package com.ll.medium.domain.article;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/list")
    public String list(Model model){
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList",articleList);
        return "article_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id")Integer id){
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }

    @GetMapping(value = "/create")
    public String articleCreate(ArticleForm articleForm){
        return "article_form";
    }

    @PostMapping(value = "/create")
    public String articleCreate(@Valid ArticleForm articleForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "article_form";
        }
        this.articleService.create(articleForm.getSubject(), articleForm.getContent());
        return "redirect:/article/list/";
    }
}
