package jp.co.tk.nucvs.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;

@Repository
public interface Covid19VaccinationScheduleRepository extends JpaRepository<Covid19VaccinationSchedule, Integer> {

    @Override
    @Query(value = "SELECT count(c.id) FROM Covid19VaccinationSchedule c")
    public long count();

    @Query(value = "SELECT cs FROM Covid19VaccinationSchedule cs INNER JOIN FETCH cs.covid19VaccinationVenue cv WHERE cv.city = :city ORDER BY cs.availabilityDate, cs.availabilityCount")
    public List<Covid19VaccinationSchedule> findByCovid19vsOrderByAvaDateAndAvaCnt(String city);

}
