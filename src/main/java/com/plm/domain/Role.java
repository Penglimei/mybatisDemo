package com.plm.domain;

import java.io.Serializable;
import java.util.List;

/**
 *  角色实体
 *
 *      与用户实体是多对多的映射关系
 */
public class Role implements Serializable {

    /**
     * 需要注意的是：
     *      此处实体类属性的命名规范与表中的不一致，
     *          实体类属性：roleName <-----> role_name ：表字段
     *
     *      后续使用需要 用到 resultMap标签区别名
     */
    private Integer roleId;
    private String roleName;
    private String roleDesc;

    // 多对多的关系映射，一个角色可以赋予多个用户
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
