package jp.co.tk.nucvs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class AppTests {
	
	@Autowired
	private App app;

	@Test
	void 正常に動作するか() throws Exception {
		app.updatedDetectionExecute();
	}

}
