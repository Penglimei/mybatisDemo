package com.plm.dao;

import com.plm.domain.User;

import java.util.List;

/**
 *  用户持久层接口
 *
 *
 *  在 mybatis 中把持久层的操作接口名称叫做 Mapper
 *  在此只是为了与 Spring 保持一致
 *  因此 IUserDao.java 等价于 IUserMapper.java
 *
 */
public interface IUserDao {

    /**
     *  查询所有
     */
    List<User> findAll();

    /**
     * 保存用户
     */
    void saveUser(User user);

    /**
     * 更新用户信息　
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据 id 删除用户信息
     * @param userId
     */
    void deleteUserById(Integer userId);

    /**
     * 根据 id查询用户信息
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 根据名称模糊查询用户信息
     * @param username
     * @return
     */
    List<User> findByName(String username);

    /**
     * 查询总用户数目
     * @return
     */
    int findTotal();
}
