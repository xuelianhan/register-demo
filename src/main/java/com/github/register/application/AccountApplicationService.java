package com.github.register.application;

import com.github.register.domain.account.Account;
import com.github.register.domain.account.AccountRepository;
import com.github.register.domain.account.DeletedStatusEnum;
import com.github.register.infrastructure.utility.Encryption;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


/**
 * Application Service Interface for user resources
 *
 * @author sniper
 * @date Fri Sep 15, 2023
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

    /**
     * Query accounts of this Admin
     * @return
     */
    public List<Account> findAllAccounts() {
        //todo
        return repository.findAll();
    }

    public void updateAccount(Account account) {
        repository.save(account);
    }

    public void markAccountDeleted(Account account) {
        account.setDeleted(DeletedStatusEnum.DELETED.getCode());
        repository.save(account);
    }

    public void markAccountDeletedById(Integer id) {
        Optional<Account> op = repository.findById(id);
        if (op.isPresent()) {
            Account account = op.get();
            account.setDeleted(DeletedStatusEnum.DELETED.getCode());
            repository.save(account);
        }
    }

    public void markAccountDeletedByIds(List<Integer> ids) {
        List<Account> list = repository.findAllByIdIn(new HashSet<>(ids));
        if (null == list || list.isEmpty()) {
            return;
        }
        list.forEach( a -> {
            a.setDeleted(DeletedStatusEnum.DELETED.getCode());
        });
        repository.saveAll(list);
    }

}
