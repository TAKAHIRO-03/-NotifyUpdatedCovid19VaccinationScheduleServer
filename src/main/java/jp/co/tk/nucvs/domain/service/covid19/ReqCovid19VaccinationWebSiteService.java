package jp.co.tk.nucvs.domain.service.covid19;

import java.io.IOException;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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

	public List<String> createQueryParm();

	/**
	 * TODO 実行してから１か月後、２か月後、３か月の３つの月を取得する。
	 *
	 * @return List<Month> １か月後、２か月後、３か月の３つの月
	 */
	default Map<Month, Integer> getThreeMonthsFromToday() {
		val today = getZonedDateTime();
		val todayMonth = today.getMonth();
		val year = today.getYear();
		Map<Month, Integer> ymMap = null;

		switch (todayMonth) {
			case NOVEMBER:
				ymMap = new TreeMap<Month, Integer>() {
					{
						put(todayMonth, year); 
						put(todayMonth.plus(1), year);
						put(todayMonth.plus(2), year + 1);
					}
				};
				break;
			case DECEMBER:
				ymMap = new TreeMap<Month, Integer>() {
					{
						put(todayMonth, year); 
						put(todayMonth.plus(1), year + 1);
						put(todayMonth.plus(2), year + 1);
					}
				};
				break;
			default:
				ymMap = new TreeMap<Month, Integer>() {
					{
						put(todayMonth, year); 
						put(todayMonth.plus(1), year);
						put(todayMonth.plus(2), year);
					}
				};
				break;
		}

		return Collections.unmodifiableMap(ymMap);
	}

	private static ZonedDateTime getZonedDateTime() {
		val zoneId = ZoneId.of("Asia/Tokyo");
		val today = ZonedDateTime.now(zoneId);
		return today;
	}

}
