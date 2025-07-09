package com.ravindersingh.accounts.service.impl;

import com.ravindersingh.accounts.constant.AccountConstant;
import com.ravindersingh.accounts.dto.AccountDto;
import com.ravindersingh.accounts.dto.CustomerAccountDto;
import com.ravindersingh.accounts.dto.CustomerDto;
import com.ravindersingh.accounts.entity.Account;
import com.ravindersingh.accounts.entity.Customer;
import com.ravindersingh.accounts.exception.CustomerAlreadyExistsException;
import com.ravindersingh.accounts.exception.ResourceNotFoundException;
import com.ravindersingh.accounts.mapper.AccountMapper;
import com.ravindersingh.accounts.mapper.CustomerMapper;
import com.ravindersingh.accounts.repository.AccountRepository;
import com.ravindersingh.accounts.repository.CustomerRepository;
import com.ravindersingh.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer =  customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with mobile number " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createAccount(savedCustomer));
    }

    @Override
    public CustomerAccountDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile number", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        return new CustomerAccountDto(
                CustomerMapper.mapToCustomerDto(customer, new CustomerDto()),
                AccountMapper.mapToAccountDto(account, new AccountDto()));
    }

    @Override
    public boolean updateAccount(AccountDto accountDto, Long accountNumber) {
        //fetch account
        //do not update account number
        boolean result = false;
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
        //update
        account.setAccountType(accountDto.getAccountType());
        account.setBranchAddress(accountDto.getBranchAddress());
        accountRepository.save(account);
        result = true;
        return result;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto, Long customerId) {
        boolean result = false;
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));
        //update
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setEmail(customerDto.getEmail());
        customerRepository.save(customer);
        result = true;
        return result;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        //check for account present with provided mobilenumber
        boolean result = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        //delete account first, then customer
        accountRepository.deleteAccountByCustomerId(customer.getCustomerId());
        customerRepository.deleteByMobileNumber(mobileNumber);
        result = true;
        return result;
    }


    private Account createAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountConstant.SAVINGS);
        account.setBranchAddress(AccountConstant.ADDRESS);
        return account;
    }


}
