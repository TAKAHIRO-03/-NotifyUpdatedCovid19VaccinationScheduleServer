package jp.co.tk.nucvs.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends RevisionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lineNotifyToken;

    @Column(name = "covid19_vaccination_url")
    private String covid19VaccinationUrl;

    @Column
    private String cityName;

}
