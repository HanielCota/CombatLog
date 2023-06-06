package com.github.hanielcota.combatlog.exceptions;

public class InvalidPlayerException extends IllegalArgumentException {

	public InvalidPlayerException(String message) {
		super(message);
	}

	public InvalidPlayerException(String message, Throwable cause) {
		super(message, cause);
	}

}
