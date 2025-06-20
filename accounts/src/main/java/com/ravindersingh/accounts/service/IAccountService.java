package com.ravindersingh.accounts.service;

import com.ravindersingh.accounts.dto.AccountDto;
import com.ravindersingh.accounts.dto.CustomerAccountDto;
import com.ravindersingh.accounts.dto.CustomerDto;

public interface IAccountService {
    void createAccount(CustomerDto customer);
    CustomerAccountDto fetchAccount(String mobileNumber);
    boolean updateAccount(AccountDto accountDto, Long accountNumber);
    boolean updateCustomer(CustomerDto customerDto, Long customerId);
    boolean deleteAccount(String mobileNumber);
}
