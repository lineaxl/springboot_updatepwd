package com.qf.service;

import com.qf.entity.User;
import org.apache.ibatis.annotations.Param;

public interface IUserService{
	User getUserByUsername(String username);

    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String passsword);

    void sendUpdatePwdUrl(User user);

    void updateUser(User user);
    User addUser(User user);

    void userResiter(String email, String code);
}