package jp.co.tk.nucvs.domain.service.line;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import jp.co.tk.nucvs.core.log.Logger;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.model.PairCovid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.model.UserInfo;
import jp.co.tk.nucvs.domain.repo.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class ReqLineNotifyService {

  private final String LINE_URL = "https://notify-api.line.me/api/notify";
  private final RestTemplate restTemplate;
  private final UserInfoRepository userInfoRepo;

  @Logger
  private static Log log;

  public void doNotify(List<Covid19VaccinationScheduleDTO> covid19vsDto) throws URISyntaxException {

    /**
     * モデルナ会場と平日のデータは削除する。
     */
    covid19vsDto.removeIf(x -> {
      val isModernaVenue = x.getCovid19VaccinationVenue().getVenue().contains("モデルナ");
      val isNotHoliday = x.getAvailabilityDate().getDayOfWeek() != DayOfWeek.SATURDAY
          || x.getAvailabilityDate().getDayOfWeek() != DayOfWeek.SUNDAY;
      return isModernaVenue || isNotHoliday;
    });

    val userInfoList = userInfoRepo.findAll();
    for (val userInfo : userInfoList) {
      val extractedCityCovid19vsDto = covid19vsDto.stream()
          .filter(x -> x.getCovid19VaccinationVenue().getCity().equals(userInfo.getCityName()))
          .collect(Collectors.toList());

      val _1stTime2ndTimePair = PairCovid19VaccinationScheduleDTO.createPair(extractedCityCovid19vsDto);
      if (CollectionUtils.isEmpty(_1stTime2ndTimePair)) {
        log.info("Vaccination schedule could not be found, so no LINE notification was sent.");
        continue;
      }

      val msg = createMsg(_1stTime2ndTimePair, userInfo.getCovid19VaccinationUrl());
      reqLineNotify(msg, userInfo);

    }

  }

  private void reqLineNotify(String msg, UserInfo userInfo) throws URISyntaxException {
    val params = new LinkedMultiValueMap<String, String>();
    params.add("message", msg);

    val headers = new HttpHeaders();
    headers.setContentType(new MediaType(MediaType.APPLICATION_FORM_URLENCODED, Charset.forName("utf-8")));
    headers.add("Authorization", "Bearer " + userInfo.getLineNotifyToken());

    RequestEntity<LinkedMultiValueMap<String, String>> req = RequestEntity.post(new URI(LINE_URL)).headers(headers)
        .body(params);
    log.info("LINE Notify request url \"" + req.getUrl() + "\"");
    val res = restTemplate.exchange(req, String.class);

    if (res.getStatusCodeValue() != 200) {
      log.warn("LINE Notify responced a status code " + res.getStatusCodeValue() + ". user_info.id" + userInfo.getId());
    }
  }

  private String createMsg(List<PairCovid19VaccinationScheduleDTO> _1stTime2ndTimePair, String covid19Url) {

    val msgBuilder = new StringBuilder();
    var count = 0;
    val fstPairVenue = _1stTime2ndTimePair.get(0).getFstApp().getCovid19VaccinationVenue();
    msgBuilder.append(System.getProperty("line.separator"));
    msgBuilder.append(fstPairVenue.getRegion() + " " + fstPairVenue.getCity());
    msgBuilder.append(System.getProperty("line.separator"));

    for (val ava : _1stTime2ndTimePair) {
      val fst = ava.getFstApp();
      val snd = ava.getSndApp();
      msgBuilder.append("====================");
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append("1回目 " + fst.getAvailabilityDate() + " " + fst.getCovid19VaccinationVenue().getArea() + " "
          + fst.getCovid19VaccinationVenue().getVenue());
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append("2回目 " + snd.getAvailabilityDate() + " " + snd.getCovid19VaccinationVenue().getArea() + " "
          + snd.getCovid19VaccinationVenue().getVenue());
      msgBuilder.append(System.getProperty("line.separator"));
      count++;
      if (count == 10) {
        break;
      }
    }
    msgBuilder.append(System.getProperty("line.separator"));
    msgBuilder.append(covid19Url);

    return msgBuilder.toString();
  }

}