package jp.co.tk.nucvs.domain.service.covid19;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationScheduleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Covid19VaccinationScheduleService {

    private final Covid19VaccinationScheduleRepository repo;

    @Transactional
    public void saveAll(List<Covid19VaccinationSchedule> covid19vs) {
        repo.saveAll(covid19vs);
    }

    public long count() {
        return repo.count();
    }

    public List<Covid19VaccinationSchedule> findByCovid19vsOrderByAvaDateAndAvaCnt(String city) {
        return repo.findByCovid19vsOrderByAvaDateAndAvaCnt(city);
    }

}
