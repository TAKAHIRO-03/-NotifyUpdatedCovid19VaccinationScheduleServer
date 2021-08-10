package jp.co.tk.nucvs.domain.service.line;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import jp.co.tk.nucvs.core.log.Logger;
import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import jp.co.tk.nucvs.domain.model.PairCovid19VaccinationScheduleDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class ReqLineNotifyService {

  private final String LINE_URL = "https://notify-api.line.me/api/notify";
  private final String ACCESS_TOKEN = "PDjmXcmFeET08ltQNTkPAxqKt0bzEdbyTfbhVAxpcbl";
  private final RestTemplate restTemplate;
  @Logger
	private static Log log;

  public void doNotify(List<Covid19VaccinationScheduleDTO> covid19vsDto, String covid19Url) {

    val _1stTime2ndTimePair = PairCovid19VaccinationScheduleDTO.createPair(covid19vsDto);
    val msg = createMsg(_1stTime2ndTimePair, covid19Url);
    val params = new LinkedMultiValueMap<String, String>();
    params.add("message", msg);

    val headers = new HttpHeaders();
    headers.setContentType(new MediaType(MediaType.APPLICATION_FORM_URLENCODED, Charset.forName("utf-8")));
    headers.add("Authorization", "Bearer " + ACCESS_TOKEN);

    try {
      RequestEntity<LinkedMultiValueMap<String, String>> req = RequestEntity.post(new URI(LINE_URL)).headers(headers)
          .body(params);
      val res = restTemplate.exchange(req, String.class);
      log.debug("status:" + res.getStatusCodeValue() + "\n" + "body:" + res.getBody());

    } catch (URISyntaxException e) {
      log.error("Catch ReqLineNotifyService.doNotify", e);
    }
  }

  private String createMsg(List<PairCovid19VaccinationScheduleDTO> _1stTime2ndTimePair, String covid19Url) {
    val msgBuilder = new StringBuilder();
    var count = 0;
    for (val ava : _1stTime2ndTimePair) {
      count++;
      val fst = ava.getFstApp();
      val snd = ava.getSndApp();
      msgBuilder.append("====================");
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append("1回目 " + fst.getAvailabilityDate());
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append(fst.getCovid19VaccinationVenue().getRegion() + " " + fst.getCovid19VaccinationVenue().getCity()
              + " " + fst.getCovid19VaccinationVenue().getArea() + " " + fst.getCovid19VaccinationVenue().getVenue());
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append("2回目 " + snd.getAvailabilityDate());
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append(snd.getCovid19VaccinationVenue().getRegion() + " " + snd.getCovid19VaccinationVenue().getCity()
              + " " + snd.getCovid19VaccinationVenue().getArea() + " " + snd.getCovid19VaccinationVenue().getVenue());
      msgBuilder.append(System.getProperty("line.separator"));
      msgBuilder.append("====================");
      if (count == 10) {
        break;
      }
    }
    msgBuilder.append(System.getProperty("line.separator"));
    msgBuilder.append(covid19Url);
    return msgBuilder.toString();
  }

}