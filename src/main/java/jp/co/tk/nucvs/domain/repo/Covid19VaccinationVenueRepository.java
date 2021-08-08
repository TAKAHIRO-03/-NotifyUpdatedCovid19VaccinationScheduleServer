package jp.co.tk.nucvs.domain.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationVenue;

@Repository
public interface Covid19VaccinationVenueRepository extends JpaRepository<Covid19VaccinationVenue, Integer> {

    List<Covid19VaccinationVenue> findByCity(String city);

}
