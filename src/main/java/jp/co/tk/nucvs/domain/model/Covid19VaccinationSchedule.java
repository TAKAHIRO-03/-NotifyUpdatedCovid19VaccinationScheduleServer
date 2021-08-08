package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "covid19_vaccination_schedule")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Covid19VaccinationSchedule extends RevisionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date availabilityDate;

    private Integer availabilityCount;

    @Column(name = "covid19_vaccination_venue_id", insertable = false, updatable = false)
    private Integer covid19VaccinationVenueId;

    @OneToOne(mappedBy = "covid19VaccinationSchedule", cascade = CascadeType.ALL)
    private Covid19VaccinationVenue covid19VaccinationVenue;

}
