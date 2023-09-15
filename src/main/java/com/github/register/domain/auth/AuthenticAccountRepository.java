package com.github.register.domain.auth;

import com.github.register.domain.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Repository for authenticated users
 *
 * @author sniper
 * @date
 **/
@Component
public class AuthenticAccountRepository {

    @Autowired
    private AccountRepository databaseUserRepo;

    public AuthenticAccount findByUsername(String username) {
        return new AuthenticAccount(Optional.ofNullable(databaseUserRepo.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("用户" + username + "不存在")));
    }
}
