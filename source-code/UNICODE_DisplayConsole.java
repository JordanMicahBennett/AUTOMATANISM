//author : jordan micah bennett

import javax.swing.JFrame;
import java.awt.GraphicsDevice.WindowTranslucency.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;
import java.awt.Toolkit;
import java.awt.Dimension;
import data.packages.UNICODE.*;

public class UNICODE_DisplayConsole 
{

    //ESTABLISH FRAME
    static JFrame frame = new JFrame ( );
    static Dimension frameDimension = new Dimension ( 800, 600 );

    //establish main program gui panel stuff
        //establish covenience pack
        static UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
        //establish duration pack for all splash panels //NOT RECORDING~ { 80, 50, 60, 1600, 400 }; //RECORDING~{ 400, 400, 400, 2600, 800 }; //TESTING~{ 8, 8, 8, 8, 8 };
        //REC 2:{ 900, 900, 900, 4400, 800 } ................ { 900, 900, 900, 4400, 800 }
        static int [ ] splashPanelDurationCollection = { 400, 400, 400, 400, 400 };
        //establsih gui panel: takes frame,
        static Environment environmentPanel = new Environment ( frame, frameDimension );
        //establish screen dimensions
        static Dimension screenDimension = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        
    //establish splash screen panel stuff
        static SplashPanelGenericInstance splashPanelI0 = null, splashPanelI1 = null, splashPanelI2 = null, splashPanelI3 = null;
        static SplashPanelGenericInstance splashPanelII = null;
        
    public static void main ( String [ ] args ) 
    {
        //main program stuff
        Thread mainPogramThread = new Thread
        (
            new Runnable ( )
            {
                public void run ( )
                {
                    new UNICODE_AudioPlayer ( ).playAudio ( "startupVoice1.wav" );
                    //establish Jframe stuff
                        //remove splash panel
                        frame.remove ( splashPanelII ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                        //add main program panel
                        frame.add ( environmentPanel );
                        //corectly display all panel components by passing panel to set content pane
                        frame.setContentPane ( environmentPanel ); /*translucency requirement*/
                        //set application window dimension
                        frame.setSize ( frameDimension );
                        //center application on screen buffer based on user's screen size
                        frame.setLocationRelativeTo ( null );
                        //set frame shape
                        frame.setShape ( new Rectangle2D.Double ( 0, 0, screenDimension.getWidth ( ), screenDimension.getHeight ( ) ) );
                        //enable full screen mode
                        //conveniencePack.enableFullScreenMode ( frame );
                        //set opacity
                        frame.setOpacity ( 0.8f );
                }
            }
        );

        
        //splash screen stuff
            //ENGINE LOGO
            splashPanelII = new SplashPanelGenericInstance ( splashPanelDurationCollection [ 4 ], "data/images/splashii.png", mainPogramThread, null ); 
            Thread splashPanelIIThread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupVoice.wav" );
                        //establish Jframe stuff      
                            //clear previous splash panel from frame.
                            frame.remove ( splashPanelI3 ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                            //add gui panel
                            frame.add ( splashPanelII );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelII ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 500, 220 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 500, 220, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            
            //OS NAME LOGO
            splashPanelI3 = new SplashPanelGenericInstance ( splashPanelDurationCollection [ 3 ], "data/images/splashi3.png", splashPanelIIThread, splashPanelII ); 

            Thread splashPanelI3Thread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupStage0c.wav" );
                        //establish Jframe stuff      
                            //clear previous splash panel from frame.
                            frame.remove ( splashPanelI2 ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                            //add gui panel
                            frame.add ( splashPanelI3 );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelI3 ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 400, 280 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 400, 280, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            splashPanelI2 = new SplashPanelGenericInstance ( splashPanelDurationCollection [ 2 ], "data/images/splashi2.png", splashPanelI3Thread, splashPanelI3 ); 
            Thread splashPanelI2Thread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupStage0b.wav" );
                        //establish Jframe stuff      
                            //clear previous splash panel from frame.
                            frame.remove ( splashPanelI1 ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                            //add gui panel
                            frame.add ( splashPanelI2 );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelI2 ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 400, 280 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 400, 280, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            
            splashPanelI1 = new SplashPanelGenericInstance ( splashPanelDurationCollection [ 1 ], "data/images/splashi1.png", splashPanelI2Thread, splashPanelI2 ); 
            Thread splashPanelI1Thread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {  
                        new UNICODE_AudioPlayer ( ).playAudio ( "startupStage0a.wav" );
                        //establish Jframe stuff      
                            //clear previous splash panel from frame.
                            frame.remove ( splashPanelI0 ); //splashPanel has been defined at this point of run time. Now we can remove splash panel from the frame, and add the main program panel instead.
                            //add gui panel
                            frame.add ( splashPanelI1 );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelI1 ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 400, 280 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 400, 280, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            splashPanelI0 = new SplashPanelGenericInstance ( splashPanelDurationCollection [ 0 ], "data/images/splashi0.png", splashPanelI1Thread, splashPanelI1 ); 
            Thread splashPanelI0Thread = new Thread
            (
                new Runnable ( )
                {
                    public void run ( )
                    {
                        new UNICODE_AudioPlayer ( ).playAudio ( "illumium~ambience.wav" );
                        //establish Jframe stuff
                            //establish look and feel
                            frame.setDefaultLookAndFeelDecorated ( true ); /*translucency requirement*/
                            //remove frame from window
                            frame.setUndecorated ( true );
                            //add gui panel
                            frame.add ( splashPanelI0 );
                            //corectly display all panel components by passing panel to set content pane
                            frame.setContentPane ( splashPanelI0 ); /*translucency requirement*/
                            //set application window dimension
                            frame.setSize ( new Dimension ( 400, 280 ) );
                            //center application on screen buffer based on user's screen size
                            frame.setLocationRelativeTo ( null );
                            //set frame shape
                            frame.setShape ( new RoundRectangle2D.Double ( 0, 0, 400, 280, 20, 20 ) );  
                            //show the frame
                            frame.setVisible ( true );
                    }
                }
            );
            splashPanelI0.commence ( );
            splashPanelI0Thread.start ( );
    }

}