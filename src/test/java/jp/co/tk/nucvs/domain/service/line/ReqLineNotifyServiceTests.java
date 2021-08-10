package jp.co.tk.nucvs.domain.service.line;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.tk.nucvs.core.CustomModelMapper;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.service.covid19.Covid19VaccinationScheduleService;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import lombok.val;

@SpringBootTest
public class ReqLineNotifyServiceTests {

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
    void LINE通知が遅れるか() throws Exception {
		val covid19vsFromDb = covid19vsService.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
		val covid19vsDto = modelMapper.mapAll(covid19vsFromDb, Covid19VaccinationScheduleDTO.class);
        reqLineService.doNotify(covid19vsDto, "https://adachi.hbf-rsv.jp/");
    }

}
