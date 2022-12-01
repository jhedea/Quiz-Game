package server.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import server.exception.DuplicateFileException;
import server.exception.IllegalFileException;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Path root = Paths.get("images/");

	@Override
	public void save(MultipartFile file, String imagePath) {
		try {
			var targetPath = root.resolve(imagePath);
			if (!targetPath.normalize().startsWith(root)) throw new IllegalFileException();
			targetPath.getParent().toFile().mkdirs();
			Files.copy(file.getInputStream(), targetPath);
		} catch (FileAlreadyExistsException e) {
			throw new DuplicateFileException();
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file.", e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

}
