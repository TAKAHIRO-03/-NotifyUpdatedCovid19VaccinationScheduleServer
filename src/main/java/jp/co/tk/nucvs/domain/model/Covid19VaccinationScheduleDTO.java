package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Covid19VaccinationScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private LocalDate availabilityDate;

    @NonNull
    private Integer availabilityCount;

    @NonNull
    private Covid19VaccinationVenue covid19VaccinationVenue;

}
