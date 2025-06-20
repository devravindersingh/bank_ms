package com.ravindersingh.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(
        name = "CustomerAccount",
        description = "Schema to hold fetch operation response"
)
public class CustomerAccountDto {
    @Valid
    private CustomerDto customer;
    @Valid
    private AccountDto account;
}
