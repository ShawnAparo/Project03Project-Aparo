package studentgui;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * JList implementation designed to accept an array of objects and display them as a scrollable
 * list. Instances will report back to registered listeners when a list entry is selected via
 * mouse double-click or selection and "enter"
 *
 * <p>Copyright: (c) 2016</p>
 *
 * @author J S Kasprzyk
 * @version 1.6
 */
@SuppressWarnings("serial")
public class DisplayObjectsAsJList extends JList<Object>
{
    /* flag that determines if diagnostics / informational displays are to be produced.
     * Note that the displays themselves have been "hidden" in folded code blocks labeled
     * with "diagnostics"     */
    private boolean diagnostics = false;

    private ListSelectionListener theListener;

    /* "this" JList will be added to a JScrollPane, which will be added to a JPanel,
     * which will be added to a Container that represents the content area of a JFrame. */
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private Container aContainer;
    private JFrame aWindow;

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    public DisplayObjectsAsJList( String windowTitle, Object[] objectArray )
    {
        /* invoke parent JList constructor, supplying initial contents of the list */
        super(objectArray);

        /* listen for mouse-based interactions with this JList */
        this.addMouseListener(new JListMouseAdapter());

        /* listen for keyboard-based interactions with this JList */
        this.addKeyListener(new JListKeyAdapter());

        /* initialize (set up) a GUI to contain and display the JList */
        this.initialize(windowTitle);
    }

    public DisplayObjectsAsJList( String windowTitle, Object[] objectArray,
      boolean diagnosticsSetting )
    {
        /* invoke parent JList constructor, supplying initial contents of the list */
        super(objectArray);

        /* listen for mouse-based interactions with this JList */
        this.addMouseListener(new JListMouseAdapter());

        /* listen for keyboard-based interactions with this JList */
        this.addKeyListener(new JListKeyAdapter());

        /* initialize (set up) a GUI to contain and display the JList */
        this.initialize(windowTitle);

        /* set the diagnostics flag to the supplied value */
        this.diagnostics = diagnosticsSetting;
    }

    // <editor-fold defaultstate="collapsed" desc="----------------------------------------"> /* */
    // </editor-fold>

