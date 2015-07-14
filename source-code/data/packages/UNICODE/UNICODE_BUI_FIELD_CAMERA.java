package data.packages.UNICODE;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import javax.swing.JPanel;
import java.awt.AlphaComposite;

public class UNICODE_BUI_FIELD_CAMERA
{
	//attributes
	private Rectangle body = null, tempBody = null;
	private int xOrigin = 0;
	private int prebakedLowerBoundLimitForY = 0, prebakedUpperBoundLimitForY = 0;
	private int width = 0, height = 0;
	private int rightScrollTrackerForBody = 0, leftScrollTrackerForBody = 0;
	private int screenWidth = ( int ) java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getWidth ( );
	private int screenHeight = ( int ) java.awt.Toolkit.getDefaultToolkit ( ).getScreenSize ( ).getHeight ( );
	private String cameraDisplayAnswer = "";
	
    //constructor
    public UNICODE_BUI_FIELD_CAMERA ( int originAndEndSpace, int _prebakedLowerBoundLimitForY, int _prebakedUpperBoundLimitForY, UNICODE_ConfigurationManager configurationManager, String _cameraDisplayAnswer )
	{
		//compute orientation
		xOrigin = 0 + originAndEndSpace;
		
		//establish camera display answer
		cameraDisplayAnswer = _cameraDisplayAnswer;
		
		//compute dimension
		prebakedLowerBoundLimitForY = _prebakedLowerBoundLimitForY;
		prebakedUpperBoundLimitForY = _prebakedUpperBoundLimitForY;
		
		width = ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ) - originAndEndSpace * 2;
		height = prebakedLowerBoundLimitForY + ( prebakedUpperBoundLimitForY / 2 );
		
		//establish camara bdy
			//body shifts to capture newly possible buis;
			body = new Rectangle ( xOrigin, prebakedUpperBoundLimitForY, width, height );
	}
	
	//methods
		//accessors
		public Rectangle getBody ( )
		{	
			return body;
		}
		
		//functioons
		public void enableBuiComponentRendering ( Graphics graphics, Graphics2D graphics2d, UNICODE_BUI_FIELD buiField, int maxBuis, boolean lineVisibilityEnquiry, double lineRendererEntityLength, double lineRendererEntitySpatialDistance, UNICODE_DescriptionViewController descriptionViewController, ArrayList <UNICODE_BUI_SHAPE> buis, ArrayList <UNICODE_BUI_DESCRIPTOR> buiDescriptors, ArrayList <UNICODE_BUI_CONNECTOR> buiConnectors, int rotationDirection, int scrollAmountTracker )
		{
			//establish camera update
			updateCamera ( rotationDirection, scrollAmountTracker );
		
			//draw connectors ( lines )
            if ( lineVisibilityEnquiry )
			{
                for ( int i = 0; i < maxBuis; i ++ )
                    if ( ( i + 1 ) < maxBuis )	
						buiConnectors.get ( i ).drawLine ( graphics, graphics2d, i, lineRendererEntityLength, lineRendererEntitySpatialDistance, body );
			}
			
			//control bui node here go, descriptor rendering
			for ( int i = 0; i < maxBuis; i ++ )
				if ( body.contains ( buis.get ( i ).getBody ( ).getBody ( ).getX ( ), buis.get ( i ).getBody ( ).getBody ( ).getY ( ) ) )
					if ( descriptionViewController.getValue ( ).equals ( "on" ) )
					{
						buis.get ( i ).drawShape ( graphics2d );
						buiDescriptors.get ( i ).drawBuiDescriptor ( graphics, graphics2d, buiField );
					}
					else
						buis.get ( i ).drawShape ( graphics2d );

		}
		
		public void draw ( Graphics2D graphics2d, Color cameraColourForBody )
		{
			if ( cameraDisplayAnswer.equals ( "on" ) )
			{
				graphics2d.setColor ( cameraColourForBody );
				graphics2d.draw ( body );
			}
		}
		
		public void updateCamera ( int rotationDirection, int scrollAmountTracker )
		{
			//leftward scrolling
			if ( rotationDirection > 0 )
			{
				leftScrollTrackerForBody = xOrigin + scrollAmountTracker;
				
				body.setBounds ( leftScrollTrackerForBody, prebakedUpperBoundLimitForY, width, height );
			}
			
			//rightward scrolling	
			else if ( rotationDirection < 0 )
			{
				rightScrollTrackerForBody = ( int ) body.getX ( ) + scrollAmountTracker;
				
				body.setBounds ( rightScrollTrackerForBody, prebakedUpperBoundLimitForY, width, height );
			}
		}
}