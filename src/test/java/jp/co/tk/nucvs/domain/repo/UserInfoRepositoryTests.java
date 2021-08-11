package jp.co.tk.nucvs.domain.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class UserInfoRepositoryTests {

    @Autowired
    UserInfoRepository repo;

    @BeforeEach
	void 関数実行前() {

    }

    @Test
	void 実行確認() throws Exception {
        val result = repo.findAll();
        System.out.println(result);
    }
    
}
