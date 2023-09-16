package com.github.register.domain.role;

import com.github.register.domain.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
@Entity
public class AuthRole extends BaseEntity {

    private String roleName;
    private Date createTime;
    private String description;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
