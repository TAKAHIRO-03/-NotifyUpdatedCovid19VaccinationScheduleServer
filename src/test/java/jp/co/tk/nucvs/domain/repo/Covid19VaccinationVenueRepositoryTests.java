package jp.co.tk.nucvs.domain.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.val;

@SpringBootTest
public class Covid19VaccinationVenueRepositoryTests {

    @Autowired
    Covid19VaccinationVenueRepository repo;

    @BeforeEach
	void 関数実行前() {

    }

    @Test
	void 実行確認() throws Exception {
        val result = repo.findAll();
        val result2 = result.get(0).getCovid19VaccinationSchedule();
        val result3 = result.get(0).getCovid19VaccinationSchedule();
        repo.deleteById(94);
    }
    
}
