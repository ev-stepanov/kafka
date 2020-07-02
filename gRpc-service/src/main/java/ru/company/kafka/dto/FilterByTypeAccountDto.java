package ru.company.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.company.kafka.model.TypeAccount;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterByTypeAccountDto {
    private UUID uuid;
    private TypeAccount typeAccount;
}
