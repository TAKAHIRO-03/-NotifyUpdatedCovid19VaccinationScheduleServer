package jp.co.tk.nucvs.domain.repo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Covid19VaccinationScheduleRepositoryTests {

    @Autowired
    Covid19VaccinationScheduleRepository repo;

    private final String BACK_UP_FILE_PATH = "/dbunit/backup.xml";

    @Test
    @DatabaseSetup(value =  BACK_UP_FILE_PATH, type = DatabaseOperation.CLEAN_INSERT)
    void 実行確認() throws Exception {
        val actual = repo.findAll();
        assertEquals(32, actual.size());
    }

}
