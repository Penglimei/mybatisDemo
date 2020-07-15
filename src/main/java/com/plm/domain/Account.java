package com.plm.domain;

import java.io.Serializable;

/**
 *  账户实体类
 *
 *      跟 用户实体类之间的关系是：一个用户对应多个账户
 *                             一个账户只属于一个用户
 */
public class Account implements Serializable {

    private Integer id;
    private Integer uid;
    private Double money;

    // 从表实体应该包含一个主表实体的对象引用
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uid=" + uid +
                ", money=" + money +
                ", user=" + user +
                '}';
    }
}
