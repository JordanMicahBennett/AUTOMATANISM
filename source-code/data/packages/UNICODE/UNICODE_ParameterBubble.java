//Author(s): Jordan Micah Bennett
package data.packages.UNICODE;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UNICODE_ParameterBubble
{
	//attributes
	private Ellipse2D parameterBubble = null;
	private UNICODE_ConveniencePack conveniencePack = null;
	
	
	//properties
	private int initialSpan = 0;
	
	//field scrolling requriements
	private UNICODE_BUI_FIELD buiField = null;
	private int scrollLowerLimit = 0, scrollUpperPadding = 0;
	private int bufferWidth = 0;
	
	//establish state enquiry
	private boolean parameterBubbleEnableEnquiry = false;

	//estalish stamp requirements
		//actual stamps
		private ArrayList <Ellipse2D> parameterStamps = null;
		//establish bui descriptors var to be initilized via bui field var.
		private ArrayList <UNICODE_BUI_DESCRIPTOR> buiDescriptors = null;
		
	//establish action window requirements
		//establish action threads
		private Thread thread0 = null, thread1 = null, thread2 = null, thread3 = null;
		//establish the box itself
		private UNICODE_MessageBoxWindow actionBox = null;
		
	//establish line style
	private Stroke descriptorEmbracerLineStyle = null;
	
	//establish spining animation requirements
	private UNICODE_RotationManager rotationManager = null;
	private Timer bubbleAnimationTimer = null;
			
	//constructor
	public UNICODE_ParameterBubble ( int initialSpan, int scrollLowerLimit, int scrollUpperPadding, int bufferWidth, UNICODE_BUI_FIELD buiField )
	{
		this.initialSpan = initialSpan;
		
		//establish field scroling requirments
		this.scrollLowerLimit = scrollLowerLimit;
		this.scrollUpperPadding = scrollUpperPadding;
		this.bufferWidth = bufferWidth;
		this.buiField = buiField;
		this.parameterStamps = new ArrayList <Ellipse2D> ( );
		this.buiDescriptors = buiField.getDescriptorList ( );
		this.rotationManager = new UNICODE_RotationManager ( );
		
		conveniencePack = new UNICODE_ConveniencePack ( );
		parameterBubble = new Ellipse2D.Double ( ( int ) getMouseLocation ( ).getX ( ), ( int ) getMouseLocation ( ).getY ( ), initialSpan, initialSpan );
		
		this.descriptorEmbracerLineStyle = new BasicStroke ( 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float [ ] { 2, 4 }, 0 );
		
		//embracer anim requirements
		this.bubbleAnimationTimer = new Timer ( 10, new bubbleAnimationListener ( ) );	
	}
	
	//methods
		//accessors
		public boolean getParameterBubbleEnableEnquiry ( )
		{
			return parameterBubbleEnableEnquiry;
		}
		public Point getMouseLocation ( )
		{
			Point returnValue = null;
			
			PointerInfo pointerInformation = MouseInfo.getPointerInfo ( );
			
			returnValue = pointerInformation.getLocation ( );
			
			return returnValue;
		}
				
		public boolean getStampExistenceEnquiry ( )
		{
			return parameterStamps.size ( ) != 0;
		}
		
		public boolean getActionBoxExistenceEnquiry ( )
		{
			return actionBox != null;
		}
		
		
		//mutators
		public void setParameterBubbleEnableEnquiry ( boolean value )
		{
			parameterBubbleEnableEnquiry = value;
		}

		public void updateParameterBubbleSize ( int rotationDirection, int bubbleAlterationRate )
		{
			int postiveSpan = ( int ) ( parameterBubble.getWidth ( ) + bubbleAlterationRate );
			int negativeSpan = ( int ) ( parameterBubble.getWidth ( ) - bubbleAlterationRate );
			
			//increment size
			if ( rotationDirection < 0 ) 
				parameterBubble = new Ellipse2D.Double ( ( int ) parameterBubble.getX ( ), ( int ) parameterBubble.getY ( ), postiveSpan, postiveSpan );
			//decrement size
			else if ( rotationDirection > 0 ) 
				parameterBubble = new Ellipse2D.Double ( ( int ) parameterBubble.getX ( ), ( int ) parameterBubble.getY ( ), negativeSpan, negativeSpan );
		}
		
		// public void allowBuiFieldSliding ( MouseEvent mouseEvent )
		// {
			// if ( parameterBubbleEnableEnquiry )
			// {
				// //if parameter bubble is being moved rightwards.
				// if ( mouseEvent.getX ( ) > ( bufferWidth - scrollUpperPadding ) )
					// buiField.slide ( "rightwards" );
				// else if ( mouseEvent.getX ( ) < scrollLowerLimit )
					// buiField.slide ( "leftwards" );
			// }
		// }
		
		public void allowBuiFieldSliding ( MouseEvent mouseEvent )
		{		
			PointerInfo pointerInformation = MouseInfo.getPointerInfo ( );
			Point cursorX = pointerInformation.getLocation ( );
			int cursorXLocation = ( int ) cursorX.getX ( );
			
			if ( parameterBubbleEnableEnquiry )
			{
				//if parameter bubble is being moved rightwards.
				if ( cursorXLocation > ( bufferWidth - scrollUpperPadding ) ) 
					buiField.slide ( "rightwards" );
				else if ( cursorXLocation < scrollLowerLimit )
					buiField.slide ( "leftwards" );
			}
		}
		
		public void enableMouseMovementResponse ( MouseEvent mouseEvent )
		{
			allowBuiFieldSliding ( mouseEvent );
		}
		
		public void enableMouseClickRespose ( )
		{
			parameterStamps.add ( parameterBubble );
			toggleDescriptorSelectionOn ( );
		}
		
		public void updateParameterBubbleLocation ( MouseEvent mouseEvent )
		{
			parameterBubble = new Ellipse2D.Double ( ( int ) mouseEvent.getX ( ), ( int ) mouseEvent.getY ( ), ( int ) parameterBubble.getWidth ( ), ( int ) parameterBubble.getHeight ( ) );
		}
	
		//other
		public void draw ( Graphics2D graphics2d, Color colour )
		{
			//save old transformation
			rotationManager.saveOldTransform ( graphics2d );
			
			if ( getParameterBubbleEnableEnquiry ( ) )
			{
				//apply rotation weh rotation bool is set true
				if ( rotationManager.getRotationEnquiry ( ) )
				{
					rotationManager.toggleTransformation ( graphics2d, ( int ) parameterBubble.getX ( ), ( int ) parameterBubble.getWidth ( ), ( int ) parameterBubble.getY ( ), ( int ) parameterBubble.getHeight ( ) );
					
					graphics2d.setColor ( colour );
					graphics2d.setStroke ( descriptorEmbracerLineStyle );
					graphics2d.draw ( parameterBubble );
				}	
		
				
				/*
				//DRAW PARAM STAMPS - TESTING
				//the following two lines:
				//allows for the generation of translucent effects on the fly.
				//Attained via: http://www.java-gaming.org/index.php/topic,1546.
				System.setProperty ( "sun.java2d.translaccel", "true" );
				System.setProperty ( "sun.java2d.ddforcevram", "true" ); 
				
				graphics2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 0.8f ) ); //set alpha composite of descriptor 
				
				for ( Ellipse2D parameterStamp : parameterStamps )
					graphics2d.fill ( parameterStamp );
				*/
			}
			//restore old affine transform
			rotationManager.restoreOldTransform ( graphics2d );
		}
		
		public void resetParameterStamps ( )
		{
			parameterStamps.clear ( );
		}
		
		public void toggleActionBox ( Thread [ ] threadArray )
		{
			setupThreads ( threadArray ); //renew thread pool to avert thread re-use error, @ each released event.
			
			actionBox = new UNICODE_MessageBoxWindow ( true, thread0, thread1, thread2, thread3, "copy | cut | delete", 0.9f,  new Color ( 0, 0, 0 ), Color.black, Color.gray, Color.white, true, "data/images/all/message box/dynamic file manipulation/","rr",  20, 20, 10, 10, 0, 8 );
		}
		
		public void setupThreads ( Thread [ ] threadArray )
		{
			//setup threads
			thread0 = threadArray [ 0 ];
			thread1 = threadArray [ 1 ];
			thread2 = threadArray [ 2 ];
			thread3 = threadArray [ 3 ];
		}
		
		//restore descriptors, that is when bubble mode is de-toggled.
		public void toggleDescriptorSelectionOff ( )
		{
			for ( UNICODE_BUI_DESCRIPTOR buiDescriptor : buiDescriptors )
				if ( buiDescriptor.getSelectionEnquiry ( ) )
					buiDescriptor.setSelectionEnquiry ( false );
		}
		
		public void reset ( JPanel context )
		{
			parameterStamps.clear ( );
		    toggleDescriptorSelectionOff ( );
			setParameterBubbleEnableEnquiry ( false );
			conveniencePack.restoreCursor ( context );
			resetParameterStamps ( );
			hideActionBox ( );
		}
		
		public void hideActionBox ( )
		{
			//we don't want to hide something that isn't there.
			//the action box only manifests when parameterBubbleEnableEnquiry is true. so we only attempt to modify actionBox only if it had manifested.
			if ( getActionBoxExistenceEnquiry ( ) ) 
			{
				actionBox.setVisible ( false );
				actionBox = null;
			}
		}
		
		
		public void toggleParameterBubbleEnableEnquiry ( JPanel context, Thread [ ] threadArray )
		{
			if ( getParameterBubbleEnableEnquiry ( ) )
			{
				//we only want to disable bubble mode when
				//no stamps exist.
				if ( !getStampExistenceEnquiry ( ) )
				{
					buiField.getBuiActionDock ( ).audioPlayer.playAudio ( "parameterBubbleDisabledVoice.wav" );
					setParameterBubbleEnableEnquiry ( false );
					conveniencePack.restoreCursor ( context );
					resetParameterStamps ( );
					bubbleAnimationTimer.stop ( );
				}
				//if some do exist, we want to bring up a message asking the user what the fuck he desires to do with his selected files.
				//this is where we will either allow deletion, copying, cutting of files, or simply cancelation of the menu option.
				//cancelation will yield that:
				//1.param stamps are cleared.
				//2.reset of descriptor selection
				//3.param bubbble mode falsification.
				else
				{
					toggleActionBox ( threadArray );
					buiField.getBuiActionDock ( ).audioPlayer.playAudio ( "fileOptionsPrepared.wav" );
				}
			}
			else
			{
				bubbleAnimationTimer.start ( );
				setParameterBubbleEnableEnquiry ( true );
				conveniencePack.hideCursor ( context );
				buiField.getBuiActionDock ( ).audioPlayer.playAudio ( "parameterBubbleEnabledVoice.wav" );
			}
		}
		
		public void toggleDescriptorSelectionOn ( )
		{
			//for each descriptor caught in any stamp, draw embracers around them!
			for ( Ellipse2D parameterStamp : parameterStamps )
				for ( UNICODE_BUI_DESCRIPTOR buiDescriptor : buiDescriptors )
				{
					int buiEmbracerX = ( int ) buiDescriptor.getBody ( ).getX ( );
					int buiEmbracerY = ( int ) buiDescriptor.getBody ( ).getY ( );
					
					if ( parameterStamp.contains ( buiEmbracerX, buiEmbracerY ) )
					{
						buiDescriptor.setSelectionEnquiry ( true );
						buiDescriptor.startEmbracerAnimation ( );
					}
				}
		}
		
		public void establishMouseReleasedEvent ( Thread [ ] threadArray )
		{
			setupThreads ( threadArray ); //renew thread pool to avert thread re-use error, @ each released event.
		}
		
	private class bubbleAnimationListener implements ActionListener 
	{
		public void actionPerformed ( ActionEvent actionEvent )
		{
			rotationManager.toggleAction ( 0.0224f, "anti-clockwise" );
			buiField.repaint ( );
		}
	}
}