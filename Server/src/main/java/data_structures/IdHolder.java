package main.java.data_structures;
/**
 * holds the (database-)id of an object, the id has to be positive(unsigned), to be valid
 * if the id is negative, e.g -1 it is not existent in the database (yet),
 * this happens for example if an object will be added to the database
 */
public interface IdHolder {
	int getId();
}
