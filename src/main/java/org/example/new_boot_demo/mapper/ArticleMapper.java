package org.example.new_boot_demo.mapper;


import org.apache.ibatis.annotations.*;
import org.example.new_boot_demo.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    // 新增文章
    @Insert("insert into article(title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, /*now(), now()*/ #{createTime}, #{updateTime})")
    void add(Article article);

    // 条件分页列表查询
    List<Article> list(Integer id, Integer categoryId, String state);

    // 获取文章详细信息
    @Select("select * from article where id = #{id}")
    Article findId(Integer id);

    // 更新文章
    @Update("update article set title=#{title}, content=#{content}, cover_img=#{coverImg}, state=#{state}, category_id=#{categoryId}, update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    // 删除文章
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
