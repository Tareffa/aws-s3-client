package br.com.tareffa.awss3client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AwsS3ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwsS3ClientApplication.class, args);
	}

}
