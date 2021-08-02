package jp.co.tk.nucvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication(scanBasePackageClasses = App.class)
@EnableScheduling
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * TODO
	 * ・定期実行を行う処理を書く
	 * ・コロナの予防接種のサイト
	 */
	@Scheduled(cron = "1 * * * * *", zone = "Asia/Tokyo")
	public void updatedDetectionExecute() {
		/**
		 * 深夜帯は実行しない。
		 */
	}

}
