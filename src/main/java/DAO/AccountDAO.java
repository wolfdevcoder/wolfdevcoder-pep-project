package DAO;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;;

public class AccountDAO {
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        Account createdAccount = null;
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    createdAccount = new Account(id, account.getUsername(), account.getPassword());
                }
                rs.close();
            }

            ps.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return createdAccount;
    }

    public Account getAccountByUsername(String username) {
        Connection conn = ConnectionUtil.getConnection();
        Account account = null;

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("account_id");
                String password = rs.getString("password");
                account = new Account(id, username, password);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }
}
