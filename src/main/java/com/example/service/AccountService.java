package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<?> registerUser(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.status(400).build();
        }

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(409).build();
        }
        Account newAccount = accountRepository.save(account);

        return ResponseEntity.ok(newAccount);
    }

    public ResponseEntity<?> loginUser(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());

        if (optionalAccount.isPresent() && optionalAccount.get().getPassword().equals(account.getPassword())) {
            return ResponseEntity.ok(optionalAccount.get());
        } else {
            return ResponseEntity.status(401).build();
        }
}

}
