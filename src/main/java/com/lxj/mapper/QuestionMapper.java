package com.lxj.mapper;

import com.lxj.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(id,title,description,type,tag,gmt_create,gmt_modified,creator) values(" +
            "#{id},#{title},#{description},#{type},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    void insert(Question question);
}
