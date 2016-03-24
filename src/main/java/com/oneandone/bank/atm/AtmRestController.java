package com.oneandone.bank.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@RestController
public class AtmRestController {
    public static class AtmRedrawRequest {
        private Integer userId;
        private Integer accountId;
        private Double amount;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getAccountId() {
            return accountId;
        }

        public void setAccountId(Integer accountId) {
            this.accountId = accountId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    @Autowired
    private AtmService atmService;

    @RequestMapping(value = "/atm", method = RequestMethod.POST, consumes = "application/json")
    public void redrawMoney(@RequestBody AtmRedrawRequest atmRedrawRequest) {
        atmService.redrawMoney(atmRedrawRequest.getUserId(), atmRedrawRequest.getAccountId(), atmRedrawRequest.getAmount());
    }





}
