package com.ravindersingh.accounts.repository;

import com.ravindersingh.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobileNumber(String mobileNumber);
    @Transactional
    @Modifying
    void deleteByMobileNumber(String mobileNumber);
}
