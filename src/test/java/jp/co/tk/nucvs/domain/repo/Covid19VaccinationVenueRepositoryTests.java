package jp.co.tk.nucvs.domain.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Covid19VaccinationVenueRepositoryTests {

    @Autowired
    Covid19VaccinationVenueRepository repo;

    @BeforeEach
	void 関数実行前() {

    }

    @Test
	void 実行確認() throws Exception {
        repo.findAll();
    }
    
}
