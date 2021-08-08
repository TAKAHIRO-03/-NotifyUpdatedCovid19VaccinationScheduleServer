package jp.co.tk.nucvs.domain.repo;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Covid19VaccinationScheduleCsmRepositoryImpl implements Covid19VaccinationScheduleCsmRepository {

    private final EntityManager em;

    @Override
    public int bulkUpsert(List<Covid19VaccinationSchedule> covid19vs) {
        // TODO Auto-generated method stub
        return 0;
    }

}
