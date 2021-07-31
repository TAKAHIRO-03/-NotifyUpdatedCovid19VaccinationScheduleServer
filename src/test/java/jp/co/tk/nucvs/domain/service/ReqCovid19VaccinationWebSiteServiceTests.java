package jp.co.tk.nucvs.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import lombok.val;

@SpringBootTest
public class ReqCovid19VaccinationWebSiteServiceTests {

	ReqCovid19VaccinationWebSiteService service;

	@BeforeEach
	void 関数実行前() {
		service = new ReqCovid19VaccinationWebSiteService() {
			@Override
			public Optional<Covid19VaccinationScheduleDTO> request() throws IOException {
				return null;
			}
		};
	}

	@Test
	void 実行日から1か月後_2か月後_3か月後の値が取得することが出来るか() throws Exception {

		val actualDataMonths = service.getThreeMonthsFromToday();
		val expectedMonthsList = List.of(
				List.of(Month.JANUARY, Month.FEBRUARY, Month.MARCH),
				List.of(Month.FEBRUARY, Month.MARCH, Month.APRIL),
				List.of(Month.MARCH, Month.APRIL, Month.MAY),
				List.of(Month.APRIL, Month.MAY, Month.JUNE),
				List.of(Month.MAY, Month.JUNE, Month.JULY),
				List.of(Month.JUNE, Month.JULY, Month.AUGUST),
				List.of(Month.JULY, Month.AUGUST, Month.SEPTEMBER),
				List.of(Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER),
				List.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER),
				List.of(Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER),
				List.of(Month.NOVEMBER, Month.DECEMBER, Month.JANUARY),
				List.of(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)
			);

		var trueCount = expectedMonthsList.stream().filter((months) -> months.equals(actualDataMonths)).count();

		assertNotEquals(0, trueCount);
		assertEquals(1, trueCount);
	}

}
