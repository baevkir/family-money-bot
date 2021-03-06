package com.familymoney.accounts.persistence.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("accounts")
public class AccountEntity {
    @Id
    private Long id;
    private String name;
}
