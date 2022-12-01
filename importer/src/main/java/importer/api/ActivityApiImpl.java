package importer.api;

import commons.model.Activity;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ActivityApiImpl implements ActivityApi {
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public void addActivities(String serverUrl, List<Activity> activities) {
		restTemplate.postForEntity(serverUrl + "/api/activities", activities, Object.class);
	}

	@Override
	public void uploadImage(String serverUrl, String imagePath, File image) {
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		var body = new LinkedMultiValueMap<>();
		body.add("file", new FileSystemResource(image));

		var requestEntity = new HttpEntity<>(body, headers);

		var requestPath = serverUrl + "/api/activities/imageUpload?imagePath={imagePath}";
		var uriVars = Map.of("imagePath", imagePath);

		restTemplate.postForEntity(requestPath, requestEntity, Object.class, uriVars);
	}

	@Override
	public void deleteAllActivities(String serverUrl) {
		restTemplate.delete(serverUrl + "/api/activities");
	}
}
