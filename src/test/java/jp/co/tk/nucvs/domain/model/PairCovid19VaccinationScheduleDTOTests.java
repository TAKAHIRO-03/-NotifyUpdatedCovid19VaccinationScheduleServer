package jp.co.tk.nucvs.domain.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import jp.co.tk.nucvs.core.CustomModelMapper;
import jp.co.tk.nucvs.domain.service.covid19.Covid19VaccinationScheduleService;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import jp.co.tk.nucvs.domain.service.line.ReqLineNotifyService;
import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class PairCovid19VaccinationScheduleDTOTests {
    
    @Autowired
    private ReqLineNotifyService reqLineService;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private ReqCovid19VaccinationWebSiteService reqVacService;

    @Autowired
    private Covid19VaccinationScheduleService covid19vsService;

    // @Test
    // void メッセージを作成することが出来るか() throws Exception {
    //     val pair = ReflectionTestUtils.invokeMethod(reqLineService.PairCovid19VaccinationScheduleDTO, name, args);
    //     val actualMsg = ReflectionTestUtils.invokeMethod(reqLineService, "createMsg", pair,
    //             "https://adachi.hbf-rsv.jp/mypage/status/");
    //     System.out.println(actualMsg);
    // }

    @Test
    void メッセージが作成出来るか() throws Exception {
		val covid19vsFromDb = covid19vsService.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
		val covid19vsDto = modelMapper.mapAll(covid19vsFromDb, Covid19VaccinationScheduleDTO.class);
        val actualData = PairCovid19VaccinationScheduleDTO.createPair(covid19vsDto);
    }

}
