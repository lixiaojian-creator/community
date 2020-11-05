package com.lxj.mapper;

import com.lxj.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(id,title,description,type,tag,gmt_create,gmt_modified,creator) values(" +
            "#{id},#{title},#{description},#{type},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    void insert(Question question);

    @Select("select * from question")
    List<Question> selectAll();

    @Select("select * from question limit #{offset},#{rows}")
    List<Question> selectPagination(@Param("offset") Integer offset, @Param("rows") Integer rows);

    @Select("select count(*) from question")
    Integer getCounts();
}
