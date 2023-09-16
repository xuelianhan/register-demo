package com.github.register.domain.auth;

import com.github.register.domain.account.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Authenticated User Models
 * <p>
 * After a user is registered, it contains its business attributes,
 * such as name, phone, and address, which are used for business generation and stored in the Account object.
 * It also contains its attributes for authentication,
 * such as password, role, and whether to deactivate, which are stored in the AuthenticAccount object.
 *
 * @author sniper
 * @date
 **/
public class AuthenticAccount extends Account implements UserDetails {

    public AuthenticAccount() {
        super();
        authorities.add(new SimpleGrantedAuthority(Role.USER));
    }

    public AuthenticAccount(Account origin) {
        this();
        BeanUtils.copyProperties(origin, this);
        //todo
        //getId() == 1 need to modify
        if (getId() == 1) {
            // Since there is no user management feature,
            // the first user in the system is given the administrator role by default
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN));
        }
    }

    /**
     * The authorizations the user has, such as read, modify, add, and so on.
     */
    private Collection<GrantedAuthority> authorities = new HashSet<>();

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Whether the account is expired or not
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Whether to lock or not
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Whether the password is expired or not
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Whether it is locked or not
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
