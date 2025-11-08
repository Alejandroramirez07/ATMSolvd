package com.atm.dao.impl;

import com.atm.dao.AccountDAO;
import com.atm.model.Account;
import com.atm.mybatis.AccountMapper;
import com.atm.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class AccountMyBatisDAO implements AccountDAO {

    @Override
    public Account findByAccountNumber(int accountNumber) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            return mapper.findByAccountNumber(accountNumber);
        }
    }

    @Override
    public void updateBalance(Account account) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            mapper.updateBalance(account);
        }
    }
}
