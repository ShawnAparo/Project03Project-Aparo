package studentcollection;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import student.Student;
import studentspec.StudentSpec;

/**
 *
 * @author joe
 */
public class StudentCollectionTest
{

    final String[] SID =
    {
        null,
        "0000001",
        "0000002",
        "0000003",
        "0000004",
        "0000005"
    };

    final String[] firstName =
    {
        null,
        "Bingo",
        "Snoopy",
        "Clarence",
        "Chrysler",
        "Jeremiah"
    };

    final String[] middleInitial =
    {
        null,
        "D",
        "",
        "H",
        "J",
        "B"
    };

    final String[] lastName =
    {
        null,
        "Schwartz",
        "Ross",
        "Poore",
        "Kimmel",
        "Bullfrog"
    };

    String[] major =
    {
        null,
        "BIO",
        "CHE",
        "PHY",
        "MAT",
        "CSC"
    };

    public StudentCollectionTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testCreate_0Args()
    {
        //---------------------------------------------------
        //testing negative capacity

        //confirming the object has been instantiated
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        assertNotNull(instance);

        //confirming the capacity is equal to the default
        int expCapacity = StudentCollectionSpec.DEFAULT_STUDENT_COLLECTION_CAPACITY;
        int resultCapacity = instance.getCapacity();
        assertEquals(expCapacity, resultCapacity);

        //confirming the collection is empty
        boolean expEmpty = true;
        boolean resultEmpty = instance.isEmpty();
        assertEquals(expEmpty, resultEmpty);

        //confirming the collection is not full
        boolean expFull = false;
        boolean resultFull = instance.isFull();
        assertEquals(expFull, resultFull);

        //confirming the student count is zero
        int expCount = 0;
        int resultCount = instance.getStudentCount();
        assertEquals(expCount, resultCount);

        //confirming the remaining spaces is equal to the default capacity
        int expRemaining = StudentCollectionSpec.DEFAULT_STUDENT_COLLECTION_CAPACITY;
        int resultRemaining = instance.getSpacesRemaining();
        assertEquals(expRemaining, resultRemaining);
    }

    @Test
    public void testCreate_1Arg()
    {
        StudentCollectionSpec instance;
        StudentCollectionSpec.StudentCollectionException thrownException;

        //--------------------------------------
        //testing negative capacity
        try
        {
            instance = StudentCollection.createStudentCollection(-1);
            thrownException = null;

        }
        catch ( StudentCollectionSpec.StudentCollectionException casualException )
        {
            instance = null;
            thrownException = casualException;
        }

        //--------------------------------------
        //testing zero capacity
        try
        {
            instance = StudentCollection.createStudentCollection(0);
            thrownException = null;

        }
        catch ( StudentCollectionSpec.StudentCollectionException casualException )
        {
            instance = null;
            thrownException = casualException;
        }

        //--------------------------------------
        //testing capacity of 1
        try
        {
            instance = StudentCollection.createStudentCollection(1);
            thrownException = null;

        }
        catch ( StudentCollectionSpec.StudentCollectionException casualException )
        {
            instance = null;
            thrownException = casualException;
        }

        //establishing expected values
        int expCapacity = 1;
        int expRemaining = 1;
        boolean expEmpty = true;
        boolean expFull = false;

        //confirming handle is not null, thrownException is null
        assertNull(thrownException);
        assertNotNull(instance);

        //confirming collection is not full, is empty, has capacity of 1, and remaining spaces is 1
        assertEquals(expCapacity, instance.getCapacity());
        assertEquals(expRemaining, instance.getSpacesRemaining());
        assertEquals(expEmpty, instance.isEmpty());
        assertEquals(expFull, instance.isFull());
    }

