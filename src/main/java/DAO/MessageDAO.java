package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        Message createdMessage = null;

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    createdMessage = new Message(id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }
                rs.close();
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return createdMessage;
    }

    public List<Message> getAllMessages() {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message m = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(m);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public List<Message> getMessagesByUserId(int userId) {
        Connection conn = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message m = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(m);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    public Message getMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        Message message = null;

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }

    public boolean deleteMessageById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        boolean success = false;

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            success = rows > 0;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public Message updateMessage(int id, String newText) {
        Connection conn = ConnectionUtil.getConnection();
        Message updatedMessage = null;

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newText);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                updatedMessage = getMessageById(id);
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedMessage;
    }
}
