package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> createMessage(Message message) {
        if (message.getMessage_text() == null || message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255) {
                return ResponseEntity.status(400).build();
        }
        Optional<Account> optionalAccount = accountRepository.findById(message.getPosted_by());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        Message savedMessage = messageRepository.save(message);
        return ResponseEntity.ok(savedMessage);
    }

    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.ok(messages);
    }
    public ResponseEntity<?> getMessageById(int messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        
        if (optionalMessage.isPresent()) {
            return ResponseEntity.ok(optionalMessage.get());
        } else {
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> deleteMessageById(int messageId) {

        Optional<Message> message = messageRepository.findById(messageId);
        if (message.isPresent()) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok("1");
        } else {
            return ResponseEntity.ok().build();
        }
    }

    public ResponseEntity<?> updateMessageText(int messageId, Message message) {
        if (message.getMessage_text() == null ) {
            return ResponseEntity.status(400).build();
        }

        if (message.getMessage_text().isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        if (message.getMessage_text().length() > 255) {
            return ResponseEntity.status(400).build();
        }
    
        Optional<Message> optionalExistingMessage = messageRepository.findById(messageId);
        if (optionalExistingMessage.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
    
        Message existingMessage = optionalExistingMessage.get();
        existingMessage.setMessage_text(message.getMessage_text());
    
        messageRepository.save(existingMessage);
        return ResponseEntity.ok("1");

    }
    

    public ResponseEntity<?> getMessagesByUserId(int accountId) {
        List<Message> messages = messageRepository.findMessagesByPostedBy(accountId);
        if (messages.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            return ResponseEntity.ok(messages);
        }
    }

}
