package org.example;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * -Xms1500M -Xmx1500M -Xss256k -XX:SurvivorRatio=6 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
 */
@SpringBootTest
public class RestReqUsersForGc {

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testProcessUserData() throws Exception {
		for (int i = 0; i < 10000; i++) {
			String result = restTemplate.getForObject("http://localhost:8080/user/processUserData", String.class);
			Thread.sleep(1000);
		}
	}

}
