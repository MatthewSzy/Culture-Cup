package com.CultureCup;

import com.CultureCup.Entities.Enum.RoleEnum;
import com.CultureCup.Entities.Role;
import com.CultureCup.Repositories.RoleRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CultureCupApplication {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CultureCupApplication.class, args);
	}

	@Bean
	InitializingBean addRolesToDatabase() {
		return () -> {
			roleRepository.save(Role.builder().name(RoleEnum.ROLE_USER).build());
			roleRepository.save(Role.builder().name(RoleEnum.ROLE_ADMIN).build());
		};
	}
}
