package ru.company.usertarantoolrequest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.company.usertarantoolrequest.model.BankAccountInfoTarantool;
import ru.company.usertarantoolrequest.repository.TarantoolRepository;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class BankAccountInfoController {
    private final TarantoolRepository repository;

    @GetMapping(path = "/bank-accounts-info")
    public ResponseEntity<List<BankAccountInfoTarantool>> findAllByCity(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String city) {
        return ResponseEntity.ok(repository.findByFirstAndLastNamesAndCity(firstName, lastName, city));
    }
}
