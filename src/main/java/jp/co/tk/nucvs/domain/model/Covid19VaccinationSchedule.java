package jp.co.tk.nucvs.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "covid19_vaccination_schedule")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"id"})
public class Covid19VaccinationSchedule extends RevisionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date availabilityDate;

    private Integer availabilityCount;

    @OneToOne
    @JoinColumn(name = "covid19_vaccination_venue_id", referencedColumnName = "id")
    private Covid19VaccinationVenue covid19VaccinationVenue;

}
