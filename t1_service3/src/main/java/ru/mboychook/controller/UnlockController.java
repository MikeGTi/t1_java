package ru.mboychook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mboychook.service.UnlockService;

import ru.mboychook.model.dto.AccountDto;
import ru.mboychook.model.dto.ClientDto;
import ru.mboychook.model.dto.UnblockDto;


@RestController
@RequestMapping("/unlock")
@RequiredArgsConstructor
public class UnlockController {
    private final UnlockService unlockService;

    @PostMapping("/client")
    public ResponseEntity<UnblockDto> unlockClient(@RequestBody ClientDto clientDto) {
        UnblockDto response = unlockService.unlockClient(clientDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/account")
    public ResponseEntity<UnblockDto> unlockAccount(@RequestBody AccountDto accountDto) {
        UnblockDto response = unlockService.unlockAccount(accountDto);
        return ResponseEntity.ok(response);
    }
}
