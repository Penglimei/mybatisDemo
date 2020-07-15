package com.plm.dao;

import com.plm.domain.Account;

import java.util.List;

public interface IAccountDao {

    /**
     *  查询所有账户，同时还要获取到当前账户的所属用户信息
     * @return
     */
    List<Account> findAll();

    /**
     * 根据account表中用户id uid查询 用户id对应的账户信息
     * @param uid
     * @return
     */
    List<Account> findAccountByUid(Integer uid);
}
