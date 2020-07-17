package ru.company.oracletarantoolloader.model.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_account_info")
public class BankAccountInfoOracle {
    @Id
    private UUID uuid;

    private String firstName;
    private String lastName;
    private String city;
    private boolean blackListed;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
