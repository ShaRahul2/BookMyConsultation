package org.upgrad.paymentservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.upgrad.paymentservice.dao.UserDao;
import org.upgrad.paymentservice.model.entity.User;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

	@Bean
	ApplicationRunner init(UserDao userDao) {
		return (ApplicationArguments args) -> initialSetup(userDao);
	}

	private void initialSetup(UserDao userRepo) {
		User user = new User("normal-user@abc.com", "$2y$12$LQOfAEPJcdbE0Ko5JBETnOsI1O7fLYYqB5XnASfjZTLYRR2FIZQ8S", "ROLE_USER");
		User admin = new User("admin-user@abc.com", "$2y$12$23JC573SCMFvChDCo5O/L.scweeGWDV0ikQ.YpfF8wrDA8d1ojAgG", "ROLE_ADMIN");
		if(userRepo.findByUsername(user.getUsername())== null){
			userRepo.save(user);
		}
		if(userRepo.findByUsername(admin.getUsername())== null){
			userRepo.save(admin);
		}
	}
}
