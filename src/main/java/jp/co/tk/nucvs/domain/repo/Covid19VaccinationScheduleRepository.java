package jp.co.tk.nucvs.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;

public interface Covid19VaccinationScheduleRepository extends JpaRepository<Covid19VaccinationSchedule, Long> {

}
