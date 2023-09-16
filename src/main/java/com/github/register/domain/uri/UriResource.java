package com.github.register.domain.uri;

import com.github.register.domain.BaseEntity;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author sniper
 * @date 16 Sep 2023
 */
@Entity
public class UriResource extends BaseEntity {

    private Integer parentId;

    private String uriName;

    private String uri;

    private String sourceFrom;

    private Date createTime;

    private Date updateTime;

    private String description;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUriName() {
        return uriName;
    }

    public void setUriName(String uriName) {
        this.uriName = uriName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
