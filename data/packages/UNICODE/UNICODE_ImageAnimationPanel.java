package data.packages.UNICODE;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;


public class UNICODE_ImageAnimationPanel extends JPanel
{
    //attributes
    private String letterResourceDirectory = "";
    private ArrayList <Image> imageList = new ArrayList <Image> ( );
    private int currentImageIndex = 0;
    private int x = 0, y = 0;
    private Timer animationTimer = null;
	private UNICODE_GuiPanel guiPanel = null;
    
    
    //constructor
    public UNICODE_ImageAnimationPanel ( String _letterResourceDirectory, Color backgroundColour, int _x, int _y, UNICODE_GuiPanel _guiPanel )
    {
        //establish apnel colour
        setBackground ( backgroundColour );
        
        //establish letter resource directory
        letterResourceDirectory = _letterResourceDirectory;
        
        //establish orientation
        x = _x;
        y = _y;
        
        //build images wrt directory
        buildImageCollection ( );
		
		guiPanel = _guiPanel;
    }
    
    
    //paint component
    public void paintComponent ( Graphics graphics )
    {
        super.paintComponent ( graphics );
        Graphics2D graphics2d = ( Graphics2D ) graphics;
        
        graphics2d.drawImage ( imageList.get ( currentImageIndex ), x, y, this );
    }
    
    //methods
    public void buildImageCollection ( )
    {
        String [ ] directoryList = new File ( letterResourceDirectory ).list ( );
        
        for ( int i = 0; i < directoryList.length; i ++ )
        {
            String directory = directoryList [ i ];
            ImageIcon imageIcon = new ImageIcon ( letterResourceDirectory + directory );
            
            Image image = imageIcon.getImage ( );
            image = createImage ( image.getSource ( ) );
            imageList.add ( image );
        }
    }
    
    public int getCurrentImageIndex ( )
    {
        return currentImageIndex;
    }
    public void incCurrentImageIndex ( )
    {
        currentImageIndex ++;
    }
    public void decCurrentImageIndex ( )
    {
        currentImageIndex --;
    }
    public void setCurrentImageIndex ( int value )
    {
        currentImageIndex = value;
    }
    
    
    public void stopAnimation ( )
    {
        animationTimer.stop ( );
    }
    
    public ActionListener getAnimationListener ( )
    {
        return new ActionListener ( )
        {
            public void actionPerformed ( ActionEvent actionEvent )
            {
                if ( getCurrentImageIndex ( ) < imageList.size ( ) - 1 )
                    incCurrentImageIndex ( );
                else
                    setCurrentImageIndex ( 0 );
					
				guiPanel.CURRENT_LEVEL = getCurrentImageIndex ( );
                repaint ( );
            }
        };
    }
}
