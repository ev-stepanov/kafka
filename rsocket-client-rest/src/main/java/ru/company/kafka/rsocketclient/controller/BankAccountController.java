package ru.company.kafka.rsocketclient.controller;

import io.rsocket.Payload;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.kafka.rsocketclient.model.BankAccountInfo;

@RestController
@RequiredArgsConstructor
public class BankAccountController {

    private final RSocketRequester requester;

    @GetMapping(value = "/account", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<BankAccountInfo> getAccounts() {
        return requester
                .route("account")
                .data(Payload.class)
                .retrieveFlux(BankAccountInfo.class);
    }

    @GetMapping(value = "/account/count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<Long> getCountAccounts() {
        return requester
                .route("account-count")
                .data(Payload.class)
                .retrieveFlux(Long.class);
    }
}
