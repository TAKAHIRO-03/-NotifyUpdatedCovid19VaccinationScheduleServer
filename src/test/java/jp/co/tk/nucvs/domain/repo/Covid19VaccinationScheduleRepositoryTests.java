package jp.co.tk.nucvs.domain.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.val;

@SpringBootTest
public class Covid19VaccinationScheduleRepositoryTests {

    @Autowired
    Covid19VaccinationScheduleRepository repo;

    @BeforeEach
	void 関数実行前() {

    }

    @Test
	void 実行確認() throws Exception {
        val result = repo.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
        System.out.println(result);
    }
    
}
