package jp.co.tk.nucvs.domain.service.covid19;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tk.nucvs.core.log.Logger;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class Covid19VaccinationScheduleService {

    private final Covid19VaccinationScheduleRepository repo;
    @Logger
	private static Log log;

    @Transactional
    public void updateAdachiAvailability(List<Covid19VaccinationSchedule> covid19vsFromWeb) {
        val covid19vsFromDb = repo.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
        val diffWebToDb = compareDbAndWeb(covid19vsFromWeb, covid19vsFromDb);
        repo.saveAll(diffWebToDb);
        log.info("Save count is " + diffWebToDb.size());

        val diffDbToWeb = compareDbAndWeb(covid19vsFromDb, covid19vsFromWeb);
        repo.deleteAll(diffDbToWeb);
        log.info("Delete count is " + diffDbToWeb.size());
    }

    @Transactional(readOnly = true)
    public List<Covid19VaccinationSchedule> findByCovid19vsOrderByAvaDateAndAvaCnt(String city) {
        return repo.findByCovid19vsOrderByAvaDateAndAvaCnt(city);
    }

    private List<Covid19VaccinationSchedule> compareDbAndWeb(List<Covid19VaccinationSchedule> o1,
            List<Covid19VaccinationSchedule> o2) {
        return o1.parallelStream().filter(x1 -> !o2.contains(x1)).collect(Collectors.toList());
    }

}
