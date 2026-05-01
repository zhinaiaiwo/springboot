package org.example.new_boot_demo.service;

import org.example.new_boot_demo.pojo.Category;

import java.util.List;

public interface CategoryService {

    // 添加文章分类
    void add(Category category);

    // 列表查询
    List<Category> list();

    // 根据 id 查询分类信息
    Category findById(Integer id);

    // 更细文章分类
    void update(Category category);

    // 删除文章分类
    void delete(Integer id);
}
