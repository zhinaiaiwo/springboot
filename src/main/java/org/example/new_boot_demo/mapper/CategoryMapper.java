package org.example.new_boot_demo.mapper;

import org.apache.ibatis.annotations.*;
import org.example.new_boot_demo.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 添加文章分类
//    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_ime) " +
//            "values (#{category.categoryName}, #{category.category}, #{id}, now(), now())")
    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{categoryName}, #{categoryAlias}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Category category);

    // 查询用户下的所有
    @Select("select * from category where create_user = #{id}")
    List<Category> list(Integer id);

    // 根据 id 查新分类信息
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    // 更新文章分类信息
    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, update_time = #{updateTime} where id = #{id}")
    void update(Category category);

    // 删除文章分类
    @Delete("delete from category where id = #{id}")
    void delete(Integer id);
}
