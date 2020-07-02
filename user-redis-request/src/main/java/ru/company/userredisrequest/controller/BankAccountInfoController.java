package ru.company.userredisrequest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.company.userredisrequest.dto.BankAccountInfoSaveDto;
import ru.company.userredisrequest.model.BankAccountInfo;
import ru.company.userredisrequest.service.BankAccountService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Bank-account-info", description = "Bank-account-info API")
public class BankAccountInfoController {
    private final BankAccountService bankAccountService;

    @Operation(summary = "Get a bank account by uuid", description = "Return a single bank-account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the bank-account",
                    content = {@Content(mediaType = "application/stream+json", schema = @Schema(implementation = BankAccountInfo.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid uuid supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Bank-account not found",
                    content = @Content)
    })
    @GetMapping(value = "/bank-account-info/{uuid}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseEntity<BankAccountInfo>> getBankAccountInfo(
            @Parameter(description = "uuid of bank account to be searched") @PathVariable String uuid) {
        return bankAccountService.findById(uuid)
                .flatMap(bankAccountInfo -> Mono.just(ResponseEntity.ok(bankAccountInfo)))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a bank account by uuid", description = "Do not return anything")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Bank account was successfully deleted"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid uuid supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Bank account not found",
                    content = @Content)
    })
    @DeleteMapping("/bank-account-info/{uuid}")
    public Mono<ResponseEntity<Void>> deleteBankAccountInfo(
            @Parameter(description = "uuid of bank account to be searched") @PathVariable String uuid) {
        return bankAccountService.deleteById(uuid)
                .flatMap(bankAccount -> Mono.just(ResponseEntity.ok().build()));
    }

    @Operation(summary = "Create bank account", description = "Return of the created bank-account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "bank-account was successfully created",
                    content = {@Content(mediaType = "application/stream+json", schema = @Schema(implementation = BankAccountInfo.class))})
    })
    @PostMapping("/bank-account-info")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankAccountInfo> createBankAccountInfo(@Valid @RequestBody BankAccountInfoSaveDto bankAccountInfoSaveDto) {
        return bankAccountService.save(bankAccountInfoSaveDto);
    }
}

