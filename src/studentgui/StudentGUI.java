package studentgui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import student.Student;
import studentspec.StudentSpec;
import studentspec.StudentSpec.StudentValidatorSpec;

import static studentspec.StudentSpec.ValidationStatus.*;

import studentcollection.StudentCollection;

import studentcollection.StudentCollectionSpec;
import studentcollection.StudentCollectionSpec.StudentCollectionException;


/**
 * User interface designed to allow a user to manage the contents of a collection of students. The
 * following functionalities are provided:
 *   - retrieve an existing collection from disk, save and save as current collection to disk,
 *     display the contents of the current collection
 *   - create student (automatically stored into current collection)
 *   - update a student currently in the collection (user must select (double-click) a student
 *     currently in the collection)
 *   - delete a student currently in the collection (user must provide SID of targeted student)
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
@SuppressWarnings("serial")
public class StudentGUI extends JFrame implements ListSelectionListener
{
    /* flag that determines if diagnostics / informational displays are to be produced.
     * Note that the displays themselves have been "hidden" in folded code blocks labeled
     * as "diagnostics"   */
    private final boolean diagnostics = false;

    /* create a JList-focused object for displaying the contents of a collection */
    private DisplayObjectsAsJList studentList;

    /* collection object used to store students   */
    private final StudentCollectionSpec roster = StudentCollection.createStudentCollection(20);

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /* main panel, which will contain student, error and button panels. Managed with
     * BorderLayout.   */
    private JPanel mainPanel;

    // <editor-fold defaultstate="collapsed" desc="---------------">
    /* */
    // </editor-fold>

    /* student information panel, which will contain a set of labels and text areas
     * designed to collect information for a new student. Managed with GridLayout.   */
    private JPanel studentInfoPanel;

    private JTextField sidTextField;
    private JTextField firstNameTextField;
    private JTextField middleInitialTextField;
    private JTextField lastNameTextField;
    private JTextField majorTextField;
    private JTextField TDCTextField;
    private JTextField TQPTextField;
    private JLabel sidLabel;
    private JLabel firstNameLabel;
    private JLabel middleInitialLabel;
    private JLabel lastNameLabel;
    private JLabel majorLabel;
    private JLabel TDCLabel;
    private JLabel TQPLabel;
    private JLabel sidInstructions;
    private JLabel firstNameInstructions;
    private JLabel middleInitialInstructions;
    private JLabel lastNameInstructions;
    private JLabel majorInstructions;
    private JLabel TDCInstructions;
    private JLabel TQPInstructions;

    // <editor-fold defaultstate="collapsed" desc="---------------">
    /* */
    // </editor-fold>

    /* error panel, which will contain a creation (instantiation) error
     * message if one is warranted. Managed with FlowLayout.   */
    private JPanel errorPanel;

    private JTextArea errorTextArea;

    // <editor-fold defaultstate="collapsed" desc="---------------">
    /* */
    // </editor-fold>

    /* general button panel, containing two subpanels (studentDisplay and file access).
     * Managed with BorderLayout.   */
    private JPanel buttonPanel;

    // <editor-fold defaultstate="collapsed" desc="---------------">
    /* */
    // </editor-fold>

    /* student action panel, containing buttons to create, update, or delete individual
     * students. Managed with FlowLayout.   */
    private JPanel studentActionPanel;

    private JButton createStudentButton;
    private JButton deleteStudentButton;
    private JButton updateStudentButton;
    private JButton clearAllFieldsButton;

    // <editor-fold defaultstate="collapsed" desc="---------------">
    /* */
    // </editor-fold>

    /* collection action panel, containing buttons to retrieve a collection from disk,
     * write a collection to disk, or display a collection. Managed with FlowLayout.   */
    private JPanel collectionActionPanel;

    private JButton retrieveButton;
    private JLabel fileBeingProcessedLabel;
    private String fileBeingProcessed = "Untitled";
    private JButton saveButton;
    private JButton showAllButton;

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    public StudentGUI()
    {
        super("Student collection Graphical User Interface");
        initializeStudentGUI();
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private void initializeStudentGUI()
    {
        Container aContainer = this.getContentPane();
        aContainer.setLayout(new FlowLayout());

        mainPanel = createMainPanel();
        aContainer.add(mainPanel);

        this.setMinimumSize(new Dimension(900, 450));
        /* place this JFrame in the center of the screen  */
        this.setLocationRelativeTo(null);
        this.pack();

        /* set up to automatically provide a user a final opportunity to write the
         * collection to disk by creating an object that will "listen" for this JFrame's
         * "close" event and interact with the user before allowing the JFrame to
         * finish closing  */
        this.addWindowListener(new CleanUpWhenClosing());
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private JPanel createMainPanel()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 10));

        createStudentDisplayPanel();
        mainPanel.add(studentInfoPanel, BorderLayout.NORTH);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        createErrorPanel();

        return mainPanel;
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private void createStudentDisplayPanel()
    {
        studentInfoPanel = new JPanel();
        studentInfoPanel.setLayout(new GridLayout(7, 3, 10, 10));

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        sidLabel = new JLabel("SID");
        sidLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(sidLabel, 0);
        sidTextField = new JTextField();
        studentInfoPanel.add(sidTextField, 1);
        sidInstructions = new JLabel("enter exactly 7 digits");
        studentInfoPanel.add(sidInstructions, 2);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(firstNameLabel, 3);
        firstNameTextField = new JTextField();
        studentInfoPanel.add(firstNameTextField, 4);
        firstNameInstructions = new JLabel("at least one non-blank character");
        studentInfoPanel.add(firstNameInstructions, 5);

        middleInitialLabel = new JLabel("Middle Initial");
        middleInitialLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(middleInitialLabel, 6);
        middleInitialTextField = new JTextField();
        studentInfoPanel.add(middleInitialTextField, 7);
        middleInitialInstructions = new JLabel(
          "zero or one character (will truncate to 1 if longer)");
        studentInfoPanel.add(middleInitialInstructions, 8);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(lastNameLabel, 9);
        lastNameTextField = new JTextField();
        studentInfoPanel.add(lastNameTextField, 10);
        lastNameInstructions = new JLabel("at least one non-blank character");
        studentInfoPanel.add(lastNameInstructions, 11);

        majorLabel = new JLabel("Major");
        majorLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(majorLabel, 12);
        majorTextField = new JTextField();
        studentInfoPanel.add(majorTextField, 13);
        majorInstructions = new JLabel("three letter code");
        studentInfoPanel.add(majorInstructions, 14);

        TDCLabel = new JLabel("Total Degree Credits");
        TDCLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(TDCLabel, 15);
        TDCTextField = new JTextField();
        studentInfoPanel.add(TDCTextField, 16);
        TDCInstructions = new JLabel("numeric, single decimal value");
        studentInfoPanel.add(TDCInstructions, 17);

        TQPLabel = new JLabel("Total Quality Points");
        TQPLabel.setHorizontalAlignment(JLabel.RIGHT);
        studentInfoPanel.add(TQPLabel, 18);
        TQPTextField = new JTextField();
        studentInfoPanel.add(TQPTextField, 19);
        TQPInstructions = new JLabel("numeric, single decimal value");
        studentInfoPanel.add(TQPInstructions, 20);
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private void createButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout(0,20));

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        createStudentActionPanel();
        buttonPanel.add(studentActionPanel, BorderLayout.NORTH);

        createCollectionActionPanel();
        buttonPanel.add(collectionActionPanel, BorderLayout.SOUTH);
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    /* initialize the panel of buttons that control manipulation of a student */
    private void createStudentActionPanel()
    {
        studentActionPanel = new JPanel(new FlowLayout());

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        createStudentButton = new JButton("Create Student");
        createStudentButton.addActionListener(new CreateStudentButtonEventProcessor());
        studentActionPanel.add(createStudentButton);

        updateStudentButton = new JButton("Update Student");
        updateStudentButton.addActionListener(new UpdateStudentButtonEventProcessor());
        studentActionPanel.add(updateStudentButton);

        deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.addActionListener(new DeleteStudentButtonEventProcessor());
        studentActionPanel.add(deleteStudentButton);

        clearAllFieldsButton = new JButton("Clear All Fields");
        clearAllFieldsButton.addActionListener(new ClearAllFieldsButtonEventProcessor());
        studentActionPanel.add(clearAllFieldsButton);
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    /* initialize the panel of buttons that control accessing and displaying a
     * collection */
    private void createCollectionActionPanel()
    {
        collectionActionPanel = new JPanel(new FlowLayout());

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        retrieveButton = new JButton("Retrieve From Disk");
        retrieveButton.addActionListener(new RetrieveButtonEventProcessor());
        collectionActionPanel.add(retrieveButton);

        saveButton = new JButton("Save To Disk");
        saveButton.addActionListener(new SaveButtonEventProcessor());
        collectionActionPanel.add(saveButton);

        fileBeingProcessedLabel = new JLabel("Current collection: " + fileBeingProcessed);
        collectionActionPanel.add(fileBeingProcessedLabel);

        showAllButton = new JButton("Show Collection");
        showAllButton.addActionListener(new ShowCollectionButtonEventProcessor());
        collectionActionPanel.add(showAllButton);
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private JPanel createErrorPanel()
    {
        errorPanel = new JPanel();
        errorPanel.setLayout(new BorderLayout());

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        errorTextArea = new JTextArea("");
        errorTextArea.setForeground(Color.red);
        errorTextArea.setLineWrap(true);
        errorTextArea.setWrapStyleWord(true);
        errorTextArea.setColumns(50);
        Font defaultFont = errorTextArea.getFont();
        Font newFont =
          new Font(defaultFont.getName(), Font.BOLD, defaultFont.getSize() + 6);
        errorTextArea.setFont(newFont);
        errorPanel.add(errorTextArea, BorderLayout.CENTER);

        return errorPanel;
    }

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    private void clearAllFields()
    {
        sidTextField.setForeground(Color.black);
        firstNameTextField.setForeground(Color.black);
        middleInitialTextField.setForeground(Color.black);
        lastNameTextField.setForeground(Color.black);
        majorTextField.setForeground(Color.black);
        TDCTextField.setForeground(Color.black);
        TQPTextField.setForeground(Color.black);

        sidInstructions.setForeground(Color.black);
        firstNameInstructions.setForeground(Color.black);
        middleInitialInstructions.setForeground(Color.black);
        lastNameInstructions.setForeground(Color.black);
        majorInstructions.setForeground(Color.black);
        TDCInstructions.setForeground(Color.black);
        TQPInstructions.setForeground(Color.black);

        sidTextField.setText("");
        firstNameTextField.setText("");
        middleInitialTextField.setText("");
        lastNameTextField.setText("");
        majorTextField.setText("");
        TDCTextField.setText("");
        TQPTextField.setText("");

        mainPanel.remove(errorPanel);
        this.validate();
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private boolean verifyUniqueness( StudentSpec newStudent )
    {
        /* determine if a student is already in the collection by attempting to retrieve
         * an already-existing student that has the "new" student's SID    */
        return roster.retrieveStudentBySID(newStudent.getSID()) == null;
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private StudentSpec processStudentFields()
    {
        boolean allValid = true;

        StudentValidatorSpec validator = Student.getStudentValidator();

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String sid = sidTextField.getText();
        if ( validator.validateSID(sid) != IS_VALID )
        {
            allValid = false;
            sidTextField.setForeground(Color.red);
            sidInstructions.setForeground(Color.red);
        }
        else
        {
            sidTextField.setForeground(Color.black);
            sidInstructions.setForeground(Color.black);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String firstName = firstNameTextField.getText();
        if ( validator.validateFirstName(firstName) != IS_VALID )
        {
            allValid = false;
            firstNameTextField.setForeground(Color.red);
            firstNameInstructions.setForeground(Color.red);
        }
        else
        {
            firstNameTextField.setForeground(Color.black);
            firstNameInstructions.setForeground(Color.black);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String middleInitial = middleInitialTextField.getText();
        if ( validator.validateMiddleInitial(middleInitial) != IS_VALID )
        {
            allValid = false;
            middleInitialTextField.setForeground(Color.red);
            middleInitialInstructions.setForeground(Color.red);
        }
        else
        {
            middleInitialTextField.setForeground(Color.black);
            middleInitialInstructions.setForeground(Color.black);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String lastName = lastNameTextField.getText();
        if ( validator.validateLastName(lastName) != IS_VALID )
        {
            allValid = false;
            lastNameTextField.setForeground(Color.red);
            lastNameInstructions.setForeground(Color.red);
        }
        else
        {
            lastNameTextField.setForeground(Color.black);
            lastNameInstructions.setForeground(Color.black);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String major = majorTextField.getText();
        if ( validator.validateMajor(major) != IS_VALID )
        {
            allValid = false;
            majorTextField.setForeground(Color.red);
            majorInstructions.setForeground(Color.red);
        }
        else
        {
            majorTextField.setForeground(Color.black);
            majorInstructions.setForeground(Color.black);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String TDCAsString = TDCTextField.getText();
        double TDC;
        try
        {
            TDC = Double.parseDouble(TDCAsString);
            if ( validator.validateGPAIngredients(TDC, TDC) != IS_VALID )
            {
                allValid = false;
                TDCTextField.setForeground(Color.red);
                TDCInstructions.setForeground(Color.red);
            }
            else
            {
                TDCTextField.setForeground(Color.black);
                TDCInstructions.setForeground(Color.black);
            }
        }
        catch ( NumberFormatException numberFormatException )
        {
            allValid = false;
            TDC = Double.NEGATIVE_INFINITY;
            TDCTextField.setForeground(Color.red);
            TDCInstructions.setForeground(Color.red);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        String TQPAsString = TQPTextField.getText();
        double TQP;
        try
        {
            TQP = Double.parseDouble(TQPAsString);
            if ( validator.validateGPAIngredients(TQP, TQP) != IS_VALID )
            {
                allValid = false;
                TQPTextField.setForeground(Color.red);
                TQPInstructions.setForeground(Color.red);
            }
            else
            {
                TQPTextField.setForeground(Color.black);
                TQPInstructions.setForeground(Color.black);
            }
        }
        catch ( NumberFormatException numberFormatException )
        {
            allValid = false;
            TQP = Double.NEGATIVE_INFINITY;
            TQPTextField.setForeground(Color.red);
            TQPInstructions.setForeground(Color.red);
        }

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        StudentSpec newStudent = null;
        if ( allValid )
        {
            try
            {
                newStudent = Student.create(sid, firstName, middleInitial, lastName,
                  major, TDC, TQP);
            }
            catch ( StudentSpec.StudentSpecException studentSpecException )
            {
                newStudent = null;

                errorTextArea.setText(studentSpecException.getMessage());

                mainPanel.add(errorPanel, BorderLayout.CENTER);
            }
        }
        else
        {
            mainPanel.remove(errorPanel);
        }
        this.validate();

        return newStudent;
    }

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    public void updateJListWindow()
    {
        if (studentList == null)
        {
            return;
        }
        studentList.setListData(convertCollectionToArray(roster));
        studentList.setListTitle("Contents of collection " + fileBeingProcessed);
    }

    private Object[] convertCollectionToArray( StudentCollectionSpec roster )
    {
        /* convert supplied collection to a simple array */
        Iterator<StudentSpec> anIterator = roster.createIterator();
        StudentSpec[] studentArray = new StudentSpec[roster.getStudentCount()];

        int index = 0;
        while ( anIterator.hasNext() )
        {
            studentArray[index] = anIterator.next();
            index++;
        }
        return studentArray;
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    @Override
    public void valueChanged( ListSelectionEvent anEvent )
    {
        // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
        if ( diagnostics )
        {
            System.out.println("In StudentGUI, valueChanged() "
              + "being invoked, StudentGUI.this = " + StudentGUI.this.getClass()
              + " (" + StudentGUI.this.hashCode() + ")");
            System.out.println("valueChanged in StudentGUI: \n" +
              "     event:    " + anEvent.getSource() + " (" + anEvent.hashCode()
              + ")\n" +
              "     index:    " + anEvent.getFirstIndex() + "\n" +
              "     contents: " + studentList.getSelectedValue());
        }// </editor-fold>

        /* use information from the currently-selected element in studentList to populate
         * the student info display area   */
        populateStudentFields();
    }

    private void populateStudentFields()
    {
        StudentSpec aStudent = ( StudentSpec ) studentList.getSelectedValue();

        sidTextField.setText(aStudent.getSID());
        firstNameTextField.setText(aStudent.getFirstName());
        middleInitialTextField.setText(aStudent.getMiddleInitial());
        lastNameTextField.setText(aStudent.getLastName());
        majorTextField.setText(aStudent.getMajor());
        TDCTextField.setText(Double.toString(aStudent.getTotalDegreeCredits()));
        TQPTextField.setText(Double.toString(aStudent.getTotalQualityPoints()));

        this.validate();
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for "close frame" events   */
    private class CleanUpWhenClosing extends WindowAdapter
    {
        /* the windowClosing method is invoked while the JFrame is in the PROCESS of
         * being closed - there is a similarly-named method "windowClosed" that is invoked
         * only AFTER the JFrame has been closed, and for our purposes, that would be
         * too late. */
        @Override
        public void windowClosing( WindowEvent anEvent )
        {
            int result = JOptionPane.showConfirmDialog(null,
              "The contents of the student collection can be\n" +
              "written to disk if desired. Click 'Yes' to access\n" +
              "a write dialog, otherwise click 'No'.",
              "Save file to disk ?", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION)
            {
                /* the SaveButtonEventProcessor inner class contains all of the logic
                 * needed to interact with the user and write the collection to disk,
                 * so create an instance of that class and then invoke its
                 * writeCollectionToDisk method. */
                new SaveButtonEventProcessor().writeCollectionToDisk();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for createStudentButton.   */
    private class CreateStudentButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            createNewStudent();
        }

        private void createNewStudent()
        {
            StudentSpec newStudent = processStudentFields();
            if ( newStudent != null )
            {
                addStudentToCollection(newStudent);
            }
        }

        private void addStudentToCollection( StudentSpec newStudent )
        {
            /* don't addStudent the new student if a student with the same SID is already
             * in the current collection */
            boolean unique = verifyUniqueness(newStudent);
            if ( unique )
            {
                roster.addStudent(newStudent);

                JOptionPane.showMessageDialog(null,
                  "Student successfully created:\n" +
                  "     " + newStudent + "\n" +
                  "     has been added to current collection",
                  "Confirmation - student created",
                  JOptionPane.INFORMATION_MESSAGE);
                clearAllFields();

                /* update the JList display) */
                updateJListWindow();
            }
            else
            {
                errorTextArea.setText(
                  "A student with this SID already exists in the\n" +
                  "current collection. A new student cannot be created\n" +
                  "for current value of SID (" + newStudent.getSID() + ")");

                mainPanel.add(errorPanel, BorderLayout.CENTER);
            }
            StudentGUI.this.validate();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for updateStudentButton.   */
    private class UpdateStudentButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            updateExistingStudent();
        }

        private void updateExistingStudent()
        {
            StudentSpec updatedStudent = processStudentFields();
            if ( updatedStudent != null )
            {
                replaceStudentInCollection(updatedStudent);
            }
        }

        private void replaceStudentInCollection( StudentSpec updatedStudent )
        {
            /* only update ("replace") the student if the student is already in the
             * current collection */
            boolean unique = verifyUniqueness(updatedStudent);
            if ( unique == false )
            {
                StudentSpec oldVersionOfStudent =
                  roster.removeStudentBySID(updatedStudent.getSID());
                roster.addStudent(updatedStudent);

                JOptionPane.showMessageDialog(null,
                  "Student successfully updated:\n" +
                  "     " + oldVersionOfStudent + "\n" +
                  "     has been has been updated to\n" + "     " + updatedStudent,
                  "Confirmation - student created",
                  JOptionPane.INFORMATION_MESSAGE);
                clearAllFields();

                /* update the JList display */
                updateJListWindow();
            }
            else
            {
                errorTextArea.setText(
                  "No student with this SID currently exists in the "
                  + "current collection. A student cannot be updated if "
                  + "the current record for the student cannot be located."
                  + "The 'missing' student SID is " + updatedStudent.getSID() + ".");

                mainPanel.add(errorPanel, BorderLayout.CENTER);
            }
            StudentGUI.this.validate();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for deleteStudentButton.   */
    private class DeleteStudentButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            deleteExistingStudent();
        }

        private void deleteExistingStudent()
        {
            String sidBeingDeleted = sidTextField.getText();
            removeStudentFromCollection(sidBeingDeleted);
        }

        private void removeStudentFromCollection( String sidBeingDeleted )
        {
            StudentSpec theStudent = roster.removeStudentBySID(sidBeingDeleted);
            if ( theStudent != null )
            {
                JOptionPane.showMessageDialog(null,
                  "Student successfully deleted:\n" + "     " + theStudent + "\n" +
                  "     has been has been removed from the collection",
                  "Confirmation - student created",
                  JOptionPane.INFORMATION_MESSAGE);
                clearAllFields();

                /* update the JList display */
                updateJListWindow();
            }
            else
            {
                errorTextArea.setText(
                  "No student with this SID currently exists in the "
                  + "current collection. A student cannot be deleted if "
                  + "the current record for the student cannot be located."
                  + "The 'missing' student SID is " + sidBeingDeleted + ".");

                mainPanel.add(errorPanel, BorderLayout.CENTER);
            }
            StudentGUI.this.validate();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for clearAllFieldsButton.   */
    private class ClearAllFieldsButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            clearAllFields();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for showCollectionButton.   */
    private class ShowCollectionButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            createStudentListDisplay();
            studentList.setVisible(true);
        }

        private void createStudentListDisplay()
        {
            if (studentList == null)
            {
                String collectionName =
                  (fileBeingProcessed==null) ? "untitled" : fileBeingProcessed;

                studentList = new DisplayObjectsAsJList(
                  "Contents of collection " + collectionName,
                  convertCollectionToArray(roster));

                // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
                if ( diagnostics )
                {
                    System.out.println("In StudentGUI, " +
                      "ShowAllStudentsAsJList.addListSelectionListener() " +
                      "being invoked, StudentGUI.this = " + StudentGUI.this.getClass() +
                      " (" + StudentGUI.this.hashCode() + ")");
                }// </editor-fold>

                studentList.addListSelectionListener(StudentGUI.this);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for retrieveFromDiskButton.   */
    private class RetrieveButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            fetchCollectionFromDisk();
        }

        /* note - this code is a direct copy of a method from a previous example */
        private void fetchCollectionFromDisk()
        {
            String fileName = null;
            do
            {
                fileName = JOptionPane.showInputDialog(null,
                  "Please enter the name of the file that contains the contents\n" +
                  "of the collection that you wish to interact with",
                  "Fetch collection from disk",
                  JOptionPane.QUESTION_MESSAGE);
                if ( fileName == null || fileName.trim().length() == 0 )
                {
                    JOptionPane.showMessageDialog(null,
                      "You must supply a value!",
                      "ERROR - MISSING RESPONSE",
                      JOptionPane.ERROR_MESSAGE);
                }
            }
            while ( fileName == null || fileName.trim().length() == 0 );

            String trimmedFilename = fileName.trim();
            boolean fileIsPresent = StudentCollectionSpec.fileExists(trimmedFilename);

            if ( !fileIsPresent )
            {
                JOptionPane.showMessageDialog(null,
                  "The file that you specified cannot be found - collection\n" +
                  "contents not retrieved",
                  "Fetch collection from disk ERROR",
                  JOptionPane.ERROR_MESSAGE);
                return;
            }

            try
            {
                roster.retrieveCollectionFromDisk(trimmedFilename);

                JOptionPane.showMessageDialog(null,
                  "Collection successfully retrieved from file " + trimmedFilename,
                  "Fetch collection from disk CONFIRMATION",
                  JOptionPane.INFORMATION_MESSAGE);

                /* cause the file that was read to be displayed in the GUI */
                fileBeingProcessed = trimmedFilename;
                fileBeingProcessedLabel.setText("Current collection: " + fileBeingProcessed);

                // (re)create the display window for the collection
                updateJListWindow();
            }
            catch ( StudentCollectionException causalException )
            {
                JOptionPane.showMessageDialog(null,
                  "Problem encountered in fetching collection from disk file " +
                  trimmedFilename + ": error received was " +
                  causalException.getMessage() + "<--" + causalException.getCause().
                  getMessage(),
                  "Fetch collection from disk ERROR ",
                  JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* class that will act as an event processor for saveToDiskButton.   */
    private class SaveButtonEventProcessor implements ActionListener
    {
        @Override
        public void actionPerformed( ActionEvent anEvent )
        {
            writeCollectionToDisk();
        }

        /* note - this code is a direct copy of a method from a previous example */
        private void writeCollectionToDisk()
        {
            String fileName = null;
            do
            {
                /* note that this version of JOptionPane.showInputDialog() allows the
                 * input area of the dialog to be initialized with a value - in this case,
                 * the value of the previously-processed file. The vale is (by default)
                 * selected (highlighted) - the user can type over the value, or just
                 * click "OK" to accept it  */
                fileName = (String) JOptionPane.showInputDialog(null,
                  "Please enter the name of the file that is to contain the contents\n"
                  + "of the current collection",
                  "Save collection to disk",
                  JOptionPane.QUESTION_MESSAGE,
                  null,
                  null,
                  fileBeingProcessed);
                if ( fileName == null || fileName.trim().length() == 0 )
                {
                    JOptionPane.showMessageDialog(null,
                      "You must supply a value!",
                      "ERROR - MISSING RESPONSE",
                      JOptionPane.ERROR_MESSAGE);
                }
            }
            while ( fileName == null || fileName.trim().length() == 0 );

            String trimmedFilename = fileName.trim();
            boolean fileIsPresent = StudentCollectionSpec.fileExists(trimmedFilename);

            // if the file exists, warn the user that any information in it is about
            // to be overwritten and therefore destroyed, and give them a chance to
            // cancel the operation
            if ( fileIsPresent )
            {
                int response = JOptionPane.showConfirmDialog(null,
                  "The file name that you specified ( " + trimmedFilename + ")\n" +
                  "already exists - if you proceed, the contents of this file\n" +
                  "will be overwritten and irrecoverably lost.\n" +
                  "Click \"OK\" to continue, \"Cancel\" to halt this process",
                  "Save collection to disk CONFIRMATION",
                  JOptionPane.OK_CANCEL_OPTION);
                // do not overwrite unless the user chooses "OK"
                if ( response == JOptionPane.CANCEL_OPTION )
                {
                    JOptionPane.showMessageDialog(null,
                      "Current collection NOT WRITTEN TO DISK",
                      "Save collection to disk CANCELLED",
                      JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            roster.writeCollectionToDisk(trimmedFilename);

            JOptionPane.showMessageDialog(null,
              "Collection successfully written to file " + trimmedFilename,
              "Save collection to disk CONFIRMATION",
              JOptionPane.INFORMATION_MESSAGE);

            /* cause the file name that was written to to be displayed in the GUI */
            fileBeingProcessed = trimmedFilename;
            fileBeingProcessedLabel.setText("Current collection: " + fileBeingProcessed);

            // (re)create the display window for the collection
            updateJListWindow();
        }
    }
}