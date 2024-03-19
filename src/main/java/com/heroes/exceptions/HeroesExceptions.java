package com.heroes.exceptions;

import org.springframework.http.HttpStatus;

public class HeroesExceptions extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The http status. */
	private  HttpStatus httpStatus;

	/** The short message. */
	private String shortMessage;
	
	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the new http status
	 */
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the short message.
	 *
	 * @return the short message
	 */
	public String getShortMessage() {
		return shortMessage;
	}

	/**
	 * Sets the short message.
	 *
	 * @param shortMessage the new short message
	 */
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}

	/**
	 * Instantiates a new hoteles exceptions.
	 *
	 * @param httpStatus the http status
	 * @param shortMessage the short message
	 */
	public HeroesExceptions(HttpStatus httpStatus, String shortMessage) {
		this.httpStatus = httpStatus;
		this.shortMessage = shortMessage;
	}

}
