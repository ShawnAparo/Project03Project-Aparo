package studentcollection;


//<editor-fold defaultstate="collapsed" desc="class-wide notes">
/**
 * Implementation of the studentcollection.StudentCollectionSpec interface. This implementation
 * provides fixed-size storage capacity for storing StudentSpec-type objects. Additional features
 * include the ability to store and/or retrieve a class instance to/from disk and to determine if
 * a file exists.
 */
//</editor-fold>
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.Iterator;
import java.util.NoSuchElementException;

import student.Student;
import studentcollection.StudentCollectionSpec.StudentCollectionException;
import studentspec.StudentSpec;

/**
 * <p>
 * Title: StudentCollection</p>
 * <p>
 * <p>
 * Description: Implementation of studentcollection/StudentCollectionSpecification.
 * Students are stored as an "unordered" collection of values stored in a storage area of specified
 * and finite capacity. Students are initially stored in the order of arrival, and later removals
 * can change the internal ordering. This implementation allows duplicate entries (that is, the
 * collection is not checked to determine if it is already in the collection when addStudent() is
 * invoked).</p>
 * <p>
 * <p>
 * Since the StudentCollectionSpecification which this StudentCollection class implements is
 * silent with respect to collection entry uniqueness, this implementation chooses to allow
 * collection entries which are duplicates of already-existing entries. This means that any two
 * entries might each contain exactly the value; more generally, it means that a given student
 * could be added to the collection more than once.</p>
 * <p>
 * <p>
 * If client software wishes to prevent duplicate entries, it is the responsibility of the client
 * software to determine if a student which is about to be added to the collection is already in the
 * collection: this can be accomplished by client software invoking the retrieveStudentBySID()
 * method and checking the result.</p>
 * <p>
 * <p>
 * Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
public class StudentCollection implements StudentCollectionSpec, Serializable
{
    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    private static final long serialVersionUID = -6102872921808735175L;

    /*
     * local constant, used as a return signal from a PRIVATE method
     */
    private static final int SEARCH_KEY_NOT_FOUND = -1;
    /*
     * handle for the storage array for the objects to be stored
     */
    private StudentSpec[] elements;
    /*
     * index of the next position to be filled
     */
    private int nextEmptyPosition;

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>
    private StudentCollection( int maxSize )
    {
        /*
         * create storage space
         */
        elements = new StudentSpec[maxSize];
        /*
         * fill the array from left to right, starting at position 0
         */
        nextEmptyPosition = 0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * <br><br><b>NOTE: This method hides StudentCollectionSpec.createStudentCollection()</b>
     */
    public static StudentCollectionSpec createStudentCollection()
    {
        return new StudentCollection(DEFAULT_STUDENT_COLLECTION_CAPACITY);
    }

    /**
     * {@inheritDoc}
     * <p>
     * <br><br><b>NOTE: This method hides StudentCollectionSpec.createStudentCollection()</b>
     */
    public static StudentCollectionSpec createStudentCollection( int capacity )
      throws StudentCollectionException
    {
        if ( capacity <= 0 )
        {
            throw new StudentCollectionException("Cannot create a collection " +
              "of capacity <= 0");
        }
        return new StudentCollection(capacity);
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>
    @Override
    public int getCapacity()
    {
        return elements.length;
    }

    @Override
    public void reset()
    {
        //SA inserted looping condition to remove each element.
        for ( int i = 0; i < nextEmptyPosition; i++ )
        {
            elements[i] = null;
        }
        nextEmptyPosition = 0;
    }

    @Override
    public boolean isFull()
    {
        return nextEmptyPosition >= elements.length;
    }

    @Override
    public boolean isEmpty()
    {
        return nextEmptyPosition == 0;
    }

    @Override
    public int getStudentCount()
    {
        return nextEmptyPosition;
    }

    @Override
    public void addStudent( StudentSpec anElement ) throws StudentCollectionException
    {
        /*
         * make sure that there is something to store
         */
        if ( anElement == null )
        {
            throw new StudentCollectionException("Null handle supplied");
        }
        /*
         * check if there is any room for another element
         */

        //SA changed to check if greater than or equal to capacity. If so, expand capacity
        if ( this.getStudentCount() == this.getCapacity() )
        {
            throw new StudentCollectionException("No room in collection " +
              "for another element");
        }

        /*
         * place the incoming student in the next empty position in the array
         */
        elements[nextEmptyPosition] = anElement;

        /*
         * the array position "pointed to" by nextEmptyPosition was just filled, so increment
         * nextEmptyPosition so that it will "point to" the next location
         */
        nextEmptyPosition++;
    }

//    private StudentSpec[] expandArray(Object[] anArray)
//    {
//        /* make the new array 50% bigger than the current array  */
//        int newSize = ( int ) (anArray.length * 1.5 + 0.5);
//        /* create the new array  */
//
//        //***SA Changed to be an array of StudentSpec objects***
//        StudentSpec[] biggerArray = new StudentSpec[newSize];
//
//        /* copy the contents of the current array into the new array   */
//        System.arraycopy(anArray, 0,
//          biggerArray, 0,
//          anArray.length);
//
//        return biggerArray;
//    }
    @Override
    public StudentSpec retrieveStudentBySID( String searchKey ) throws IllegalArgumentException
    {
        //***SA added error if String is null
        if ( searchKey == null )
        {
            throw new IllegalArgumentException("Search key can't be null");
        }

        StudentSpec result = null;
        /*
         * try to locate a student with a matching identifier (key)
         */
        int foundPosition = findPositionWithMatchingKey(searchKey);

        if ( foundPosition != SEARCH_KEY_NOT_FOUND )
        {
            result = elements[foundPosition];
        }

        return result;
    }

    // <editor-fold defaultstate="collapsed" desc="ALTERNATE implementation of retrieveStudentBySID()">
    /* Note - this method provides an ALERNATE IMPLEMENTATION of the previous method, using an
     * Iterator object to examine the contents of the collection rather than manually defining and
     * manipulating an index variable. This implementation will be less efficient than the previous
     * method, but may be more easily understood and maintained as the detailed manipulation logic
     * is embedded in the Iterator implementation.
     */
    // </editor-fold>
//    public StudentSpec retrieveByKeyUsingAnIterator( String searchKey )
//    {
//        if ( searchKey != null )
//        {
//            /* create an iterator object to manage examining the collection  */
//            Iterator<StudentSpec> anIterator = this.createIterator();
//            /* as long as there is another element to look at, get it an look at it */
//            while ( anIterator.hasNext() )
//            {
//                /* get the next element in th ecollection  */
//                StudentSpec aStudent = anIterator.next();
//                /* check if the retrieved element matches the supplied search key */
//                if ( aStudent.getSID().equals(searchKey) )
//                {
//                    return aStudent;
//                }
//            }
//        }
//
//        /* no match was made before running out of elements  */
//        return null;
//    }
    private int findPositionWithMatchingKey( String searchKey )
    {
        //<editor-fold defaultstate="collapsed" desc="notes on the use of 'assert'">
        /*
         * This is a PRIVATE method, so verify that the parameter value is non-null: if it IS null,
         * there is a logic error in the invoking method (which is part of this class!), and program
         * execution should be stopped so that the logic error can be tracked down and fixed.
         *
         * Note the use of "assert" rather than "if" - this is appropriate because this is a PRIVATE
         * method and under our direct control: since we control all of the methods that might
         * invoke THIS method, we can assume that we have previously insured that the supplied
         * search key was non-null - so, if the supplied searchKey IS null, the only explanation is
         * that there's a logic mistake somewhere in this class. If the assert statement evaluates
         * to true, execution continues; if it evaluates to false, then a runtime exception is
         * thrown that should cause program termination.
         */
        //</editor-fold>
        assert searchKey != null;

        int index = 0;

        /*
         * keep examining elements of the array as long as there are filled positions remaining to
         * be examined AND a match has not yet been made
         */
        while ( index < nextEmptyPosition && !elements[index].getSID().equals(searchKey) )
        {
            /*
             * the current position of the array does not have a matching SID - move on to the next
             * position
             */
            index++;
        }

        /*
         * if "index" is less than the next empty position in the collection, the search stopped
         * because a match was made, so return the POSITION where the match was located
         */
        int result;
        if ( index < nextEmptyPosition )
        {
            result = index;
        }
        else
        {
            result = SEARCH_KEY_NOT_FOUND;
        }
        return result;
    }

    @Override
    public StudentSpec removeStudentBySID( String searchKey )
    {
        StudentSpec result = null;
        if ( searchKey != null )
        {
            /*
             * try to locate a student with a matching SID
             */
            int foundPosition = findPositionWithMatchingKey(searchKey);

            if ( foundPosition != SEARCH_KEY_NOT_FOUND )
            {
                StudentSpec temp = elements[foundPosition];

                //if Student is not last in list, last in list is moved to position
                if ( foundPosition < nextEmptyPosition - 1 )
                {
                    elements[foundPosition] = elements[nextEmptyPosition - 1];
                }

                //last in list is deleted
                elements[nextEmptyPosition - 1] = null;

                //***SA ADDED THIS SECTION***
                nextEmptyPosition--;

                result = temp;
            }
        }

        return result;
    }

    //<editor-fold defaultstate="collapsed" desc="explanation of why this method is commented">
    /*
     * This PRIVATE method is an example of a commonly-implemented "utility" method that is helpful
     * during initial testing of a "collection" class - it provides a quick way to display the
     * actual contents of the collection, edited for developer (as opposed to user) readability. It
     * can and almost always does include information that would normally be inadvisable to make
     * available to the "outside world".
     *
     * After testing is completed, methods like this are often deleted. Commenting them has the same
     * affect as removing them in terms of execution efficiency and memory requirements, but offers
     * the added ability to "reactivate" the code in the future if it is needed during an
     * enhancement effort.
     */
    //</editor-fold>
//    private void displayContents()
//    {
//        System.out.println(" Current collection contents");
//        if ( nextEmptyPosition > 0 )
//        {
//            for ( int i = 0; i <
//              nextEmptyPosition; i++ )
//            {
//                System.out.println("  (" + (i + 1) + ") " + elements[i].toString());
//            }
//            System.out.flush();
//        }
//        else
//        {
//            System.out.println("  The collection is currently empty");
//            System.out.flush();
//        }
//    }
    @Override
    public String toString()
    {
        /*
         * define a work area into which all of the entries of the collection will be concanentated.
         */
        StringBuilder result = new StringBuilder(500);
        /*
         * if the collection is empty, return String that explicitly indicates that fact
         */
        if ( nextEmptyPosition == 0 )
        {
            result.append("empty collection");
        }
        else
        {
            /*
             * initialize the work area with the entry in the initial position
             */
            result.append(elements[0].toString());

            /*
             * for all remaining entries, concatenate a delimiter (&) and the entry onto the end of
             * the work area
             */
            for ( int index = 1; index < nextEmptyPosition; index++ )
            {
                result.append('&');
                result.append(elements[index]);
            }
        }
        return result.toString();
    }

    @Override
    public int getSpacesRemaining()
    {
        return elements.length - nextEmptyPosition;
    }

    @Override
    public Iterator<StudentSpec> createIterator()
    {
        return new CollectionIterator();
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>
    /**
     * <p>
     * Title:CollectionIterator</p>
     * <p/>
     * <p>
     * Description: implementation of the standard Iterator functionalities available for
     * manipulating a collection of student objects. This class does not provide an implementation
     * for remove() and will throw an UnsupportedOperationException if remove() is invoked.</p>
     * <p/>
     * <p>
     * <p>
     * Copyright: (c) 2016</p>
     *
     * @author J S Kasprzyk
     * @version 1.6
     */
    private class CollectionIterator implements Iterator<StudentSpec>
    {
        private int nextPositionToVisit;

        CollectionIterator()
        {
            nextPositionToVisit = 0;
        }

        @Override
        public boolean hasNext()
        {
            return nextPositionToVisit < nextEmptyPosition;
        }

        @Override
        public StudentSpec next()
        {
            if ( nextPositionToVisit < nextEmptyPosition )
            {
                StudentSpec temp;
                temp = elements[nextPositionToVisit];
                nextPositionToVisit++;

                return temp;
            }
            else
            {
                throw new NoSuchElementException("No remaining elements in " +
                  "the collection");
            }
        }

        @Override
        public void remove() throws UnsupportedOperationException
        {
            throw new UnsupportedOperationException("Remove is not supported " +
              "for this iterator");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /* methods relating to persisting the collection to disk */
    @Override
    public void writeCollectionToDisk( String targetFileName ) throws
      IllegalArgumentException, StudentCollectionException
    {
        if ( targetFileName == null )
        {
            throw new IllegalArgumentException("File name of 'null' supplied, cannot write");
        }
        String localTargetFileName = targetFileName.trim();
        if ( localTargetFileName.length() == 0 )
        {
            throw new IllegalArgumentException("Blank name supplied, cannot write");
        }

        try
        {

            /* create a File object that will be used to reference the supplied
             * file name. */
            File theFile = new File(localTargetFileName);

            /* each of the next three statements MUST be enclosed in a try/catch structure
             * because each can throw an IOException. */

 /* attempt to create an ObjectOutputStream
             * that "wraps" the created File. */
            ObjectOutputStream theOutputFile =
              new ObjectOutputStream(new FileOutputStream(theFile));
            /* attempt to write the collection to the file. */
            theOutputFile.writeObject(this);
            /* attempt to close the file. */
            theOutputFile.close();
        }
        catch ( IOException causalException )
        {
            /* problem writing information to disk - nothing changes with respect to
             * the collection. */
            throw new StudentCollectionException("Problem writing to file" +
              localTargetFileName + ": " + causalException.getMessage(), causalException);
        }
    }

    @Override
    public void retrieveCollectionFromDisk( String sourceFileName )
      throws IllegalArgumentException, StudentCollectionException
    {
        if ( sourceFileName == null )
        {
            throw new IllegalArgumentException("(IllegalArgumentException) File name of \"null\" " +
              "supplied, cannot read");
        }
        String localSourceFileName = sourceFileName.trim();
        if ( localSourceFileName.length() == 0 )
        {
            throw new IllegalArgumentException("(IllegalArgumentException) Blank name supplied, " +
              "cannot read");
        }
        /* define a new (temporary) collection */
        StudentCollection collectionBeingRead;
        /* create the file object */
        File theFile = new File(localSourceFileName);

        /* determine if the file exists */
        if ( theFile.exists() )
        {
            try
            {
                /* "wrap" the file in an ObjectInputStream */
                ObjectInputStream theInputFile =
                  new ObjectInputStream(new FileInputStream(theFile));

                /* read the collection from the file into the TEMPORARY collection */
                collectionBeingRead = ( StudentCollection ) theInputFile.readObject();
                /* close the file */
                theInputFile.close();

                /* copy the contents of the TEMPORARY collection into the current ("this")
                 * collection. */
                this.nextEmptyPosition = collectionBeingRead.nextEmptyPosition;
                this.elements = collectionBeingRead.elements;
            }
            catch ( InvalidClassException causalException )
            {
                /* the file does not contain a collection object that is compatible with
                 * this class - nothing changes with respect to the collection */
                throw new StudentCollectionException(
                  "(InvalidClassException) Specified file contains " +
                  "an object of class StudentCollection but with a different SVUID, or StudentCollection " +
                  "does not have an accessible no-arg constructor - " +
                  causalException.getMessage(), causalException);
            }
            catch ( ClassCastException causalException )
            {
                /* the file does not contain a collection object that is compatible with
                 * this class - nothing changes with respect to the collection */
                throw new StudentCollectionException(
                  "(ClassCastException) Specified file contains " +
                  "an object of a class other than StudentCollection - " +
                  causalException.getMessage(), causalException);
            }
            catch ( ClassNotFoundException causalException )
            {
                /* the file does not contain a collection object that is compatible with
                 * this class - nothing changes with respect to the collection */
                throw new StudentCollectionException("(ClassNotFoundException) Specified file " +
                  "contains an object of an unknown class type - " +
                  causalException.getMessage(), causalException);
            }
            catch ( IOException causalException )
            {
                /* problem reading information from disk - nothing changes with respect
                 * to the collection */
                throw new StudentCollectionException("(IOException) Problem reading from file - " +
                  causalException.getMessage(), causalException);
            }
        }
        else
        {
            /* file not found - nothing changes */
            throw new IllegalArgumentException("(FileDoesNotExist) Problem reading from file: " +
              localSourceFileName + ": file does not exist");
        }
    }

}
