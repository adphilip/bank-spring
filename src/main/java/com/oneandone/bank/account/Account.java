package com.oneandone.bank.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oneandone.bank.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

//Spring Data ORM - Make this an entity
@Entity
public class Account {
    //Spring Data ORM - Mark the id field as primary key
    //Spring Data ORM - Make the id field as auto generated
    @Id
    @GeneratedValue
    private Integer id;
    @JsonIgnore
    //Spring Data ORM - Set the many-to-one relation
    @ManyToOne
    private User user;
    private Double funds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;
        if (user != null ? !user.equals(account.user) : account.user != null) return false;
        return funds != null ? funds.equals(account.funds) : account.funds == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (funds != null ? funds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user +
                ", funds=" + funds +
                '}';
    }
}
