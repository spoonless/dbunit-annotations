package org.dbunit.ext.annotation;

public class DbUnitAnnotationException extends Exception {

	private static final long serialVersionUID = -5006442477143966215L;

	public DbUnitAnnotationException() {
	}

	public DbUnitAnnotationException(String message) {
		super(message);
	}

	public DbUnitAnnotationException(Throwable cause) {
		super(cause);
	}

	public DbUnitAnnotationException(String message, Throwable cause) {
		super(message, cause);
	}

}
