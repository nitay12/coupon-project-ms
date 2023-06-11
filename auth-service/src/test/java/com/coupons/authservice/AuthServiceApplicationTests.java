package com.coupons.authservice;

import com.coupons.authservice.model.User;
import com.coupons.authservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
class AuthServiceApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@BeforeEach
	void setUp() {
		userRepository.save(User.builder()
				.email("testUser@mail.com")
				.username("TEST")
				.password("123456")
				.build());
	}
	@Test
	void findAll(){
		final List<User> users = userRepository.findAll();
		assertThat(users, hasSize(1));
	}

}
