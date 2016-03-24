package com.oneandone.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

//Spring Data JPA - Use instead of AccountRepository
@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
    Collection<Account> findByUserId(Integer userId);
}