    private void initialize( String windowTitle )
    {
        /* limit selection to a single list entry in the JList */
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        /* set each row of the JList to a fixed height (for aesthetic and
         * spacing purposes)  */
        this.setFixedCellHeight(20);

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        /* create a JScrollPane and add this list to it */
        scrollPane = new JScrollPane(null,
          ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
          ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLayout(new ScrollPaneLayout());
        /* setting the preferred size of the scroll pane is critical to getting the
         * scroll bars to appear - if the preferred size is NOT set, the vertical
         * scroll bar tends to appear as needed, but the horizontal one does not -
         * instead, the scroll pane tends to expand horizontally indefinitely as needed
         *
         * also, note that the vertical dimension (100) is consciously chosen as an
         * exact multiple of the fixed cell height (20) - this neatly fits a set of rows
         * into the scroll pane without any rows being "cut in half" horizontally   */
        scrollPane.setPreferredSize(new Dimension(300, 100));
        /* add "this" JList to the viewport of the scroll pane */
        scrollPane.getViewport().add(this);

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        /* create a main panel and add the scroll pane to it */
        mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(scrollPane);

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        /* create a container and add the main panel to it */
        aContainer = new Container();
        aContainer.setLayout(new BorderLayout());
        aContainer.add(mainPanel, BorderLayout.CENTER);

        // <editor-fold defaultstate="collapsed" desc="---------------">
        /* */
        // </editor-fold>

        /* create a window and add the container to it */
        aWindow = new JFrame(windowTitle);
        /* create a FlowLayout object, then customize it */
        FlowLayout windowLayout = new FlowLayout();
        /* insert some "padding" space above and below the components in the window */
        windowLayout.setVgap(10);
        aWindow.setLayout(windowLayout);
        aWindow.setContentPane(aContainer);
        aWindow.setPreferredSize(new Dimension(350, 150));
        /* center the window on the screen */
        aWindow.setLocationRelativeTo(null);
        aWindow.pack();
        /* instead of having the window close itself when the user clicks "close" ("X"),
         * have the window hide itself - if the window is required by the user in the
         * future, it's more efficient to make the existing window visible than it is
         * to re-create the window and all of its components  */
        aWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        aWindow.setVisible(false);
    }

    // <editor-fold defaultstate="collapsed" desc="-------------------------------------------------------------------------------------"> /* */
    // </editor-fold>

    @Override
    public void addListSelectionListener( ListSelectionListener aListener )
    {
        /* remember who is listening to this JList */
        // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
        if ( diagnostics )
        {
            System.out.println("In ShowAllStudentsAsJList.addListSelectionListener() "
              + "- aListener is " + aListener.getClass() + " (" + aListener.hashCode()
              + ")");
            System.out.flush();
        }// </editor-fold>
        this.theListener = aListener;
    }

    /* override the setVisible() method, replacing the default implementation with
     * one that tells the JFrame that encloses this JList to set its visibility. The
     * default implementation of this method affects only the visibility of the JList
     * component of the window */
    @Override
    public void setVisible( boolean isVisible )
    {
        positionTheWindow();
        aWindow.setVisible(isVisible);
    }

    private void positionTheWindow()
    {
        /* determine the location and width of the component that is listening */
        int listenerXLocation = ( ( Component ) theListener ).getX();
        int listenerYLocation = ( ( Component ) theListener ).getY();
        int listenerWidth = ( ( Component ) theListener ).getWidth();

        /* determine the width and height of this window that contains this JList */
        int windowWidth = aWindow.getWidth();
        int windowHeight = aWindow.getHeight();

        /* make this window appear just above the listener */
        int windowYLocation = listenerYLocation - windowHeight;
        /* center this window on the listener */
        int windowXLocation = listenerXLocation + ( ( listenerWidth - windowWidth ) / 2 );

        /* set the location of this window */
        aWindow.setLocation(windowXLocation, windowYLocation);
    }

    /* override the setListData() method, first setting the list data, then re-validating
     * the JFrame that encloses the JList to insure that the JFrame is sized
     * appropriately     */
    @Override
    public void setListData( Object[] objectList )
    {
        super.setListData(objectList);
        aWindow.pack();
    }

    public void setListTitle( String listTitle )
    {

        aWindow.setTitle(listTitle);
        aWindow.pack();
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* MouseAdapter is a convenience class that makes it easy to listen for specific
     * mouse actions and ignore all others */
    private class JListMouseAdapter extends MouseAdapter
    {
        @Override
        public void mouseClicked( MouseEvent aMouseEvent )
        {
            /* if there's no one listening, don't bother to process the event */
            if ( theListener == null )
            {
                return;
            }

            /* check for a double-click */
            if ( aMouseEvent.getClickCount() == 2 )
            {
                // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
                if ( diagnostics )
                {
                    System.out.println("In JListMouseAdapter");
                    System.out.println("   Selected object is " + getSelectedValue());
                    System.out.println(
                      "   Sending VALUE_CHANGED from "
                      + "ShowAllStudentsAsJList.JListMouseAdapter.mouseClicked() "
                      + "to ListSelectionListener " + theListener.getClass()
                      + " (" + theListener.hashCode() + ")");
                    System.out.flush();

                    System.out.println("   this: "
                      + DisplayObjectsAsJList.this.getClass() + " (" + DisplayObjectsAsJList.this.
                      hashCode() + ")");
                    System.out.println("   getSelectedIndex() = " + getSelectedIndex());
                }// </editor-fold>

                /* inform the listener that the JList's selection has changed */
                theListener.valueChanged(new ListSelectionEvent(this,
                  getSelectedIndex(), getSelectedIndex(), false));

                // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
                if ( diagnostics )
                {
                    System.out.println(
                      "   SENT VALUE_CHANGED from "
                      + "ShowAllStudentsAsJList.JListMouseAdapter.mouseClicked() "
                      + "to ListSelectionListener " + theListener.getClass()
                      + " (" + theListener.hashCode() + ")");
                    System.out.flush();
                }// </editor-fold>
                aMouseEvent.consume();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"> /* */
    // </editor-fold>

    /* KeyAdapter is a convenience class that makes it easy to listen for specific
     * keyboard actions and ignore all others */
    private class JListKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyReleased( KeyEvent aKeyEvent )
        {
            /* if there's no one listening, don't bother to process the event */
            if ( theListener == null )
            {
                return;
            }

            /* check for the "enter" key being released */
            if ( aKeyEvent.getKeyCode() == KeyEvent.VK_ENTER )
            {
                // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
                if ( diagnostics )
                {
                    System.out.println("In JListKeyAdapter");
                    System.out.println(
                      "   Sending VALUE_CHANGED from "
                      + "ShowAllStudentsAsJList.JListKeyAdapter.keyReleased() "
                      + "to ListSelectionListener " + theListener.getClass()
                      + " (" + theListener.hashCode() + ")");
                    System.out.flush();

                    System.out.println("   this: "
                      + DisplayObjectsAsJList.this.getClass() + " (" + DisplayObjectsAsJList.this.
                      hashCode() + ")");
                    System.out.println("   getSelectedIndex() = " + getSelectedIndex());
                }// </editor-fold>

                /* inform the listener that the JList's selection has changed
                 */
                theListener.valueChanged(new ListSelectionEvent(this,
                  getSelectedIndex(), getSelectedIndex(), false));

                // <editor-fold defaultstate="collapsed" desc="diagnostic displays">
                if ( diagnostics )
                {
                    System.out.println(
                      "   SENT VALUE_CHANGED from "
                      + "ShowAllStudentsAsJList.JListKeyAdapter.mouseClicked() "
                      + "to ListSelectionListener " + theListener.getClass()
                      + " (" + theListener.hashCode() + ")");
                    System.out.flush();
                }// </editor-fold>
                aKeyEvent.consume();
            }
        }
    }
}
