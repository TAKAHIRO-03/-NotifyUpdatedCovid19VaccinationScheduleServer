package jp.co.tk.nucvs.domain.service.line;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import jp.co.tk.nucvs.core.CustomModelMapper;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.service.covid19.Covid19VaccinationScheduleService;
import lombok.val;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ReqLineNotifyServiceTests {

    @Autowired
    private ReqLineNotifyService reqLineService;

    @Autowired
    private CustomModelMapper modelMapper;

    @Autowired
    private Covid19VaccinationScheduleService covid19vsService;

    @Test
    void LINE通知が遅れるか() throws Exception {
		val covid19vsFromDb = covid19vsService.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
		val covid19vsDto = modelMapper.mapAll(covid19vsFromDb, Covid19VaccinationScheduleDTO.class);
        reqLineService.doNotify(covid19vsDto);
    }

}
