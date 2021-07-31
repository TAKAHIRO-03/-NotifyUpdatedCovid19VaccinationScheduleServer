package jp.co.tk.nucvs.domain.service.covid19;

import java.io.IOException;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import lombok.val;

/**
 *
 * コロナ予防接種サイトにリクエストを送るインターフェース
 *
 * @author KOBAYASHI TAKAHIRO
 *
 */
public interface ReqCovid19VaccinationWebSiteService {

	public Optional<Covid19VaccinationScheduleDTO> request() throws IOException;

	/**
	 * TODO
	 * 実行してから１か月後、２か月後、３か月の３つの月を取得する。
	 *
	 * @return List<Month> １か月後、２か月後、３か月の３つの月
	 */
	default List<Month> getThreeMonthsFromToday(){
		val zoneId = ZoneId.of("Asia/Tokyo");
		val today = ZonedDateTime.now(zoneId);
		val todayMonth = today.getMonth();
		
		return List.of(todayMonth, todayMonth.plus(1), todayMonth.plus(2));
	}

}
