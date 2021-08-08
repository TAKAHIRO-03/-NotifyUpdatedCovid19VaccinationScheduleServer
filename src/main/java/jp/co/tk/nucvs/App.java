package jp.co.tk.nucvs;

import java.io.IOException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationSchedule;
import jp.co.tk.nucvs.domain.service.covid19.Covid19VaccinationScheduleService;
import jp.co.tk.nucvs.domain.service.covid19.ReqCovid19VaccinationWebSiteService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackageClasses = App.class)
@EnableScheduling
@RequiredArgsConstructor
public class App {

	private final ReqCovid19VaccinationWebSiteService reqService;
	private final Covid19VaccinationScheduleService covid19vsService;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * TODO
	 * ・定期実行を行う処理を書く
	 * ・コロナの予防接種のサイト
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Scheduled(cron = "1 * * * * *", zone = "Asia/Tokyo")
	public void updatedDetectionExecute() throws IOException, InterruptedException {
		log.info("Start notifies you of updates to your Covid19 vaccination appointments.");

 		val dtoList = reqService.request();
		val modelMapper = modelMapper();
		val covid19vs = dtoList.parallelStream().map(ey -> modelMapper.map(ey, Covid19VaccinationSchedule.class)).collect(Collectors.toList());
		if(covid19vsService.count() == 0) {
			covid19vsService.insertAll(covid19vs);
		}
	}

	@Bean
	public ModelMapper modelMapper() {
		val modelMapper = new ModelMapper();
		return modelMapper;
	}

}
