package com.ravindersingh.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer information"
)
public class CustomerDto {
    @Schema(
            description = "Name of the customer", example = "John Doe"
    )
    @NotEmpty(message = "Name can not be null or empty")
    @Size(min = 5, max = 30, message = "The length of the character name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email of the customer", example = "johndoe@email.com"
    )
    @NotEmpty(message = "Email can not be null or empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile number of the customer", example = "9999999999"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be 10 digits")
    private String mobileNumber;
}
