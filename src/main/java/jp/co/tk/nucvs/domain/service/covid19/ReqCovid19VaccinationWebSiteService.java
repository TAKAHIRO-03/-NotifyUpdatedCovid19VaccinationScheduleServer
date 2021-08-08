package jp.co.tk.nucvs.domain.service.covid19;

import java.io.IOException;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

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

	public List<Covid19VaccinationScheduleDTO> request() throws IOException, InterruptedException;

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36";

	/**
	 * 実行してから１か月後、２か月後、３か月の３つの月を取得する
	 *
	 * @return Map<Month> １か月後、２か月後、３か月の３つの月
	 */
	default Map<Month, Integer> getThreeMonthsFromToday() {
		val today = getZonedDateTime();
		val todayMonth = today.getMonth();
		val year = today.getYear();
		val ymMap = new HashMap<Month, Integer>();

		switch (todayMonth) {
		case NOVEMBER:
			ymMap.put(todayMonth, year);
			ymMap.put(todayMonth.plus(1), year);
			ymMap.put(todayMonth.plus(2), year + 1);
			break;
		case DECEMBER:
			ymMap.put(todayMonth, year);
			ymMap.put(todayMonth.plus(1), year + 1);
			ymMap.put(todayMonth.plus(2), year + 1);
			break;
		default:
			ymMap.put(todayMonth, year);
			ymMap.put(todayMonth.plus(1), year);
			ymMap.put(todayMonth.plus(2), year);
			break;
		}

		return Collections.unmodifiableMap(ymMap);
	}

	/**
	 * 
	 * JSoupでリクエストを送るときのオブジェクトを初期化
	 * 
	 * @param url https://...から始まる値
	 * @return Connection
	 */
	default Connection initJsoup(String url) {
		val con = Jsoup.connect(url);
		con.userAgent(USER_AGENT);
		con.header("Accept-Language", "ja");
		con.header("Accept-Encoding", "gzip, deflate");
		con.maxBodySize(0);
		con.timeout(0);
		return con;
	}

	/**
	 * 
	 * JST時間を返却
	 * テストでZonedDateTimeのモックを作るために定義
	 * 
	 * @return ZonedDateTime JST時間 
	 */
	private static ZonedDateTime getZonedDateTime() {
		val zoneId = ZoneId.of("Asia/Tokyo");
		val today = ZonedDateTime.now(zoneId);
		return today;
	}

}
