package jp.co.tk.nucvs.domain.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import jp.co.tk.nucvs.domain.repo.Covid19VaccinationScheduleRepository;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationVenueRepository;
import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Covid19VaccinationScheduleTests extends RevisionInfo implements Serializable {

    @Autowired
    Covid19VaccinationVenueRepository venueRepo;

    @Autowired
    Covid19VaccinationScheduleRepository scheRepo;

    @BeforeEach
    void 関数実行前() {
    }

    @Test
    void idとcreated_timeとupdated_timeの値が異なっていてそれ以外は同じ値の時equalsメソッドがtrueを返却するか() throws Exception {
        val o1 = new Covid19VaccinationSchedule();
        o1.setId(1);
        o1.setAvailabilityDate(LocalDate.parse("2021-08-11", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        o1.setAvailabilityCount(1);
        o1.setCovid19VaccinationVenue(venueRepo.findById(28).get());
        o1.setCreatedTime(new Date());
        o1.setUpdatedTime(new Date());

        val o2 = new Covid19VaccinationSchedule();
        o2.setAvailabilityDate(LocalDate.parse("2021-08-11", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        o2.setAvailabilityCount(1);
        o2.setCovid19VaccinationVenue(venueRepo.findById(28).get());

        assertTrue(o1.equals(o2));
    }


}
