package jp.co.tk.nucvs.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;

@Repository
public interface Covid19VaccinationScheduleRepository extends JpaRepository<Covid19VaccinationSchedule, Long> {

    @Override
    @Query(value = "SELECT count(c.id) FROM Covid19VaccinationSchedule c")
    public long count();

}
