package com.ravindersingh.loans.controller;

import com.ravindersingh.loans.constant.LoanConstant;
import com.ravindersingh.loans.dto.ErrorResponseDto;
import com.ravindersingh.loans.dto.LoanDto;
import com.ravindersingh.loans.dto.LoansContactInfoDto;
import com.ravindersingh.loans.dto.ResponseDto;
import com.ravindersingh.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanService loanService;
    private final LoansContactInfoDto loansContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "HTTP Status CREATED"
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                  String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstant.STATUS_201, LoanConstant.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoanDto> fetchLoanDetails(@RequestParam
                                                     @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                     String mobileNumber) {
        LoanDto loansDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "417", description = "Expectation Failed"
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = loanService.updateLoan(loanDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstant.STATUS_200, LoanConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstant.STATUS_417, LoanConstant.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "417", description = "Expectation Failed"
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoanConstant.STATUS_200, LoanConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstant.STATUS_417, LoanConstant.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/build-info")
    @Operation(
            summary = "Get build information",
            description = "Get build information that is deployed into loans microservice",
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

    @GetMapping("/contact-info")
    @Operation(
            summary = "Get contact information",
            description = "Get contact information that is deployed into loans microservice",
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
    public ResponseEntity<LoansContactInfoDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }

}