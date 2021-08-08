package jp.co.tk.nucvs.domain.service.covid19;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.repo.Covid19VaccinationVenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReqAdachikuServiceImpl implements ReqCovid19VaccinationWebSiteService {

	private final Covid19VaccinationVenueRepository repo;

	private final String URL = "https://adachi.hbf-rsv.jp/mypage/status/";

	private final int maxVenue = 14;

	@Override
	public List<Covid19VaccinationScheduleDTO> request() throws IOException {

		val con = Jsoup.connect(URL);
		con.userAgent(USER_AGENT);
		con.header("Accept-Language", "ja");
		val venueListFromDb = repo.findByCity("足立区");

		// TODO 空きのみを抽出する 最大値31日以内で検索
		val document = con.get();
		val test = document.getElementsByClass("mt-3").select("strong").text();
		val venueListFromWeb = document.getElementsByClass("mt-3").select("strong").text().split(" "); // 足立入谷小学校 ...
		val dateListFromWeb = document.getElementsByClass("couter").select("th").text().split(" [^0-9] "); // 1 2 3 4...
		val availabilityFromWeb = document.getElementsByClass("couter").select("span").text().split(" "); // - 0人 × ...

		// 日付の最大値検索
		var maxDate = 0;
		for (int i = 26; i <= 31; i++) {
			val gettedDate = Integer.valueOf(dateListFromWeb[i]);
			if (maxDate <= gettedDate) {
				maxDate = gettedDate;
			}
		}

		var dtoList = new ArrayList<Covid19VaccinationScheduleDTO>();
		for (var venue : venueListFromWeb) {
			for (int i = 0; i < maxDate; i++) {
				var offsetCount = 1;
				for (int start = i * maxDate, end = start + maxDate; start < end; start++) {
					if (availabilityFromWeb[start].matches("^[1-9].*")) {
						val availabilityCount = Integer.valueOf(availabilityFromWeb[start].replaceAll("人", ""));
						val offsetCountStr = String.valueOf(offsetCount);
						val inpDateStr = "2016/06/" + offsetCountStr.format("%2s", offsetCountStr).replace(" ", "0") + " 00:00:00";
						val sdformat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
						Date dateTime = null;
						try {
							dateTime = sdformat.parse(inpDateStr);
						} catch (ParseException e) {
							log.error("日付のパースに失敗しました", e);
						}
						val covid19VaccinationVenue = venueListFromDb.stream().filter(x -> x.getVenue().equals(venue)).findFirst();
						if(covid19VaccinationVenue.isPresent()) {
							dtoList.add(new Covid19VaccinationScheduleDTO(dateTime, availabilityCount, covid19VaccinationVenue.get()));
						}
					}
					offsetCount++;
				}
			}
		}

		return dtoList;
	}

	@Override
	public List<String> createQueryParm() {
		val monthsMap = getThreeMonthsFromToday();
		val queryParamList = new ArrayList<String>();
		for (var entry : monthsMap.entrySet()) {
			queryParamList.add("?year=" + entry.getValue() + "&month=" + entry.getKey().getValue() + "#c");
		}
		return Collections.unmodifiableList(queryParamList);
	}

}
