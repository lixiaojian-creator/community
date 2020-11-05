package com.lxj.mapper;

import com.lxj.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(id,account_id,name,token,gmt_create,gmt_modified,avatar_uri) values(#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUri})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findOneByToken(String token);

    @Select("select * from user where id=#{id}")
    User findOneById(int id);
}
