package org.example.new_boot_demo.controller;

import org.example.new_boot_demo.pojo.Category;
import org.example.new_boot_demo.pojo.Result;
import org.example.new_boot_demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        // 添加文章分类
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();

        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result<Category> findById(Integer id){
        Category c = categoryService.findById(id);
        return Result.success(c);
    }


    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }


    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
