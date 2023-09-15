package com.github.register.application;

import com.github.register.domain.account.Account;
import com.github.register.domain.account.AccountRepository;
import com.github.register.infrastructure.utility.Encryption;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;


/**
 * 用户资源的应用服务接口
 **/
@Named
@Transactional
public class AccountApplicationService {

    @Inject
    private AccountRepository repository;

    @Inject
    private Encryption encoder;

    public void createAccount(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
    }

    public Account findAccountByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void updateAccount(Account account) {
        repository.save(account);
    }

}
