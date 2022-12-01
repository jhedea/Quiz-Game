package client.usecase;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RememberUsernameUseCaseImpl implements RememberUsernamesUseCase {

	private final Path root = Paths.get("usernames/");

	@Override
	public List<String> readUsernames() {
		try {
			List<String> usernames = new ArrayList<>();
			String path = root.resolve("usernames.txt").toString();

			Scanner scanner = new Scanner(new File(path));
			while (scanner.hasNextLine()) {
				String next = scanner.nextLine();
				if (usernames.contains(next) || next.equals("")) {
					continue;
				}
				usernames.add(next);
			}
			return usernames;
		} catch (FileNotFoundException e) {
			// The file is not found. This is fine at the first launch
		}
		return List.of();
	}

	@Override
	public void writeUsername(String username) {
		try {
			root.toFile().mkdirs();
			File file = root.resolve("usernames.txt").toFile();
			Writer writer = new FileWriter(file, true);
			writer.write(username + "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
