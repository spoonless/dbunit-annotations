package org.dbunit.ext.annotation;

/**
 * Defines the interface contract for operations performed on the database. 
 * This enumeration encapsulates the original DbUnit constants defined in
 * {@link org.dbunit.operation.DatabaseOperation DatabaseOperation}.
 * 
 * @author David Gayerie
 * @version 1.0
 * @since 2010
 */
public enum DatabaseOperation {

	NONE(org.dbunit.operation.DatabaseOperation.NONE), 
	DELETE(org.dbunit.operation.DatabaseOperation.DELETE), 
	DELETE_ALL(org.dbunit.operation.DatabaseOperation.DELETE_ALL), 
	CLEAN_INSERT(org.dbunit.operation.DatabaseOperation.CLEAN_INSERT), 
	TRUNCATE(org.dbunit.operation.DatabaseOperation.TRUNCATE_TABLE), 
	REFRESH(org.dbunit.operation.DatabaseOperation.REFRESH), 
	INSERT(org.dbunit.operation.DatabaseOperation.INSERT), 
	UPDATE(org.dbunit.operation.DatabaseOperation.UPDATE);

	private org.dbunit.operation.DatabaseOperation databaseOperation;

	/**
	 * @return The original constant from the DbUnit framework.
	 */
	public org.dbunit.operation.DatabaseOperation convert() {
		return databaseOperation;
	}

	/**
	 * Constructor
	 * 
	 * @param databaseOperation
	 *            associates the enumeration from the original DbUnit constant.
	 */
	private DatabaseOperation(
			org.dbunit.operation.DatabaseOperation databaseOperation) {
		this.databaseOperation = databaseOperation;
	}
}
