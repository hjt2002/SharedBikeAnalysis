package com.example.sharedbike.mapper;

import com.example.sharedbike.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User>findAll();
    public User findById(String userid) throws DataAccessException;
    public int insert(User user);
    public int delete(String userid);
    public int modifyUsername(String userid,String username);
    public int modifyPassword(@Param("userid") String userid, @Param("password") String password);
    public int modifyEmail(@Param("userid") String userid,@Param("email") String email);
}
