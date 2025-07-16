package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return null;
        }

        return messageDAO.insertMessage(message);
    }

    public Message updateMessage(int id, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return null;
        }

        return messageDAO.updateMessage(id, newText);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageDAO.getMessagesByUserId(userId);
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public boolean deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }    
}
