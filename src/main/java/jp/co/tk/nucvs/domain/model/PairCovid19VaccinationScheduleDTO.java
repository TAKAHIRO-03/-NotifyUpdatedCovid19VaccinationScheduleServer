package jp.co.tk.nucvs.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.val;

@Getter
public class PairCovid19VaccinationScheduleDTO {

  private Covid19VaccinationScheduleDTO fstApp;
  private Covid19VaccinationScheduleDTO sndApp;

  private PairCovid19VaccinationScheduleDTO(Covid19VaccinationScheduleDTO fstApp,
      Covid19VaccinationScheduleDTO sndApp) {
    val fstLd = fstApp.getAvailabilityDate();
    val fstLdPlus21 = fstLd.plusDays(21);
    val sndLd = sndApp.getAvailabilityDate();

    if( ! fstApp.getCovid19VaccinationVenue().getCity().equals(sndApp.getCovid19VaccinationVenue().getCity())){
      val errorMsg = "The city are different. " + "The first vaccination appointment is " + fstApp
          + "," + "The second vaccination appointment is " + sndApp + ".";
      throw new IllegalArgumentException(errorMsg);
    }

    if (fstLdPlus21.isAfter(sndLd)) {
      val errorMsg = "The second vaccination appointment has not been more "
          + "than 21 days since the first vaccination appointment. " + "The first vaccination appointment is " + fstApp
          + "," + "The second vaccination appointment is " + sndApp + ".";
      throw new IllegalArgumentException(errorMsg);
    }

    this.fstApp = fstApp;
    this.sndApp = sndApp;
  }

  public static List<PairCovid19VaccinationScheduleDTO> createPair(List<Covid19VaccinationScheduleDTO> covid19vsDto) {
    if (CollectionUtils.isEmpty(covid19vsDto)) {
      return Collections.emptyList();
    }

    val pair = new ArrayList<PairCovid19VaccinationScheduleDTO>();
    for (val fst : covid19vsDto) {
      val fstLd = fst.getAvailabilityDate();
      val fstLdPlus21 = fstLd.plusDays(21);
      covid19vsDto.parallelStream().filter(snd -> {
        if( ! fst.getCovid19VaccinationVenue().getCity().equals(snd.getCovid19VaccinationVenue().getCity())){
          return false;
        }
        val sndLd = snd.getAvailabilityDate();
        return fstLdPlus21.isEqual(sndLd) || fstLdPlus21.isBefore(sndLd);
      }).forEach(snd -> {
        pair.add(new PairCovid19VaccinationScheduleDTO(fst, snd));
      });
    }
    return Collections.unmodifiableList(pair);
  }

}
