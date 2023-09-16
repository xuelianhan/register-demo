package com.github.register.domain.role;

import com.github.register.domain.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
@Entity
public class AuthAccountRole extends BaseEntity {

    private Integer accountId;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
