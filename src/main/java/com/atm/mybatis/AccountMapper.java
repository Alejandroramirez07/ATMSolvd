package com.atm.mybatis;

import com.atm.model.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    Account findByAccountNumber(@Param("accountNumber") int accountNumber);
    void updateBalance(Account account);
}
