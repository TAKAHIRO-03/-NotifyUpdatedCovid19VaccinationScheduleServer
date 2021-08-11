package jp.co.tk.nucvs.domain.service.covid19.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import lombok.val;

@SpringBootTest
public class ReqCovid19VaccinationWebSiteServiceTests {

	ReqCovid19VaccinationWebSiteService service;

	@BeforeEach
	void 関数実行前() {
		service = new ReqCovid19VaccinationWebSiteService() {
			@Override
			public List<Covid19VaccinationScheduleDTO> request() throws IOException {
				return null;
			}
		};
	}

	@Test
	void 実行日が10月の時_年の値は同一であること() throws Exception {

		val zoneId = ZoneId.of("Asia/Tokyo");
		try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
			String ymd = "20211001";
			mocked.when(() -> {
				Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
				method.setAccessible(true);
				method.invoke(service);
			}).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
					Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
			var actualMonths = service.getThreeMonthsFromToday();
			var expectedMonths = Collections.unmodifiableMap(new TreeMap<Month, Integer>() {
				{
					put(Month.OCTOBER, 2021);
					put(Month.NOVEMBER, 2021);
					put(Month.DECEMBER, 2021);
				}
			});

			assertEquals(expectedMonths, actualMonths);
		}
		;

	}

	@Test
	void 実行日が11月の時_1月の年の値は11月の年の値の翌年であること() throws Exception {

		val zoneId = ZoneId.of("Asia/Tokyo");
		try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
			String ymd = "20211101";
			mocked.when(() -> {
				Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
				method.setAccessible(true);
				method.invoke(service);
			}).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
					Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
			var actualMonths = service.getThreeMonthsFromToday();
			var expectedMonths = Collections.unmodifiableMap(new TreeMap<Month, Integer>() {
				{
					put(Month.NOVEMBER, 2021);
					put(Month.DECEMBER, 2021);
					put(Month.JANUARY, 2022);
				}
			});

			assertEquals(expectedMonths, actualMonths);
		};

	}

	@Test
	void 実行日が12月の時_1月と2月の年の値は12月の年の値の翌年であること() throws Exception {

		val zoneId = ZoneId.of("Asia/Tokyo");
		try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
			String ymd = "20211201";
			mocked.when(() -> {
				Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
				method.setAccessible(true);
				method.invoke(service);
			}).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
					Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
			var actualMonths = service.getThreeMonthsFromToday();
			var expectedMonths = Collections.unmodifiableMap(new TreeMap<Month, Integer>() {
				{
					put(Month.DECEMBER, 2021);
					put(Month.JANUARY, 2022);
					put(Month.FEBRUARY, 2022);
				}
			});

			assertEquals(expectedMonths, actualMonths);
		};

	}

}