    /**
     * Test of getCapacity method, of class StudentCollection.
     */
    @Test
    public void testGetCapacity()
    {
        System.out.println("getCapacity");

        //instatiating StudentCollection with default capacity
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();

        //confirming collection is empty
        boolean expEmpty = true;
        boolean resultEmpty = instance.isEmpty();
        assertEquals(expEmpty, resultEmpty);

        //confirming capacity is equal to default capacity
        int expResult = StudentCollectionSpec.DEFAULT_STUDENT_COLLECTION_CAPACITY;
        int result = instance.getCapacity();
        assertEquals(expResult, result);

        //--------------------------------------
        //instantiating StudentCollection with defined capacity
        StudentCollectionSpec instance2 = StudentCollection.createStudentCollection(5);

        //confirming collection is empty
        expEmpty = true;
        resultEmpty = instance2.isEmpty();
        assertEquals(expEmpty, resultEmpty);

        //confirming capacty is equal to defined capacity
        expResult = 5;
        result = instance2.getCapacity();
        assertEquals(expResult, result);
    }

    /**
     * Test of reset method, of class StudentCollection.
     */
    @Test
    public void testReset()
    {
        System.out.println("reset");
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2]);
        StudentSpec student3 = Student.create(SID[3], firstName[3], middleInitial[3], lastName[3],
          major[3]);
        StudentSpec student4 = Student.create(SID[4], firstName[4], middleInitial[4], lastName[4],
          major[4]);
        StudentSpec student5 = Student.create(SID[5], firstName[5], middleInitial[5], lastName[5],
          major[5]);

        instance.addStudent(student1);
        instance.addStudent(student2);
        instance.addStudent(student3);
        instance.addStudent(student4);
        instance.addStudent(student5);

        instance.reset();

        int expResult = 0;
        int result = instance.getStudentCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of isFull method, of class StudentCollection.
     */
    @Test
    public void testIsFull()
    {
        System.out.println("isFull");
        StudentCollectionSpec instance = StudentCollection.createStudentCollection(5);
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2]);
        StudentSpec student3 = Student.create(SID[3], firstName[3], middleInitial[3], lastName[3],
          major[3]);
        StudentSpec student4 = Student.create(SID[4], firstName[4], middleInitial[4], lastName[4],
          major[4]);
        StudentSpec student5 = Student.create(SID[5], firstName[5], middleInitial[5], lastName[5],
          major[5]);

        //--------------------------------------
        //confirm empty StudentCollection is not full
        boolean expResult = false;
        boolean result = instance.isFull();
        assertEquals(expResult, result);

        //--------------------------------------
        //confirm StudentCollection is not full after 1 student has been added
        instance.addStudent(student1);
        expResult = false;
        result = instance.isFull();
        assertEquals(expResult, result);

        //--------------------------------------
        //confirm StudentCollection is not full after 4 students have been added
        instance.addStudent(student2);
        instance.addStudent(student3);
        instance.addStudent(student4);
        expResult = false;
        result = instance.isFull();
        assertEquals(expResult, result);

        //--------------------------------------
        //confirm StudentCollection is full when 5th student is added
        instance.addStudent(student5);
        expResult = true;
        result = instance.isFull();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEmpty method, of class StudentCollection.
     */
    @Test
    public void testIsEmpty()
    {
        System.out.println("isEmpty");
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);

        //--------------------------------------
        //confirm StudentObject is empty
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);

        //--------------------------------------
        //add Student to StudentCollection and confirm StudentCollection is not empty
        instance.addStudent(student1);
        expResult = false;
        result = instance.isEmpty();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStudentCount method, of class StudentCollection.
     */
    @Test
    public void testGetStudentCount()
    {
        System.out.println("getStudentCount");
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);

        //--------------------------------------
        //confirm empty StudentCollection count is zero
        int expResult = 0;
        int result = instance.getStudentCount();
        assertEquals(expResult, result);

        //--------------------------------------
        //confirm StudentCollection count is 1 after adding Student
        instance.addStudent(student1);
        expResult = 1;
        result = instance.getStudentCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of addStudent method, of class StudentCollection.
     */
    @Test
    public void testAddStudent()
    {
        System.out.println("addStudent");

        StudentCollectionSpec.StudentCollectionException thrownException = null;

        //--------------------------------------
        //Initialize a StudentCollection of size 3. Add first Student to collection
        StudentCollectionSpec instance = StudentCollection.createStudentCollection(3);

        instance.addStudent(Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]));

        assertEquals(1, instance.getStudentCount());

        assertFalse(instance.isEmpty());

        assertFalse(instance.isFull());

        assertEquals(2, instance.getSpacesRemaining());

        assertTrue(( instance.retrieveStudentBySID(SID[1]) != null ) &&
          ( instance.retrieveStudentBySID(SID[1]).getSID().equals(SID[1]) ));

        //--------------------------------------
        //Adding a second student with a using SID2 to the collection
        instance.addStudent(Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2]));

        assertEquals(2, instance.getStudentCount());

        assertFalse(instance.isEmpty());

        assertFalse(instance.isFull());

        assertEquals(1, instance.getSpacesRemaining());

        assertTrue(( instance.retrieveStudentBySID(SID[2]) != null ) &&
          ( instance.retrieveStudentBySID(SID[2]).getSID().equals(SID[2]) ));
        assertTrue(( instance.retrieveStudentBySID(SID[1]) != null ) &&
          ( instance.retrieveStudentBySID(SID[1]).getSID().equals(SID[1]) ));

        //--------------------------------------
        //Adding a third student using SID3 to the collection
        instance.addStudent(Student.create(SID[3], firstName[3],
          middleInitial[3], lastName[3], major[3]));

        assertEquals(3, instance.getStudentCount());

        assertFalse(instance.isEmpty());

        assertTrue(instance.isFull());

        assertEquals(0, instance.getSpacesRemaining());

        assertTrue(( instance.retrieveStudentBySID(SID[3]) != null ) &&
          ( instance.retrieveStudentBySID(SID[3]).getSID().equals(SID[3]) ));

        //--------------------------------------
        //Adding a fourth student using SID4 to the collection
        //Confirming StudentCollectionException is thrown
        try
        {
            instance.addStudent(Student.create(SID[4],
              firstName[4], middleInitial[4], lastName[4], major[4]));
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //--------------------------------------
        //Empty the Student Collection
        instance.reset();

        try
        {
            instance.addStudent(null);
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            thrownException = e;
        }
        assertNotNull(thrownException);
        assertEquals(0, instance.getStudentCount());
        assertTrue(instance.isEmpty());
        assertFalse(instance.isFull());
        assertEquals(3, instance.getSpacesRemaining());
    }

    /**
     * Test of retrieveStudentBySID method, of class StudentCollection.
     */
    @Test
    public void testRetrieveStudentBySID()
    {
        System.out.println("retrieveStudentBySID");

        //Create StudentCollection and Student objects. Add Student to Collection
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        StudentSpec expStudent = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1],
          100.00, 350.00);
        instance.addStudent(expStudent);

        //-------------------------------------------------
        //confirm that Student SID matches expected SID
        String expSID = SID[1];
        String resultSID = expStudent.getSID();

        assertEquals(expSID, resultSID);

        //confirm that Student can be retrieved and retrieved Student is as expected
        StudentSpec resultStudent;
        resultStudent = instance.retrieveStudentBySID(resultSID);
        expSID = resultStudent.getSID();

        assertNotNull(resultStudent);
        assertEquals(expSID, resultSID);

        //-------------------------------------------------
        //confirm Student object is not returned if empty string is passed
        resultStudent = instance.retrieveStudentBySID("");
        assertNull(resultStudent);

        //-------------------------------------------------
        //confirm Student object is not returned if invalid string is passed
        String invalidSID = "9999999";
        resultStudent = instance.retrieveStudentBySID(invalidSID);
        assertNull(resultStudent);

        //-------------------------------------------------
        //confirm IllegalArgumentException is thrown if null search key is passed
        IllegalArgumentException anException = null;

        try
        {
            instance.retrieveStudentBySID(null);
        }
        catch ( IllegalArgumentException e )
        {
            anException = e;
        }

        assertNotNull(anException);
    }

    /**
     * Test of removeStudentBySID method, of class StudentCollection.
     */
    @Test
    public void testRemoveStudentBySID()
    {
        System.out.println("removeStudentBySID");

        StudentCollectionSpec instance;

        StudentSpec student1 = Student.create(SID[1], firstName[1],
          middleInitial[1], lastName[1], major[1], 100.00, 350.00);

        StudentSpec student2 = Student.create(SID[2], firstName[2],
          middleInitial[2], lastName[2], major[2], 100.00, 350.00);

        StudentSpec resultStudent;

        String expSID;

        String resultSID;

        //-------------------------------------------
        //Add one Student to a Collection. Remove student and confirm collection is empty
        instance = StudentCollection.createStudentCollection();
        instance.addStudent(student1);

        expSID = student1.getSID();
        resultStudent = instance.removeStudentBySID(expSID);

        resultSID = resultStudent.getSID();

        assertEquals(expSID, resultSID);
        assertTrue(instance.isEmpty());

        //-------------------------------------------
        //Add both Students to a Collection. Remove one student.
        //Confirm removed Student is as expected. Confirm second student was not removed.
        instance = StudentCollection.createStudentCollection();
        instance.addStudent(student1);
        instance.addStudent(student2);

        expSID = student1.getSID();
        resultStudent = instance.removeStudentBySID(expSID);

        resultSID = resultStudent.getSID();

        assertEquals(expSID, resultSID);
        assertTrue(!instance.isEmpty());

        //-------------------------------------------
        //Confirm passing empty string does not remove student
        instance = StudentCollection.createStudentCollection();
        instance.addStudent(student1);

        resultStudent = instance.removeStudentBySID("");

        assertNull(resultStudent);
        assertEquals(1, instance.getStudentCount());
        assertTrue(!instance.isEmpty());

        //-------------------------------------------
        //Confirm passing null value does not remove student
        instance = StudentCollection.createStudentCollection();
        instance.addStudent(student1);

        resultStudent = instance.removeStudentBySID(null);

        assertNull(resultStudent);
        assertEquals(1, instance.getStudentCount());
        assertTrue(!instance.isEmpty());
    }

    /**
     * Test of toString method, of class StudentCollection.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        StudentSpec student1, student2;

        // scenario 1
        // Empty Collection
        StudentCollectionSpec instance = StudentCollection.createStudentCollection(2);
        String expResult = "empty collection";
        String result = instance.toString();
        assertEquals(expResult, result);

        // scenario 2
        // in same collection, add two student objects
        student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1], major[1],
          100.0, 350.0);
        instance.addStudent(student1);
        student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2], major[2],
          100.0, 350.0);
        instance.addStudent(student2);
        expResult = String.format("%s|%s|%s|%s|%s|%.1f|%.1f",
          SID[1],
          lastName[1],
          firstName[1],
          middleInitial[1],
          major[1],
          100.0,
          350.0) +
          "&" +
          String.format("%s|%s|%s|%s|%s|%.1f|%.1f",
            SID[2],
            lastName[2],
            firstName[2],
            middleInitial[2],
            major[2],
            100.0,
            350.0);
        result = instance.toString();

        assertEquals(expResult, result);
    }

    /**
     * Test of createIterator method, of class StudentCollection.
     */
    @Test
    public void testCreateIterator()
    {
        System.out.println("createIterator");

        //create four Student Objects
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1], 100.00, 350.00);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2], 100.00, 350.00);
        StudentSpec student3 = Student.create(SID[3], firstName[3], middleInitial[3], lastName[3],
          major[3], 100.00, 350.00);
        StudentSpec student4 = Student.create(SID[3], firstName[3], middleInitial[3], lastName[3],
          major[3], 100.00, 350.00);

        //create reference collection & insert Students
        StudentCollectionSpec instance = StudentCollection.createStudentCollection(4);

        instance.addStudent(student1);
        instance.addStudent(student2);
        instance.addStudent(student3);
        instance.addStudent(student4);

        //create a checklist collection & insert Students
        ArrayList<StudentSpec> studentArray = new ArrayList();
        studentArray.add(student1);
        studentArray.add(student2);
        studentArray.add(student3);
        studentArray.add(student4);

        //invoke createIterator via reference collection
        Iterator<StudentSpec> studentIterator = instance.createIterator();

        //-------------------------------------------
        //confirm iterator visits each element in collection
        assertNotNull(studentIterator);

        StudentSpec currentStudent;

        int numOfStudents = instance.getStudentCount();
        int counter = 0;

        assertTrue(studentIterator.hasNext());

        while ( counter < numOfStudents )
        {
            currentStudent = studentIterator.next();
            assertTrue(studentArray.contains(currentStudent));
            studentArray.remove(currentStudent);
            counter++;
        }

        assertEquals(counter, numOfStudents);
        assertTrue(studentArray.isEmpty());
        assertFalse(studentIterator.hasNext());

        NoSuchElementException thrownException = null;

        try
        {
            currentStudent = studentIterator.next();
        }
        catch ( NoSuchElementException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //-------------------------------------------
        //confirm iterator will not iterate over empty collection
        instance = StudentCollection.createStudentCollection(4);

        currentStudent = null;

        thrownException = null;

        assertNotNull(studentIterator);
        assertFalse(studentIterator.hasNext());

        try
        {
            currentStudent = studentIterator.next();
        }
        catch ( NoSuchElementException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //-------------------------------------------
        //confirm iterator remove operation is unsupported when collection is full
        instance = StudentCollection.createStudentCollection(5);

        student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1], 100.00, 350.00);
        student3 = Student.create(SID[3], firstName[3], middleInitial[3], lastName[3],
          major[3], 100.00, 350.00);
        StudentSpec student5 = Student.create(SID[5], firstName[5], middleInitial[5], lastName[5],
          major[5], 100.00, 350.00);

        instance.addStudent(student1);
        instance.addStudent(student3);
        instance.addStudent(student5);

        assertNotNull(studentIterator);

        UnsupportedOperationException thrownException2 = null;

        try
        {
            studentIterator.remove();
        }
        catch ( UnsupportedOperationException e )
        {
            thrownException2 = e;
        }

        assertNotNull(thrownException2);

        //-------------------------------------------
        //confirm iterator remove operation is unsupported when collection is empty
        instance = StudentCollection.createStudentCollection();

        thrownException2 = null;

        try
        {
            studentIterator.remove();
        }
        catch ( UnsupportedOperationException e )
        {
            thrownException2 = e;
        }

        assertNotNull(thrownException2);
    }

    /**
     * Test of getSpacesRemaining method, of class StudentCollection.
     */
    @Test
    public void testGetSpacesRemaining()
    {
        System.out.println("getSpacesRemaining");

        StudentCollectionSpec instance;
        int initialCapacity = 5;
        int expSpaces;
        int resultSpaces;

        StudentSpec student1 = Student.create(SID[1], firstName[1],
          middleInitial[1], lastName[1], major[1], 100.00, 350.00);
        StudentSpec student2 = Student.create(SID[2], firstName[2],
          middleInitial[2], lastName[2], major[2], 100.00, 350.00);
        StudentSpec student3 = Student.create(SID[3], firstName[3],
          middleInitial[3], lastName[3], major[3], 100.00, 350.00);
        StudentSpec student4 = Student.create(SID[4], firstName[4],
          middleInitial[4], lastName[4], major[4], 100.00, 350.00);
        StudentSpec student5 = Student.create(SID[5], firstName[5],
          middleInitial[5], lastName[5], major[5], 100.00, 350.00);

        //-------------------------------------
        //Confirm empty collection has spaces remaining equal to initial capacity
        instance = StudentCollection.createStudentCollection(initialCapacity);

        expSpaces = initialCapacity;
        resultSpaces = instance.getSpacesRemaining();

        assertEquals(expSpaces, resultSpaces);

        //-------------------------------------
        //Add one Student to collection. Confirm remaining spaces equals initial capacity less one
        instance = StudentCollection.createStudentCollection(initialCapacity);
        instance.addStudent(student1);

        expSpaces = initialCapacity - 1;
        resultSpaces = instance.getSpacesRemaining();

        assertEquals(expSpaces, resultSpaces);

        //-------------------------------------
        //Fill collection. Confirm spaces remaining is zero.
        instance = StudentCollection.createStudentCollection(initialCapacity);
        instance.addStudent(student1);
        instance.addStudent(student2);
        instance.addStudent(student3);
        instance.addStudent(student4);
        instance.addStudent(student5);

        expSpaces = 0;
        resultSpaces = instance.getSpacesRemaining();

        assertEquals(expSpaces, resultSpaces);
    }

    /**
     * Test of fileExists method, of class StudentCollection.
     */
    @Test
    public void testFileExists()
    {
        System.out.println("fileExists");
        StudentCollectionSpec instance = StudentCollection.createStudentCollection(2);
        String fileName;
        Exception thrownException;

        boolean expFileExists = false;
        boolean resultFileExists = false;

        //-------------------------------
        //confirming null file name throws IllegalArgumentException
        fileName = null;
        thrownException = null;

        try
        {
            instance.writeCollectionToDisk(fileName);
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //-------------------------------
        //confirming empty string file name throws IllegalArgumentException
        fileName = "";
        thrownException = null;

        try
        {
            instance.writeCollectionToDisk(fileName);
            thrownException = null;
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //-------------------------------
        //confirming file exists after writing collection to disk
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1], 100.0, 350.0);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2], 100.0, 350.0);

        instance.addStudent(student1);
        instance.addStudent(student2);

        fileName = "fileExistsCollection.txt";

        instance.writeCollectionToDisk(fileName);

        expFileExists = true;
        resultFileExists = StudentCollectionSpec.fileExists(fileName);
        assertEquals(expFileExists, resultFileExists);

        //-------------------------------
        //confirming fake filename does not exists
        fileName = "notAFileThatExists.txt";
        expFileExists = false;
        resultFileExists = StudentCollectionSpec.fileExists(fileName);
        assertEquals(expFileExists, resultFileExists);

    }

    /**
     * Test of writeCollectionToDisk method, of class StudentCollection.
     */
    @Test
    public void testWriteCollectionToDisk()
    {
        System.out.println("writeCollectionToDisk");
        String targetFileName = null;
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        Exception thrownException = null;

        //---------------------------------------
        //confirming null file name throws IllegalArgumentException
        try
        {
            instance.writeCollectionToDisk(targetFileName);
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //---------------------------------------
        //confirming empty string file name throws IllegalArgumentException
        thrownException = null;
        targetFileName = "";

        try
        {
            instance.writeCollectionToDisk(targetFileName);
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }
        assertNotNull(thrownException);

        //---------------------------------------
        //confirming contents can be written to and read from file
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2], lastName[2],
          major[2]);

        StudentCollectionSpec referenceCollection = StudentCollection.createStudentCollection(5);
        StudentCollectionSpec checklistCollection = StudentCollection.createStudentCollection(5);

        targetFileName = "referenceCollection.txt";
        thrownException = null;

        referenceCollection.addStudent(student1);
        referenceCollection.addStudent(student2);

        checklistCollection.addStudent(student1);
        checklistCollection.addStudent(student2);

        //confirming no exception thrown when written to disk
        try
        {
            referenceCollection.writeCollectionToDisk(targetFileName);

        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            thrownException = e;
        }
        assertNull(thrownException);

        //confirming file exists
        boolean expectedFileExists = true;
        boolean resultFileExists = StudentCollectionSpec.fileExists(targetFileName);

        assertEquals(expectedFileExists, resultFileExists);

        //creating retrieved collection of size 10
        StudentCollectionSpec retrievedCollection = StudentCollection.createStudentCollection(10);

        //invoke retrieveCollectionFromDisk()
        thrownException = null;
        try
        {
            retrievedCollection.retrieveCollectionFromDisk(targetFileName);

        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            thrownException = e;
        }
        assertNull(thrownException);

        //creating iterator and removing students from checklist collection
        Iterator<StudentSpec> studentIterator = retrievedCollection.createIterator();
        StudentSpec currentStudent;
        thrownException = null;

        int counter = 0;

        while ( counter < retrievedCollection.getStudentCount() )
        {
            currentStudent = studentIterator.next();

            try
            {
                checklistCollection.removeStudentBySID(currentStudent.getSID());

            }
            catch ( StudentCollectionSpec.StudentCollectionException e )
            {
                thrownException = e;
            }
            counter++;
        }

        //confirming referenceCollection count == retrievedCollection count
        int expStudentCount = 2;
        int resultStudentCount = retrievedCollection.getStudentCount();
        assertEquals(expStudentCount, resultStudentCount);

        //confirming checklistCollection count == 0
        expStudentCount = 0;
        resultStudentCount = checklistCollection.getStudentCount();
        assertEquals(expStudentCount, resultStudentCount);

    }

    /**
     * Test of retrieveCollectionFromDisk method, of class StudentCollection.
     */
    @Test
    public void testRetrieveCollectionFromDisk()
    {
        System.out.println("retrieveCollectionFromDisk");
        String targetFileName;
        StudentCollectionSpec instance = StudentCollection.createStudentCollection();
        Exception thrownException;

        //---------------------------------------
        //confirming null file name throws IllegalArgumentException
        targetFileName = null;
        thrownException = null;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }

        assertNotNull(thrownException);

        //---------------------------------------
        //confirming empty string file name throws IllegalArgumentException
        targetFileName = "";
        thrownException = null;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
        }
        catch ( IllegalArgumentException e )
        {
            thrownException = e;
        }
        assertNotNull(thrownException);

        //-------------------------------
        //confirming fake filename does not exist
        targetFileName = "notAFileThatExists.txt";
        boolean expFileExists = false;
        boolean resultFileExists;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
            resultFileExists = true;
            thrownException = null;
        }
        catch ( IllegalArgumentException e )
        {
            resultFileExists = false;
            thrownException = e;
        }

        assertNotNull(thrownException);
        assertEquals(expFileExists, resultFileExists);

        //---------------------------------
        //Confirm that file name exists, but does not contain serialized content
        targetFileName = "bogus.txt";
        boolean expSerialized = false;
        boolean resultSerialized;
        thrownException = null;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
            resultSerialized = true;
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            thrownException = e;
            resultSerialized = false;
        }
        assertNotNull(thrownException);
        assertEquals(expSerialized, resultSerialized);

        //-------------------------------------
        //Confirm retrieving serialized object that is not of type StudentCollection
        //will throw a StudentCollectionException
        thrownException = null;
        targetFileName = "serializedStudent.txt";

        //Create and serialize Student object
        StudentSpec student1 = Student.create(SID[1], firstName[1], middleInitial[1], lastName[1],
          major[1]);
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(targetFileName));
            oos.writeObject(student1);
        }
        catch ( Exception e )
        {
            System.out.println("Unexpected exception in TestRetrieveCollection");
        }
        assertNull(thrownException);

        //Confirm that reading serialized Student to StudentCollection throws exception
        boolean expObjectIsValid = false;
        boolean resultObjectIsValid;
        thrownException = null;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
            resultObjectIsValid = true;
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            resultObjectIsValid = false;
            thrownException = e;
        }

        assertNotNull(thrownException);
        assertEquals(expObjectIsValid, resultObjectIsValid);

        //---------------------------------------
        //Confirm that reading a serialized file of class StudentCollection with
        //different svUID will throw an exception
        instance = StudentCollection.createStudentCollection();
        thrownException = null;
        targetFileName = "differentSVUID.txt";

        /* StudentCollection was written to disk with below statements.
         * SVUID was manually changed in StudentCollection class
         * before writing StudentCollection to disk.
         * try
         * {
         * ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(targetFileName));
         * oos.writeObject(instance);
         * }
         * catch ( Exception e )
         * {
         * System.out.println("Unexpected exception in TestRetrieveCollection");
         * }
         *
         * assertNull(thrownException);
         */
        boolean expSVUIDIsCurrent = false;
        boolean resultSVUIDIsCurrent;

        try
        {
            instance.retrieveCollectionFromDisk(targetFileName);
            resultSVUIDIsCurrent = true;
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            resultObjectIsValid = false;
            thrownException = e;
        }

        assertNotNull(thrownException);
        assertEquals(expObjectIsValid, resultObjectIsValid);

        //---------------------------------------
        //Write contents of collection to a specified file such that the contents can be read back at a later date

        //First, create two students with different SIDs
        student1 = Student.create(SID[1], firstName[1], middleInitial[1],
          lastName[1], major[1]);
        StudentSpec student2 = Student.create(SID[2], firstName[2], middleInitial[2],
          lastName[2], major[2]);

        //create collections, add the two student objects to the reference and checklist collections
        StudentCollectionSpec referenceCollection = StudentCollection.createStudentCollection(5);
        StudentCollectionSpec checklistCollection = StudentCollection.createStudentCollection(5);
        StudentCollectionSpec retrievedCollection = StudentCollection.createStudentCollection(10);

        referenceCollection.addStudent(student1);
        referenceCollection.addStudent(student2);
        checklistCollection.addStudent(student1);
        checklistCollection.addStudent(student2);
        Exception nullException = null;

        //Invoking WriteCollectionToDisk via reference collection
        targetFileName = "ReadTest";
        try
        {
            referenceCollection.writeCollectionToDisk(targetFileName);
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            nullException = e;
            System.out.println("Could not write the StudentCollection to disk");
        }

        //confirm no exception thrown after collection written to disk
        assertNull(nullException);
        //confirm file exists
        assertTrue(StudentCollectionSpec.fileExists(targetFileName));


        //invoke retrieveCollectionFromDisk via the retrieved collection.
        //confirm no exception is thrown
        nullException = null;

        try
        {
            retrievedCollection.retrieveCollectionFromDisk(targetFileName);
        }
        catch ( StudentCollectionSpec.StudentCollectionException e )
        {
            nullException = e;
            System.out.println("Could not retrieve the StudentCollection from disk");
        }

        assertNull(nullException);

        Iterator<StudentSpec> studentIterator = retrievedCollection.createIterator();

        StudentSpec currentStudent;
        StudentSpec removedStudent;

        String currentSID, currentFirstName, currentMiddleInitial, currentLastName, currentMajor;
        String removedSID, removedFirstName, removedMiddleInitial, removedLastName, removedMajor;

        int count = 0;
        while ( studentIterator.hasNext() )
        {
            currentStudent = studentIterator.next();
            removedStudent = checklistCollection.removeStudentBySID(currentStudent.getSID());

            currentSID = currentStudent.getSID();
            currentFirstName = currentStudent.getFirstName();
            currentMiddleInitial = currentStudent.getMiddleInitial();
            currentLastName = currentStudent.getLastName();
            currentMajor = currentStudent.getMajor();

            removedSID = removedStudent.getSID();
            removedFirstName = removedStudent.getFirstName();
            removedMiddleInitial = removedStudent.getMiddleInitial();
            removedLastName = removedStudent.getLastName();
            removedMajor = removedStudent.getMajor();

            assertEquals(currentSID, removedSID);
            assertEquals(currentFirstName, removedFirstName);
            assertEquals(currentMiddleInitial, removedMiddleInitial);
            assertEquals(currentLastName, removedLastName);
            assertEquals(currentMajor, removedMajor);

            count++;
        }

        assertEquals(0, checklistCollection.getStudentCount());
        assertEquals(referenceCollection.getStudentCount(), retrievedCollection.getStudentCount());
        assertEquals(retrievedCollection.getStudentCount(), count);
    }

}
