package com.ravindersingh.loans;

import com.ravindersingh.loans.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(LoansContactInfoDto.class)
@OpenAPIDefinition(
		info = @Info(
				title = "Loan microservice REST API Documentiation",
				description = "EazyBank Card microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Ravinder Singh",
						email = "ravidnersingh@jmail.com",
						url = "http://www.woogle.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.woogle.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "More information and Reference docs",
				url = "http://www.spring-dummy.com"
		)
)
public class LoansApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
