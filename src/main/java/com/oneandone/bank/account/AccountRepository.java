package com.oneandone.bank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Collection<Account> findByUserId(Integer userId);
}

