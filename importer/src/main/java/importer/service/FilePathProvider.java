package importer.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePathProvider {

	public File checkIfJsonFileExists(String filePath) throws IOException {
		File file;
		Path path = Path.of(filePath, "activities.json");
		if (Files.exists(path)) {
			file = new File(path.toString());
			return file;
		} else {
			throw new IOException("Cannot find an activities.json file from the provided file path. "
					+ "Please provide a path to a directory containing an activities.json file.");
		}
	}
}
