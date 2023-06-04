package net.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestEx extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6543114371260629548L;
	
	public BadRequestEx() {
    }

    public BadRequestEx(String message) {
        super(message);
    }

    public BadRequestEx(Throwable cause) {
        super(cause);
    }

    public BadRequestEx(String message, Throwable cause) {
        super(message, cause);
    }
}
