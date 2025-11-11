package com.atm.factory;

import com.atm.dao.AccountDAO;
import com.atm.dao.impl.AccountMyBatisDAO;

public class DAOFactory {
    public static AccountDAO createAccountDAO() {
        return new AccountMyBatisDAO();
    }
}
