package com.sakura.study.service;

import com.sakura.study.dto.PageRequest;
import com.sakura.study.dto.UserDto;
import com.sakura.study.model.User;
import com.sakura.study.utils.ResponseResult;

import java.util.List;

public interface UserService {
    /**
     * 获取分页的用户列表
     * @param page
     * @return
     */
    ResponseResult getPageUsers(PageRequest page,String realName);
    /**
     * 获取家列表，可搜索
     * @param username
     * @return
     */
    List<User> getParents(String username);

    /**
     * 添加用户
     * @param token
     * @param user
     * @return
     */
    User add(String token, User user);

    /**
     * 修改用户
     * @param token
     * @param user
     */
    void edit(String token, User user);

    /**
     * 删除用户
     * @param token
     * @param id
     */
    void delete(String token, Integer id);

    /**
     * api
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * api
     * 获取用户详细信息
     * @param userId
     * @return
     */
    UserDto getUserInfo(Integer userId);

    /**
     * api
     * 用户注册
     * @param userDto
     * @return
     */
    UserDto register(UserDto userDto);
}
