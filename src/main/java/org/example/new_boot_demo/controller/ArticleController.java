package org.example.new_boot_demo.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.example.new_boot_demo.pojo.Article;
import org.example.new_boot_demo.pojo.PageBean;
import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.service.ArticleService;
import org.example.new_boot_demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize,
                                          @RequestParam(required = false) Integer categoryId,
                                          @RequestParam(required = false) String state){
        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }

    @GetMapping("/detail")
    public Result<Article> findById(@RequestParam Integer id){

        Article ad = articleService.findById(id);

        return Result.success(ad);
    }

    @PutMapping
    public Result update(@RequestBody Article article){

        articleService.update(article);

        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id){

        articleService.delete(id);
        return Result.success();
    }
}
