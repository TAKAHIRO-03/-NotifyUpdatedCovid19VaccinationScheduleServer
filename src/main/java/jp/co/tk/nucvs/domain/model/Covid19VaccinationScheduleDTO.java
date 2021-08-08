package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Covid19VaccinationScheduleDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Date availabilityDate;

    private Integer availabilityCount;

    private Covid19VaccinationVenue covid19VaccinationVenue;

}
