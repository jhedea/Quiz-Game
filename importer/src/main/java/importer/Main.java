package importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import importer.api.ActivityApiImpl;
import importer.service.FilePathProvider;
import importer.service.ImportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger("Main");

	public static void main(String[] args) {
		if (args.length < 2) throw new IllegalArgumentException("Not enough arguments provided");
		var serverUrl = args[0];
		var filePath = args[1];
		var flags = Arrays.stream(args).skip(2).toList();

		var service = new ImportServiceImpl(new ActivityApiImpl(), new ObjectMapper(), new FilePathProvider());

		LOGGER.info("Starting Importer");

		if (flags.contains("-D")) {
			LOGGER.info("Removing all activities...");
			service.deleteAllActivities(serverUrl);
			LOGGER.info("Successfully removed");
		}

		LOGGER.info("Importing activities...");
		service.importActivitiesFromFile(serverUrl, filePath);
		LOGGER.info("Successfully imported");
	}
}
