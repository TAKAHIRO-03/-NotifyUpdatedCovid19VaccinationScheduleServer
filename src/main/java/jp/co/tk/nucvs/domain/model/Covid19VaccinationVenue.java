package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@EqualsAndHashCode(exclude = {"covid19VaccinationSchedule"})
public class Covid19VaccinationVenue implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String region;

    private String city;

    private String area;

    private String venue;
 
    @OneToMany(mappedBy = "covid19VaccinationVenue", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Covid19VaccinationSchedule> covid19VaccinationSchedule;

}
