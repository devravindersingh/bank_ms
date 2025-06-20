package com.ravindersingh.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountDto {
    @Schema(
            description = "Account number of the customer", example = "1002930222"
    )
    @NotEmpty(message = "AccountNumber can not be null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the customer", example = "Salary"
    )
    @NotEmpty(message = "AccountType can not be null or empty")
    private String accountType;

    @Schema(
            description = "Branch address of the Bank branch", example = "123 Street City"
    )
    @NotEmpty(message = "BranchAddress can not be null or empty")
    private String branchAddress;
}
