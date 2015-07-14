//:---------------------------------------------:
//:--: Author: Jordan Micah Bennett
//:--: Title: Food Frenzy Media Player
//:---------------------------------------------:
package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.awt.Component;
import java.net.URL;
import javax.media.Manager;
import javax.media.Player;
import javax.media.Control;
import javax.media.Time;
import javax.media.control.FramePositioningControl;
import javax.swing.JPanel;
import java.io.File;
import java.awt.BorderLayout;

public class UNICODE_MediaPlayer extends JPanel 
{
	//attributes
	private Player player = null;
	private Component video = null, controller = null;
	private FramePositioningControl framePositioningControl = null;
	private boolean videoStartEnquiry = false;
	private boolean audioOnEnquiry = false;
	
    //constructor
    public UNICODE_MediaPlayer ( URL url )
    {
        setLayout ( new BorderLayout ( ) );
        try
        {
            player = Manager.createRealizedPlayer ( url ); //stream url to manager
            video = player.getVisualComponent ( ); //create visual segment
            controller = player.getControlPanelComponent ( ); //create visual segment
			framePositioningControl = ( FramePositioningControl ) player.getControl ( "javax.media.control.FramePositioningControl" ); //create frame positioning controller
			//control = player.getControlComponent ( );
            add ( video, BorderLayout.CENTER ); //add video to this panel
            //add ( controller, BorderLayout.SOUTH ); //add video controller to this panel
        }
        catch ( Exception error )
        {
        }
    }

	//accessors
	public Component getVideo ( )
	{
		return video;
	}
	
	public Component getController ( )
	{
		return controller;
	}

	public Player getPlayer ( )
	{
		return player;
	}
	
	public double getMillisecondDuration ( )
	{
		return player.getDuration ( ).getSeconds ( ) * 1000;
	}
	
	public boolean getVideoStartEnquiry ( )
	{
		return videoStartEnquiry;
	}
	
	public void triggerVideoDisplayOnOff ( )
	{
		if ( !videoStartEnquiry )
		{
			videoStartEnquiry = true;
			player.start ( );
		}
		else
		{
			player.stop ( );
			videoStartEnquiry = false;
		}	
	}
	
                        
	public void playVideo ( )
	{
		player.start ( );
	}
	
	public void stopVideo ( )
	{
		player.stop ( );
	}
	
	public void triggerVideoAudioOnOff ( )
	{
		if ( !audioOnEnquiry )
		{
			audioOnEnquiry = true;
			player.getGainControl ( ).setMute ( true );
		}
		else
		{
			player.getGainControl ( ).setMute ( false );
			audioOnEnquiry = false;
		}	
	}
	
	public void triggerVideoFramePosition ( int skipRate )
	{
		framePositioningControl.skip ( skipRate );
	}
}
