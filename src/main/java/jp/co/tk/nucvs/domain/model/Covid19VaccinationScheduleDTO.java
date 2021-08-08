package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class Covid19VaccinationScheduleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @NonNull
    private Date availabilityDate;

    @NonNull
    private Integer availabilityCount;

    @NonNull
    private Covid19VaccinationVenue covid19VaccinationVenue;

}
