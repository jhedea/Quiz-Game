package client.usecase;

import java.util.List;

public interface RememberUsernamesUseCase {

	/**
	 * Reads previously used usernames from usernames.txt.
	 *
	 * @return returns a list of previously used usernames
	 */
	List<String> readUsernames();

	/**
	 * Writes a new username to the usernames.txt file.
	 *
	 * @param username the username that is written to the file
	 */
	void writeUsername(String username);
}
