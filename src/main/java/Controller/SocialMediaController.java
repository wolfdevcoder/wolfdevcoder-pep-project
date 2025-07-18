package Controller;

import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 // trying again
public class SocialMediaController {

    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);

        app.post("/messages", this::createMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        Account created = accountService.register(account);

        if (created != null) {
            ctx.json(created);
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {
        Account login = ctx.bodyAsClass(Account.class);
        Account authenticated = accountService.login(login.getUsername(), login.getPassword());

        if (authenticated != null) {
            ctx.json(authenticated);
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler(Context ctx) {
        Message msg = ctx.bodyAsClass(Message.class);
        Message created = messageService.createMessage(msg);

        if (created != null) {
            ctx.json(created);
        } else {
            ctx.status(400);
        }
    }

    private void updateMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = ctx.bodyAsClass(Message.class);
        Message updated = messageService.updateMessage(messageId, msg.getMessage_text());

        if (updated != null) {
            ctx.json(updated);
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessagesByUserHandler(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> userMessages = messageService.getMessagesByUserId(userId);
        ctx.json(userMessages);
    }

    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }


    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null && messageService.deleteMessageById(messageId)) {
            ctx.json(message);
        } else {
            ctx.status(200);
        }
    }
}