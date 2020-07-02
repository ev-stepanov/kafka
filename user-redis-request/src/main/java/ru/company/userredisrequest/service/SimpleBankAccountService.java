package ru.company.userredisrequest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.company.userredisrequest.dto.BankAccountInfoSaveDto;
import ru.company.userredisrequest.exception.BadRequest;
import ru.company.userredisrequest.model.BankAccountInfo;
import ru.company.userredisrequest.repository.SimpleReactiveBankAccountInfoRepository;
import ru.company.userredisrequest.util.DtoConverter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleBankAccountService implements BankAccountService {
    private final SimpleReactiveBankAccountInfoRepository repository;

    @Override
    public Mono<BankAccountInfo> findById(String uuid) {
        if (uuid.length() < 36) {
            throw new BadRequest("Length of uuid less than 36");
        }
        return repository.findById(uuid);
    }

    @Override
    public Mono<BankAccountInfo> save(BankAccountInfoSaveDto bankAccountInfoSaveDto) {
        return repository.save(DtoConverter.bankAccountInfoSaveDtoConverter(bankAccountInfoSaveDto));
    }

    @Override
    public Mono<Void> deleteById(String uuid) {
        return repository.deleteById(uuid);
    }

    @Override
    public Flux<String> getAllKeys() {
        return null;
    }
}
