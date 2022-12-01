package importer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.model.Activity;
import importer.api.ActivityApi;
import importer.model.ImportedActivity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImportServiceImplTest {
	private static final ImportedActivity[] FAKE_IMPORTED_ACTIVITIES = new ImportedActivity[] {
			new ImportedActivity("id1", "01/image1.jpg", "A1", 1L, null),
			new ImportedActivity("id2", "02/image2.jpg", "A2", 2L, null),
			new ImportedActivity("id3", "03/image3.jpg", "A3", 3L, null)
	};
	private static final List<Activity> FAKE_ACTIVITIES = List.of(
			new Activity(-1, "A1", "http://${SERVER_ADDRESS}/images/01/image1.jpg", 1f),
			new Activity(-1, "A2", "http://${SERVER_ADDRESS}/images/02/image2.jpg", 2f),
			new Activity(-1, "A3", "http://${SERVER_ADDRESS}/images/03/image3.jpg", 3f)
	);
	private static final List<String> FAKE_RELATIVE_IMAGE_PATHS = List.of(
			"01/image1.jpg",
			"02/image2.jpg",
			"03/image3.jpg"
	);
	private static final List<File> FAKE_IMAGE_FILES = List.of(
			new File("dir/01/image1.jpg"),
			new File("dir/02/image2.jpg"),
			new File("dir/03/image3.jpg")
	);

	private static final File FAKE_FILE = new File("dir/activities.json");

	@Mock
	private ActivityApi activityApi;
	@Mock
	private ObjectMapper objectMapper;
	@Mock
	private FilePathProvider filePathProvider;

	@Captor
	private ArgumentCaptor<String> stringCaptor;
	@Captor
	private ArgumentCaptor<File> fileCaptor;

	private ImportServiceImpl createService() {
		return new ImportServiceImpl(activityApi, objectMapper, filePathProvider);
	}

	@Test
	public void importServicesFromFile_should_add_activities_via_api() throws IOException {
		when(objectMapper.readValue(FAKE_FILE, ImportedActivity[].class)).thenReturn(FAKE_IMPORTED_ACTIVITIES);
		when(filePathProvider.checkIfJsonFileExists("dir")).thenReturn(FAKE_FILE);

		var service = createService();

		service.importActivitiesFromFile("url", "dir");

		verify(activityApi).addActivities("url", FAKE_ACTIVITIES);
	}

	@Test
	public void importServicesFromFile_should_upload_images_via_api() throws IOException {
		when(objectMapper.readValue(FAKE_FILE, ImportedActivity[].class)).thenReturn(FAKE_IMPORTED_ACTIVITIES);
		when(filePathProvider.checkIfJsonFileExists("dir")).thenReturn(FAKE_FILE);

		var service = createService();

		service.importActivitiesFromFile("url", "dir");

		verify(activityApi, times(FAKE_IMPORTED_ACTIVITIES.length))
				.uploadImage(eq("url"), stringCaptor.capture(), fileCaptor.capture());
		assertEquals(FAKE_RELATIVE_IMAGE_PATHS, stringCaptor.getAllValues());
		assertEquals(FAKE_IMAGE_FILES, fileCaptor.getAllValues());
	}

	@Test
	public void deleteAllActivities_should_delete_activities_via_api() {
		var service = createService();
		service.deleteAllActivities("url");

		verify(activityApi).deleteAllActivities("url");
	}
}
