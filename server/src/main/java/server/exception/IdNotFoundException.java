package server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * An exception thrown when a user tries to find an activity from the DB by an id that doesn't exist.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No activity with the specified ID found.")
public class IdNotFoundException extends RuntimeException {
}
