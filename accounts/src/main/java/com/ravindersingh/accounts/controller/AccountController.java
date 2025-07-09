package com.ravindersingh.accounts.controller;

import com.ravindersingh.accounts.constant.AccountConstant;
import com.ravindersingh.accounts.dto.*;
import com.ravindersingh.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
@Tag(
        name = "CRUD REST API's for Accounts in Easybank",
        description = "CRUD REST API's in Easybank to CREATE, UPDATE, FETCH and DELETE account details"
)
public class AccountController {

    private final IAccountService accountService;
    private final Environment env;
    private final AccountContactInfoDto accountContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @PostMapping(path = "/create")
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank",
            responses = {
                    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
            }
    )
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customer) {
        accountService.createAccount(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201, AccountConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "Fetch Account & Customer details",
            description = "REST API to fetch new Customer & Account inside EazyBank",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "404", description = "HTTP Status NOT_FOUND",
                        content = @Content(
                                schema = @Schema(
                                        implementation = ErrorResponseDto.class
                                )
                        )
                    )
            }
    )
    public ResponseEntity<CustomerAccountDto> fetchAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is 10 digits")
            @RequestParam String mobileNumber) {
        CustomerAccountDto customerAccountDto = accountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAccountDto);
    }

    @PutMapping("/update/account/{accountNumber}")
    @Operation(
            summary = "Update Account details",
            description = "REST API to update Account detials inside EasyBank",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "404", description = "HTTP Status NOT_FOUND",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ResponseDto> updateAccount(
            @Valid @RequestBody AccountDto accountDto,
            @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
            @PathVariable Long accountNumber) {
        boolean result = accountService.updateAccount(accountDto, accountNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200, String.format("Update request processed %s", result)));
    }

    @PutMapping("/update/customer/{customerId}")
    @Operation(
            summary = "Update Customer details",
            description = "REST API to update Customer detials inside EasyBank",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "404", description = "HTTP Status NOT_FOUND",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )            }
    )
    public ResponseEntity<ResponseDto> updateAccount(
            @Valid @RequestBody CustomerDto customerDto,
            @PathVariable Long customerId) {
        boolean result = accountService.updateCustomer(customerDto, customerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200, String.format("Update request processed %s", result)));
    }

    @DeleteMapping("/delete/account")
    @Operation(
            summary = "Delete Account ",
            description = "REST API to delete Account detials inside EasyBank",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL SERVER",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )            }
    )
    public ResponseEntity<ResponseDto> deleteAccountByMobileNumber(
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is 10 digits")
            @RequestParam String mobileNumber) {
        boolean result = accountService.deleteAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200, String.format("Delete Request processed %s", result)));

    }

    @GetMapping("/build-info")
    @Operation(
            summary = "Get build information",
            description = "Get build information that is deployed into cards microservice",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL SERVER ERROR",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/java-version")
    @Operation(
            summary = "Get Java information",
            description = "Get Java information that is deployed into cards microservice",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL SERVER ERROR",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env.getProperty("java.version"));
    }

    @GetMapping("/contact-info")
    @Operation(
            summary = "Get contact information",
            description = "Get contact information that is deployed into accounts microservice",
            responses = {
                    @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
                    @ApiResponse(responseCode = "500", description = "HTTP Status INTERNAL SERVER ERROR",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<AccountContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }

}
