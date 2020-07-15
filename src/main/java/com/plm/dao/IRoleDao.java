package com.plm.dao;

import com.plm.domain.Role;
import java.util.List;

public interface IRoleDao {

    /**
     * 查询所有的角色信息 以及对应的用户信息
     * @return
     */
    List<Role> findAll();

}
