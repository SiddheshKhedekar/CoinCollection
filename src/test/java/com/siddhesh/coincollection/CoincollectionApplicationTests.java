package com.siddhesh.coincollection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoincollectionApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainMethodRuns() {
		// Explicitly call main to increase code coverage
		CoincollectionApplication.main(new String[] {});
	}

}
