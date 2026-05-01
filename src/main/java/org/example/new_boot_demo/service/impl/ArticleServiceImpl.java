package org.example.new_boot_demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.new_boot_demo.mapper.ArticleMapper;
import org.example.new_boot_demo.pojo.Article;
import org.example.new_boot_demo.pojo.PageBean;
import org.example.new_boot_demo.service.ArticleService;
import org.example.new_boot_demo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1. 创建 PageBean 对象
        PageBean<Article> pageBean = new PageBean<>();

        // 2. 开启分页查询   借助于 PageHelper 插件
        PageHelper.startPage(pageNum, pageSize);

        // 3. 调用 mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Article> as = articleMapper.list(id, categoryId, state);
        // page 中提供了方法， 可以获取 PageHelper 分页查询后，得到的总记录条数和当前页数据
        Page<Article> page = (Page<Article>) as;

        // 把数据填充到 PageBean 对象中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }

    @Override
    public Article findById(Integer id) {
        return  articleMapper.findId(id);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
