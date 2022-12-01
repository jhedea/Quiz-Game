package server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception thrown when a user tries to upload a file with an illegal destination path, e.g.: containing ".."
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Illegal file path")
public class IllegalFileException extends RuntimeException {
}
