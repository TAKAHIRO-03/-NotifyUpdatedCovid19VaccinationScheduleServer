package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity(name = "covid19_vaccination_venue")
@AllArgsConstructor
@Getter
public class Covid19VaccinationVenue implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String prefecture;

    private String district;

    private String venue;

}
