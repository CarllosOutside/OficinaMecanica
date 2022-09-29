package com.projeto.oficina;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OficinaApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("teste");
	}

}
