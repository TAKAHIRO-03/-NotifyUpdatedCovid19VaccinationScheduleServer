package jp.co.tk.nucvs.domain.service.covid19.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jp.co.tk.nucvs.domain.model.Covid19VaccinationScheduleDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestWebSiteService {
    
	@Qualifier("ReqAdachikuServiceImpl")
	private final ReqCovid19VaccinationWebSiteService adachiService;

	public List<Covid19VaccinationScheduleDTO> request() throws IOException, InterruptedException, URISyntaxException {
		return adachiService.request();
	}
}
