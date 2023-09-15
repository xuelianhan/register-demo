package com.github.register.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.register.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


/**
 * Entity of User
 *
 * @author zhouzhiming
 * @author sniper
 * @date
 */
@Entity
public class Account extends BaseEntity {

    @NotEmpty(message = "The username cannot be null.")
    private String username;

    /**
     * The password fields are not involved in serialization (but deserialization is), not in updates (but insertion is)
     * This means that the password field will not be leaked when fetching objects (many operations are associated with user objects);
     * It also means that functions like "change password" cannot be handled by the interface to the user object's resources
     * (because the password is not updated when the object is updated), and need to be handled by a separate interface.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(updatable = false)
    private String password;

    @NotEmpty(message = "The nick name of user cannot be null.")
    private String name;

    private String avatar;

    @Pattern(regexp = "1\\d{10}", message = "The phone format is illegal.")
    private String telephone;

    @Email(message = "The email format is illegal")
    private String email;

    private String location;

    /**
     * Whether the status is deleted:
     * 0: exist
     * 1: deleted.
     */
    private Integer deleted = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
