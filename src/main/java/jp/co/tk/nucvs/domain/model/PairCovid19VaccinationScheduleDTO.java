package jp.co.tk.nucvs.domain.model;

import java.time.ZoneId;

import lombok.Getter;
import lombok.val;

@Getter
public class PairCovid19VaccinationScheduleDTO {

    private Covid19VaccinationScheduleDTO fstApp;
    private Covid19VaccinationScheduleDTO sndApp;
    private final ZoneId zoneId = ZoneId.of("Asia/Tokyo");

    public PairCovid19VaccinationScheduleDTO(Covid19VaccinationScheduleDTO fstApp,
            Covid19VaccinationScheduleDTO sndApp) {
        val fstLd = fstApp.getAvailabilityDate();
        val fstLdPlus21 = fstLd.plusDays(21);
        val sndLd = sndApp.getAvailabilityDate();

        if (fstLdPlus21.isBefore(sndLd)) {
            String errorMsg = "The second vaccination appointment has not been more "
                    + "than 21 days since the first vaccination appointment. " + "The first vaccination appointment is "
                    + fstApp + "," + "The second vaccination appointment is " + sndApp + ".";
            throw new IllegalArgumentException(errorMsg);
        }

        this.fstApp = fstApp;
        this.sndApp = sndApp;
    }
}