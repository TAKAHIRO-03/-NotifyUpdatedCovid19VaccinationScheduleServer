package jp.co.tk.nucvs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import jp.co.tk.nucvs.core.CustomModelMapper;
import jp.co.tk.nucvs.core.log.LogFactory;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.service.covid19.Covid19VaccinationScheduleService;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import jp.co.tk.nucvs.domain.service.line.ReqLineNotifyService;
import lombok.val;

@SpringBootApplication(scanBasePackageClasses = App.class)
@EnableScheduling
public class App {

	@Autowired
	private ReqCovid19VaccinationWebSiteService reqCovid19Service;

	@Autowired
	private Covid19VaccinationScheduleService covid19vsService;

	@Autowired
	private ReqLineNotifyService reqLineService;
	
	private static final Log log = LogFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * 
	 * cronで定期実行を行う
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 */
	@Scheduled(cron = "${scheduling.cron}", zone = "Asia/Tokyo")
	public void updatedDetectionExecute() throws IOException, InterruptedException, URISyntaxException {

		log.info("Start notify you of updates to your Covid19 vaccination appointments.");

		val dtoList = reqCovid19Service.request();
		if(dtoList.isEmpty()) {
			log.info("The cron terminated because the data in the website is empty.");
			return;
		}
		
		val modelMapper = modelMapper();
		val covid19vsFromWeb = modelMapper.mapAll(dtoList, Covid19VaccinationSchedule.class);
		covid19vsService.updateAdachiAvailability(covid19vsFromWeb);

		val covid19vsFromDb = covid19vsService.findByCovid19vsOrderByAvaDateAndAvaCnt("足立区");
		/**
		 * モデルナ会場と平日のデータは削除する。
		 */
		covid19vsFromDb.removeIf(x -> { 
			val isModernaVenue = x.getCovid19VaccinationVenue().getVenue().contains("モデルナ");
			val isNotHoliday = x.getAvailabilityDate().getDayOfWeek() != DayOfWeek.SATURDAY || x.getAvailabilityDate().getDayOfWeek() != DayOfWeek.SUNDAY;
			return isModernaVenue || isNotHoliday;
		});
		val covid19vsDto = modelMapper.mapAll(covid19vsFromDb, Covid19VaccinationScheduleDTO.class);
		reqLineService.doNotify(covid19vsDto, new URI("https://adachi.hbf-rsv.jp/").toString());
		
		log.info("The cron terminated.");
	}

	@Bean
	public CustomModelMapper modelMapper() {
		val modelMapper = new CustomModelMapper();
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new SimpleClientHttpRequestFactory());
	}

}
