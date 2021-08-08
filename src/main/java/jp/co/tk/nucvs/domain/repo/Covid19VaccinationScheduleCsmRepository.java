package jp.co.tk.nucvs.domain.repo;

import java.util.List;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;

public interface Covid19VaccinationScheduleCsmRepository {

    public int bulkUpsert(List<Covid19VaccinationSchedule> covid19vs);

}
