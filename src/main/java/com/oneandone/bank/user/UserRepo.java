package com.oneandone.bank.user;

import org.springframework.data.jpa.repository.JpaRepository;

//TODO Spring Data JPA - Use instead of UserRepository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findOneByName(String name);
}
