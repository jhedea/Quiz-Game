package server.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	void save(MultipartFile file, String imagePath);

	void deleteAll();

}
