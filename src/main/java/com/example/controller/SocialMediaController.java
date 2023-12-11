package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        return accountService.registerUser(account);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        return accountService.loginUser(account);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<?> getMessageById(@PathVariable("message_id") int messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("message_id") int messageId) {
        return messageService.deleteMessageById(messageId);
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageText( @PathVariable("message_id") int messageId, @RequestBody Message message) {
        return messageService.updateMessageText(messageId, message);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<?> getMessagesByUserId(@PathVariable("account_id") int accountId) {
        return messageService.getMessagesByUserId(accountId);
    }



}



