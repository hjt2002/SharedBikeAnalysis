package com.example.sharedbike.service;

import com.example.sharedbike.entity.User;
import com.example.sharedbike.mapper.UserMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {



    final
    UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //findAll查询所有
    public List<User> findALL(){
        return userMapper.findAll();
    }


    //login登录
    public Map<String,Object> login(String userid,String password){

        try {
            User user = userMapper.findById(userid);
            String type = user.getTYPE();
            Map<String, Object> map = new HashMap<String, Object>();
            if (user.getUSERID() == null) {
                map.put("type", type);
                map.put("state", "account fail");
                return map;
            } else {
                //匹配到账号
                if (user.getPASSWORD().equals(password)) {
                    //　check the password of account
                    map.put("type", type);
                    map.put("state", "success");
                    return map;
                } else {
                    map.put("type", type);
                    map.put("state", "password fail");
                    return map;
                }
            }
        }catch (DataAccessException e){
            System.out.println("select null data");
        }
        return null;
    }

    //register注册
    public String register(User user){
        try{
        //检查账号是否已经存在
        User user01 = userMapper.findById(user.getUSERID());
        if(user01 == null){
            //账号不存在

                int count = userMapper.insert(user);
                if(count == 1){
                    return "success";
                }
//                return "success";
        }else{
            return "false";
            //账号已经存在
        }
            return "false";
        }catch (DataAccessException e){
            return "-1";
        }
    }

    //delete删除
    public String deleteAccount(String userid){
        int count = userMapper.delete(userid);
        if(count == 1){
            return "success";
        }else{
            return "false";
        }
    }
    //modify修改
    public String modify(String userid,String username,String password,String email){
        int count=0;
        String flag =  "false";
        if(email != null && !email.equals("")){
            //修改邮箱
            count = userMapper.modifyEmail(userid,email);
            if(count==0){
                flag = "false";
            } else {
                flag = "success";
            }
        }

        if(username !=null && !username.equals("")){
            //修改用户名
            count = userMapper.modifyUsername(userid,username);
            if(count==0){
                flag = "false";
            } else {
                flag = "success";
            }
        }

        if (password != null && !password.equals("")) {
            //修改密码
            count = userMapper.modifyPassword(userid,password);
            if(count==0){
                flag =  "false";
            } else {
                flag = "success";
            }
        }
        return flag;
    }
}
