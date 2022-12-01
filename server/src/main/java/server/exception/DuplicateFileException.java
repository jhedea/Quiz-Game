package server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception thrown when a user tries to upload an image that already exists
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Image already exists")
public class DuplicateFileException extends RuntimeException {
}
