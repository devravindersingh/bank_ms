package com.ravindersingh.accounts.repository;

import com.ravindersingh.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerId(Long customerId);
    Optional<Account> findByAccountNumber(Long accountNumber);
    @Transactional
    @Modifying
    void deleteAccountByCustomerId(Long customerId);
}
