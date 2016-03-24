package com.oneandone.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OldAccountRepository {

    private Map<Integer, Account> repo = new ConcurrentHashMap<>();
    private AtomicInteger index = new AtomicInteger(0);

    //TODO Spring Core - Add the account to the repository (and increment the index) if it does not exists. Update the account if it's in the repo
    // set the proper id to the added account
    public Account save(Account account) {
        Integer id = account.getId();

        if(id == null) {
            id = index.incrementAndGet();
            account.setId(id);
        }

        repo.put(id, account);
        return account;
    }

    //TODO Spring Core - Find the account by it's id
    public Account findOne(Integer id) {

        return repo.get(id);
    }

    //TODO Spring Core - Find all the accounts of an user specified by the id
    public Collection<Account> findByUserId(Integer userId) {
        Collection<Account> accounts = new LinkedList<>();

        for (Account account : repo.values()) {
            if (account.getUser().getId().equals(userId)){
                accounts.add(account);
            }
        }

        return accounts;
    }

    //TODO Spring Core - Find all the accounts in the repository
    public Collection<Account> findAll() {

        return repo.values();
    }

    //TODO Spring Core - Delete an account by it's id
    public void delete(Integer id) {
        repo.remove(id);

    }

    //TODO Spring Core - Delete all the accounts in the repository
    public void deleteAll() {
        repo.clear();
    }


    public interface AccountRepo extends JpaRepository<Account, Integer> {
        Collection<Account> findByUserId(Integer userId);
    }

}
