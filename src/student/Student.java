package student;


import java.io.Serializable;
import studentspec.StudentSpec;
import static studentspec.StudentSpec.ValidationStatus.*;


/**
 * Implementation of studentspec/StudentSpec interface; provides two create() proxy constructor
 * methods to be used in instantiating Student objects.
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
public class Student implements StudentSpec, Serializable
{
    private static final long serialVersionUID = -8921835101883579059L;

    private String firstName;
    private String lastName;
    private String middleInitial;
    private String sid;
    private String major;
    private double totalDegreeCredits;
    private double totalQualityPoints;

    private static StudentSpec.StudentValidatorSpec studentValidator =
      new Student.StudentValidator();

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    private Student()
    {
        super();

        // nothing to do here - this constructor does not initialize attributes
    }

    /**
     * Creates a StudentSpec-type object when supplied with valid initialization values.
     * If any supplied value is not valid, this method will return null.
     *
     * @param theSID - SID (7 digits)
     * @param theFirstName - first name (at least one character in length)
     * @param theMiddleInitial - middle initial (if null or an empty string, initialized
     *        to an empty string, otherwise initialized to the first character of the
     *        supplied string)
     * @param theLastName - last name (at least one character in length)
     * @param theMajor - academic major (exactly three characters in length)
     * @param theTotalDegreeCredits - total degree credits (non-negative, must produce
     *        a valid GPA in conjunction with total quality points)
     * @param theTotalQualityPoints - total quality points (non-negative, must produce
     *        a valid GPA in conjunction with total degree credits)
     *
     * @return a StudentSpec-type object; if any supplied value is not value, null
     *         is returned.
     *
     * @throws studentspec.StudentSpec.StudentSpecException if any supplied value is
     *         not valid
     */
    public static StudentSpec create( String theSID,
      String theFirstName, String theMiddleInitial, String theLastName,
      String theMajor, double theTotalDegreeCredits, double theTotalQualityPoints )
      throws StudentSpec.StudentSpecException
    {
        Student newStudent = new Student();
        try
        {
            newStudent.setSID(theSID);
            newStudent.setLastName(theLastName);
            newStudent.setFirstName(theFirstName);
            newStudent.setMiddleInitial(theMiddleInitial);
            newStudent.setMajor(theMajor);
            newStudent.setGPAIngredients(theTotalDegreeCredits, theTotalQualityPoints);
            return newStudent;
        }
        catch ( StudentSpec.StudentSpecException causalException )
        {
            throw new StudentSpec.StudentSpecException("Problem detected in create(): " +
              causalException.getMessage(), causalException);
        }
    }

    /**
     * Creates a StudentSpec-type object when supplied with valid initialization values.
     * If any supplied value is not valid, this method will return null.
     *
     * @param theSID - SID (7 digits)
     * @param theFirstName - first name (at least one character in length)
     * @param theMiddleInitial - middle initial (if null or an empty string, initialized
     *        to an empty string, otherwise initialized to the first character of the
     *        supplied string)
     * @param theLastName - last name (at least one character in length)
     * @param theMajor - academic major (exactly three characters in length)
     *
     * @return a StudentSpec-type object; if any supplied value is not value, null
     *         is returned.
     *
     * @throws studentspec.StudentSpec.StudentSpecException if any supplied value is
     *         not valid
     */
    public static StudentSpec create( String theSID,
      String theFirstName, String theMiddleInitial, String theLastName,
      String theMajor )
      throws StudentSpec.StudentSpecException
    {
        Student newStudent = new Student();
        try
        {
            newStudent.setSID(theSID);
            newStudent.setLastName(theLastName);
            newStudent.setFirstName(theFirstName);
            newStudent.setMiddleInitial(theMiddleInitial);
            newStudent.setMajor(theMajor);
            newStudent.setGPAIngredients(
              DEFAULT_TOTAL_DEGREE_CREDITS, DEFAULT_TOTAL_QUALITY_POINTS);
            return newStudent;
        }
        catch ( StudentSpec.StudentSpecException causalException )
        {
            throw new StudentSpec.StudentSpecException("Problem detected in create(): " +
              causalException.getMessage(), causalException);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    @Override
    public String getSID()
    {
        return sid;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public String getMiddleInitial()
    {
        return middleInitial;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public String getMajor()
    {
        return major;
    }

    @Override
    public double getTotalDegreeCredits()
    {
        return totalDegreeCredits;
    }

    @Override
    public double getTotalQualityPoints()
    {
        return totalQualityPoints;
    }

    @Override
    public String toString()
    {
        StringBuilder temp = new StringBuilder(sid);
        temp.append("|");
        temp.append(lastName);
        temp.append("|");
        temp.append(firstName);
        temp.append('|');
        temp.append(middleInitial);
        temp.append('|');
        temp.append(major);
        temp.append("|");
        temp.append(totalDegreeCredits);
        temp.append('|');
        temp.append(totalQualityPoints);

        return temp.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    @Override
    public void setFirstName( String theFirstName ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateFirstName(theFirstName);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set first name - " +
              result);
        }
        firstName = theFirstName.trim();
    }


    @Override
    public void setMiddleInitial( String theMiddleInitial ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateMiddleInitial(theMiddleInitial);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set middle initial - " +
              result);
        }
        if ( theMiddleInitial == null || theMiddleInitial.trim().length() == 0 )
        {
            middleInitial = "";
        }
        else
        {
            middleInitial = theMiddleInitial.trim().substring(0, 1);
        }
    }

    @Override
    public void setLastName( String theLastName ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateLastName(theLastName);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set last name - " +
              result);
        }
        lastName = theLastName.trim();
    }

    /**
     * Set the student's SID. The supplied value must be a string of length 7 and
     * be all digits.
     *
     * @param theSID - the new major
     *
     * @return signal indicating the result of attempting to use the supplied value
     *         to set major
     */
    private void setSID( String theSID ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateSID(theSID);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set SID - " +
              result);
        }
        sid = theSID.trim();
    }

    @Override
    public void setMajor( String theMajor ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateMajor(theMajor);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set major - " +
              result);
        }
        major = theMajor.trim();
    }

    @Override
    public void setGPAIngredients( double newTotalDegreeCredits,
      double newTotalQualityPoints ) throws StudentSpec.StudentSpecException
    {
        StudentSpec.ValidationStatus result = studentValidator.validateGPAIngredients(
          newTotalDegreeCredits,
          newTotalQualityPoints);
        if ( result != IS_VALID )
        {
            throw new StudentSpec.StudentSpecException("Cannot set GPA ingredients - " +
              result);
        }
        totalDegreeCredits = newTotalDegreeCredits;
        totalQualityPoints = newTotalQualityPoints;
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    public static StudentSpec.StudentValidatorSpec getStudentValidator()
    {
        return studentValidator;
    }

    private static class StudentValidator implements StudentSpec.StudentValidatorSpec
    {
        private StudentValidator()
        {
            super();
        }

        @Override
        public StudentSpec.ValidationStatus validateFirstName( String theFirstName )
        {
            if ( theFirstName == null )
            {
                return FIRST_NAME_NULL;
            }
            String trimmedFirstName = theFirstName.trim();
            if ( trimmedFirstName.isEmpty() )
            {
                return FIRST_NAME_EMPTY;
            }
            return IS_VALID;
        }

        @Override
        public StudentSpec.ValidationStatus validateMiddleInitial( String theMiddleInitial )
        {
            return IS_VALID;
        }

        @Override
        public StudentSpec.ValidationStatus validateLastName( String theLastName )
        {
            if ( theLastName == null )
            {
                return LAST_NAME_NULL;
            }
            String trimmedLastName = theLastName.trim();
            if ( trimmedLastName.isEmpty() )
            {
                return LAST_NAME_EMPTY;
            }
            return IS_VALID;
        }

        @Override
        public StudentSpec.ValidationStatus validateSID( String theSID )
        {
            if ( theSID == null )
            {
                return SID_NULL;
            }
            String trimmedSID = theSID.trim();
            if ( trimmedSID.isEmpty() )
            {
                return SID_EMPTY;
            }
            if ( trimmedSID.length() != 7 )
            {
                return SID_NOT_SEVEN_CHARACTERS;
            }

            try
            {
                Integer.parseInt(trimmedSID);
            }
            catch ( NumberFormatException causalException )
            {
                return SID_NOT_SEVEN_DIGITS;
            }

            return IS_VALID;
        }

        @Override
        public StudentSpec.ValidationStatus validateMajor( String theMajor )
        {
            if ( theMajor == null )
            {
                return MAJOR_NULL;
            }
            String trimmedMajor = theMajor.trim();
            if ( trimmedMajor.isEmpty() )
            {
                return MAJOR_EMPTY;
            }
            if ( trimmedMajor.length() != 3 )
            {
                return MAJOR_NOT_THREE_CHARACTERS;
            }
            return IS_VALID;
        }

        @Override
        public StudentSpec.ValidationStatus validateGPAIngredients( double newTotalDegreeCredits,
          double newTotalQualityPoints )
        {
            if ( newTotalDegreeCredits < 0.0 )
            {
                return TOTAL_DEGREE_CREDITS_NEGATIVE;
            }
            if ( newTotalQualityPoints < 0.0 )
            {
                return TOTAL_QUALITY_POINTS_NEGATIVE;
            }

            if ( newTotalQualityPoints > 0.0 && newTotalDegreeCredits == 0.0 )
            {
                // cannot have quality points if there are no degree credits
                return ILLEGAL_TOTAL_QUALITY_POINTS_VALUE;
            }
            // don't try to calculate GPA if total degree credits is 0
            if ( newTotalDegreeCredits > 0.0 )
            {
                double GPA = newTotalQualityPoints / newTotalDegreeCredits;
                if ( GPA < MIN_GPA || GPA > MAX_GPA )
                {
                    return RESULTING_GPA_OUT_OF_RANGE;
                }
            }
            return IS_VALID;
        }
    }
}
