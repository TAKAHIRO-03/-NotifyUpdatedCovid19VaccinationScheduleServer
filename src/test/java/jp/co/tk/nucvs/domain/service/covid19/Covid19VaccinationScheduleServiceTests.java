package jp.co.tk.nucvs.domain.service.covid19;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import jp.co.tk.nucvs.domain.repo.Covid19VaccinationScheduleRepository;
import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Covid19VaccinationScheduleServiceTests {
    
    @Autowired
    @InjectMocks
    private Covid19VaccinationScheduleService service;

    @SpyBean
    private Covid19VaccinationScheduleRepository repo;

    private final String BACK_UP_FILE_PATH = "/dbunit/backup.xml";

    @Test
    @DatabaseSetup(value = BACK_UP_FILE_PATH,  type = DatabaseOperation.CLEAN_INSERT)
    void 実行確認() throws Exception {
        val covid19vsFromDb = repo.findAllByOrderByAvailabilityDateAscAvailabilityCountAsc();
        doReturn(List.of(covid19vsFromDb.get(0))).when(repo).findAllByOrderByAvailabilityDateAscAvailabilityCountAsc();
        service.updateAll(covid19vsFromDb);
        verify(repo, times(1)).deleteAll(anyList());
        verify(repo, times(1)).saveAll(anyList());
    }

}
