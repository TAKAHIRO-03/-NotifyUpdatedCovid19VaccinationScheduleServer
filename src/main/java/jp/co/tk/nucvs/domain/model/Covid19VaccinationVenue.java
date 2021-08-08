package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "covid19_vaccination_venue")
@NoArgsConstructor
@Getter
@ToString(exclude = {"covid19VaccinationSchedule"})
@EqualsAndHashCode
public class Covid19VaccinationVenue implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String region;

    private String city;

    private String area;

    private String venue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "covid19_vaccination_venue_id")
    private Covid19VaccinationSchedule covid19VaccinationSchedule;

}
