package com.familymoney.accounts.persistence.repository;

import com.familymoney.accounts.persistence.entity.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AccountRepository extends R2dbcRepository<AccountEntity, Long> {
}
