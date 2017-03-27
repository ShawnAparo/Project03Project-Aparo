package studentspec;


/**
 * Specification of the requirements for a class intended to store information about students.
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
public interface StudentSpec
{
    public static final double MAX_GPA = 4.0;
    public static final double MIN_GPA = 0.0;
    public static final double DEFAULT_TOTAL_DEGREE_CREDITS = 0.0;
    public static final double DEFAULT_TOTAL_QUALITY_POINTS = 0.0;

    /**
     * Enumeration used in conjunction with StudentSpec.StudentValidatorSpec to inform users
     * of validation issues detected during validation of an expected field within a class that
     * implements StudentSpec.
     *
     * <p>Copyright: (c) 2016</p>
     *
     * @author J S Kasprzyk
     * @version 1.6
     */
    public static enum ValidationStatus
    {
        IS_VALID,
        FIRST_NAME_NULL, FIRST_NAME_EMPTY,
        LAST_NAME_NULL, LAST_NAME_EMPTY,
        SID_NULL, SID_EMPTY, SID_NOT_SEVEN_CHARACTERS, SID_NOT_SEVEN_DIGITS,
        TOTAL_DEGREE_CREDITS_NEGATIVE, TOTAL_QUALITY_POINTS_NEGATIVE,
        ILLEGAL_TOTAL_QUALITY_POINTS_VALUE, RESULTING_GPA_OUT_OF_RANGE,
        MAJOR_NULL, MAJOR_EMPTY, MAJOR_NOT_THREE_CHARACTERS
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /**
     * Returns the student's SID
     *
     * @return the student's SID
     */
    public String getSID();

    /**
     * Returns the student's first name
     *
     * @return the student's first name
     */
    public String getFirstName();

    /**
     * Returns the student's middle initial
     *
     * @return the student's middle initial (returns an empty string if there is no
     * known middle initial)
     */
    public String getMiddleInitial();

    /**
     * Returns the student's last name
     *
     * @return the student's last name
     */
    public String getLastName();

    /**
     * Returns the student's academic major
     *
     * @return the student's major
     */
    public String getMajor();

    /**
     * Returns the student's total degree credits (credits from courses that have been
     * completed with a passing grade)
     *
     * @return the student's total degree credits
     */
    public double getTotalDegreeCredits();

    /**
     * Returns the student's total quality points (determined by summing course credits
     * multiplied by numeric course grade for all completed courses that do not have a
     * recorded grade of Pass)
     *
     * @return the student's total quality points
     */
    public double getTotalQualityPoints();


    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /**
     * Returns a string representing the data values for this students
     *
     * @return a string representation of student information
     */
    @Override
    public String toString();

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /**
     * Set the student's first name. The supplied value must be a string at least one
     * character in length.
     *
     * @param theFirstName - the new first name
     *
     * @throws studentspec.StudentSpec.StudentSpecException if the supplied value is
     *         not valid
     */
    public void setFirstName( String theFirstName ) throws StudentSpec.StudentSpecException;

    /**
     * Set the student's middle initial. If the supplied value is null or an empty string,
     * the middle initial is set to an empty string; if the supplied value is a length of
     * one or greater, the middle initial is set to the first character of the supplied
     * string.
     *
     * @param theMiddleInitial - the new middle initial
     *
     * @throws studentspec.StudentSpec.StudentSpecException if the supplied value is
     *         not valid
     */
    public void setMiddleInitial( String theMiddleInitial ) throws StudentSpec.StudentSpecException;

    /**
     * Set the student's last name. The supplied value must be a string at least one
     * character in length.
     *
     * @param theLastName - the new last name
     *
     * @throws studentspec.StudentSpec.StudentSpecException if the supplied value is
     *         not valid
     */
    public void setLastName( String theLastName ) throws StudentSpec.StudentSpecException;

    /**
     * Set the student's major. The supplied value must be a string of length 3.
     *
     * @param theMajor - the new major
     *
     * @throws studentspec.StudentSpec.StudentSpecException if the supplied value is
     *         not valid
     */
    public void setMajor( String theMajor ) throws StudentSpec.StudentSpecException;

    /**
     * Set the student's total degree credits and total quality points. The supplied
     * values must be non-negative, and when used to calculate GPA must produce a value
     * between MIN_GPA and MAX_GPA inclusive.
     *
     * @param theTotalDegreeCredits - the new total degree credits
     * @param theTotalQualityPoints - the new total quality points
     *
     * @throws studentspec.StudentSpec.StudentSpecException if the supplied value is
     *         not valid
     */
    public void setGPAIngredients( double theTotalDegreeCredits,
      double theTotalQualityPoints ) throws StudentSpec.StudentSpecException;

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /**
     * Interface used to define validation specifications for fields of classes implementing
     * StudentSpec.
     *
     * <p>Copyright: (c) 2016</p>
     *
     * @author J S Kasprzyk
     * @version 1.6
     */
    public interface StudentValidatorSpec
    {
        /**
         * Validate the student's SID. The supplied value must be a string of length 7 and
         * be all digits.
         *
         * @param theSID - the value being validated
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateSID( String theSID );

        /**
         * Validate the student's first name. The supplied value must be a string at least
         * one character in length.
         *
         * @param theFirstName - the value being validated
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateFirstName( String theFirstName );

        /**
         * Validate the student's middle initial. If the supplied value is null or an
         * empty string, the middle initial is set to an empty string; if the supplied
         * value is a length of one or greater, the middle initial is set to the first
         * character of the supplied string.
         *
         * @param theMiddleInitial - the value being validated
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateMiddleInitial( String theMiddleInitial );

        /**
         * Validate the student's last name. The supplied value must be a string at least
         * one character in length.
         *
         * @param theLastName - the value being validated
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateLastName( String theLastName );

        /**
         * Validate the student's major. The supplied value must be a string of length 3.
         *
         * @param theMajor - the value being validated
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateMajor( String theMajor );

        /**
         * Validate the student's total degree credits and total quality points. The
         * supplied values must be non-negative, and when used to calculate GPA must
         * produce a value between MIN_GPA and MAX_GPA.
         *
         * @param theTotalDegreeCredits - the value being validated as total degree
         *        credits
         * @param theTotalQualityPoints - the value being validated as total quality
         *        points
         *
         * @return signal indicating the result of validating the supplied value
         */
        public StudentSpec.ValidationStatus validateGPAIngredients( double theTotalDegreeCredits,
          double theTotalQualityPoints );
    }

    // <editor-fold defaultstate="collapsed" desc="------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    /**
     * Specialized RuntimeException used to report exceptions thrown by classes
     * implementing StudentSpec.
     *
     * <p>Copyright: (c) 2016</p>
     *
     * @author J S Kasprzyk
     * @version 1.6
     */
    public class StudentSpecException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;

        /**
         * Creates an unchecked exception
         * @param cause the direct cause of the exception being created
         */
        public StudentSpecException( String cause )
        {
            super(cause);
        }

        /**
         * Creates an unchecked exception
         *
         * @param cause context-specific cause of the exception being created
         * @param causalException the direct cause of the exception being created
         */
        public StudentSpecException( String cause, Exception causalException )
        {
            super(cause, causalException);
        }
    }
}
