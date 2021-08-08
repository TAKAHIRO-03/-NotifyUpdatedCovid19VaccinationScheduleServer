package jp.co.tk.nucvs.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationVenueRepository;
import jp.co.tk.nucvs.domain.service.covid19.ReqAdachikuServiceImpl;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import jp.co.tk.nucvs.domain.service.exception.ParseDomException;
import lombok.val;

@SpringBootTest
public class ReqAdachikuServiceImplTests {

    @Autowired
    ReqAdachikuServiceImpl service;

    @Autowired
    Covid19VaccinationVenueRepository repo;

    @BeforeEach
    void 関数実行前() {
    }

    @Test
    void 例外が発生せずに正常にリクエストが出来るか() throws Exception {
        service.request();
    }

    @Test
    void 存在しないURLを指定した時に例外が発生するか() throws Exception {
        ReflectionTestUtils.setField(service, "URL", "https://example.com/test");
        val actualException = assertThrows(HttpStatusException.class, () -> service.request());
        assertEquals(404, actualException.getStatusCode());
        ReflectionTestUtils.setField(service, "URL", "https://adachi.hbf-rsv.jp/mypage/status/");
    }

    @Test
    void DOM解析_2021年8月() throws Exception {
        val zoneId = ZoneId.of("Asia/Tokyo");
        try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
            String ymd = "20210801";
            mocked.when(() -> {
                Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
                method.setAccessible(true);
                method.invoke(service);
            }).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
                    Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
            val path = "src/test/resouces/2021_08_adachi.html";
            val doc = Jsoup.parse(new File(path), "UTF-8");
            val venueListFromDb = repo.findByCity("足立区");
            val dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
            val threeMonthsMap = service.getThreeMonthsFromToday();

            for (var monthEntry : threeMonthsMap.entrySet()) {
                if(monthEntry.getKey() != Month.AUGUST){
                    continue;
                }
                ReflectionTestUtils.invokeMethod(service, "parseDom", doc, monthEntry, dtoList, venueListFromDb);
            }
            assertTrue(dtoList.size() == 1);
        };

    }

    @Test
    void DOM解析_2021年9月() throws Exception {
        val zoneId = ZoneId.of("Asia/Tokyo");
        try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
            String ymd = "20210901";
            mocked.when(() -> {
                Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
                method.setAccessible(true);
                method.invoke(service);
            }).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
                    Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
            val path = "src/test/resouces/2021_09_adachi.html";
            val doc = Jsoup.parse(new File(path), "UTF-8");
            val venueListFromDb = repo.findByCity("足立区");
            val dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
            val threeMonthsMap = service.getThreeMonthsFromToday();

            for (var monthEntry : threeMonthsMap.entrySet()) {
                if(monthEntry.getKey() != Month.SEPTEMBER){
                    continue;
                }
                ReflectionTestUtils.invokeMethod(service, "parseDom", doc, monthEntry, dtoList, venueListFromDb);
            }
            assertTrue(dtoList.size() == 72);
        };

    }

    @Test
    void DOM解析_2021年10月() throws Exception {
        val zoneId = ZoneId.of("Asia/Tokyo");
        try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
            String ymd = "20211001";
            mocked.when(() -> {
                Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
                method.setAccessible(true);
                method.invoke(service);
            }).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
                    Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
            val path = "src/test/resouces/2021_10_adachi.html";
            val doc = Jsoup.parse(new File(path), "UTF-8");
            val venueListFromDb = repo.findByCity("足立区");
            val dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
            val threeMonthsMap = service.getThreeMonthsFromToday();

            for (var monthEntry : threeMonthsMap.entrySet()) {
                if(monthEntry.getKey() != Month.OCTOBER){
                    continue;
                }
                ReflectionTestUtils.invokeMethod(service, "parseDom", doc, monthEntry, dtoList, venueListFromDb);
            }
            assertTrue(dtoList.size() == 0);
        };

    }

    @Test
    void DOMが変更された時ParseDomExceptionが発生されるか() throws Exception {
        val zoneId = ZoneId.of("Asia/Tokyo");
        try (val mocked = mockStatic(ReqCovid19VaccinationWebSiteService.class)) {
            String ymd = "20211001";
            mocked.when(() -> {
                Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
                method.setAccessible(true);
                method.invoke(service);
            }).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
                    Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
            val path = "src/test/resouces/2021_10_adachi_change_dom.html";
            val doc = Jsoup.parse(new File(path), "UTF-8");
            val venueListFromDb = repo.findByCity("足立区");
            val dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
            val threeMonthsMap = service.getThreeMonthsFromToday();

            for (var monthEntry : threeMonthsMap.entrySet()) {
                if(monthEntry.getKey() != Month.OCTOBER){
                    continue;
                }
                val actualException = assertThrows(ParseDomException.class, () -> ReflectionTestUtils
                        .invokeMethod(service, "parseDom", doc, monthEntry, dtoList, venueListFromDb));
                assertEquals("Failed parse DOM. parseDom function tried to get css class is couter", actualException.getMessage());
            }
            assertTrue(dtoList.size() == 0);
        };

    }

}
