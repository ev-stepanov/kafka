package ru.company.kafka.rsocketclient.controller;

import io.rsocket.Payload;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.company.kafka.rsocketclient.model.BankAccountInfo;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class BankAccountController {

    private final RSocketRequester requester;

    @GetMapping(value = "/accounts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<BankAccountInfo> getAccounts() {
        return requester
                .route("accounts")
                .data(Payload.class)
                .retrieveFlux(BankAccountInfo.class);
    }

    @GetMapping(value = "/accounts/count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Long> getCountAccounts() {
        return requester
                .route("accounts-count")
                .data(Payload.class)
                .retrieveFlux(Long.class);
    }

    @GetMapping(value = "/accounts/{uuid}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<BankAccountInfo> getCountAccounts(@PathVariable String uuid) {
        return requester
                .route("account-by-uuid")
                .data(uuid)
                .retrieveMono(BankAccountInfo.class);
    }
}
