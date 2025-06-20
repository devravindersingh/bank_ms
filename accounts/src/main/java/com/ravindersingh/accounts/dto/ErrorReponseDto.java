package com.ravindersingh.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error responses"
)
public class ErrorReponseDto {
    @Schema(
            description = "api endpoint path ", example = "/api/request"
    )
    private String apiPath;
    @Schema(
            description = "error code resembling problem", example = "401"
    )
    private HttpStatus errorCode;
    @Schema(
            description = "structured message explaining what happened", example = "Not valid mobile number"
    )
    private String errorMessage;
    @Schema(
            description = "time at which error occured ", example = "11:04:25::12:32"
    )
    private LocalDateTime errorTime;
}
