package main;


import javax.swing.JFrame;
import studentgui.StudentGUI;

/**
 * Starting point for execution
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
public class Main
{
    // private constructor to prevent instantiation
    private Main()
    {

    }

    public static void main( String[] args )
    {
        StudentGUI theStudentGUI = new StudentGUI();
        theStudentGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theStudentGUI.setVisible(true);
    }
}
