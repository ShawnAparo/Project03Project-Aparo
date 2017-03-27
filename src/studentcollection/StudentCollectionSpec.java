package studentcollection;


import java.io.File;
import java.io.Serializable;

import studentspec.StudentSpec;

import java.util.Iterator;

/**
 * <p>Title: StudentCollectionSpec</p>
 *
 * <p>Description: specification of the functionalities available for manipulating a
 * collection of Student objects. Note that this specification is silent on the issue
 * of duplicate collection entries, that is, this specification neither requires nor
 * forbids that collection entries be unique (distinguishable from all other collection
 * entries in some manner).</p>
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
public interface StudentCollectionSpec extends Serializable
{
    static final long serialVersionUID = -6102861241800042L;

    /**
     * Default size of collection when invoking parameterless proxy constructor.
     */
    public final static int DEFAULT_STUDENT_COLLECTION_CAPACITY = 5000;

    /**
     * create a StudentCollectionSpec-type object with capacity
     * StudentCollectionSpec.DEFAULT_STUDENT_COLLECTION_CAPACITY.
     *
     * @return a StudentCollectionSpec-type object with capacity
     * StudentCollectionSpec.DEFAULT_STUDENT_COLLECTION_CAPACITY.
     */
    public static StudentCollectionSpec createStudentCollection()
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Create a collection of the specified size. An exception will be thrown if the specified size
     * is less than or equal to 0. This method hides StudentCollectionSpec.createStudentCollection(int).
     *
     * @param capacity capacity of collection.
     * @return StudentCollectionSpec-type object of specified capacity
     *
     * @throws StudentCollectionException if a zero or negative collection capacity is specified.
     */
    public static StudentCollectionSpec createStudentCollection( int capacity )
     throws StudentCollectionException
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * return the maximum number of elements that can be stored at any one time
     *
     * @return the maximum number of elements that can be stored in the collection
     */
    public int getCapacity();

    /**
     * empty the collection ("throw away" the current contents)
     */
    public void reset();

    /**
     * return whether the collection is full
     *
     * @return boolean indicates whether the collection is currently filled to capacity
     */
    public boolean isFull();

    /**
     * return whether the collection is empty
     *
     * @return whether the collection is currently empty
     */
    public boolean isEmpty();

    /**
     * return the number of elements currently in the collection
     *
     * @return the number of elements currently in the collection
     */
    public int getStudentCount();

    /**
     * return the number of remaining empty positions
     *
     * @return the number of remaining empty positions in the collection
     */
    public int getSpacesRemaining();

    /**
     * add a student to the collection
     *
     * @param aStudent object to be added to the collection
     * @throws StudentCollectionException thrown if the collection is full at the time of invocation
     */
    public void addStudent(StudentSpec aStudent) throws StudentCollectionException;

    /**
     * find a student object with an SID that matches the supplied SID;
     * the Student is NOT removed from the collection
     *
     * @param sidKey the SID that a student is to be matched against
     * @return the student with the specified SID (null if not found)
     */
    public StudentSpec retrieveStudentBySID(String sidKey);

    /**
     * find a student object with an SID that matches the supplied SID;
     * the student IS REMOVED FROM THE COLLECTION
     *
     * @param sidKey the SID that a student is to be matched against
     * @return the student with the specified SID (null if not found)
     */
    public StudentSpec removeStudentBySID(String sidKey);

    /**
     * return the contents of the collection, represented as a single String
     *
     * @return the contents of the collection
     */
    @Override
    public String toString();

    /**
     * return an object capable of visiting each element of the
     * collection exactly once
     *
     * @return an Iterator object
     */
    public Iterator<StudentSpec> createIterator();

    /**
     * determines if the supplied file name exists. If the file name is prefixed with a complete
     * path name, the specific folder/file is checked; if there is no path, the file is checked for
     * in the default working folder. Throws IllegalArgumentException if the supplied file name
     * is null or blank.
     *
     * @param fileName file name
     * @return true if file name already exists
     *
     * @throws IllegalArgumentException if the supplied file name is null or blank.
     */
    public static boolean fileExists( String fileName )
      throws IllegalArgumentException
    {
        boolean result;
        /* determine if a file name was supplied or not */
        if ( fileName == null )
        {
            throw new IllegalArgumentException("File name of 'null' supplied, " +
              "cannot determine if the file exists");
        }
        String localFileName = fileName.trim();
        if ( localFileName.length() == 0 )
        {
            throw new IllegalArgumentException("Blank name supplied, cannot " +
              "determine if the file exists");
        }

        /* create a File object in order to determine if the specified file name exists */
        File theFile = new File(localFileName);
        result = theFile.exists();

        return result;
    }


    /**
     * reads the contents of the supplied file into a StudentCollectionSpec-type object; the
     * file must have been previously written via Serializable method(s). Exception will be thrown
     * if the supplied file name is null or blank, does not exist, does not contain a
     * StudentCollectionSpec-type object, or has a serialVersionUID other than that of the class
     * version reading the file.
     *
     * @param filename the file to be read
     *
     * @throws IllegalArgumentException if the supplied file name is null or blank.
     * @throws StudentCollectionException if the file does not exist, does not contain a
     * StudentCollectionSpec-type object, has a serialVersionUID other than that of the class
     * version reading the file, or contains non-serialized data.
     */
    public void retrieveCollectionFromDisk( String filename )
      throws IllegalArgumentException, StudentCollectionException;

    /**
     * writes the contents of this StudentCollectionSpec object to the supplied file. The file will
     * be overwritten if it already exists - use this class' fileExists() method to prevent
     * overwriting if appropriate. Exception will be thrown if the supplied file name is null or
     * blank or if the file cannot be written to because of system issues.
     *
     * @param filename the file to be written
     *
     * @throws IllegalArgumentException if the supplied file name is null or blank.
     * @throws StudentCollectionException if the file cannot be written to because of system issues.
     */
    public void writeCollectionToDisk( String filename )
      throws IllegalArgumentException, StudentCollectionException;

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /**
     * Specialized RuntimeException used to report exceptions thrown by classes
     * implementing StudentCollectionSpec.
     *
     * <p>Copyright: (c) 2016</p>
     *
     * @author J S Kasprzyk
     * @version 1.6
     */
    public class StudentCollectionException extends RuntimeException
    {
        private static final long serialVersionUID = 8157997298448463708L;

        /* constructor that accepts a direct reason why the exception is to be created. */
        public StudentCollectionException(String cause)
        {
            super(cause);
        }

        /* constructor that accepts an indirect reason why the exception is created,
         * along with the direct reason (cause) of the exception. */
        public StudentCollectionException(String cause, Throwable causalException)
        {
            super(cause, causalException);
        }

    }

}