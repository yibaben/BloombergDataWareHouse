package com.progressSoft.clusteredDataWarehouse;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "BLOOMBERG DATA WAREHOUSE REST APIs",
				description = "Bloomberg Data Warehouse To Analyse Forex Deals REST APIs Documentation",
				version = "v1.0.0",
				contact = @Contact(
						name = "progressSoft Limited",
						email = "info@progress.com",
						url = "https://www.progresssoft.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.progresssoft.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Bloomberg Data Warehouse To Analyse Forex Deals REST APIs Documentation",
				url = "https://www.progresssoft.com"
		)
)


@SpringBootApplication
public class ClusteredDataWarehouseApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClusteredDataWarehouseApplication.class, args);
	}

}
