package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }

        if (account.getPassword() == null || account.getPassword().length() < 5) {
            return null;
        }

        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }

        return accountDAO.insertAccount(account);
    }

    public Account login(String username, String password) {
        Account account = accountDAO.getAccountByUsername(username);

        if (account != null && account.getPassword().equals(password)) {
            return account;
        }

        return null;
    }
}
