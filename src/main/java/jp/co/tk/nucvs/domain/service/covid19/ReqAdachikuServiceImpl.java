package jp.co.tk.nucvs.domain.service.covid19;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationVenue;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationVenueRepository;
import jp.co.tk.nucvs.domain.service.exception.ParseDomException;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReqAdachikuServiceImpl implements ReqCovid19VaccinationWebSiteService {

	private final Covid19VaccinationVenueRepository repo;
	private final String URL;

	@Autowired
	public ReqAdachikuServiceImpl(Covid19VaccinationVenueRepository repo) {
		this.repo = repo;
		this.URL = "https://adachi.hbf-rsv.jp/mypage/status/";
	}

	@Override
	public List<Covid19VaccinationScheduleDTO> request() throws IOException, InterruptedException {

		// 実行日の日付から３か月間の月と年を返却
		val threeMonthsMap = getThreeMonthsFromToday();

		// DBのデータ取得
		val venueListFromDb = repo.findByCity("足立区");

		// 結果格納用のリスト定義
		val dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
		for (var monthEntry : threeMonthsMap.entrySet()) {

			// 連続でリクエストが飛ばないように1秒間隔を空ける。
			Thread.sleep(1000);

			// Jsoup初期化
			val con = initJsoup(URL + "?year=" + monthEntry.getValue() + "&month=" + monthEntry.getKey().getValue());

			// リクエスト
			val doc = con.get();

			// DOM解析
			try {
				parseDom(doc, monthEntry, dtoList, venueListFromDb);
			} catch (ParseException | ParseDomException e) {
				log.error(e.getMessage(), e);
			}
		}

		 Collections.sort(dtoList, new Comparator<Covid19VaccinationScheduleDTO>(){
			@Override
			public int compare(Covid19VaccinationScheduleDTO o1, Covid19VaccinationScheduleDTO o2) {
				if(o1.getAvailabilityDate().getTime() < o2.getAvailabilityDate().getTime()) {
					return -1;
				} else if(o1.getAvailabilityDate().getTime() > o2.getAvailabilityDate().getTime()) {
					return 1;
				} else {
					return 0;
				}
			}
		 });

		return Collections.unmodifiableList(dtoList);
	}

	private void parseDom(Document doc, Map.Entry<Month, Integer> monthEntry,
			List<Covid19VaccinationScheduleDTO> dtoList, List<Covid19VaccinationVenue> venueListFromDb) throws ParseException {

		int maxDateAsInt = 0;
		String[] venueListFromWeb = {};
		String[] availabilityFromWeb = {};
		val cssClass = "couter";
		try {
			// 日付の最大値検索
			val dateListFromWeb = doc.getElementsByClass(cssClass).select("th").text().split(" [^0-9] "); // 1 2 3 4...
			for (int i = 26; i <= 31; i++) {
				val gotDate = Integer.valueOf(dateListFromWeb[i]);
				if (maxDateAsInt <= gotDate) {
					maxDateAsInt = gotDate;
				}
			}
			venueListFromWeb = doc.getElementsByClass(cssClass).select("strong").text().split(" "); // 足立入谷小学校 ...
			availabilityFromWeb = doc.getElementsByClass(cssClass).select("span").text().split(" "); // - 0人 × ...
		} catch (RuntimeException e) {
			throw new ParseDomException("Failed parse DOM. parseDom function tried to get css class is " + cssClass, e);
		}

		for (int i = 0; i < venueListFromWeb.length; i++) {
			var _1stToEndOfMonthCnt = 1;
			for (int start = i * maxDateAsInt, end = start + maxDateAsInt; start < end; start++) {
				if (availabilityFromWeb[start].matches("^[1-9].*")) {
					val availabilityCnt = Integer.valueOf(availabilityFromWeb[start].replaceAll("人", ""));
					val _1stToEndOfMonthCntStr = String.valueOf(_1stToEndOfMonthCnt);
					val inpDateStr = monthEntry.getValue() + "/" + monthEntry.getKey().getValue() + "/"
							+ _1stToEndOfMonthCntStr.format("%2s", _1stToEndOfMonthCntStr).replace(" ", "0") + " 00:00:00";
					val sdformat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
					val dateTime = sdformat.parse(inpDateStr);
					val venue = venueListFromWeb[i].trim();
					val covid19VaccinationVenue = venueListFromDb.stream().filter(x -> x.getVenue().equals(venue))
							.findFirst();
					if (covid19VaccinationVenue.isPresent()) {
						dtoList.add(new Covid19VaccinationScheduleDTO(dateTime, availabilityCnt,
								covid19VaccinationVenue.get()));
					} else {
						log.warn("\"" + venue + "\"" + " is Not found venue in DB.");
					}
				}
				_1stToEndOfMonthCnt++;
			}
		}
	}

}
