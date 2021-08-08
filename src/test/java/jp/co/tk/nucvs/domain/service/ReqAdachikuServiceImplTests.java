package jp.co.tk.nucvs.domain.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.tk.nucvs.domain.service.covid19.ReqAdachikuServiceImpl;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import lombok.val;

@SpringBootTest
public class ReqAdachikuServiceImplTests {

    @Autowired
    ReqAdachikuServiceImpl service;

    @BeforeEach
    void 関数実行前() {

    }

    @Test
    void 実行確認() throws Exception {
        service.request();
    }

    @Test
    void 今日から3か月間のクエリパラメータが取得出来ること() throws Exception {
        val zoneId = ZoneId.of("Asia/Tokyo");
        try (MockedStatic<ReqCovid19VaccinationWebSiteService> mocked = mockStatic(
                ReqCovid19VaccinationWebSiteService.class)) {
            String ymd = "20211101";
            mocked.when(() -> {
                Method method = ReqCovid19VaccinationWebSiteService.class.getDeclaredMethod("getZonedDateTime");
                method.setAccessible(true);
                method.invoke(service);
            }).thenReturn(ZonedDateTime.of(Integer.valueOf(ymd.substring(0, 4)), Integer.valueOf(ymd.substring(4, 6)),
                    Integer.valueOf(ymd.substring(6, 8)), 0, 0, 0, 0, zoneId));
            var actualCreateParamList = service.createQueryParm();
            assertTrue(actualCreateParamList.contains("?year=2021&month=11#c"));
            assertTrue(actualCreateParamList.contains("?year=2021&month=12#c"));
            assertTrue(actualCreateParamList.contains("?year=2022&month=1#c"));
        };
    }

}
