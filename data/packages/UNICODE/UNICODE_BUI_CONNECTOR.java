package data.packages.UNICODE;
import java.awt.geom.Line2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.QuadCurve2D;

public class UNICODE_BUI_CONNECTOR
{
    //attributes
    private UNICODE_GlowingLine buiConnector = null;
    //runtime transformation requirements
    private AffineTransform oldAffineTransformation;
    //establish bui shape
    private ArrayList <UNICODE_BUI_SHAPE> buiShapes = null;
	//establish description view controler answer
	private String descriptionViewControllerAnswer = "";
	//establish colour scheme
	private Color connectorColour = null, connectorGlowColour = null;
	//glow properties
	private int connectorThickness = 0, connectorGlowThickness = 0, connectorGlowQualityMultiplier = 0, connectorGlowBrightness = 0;
	//UPDATE: dotted line rendering
	private UNICODE_LineRenderer lineRenderer = null;
	
	
    //constructor
    public UNICODE_BUI_CONNECTOR ( int x1, int y1, int x2, int y2, ArrayList <UNICODE_BUI_SHAPE> _buiShapes, String _descriptionViewControllerAnswer, Color _connectorColour, Color _connectorGlowColour, int _connectorThickness, int _connectorGlowThickness, int _connectorGlowQualityMultiplier, int _connectorGlowBrightness )
    {
		//establish description view controller answer
		descriptionViewControllerAnswer = _descriptionViewControllerAnswer;
		
		//establish colour scheme
		connectorColour = _connectorColour;
		connectorGlowColour = _connectorGlowColour;
		
		//establish glow properties
		connectorThickness = _connectorThickness; 
		connectorGlowThickness = _connectorGlowThickness;
		connectorGlowQualityMultiplier = _connectorGlowQualityMultiplier;
		connectorGlowBrightness = _connectorGlowBrightness;
	
        //establish bui shape
        buiShapes = _buiShapes;
        
        //establish line 2, 10, 2, 0,
        buiConnector = new UNICODE_GlowingLine ( connectorThickness, connectorGlowThickness, connectorGlowQualityMultiplier, connectorGlowBrightness, connectorColour, connectorGlowColour.getRed ( ), connectorGlowColour.getGreen ( ), connectorGlowColour.getBlue ( ) );
    
		//establish connector
		lineRenderer = new UNICODE_LineRenderer ( );
	}
    
    public UNICODE_GlowingLine getBody ( )
    {
        return buiConnector;
    }
    
    public void drawLine ( Graphics graphics, Graphics2D graphics2d, int buiShapesIndex, double dotWidth, double dotSpace, Rectangle buiCameraFrame )
    {
        //DRAW SHAPE, WHILE CONTROLLING WHICH SHAPE MAY BE RELOCATED @ RUNTIME.
            //establish saved affine transformation
            // establishOldAffineTransform ( graphics2d );
            //translate line
            // if ( buiShapes.get ( buiShapesIndex ).getRelocationEnquiry ( ) )
            // {
//                 if ( ( buiShapesIndex + 1 ) < maxBuis )
//                 {
                    double x1 = buiShapes.get ( buiShapesIndex ).getBody ( ).getBody ( ).getX ( );
                    double x2 = buiShapes.get ( buiShapesIndex + 1 ).getBody ( ).getBody ( ).getX ( );
                    double y1 = buiShapes.get ( buiShapesIndex ).getBody ( ).getBody ( ).getY ( ) + buiShapes.get ( buiShapesIndex ).getBody ( ).getBody ( ).getHeight ( ) / 2;
                    double y2 = buiShapes.get ( buiShapesIndex + 1 ).getBody ( ).getBody ( ).getY ( ) + buiShapes.get ( buiShapesIndex ).getBody ( ).getBody ( ).getHeight ( ) / 2;
                    
                    //buiConnector.setOrientation ( ( int ) x1, ( int ) y1, ( int ) x2, ( int ) y2 );
					

//                 }
            // }
                
            //draw bui connector
			//buiConnector.draw ( graphics );
            lineRenderer.drawDottedLine ( graphics, ( int ) x1, ( int ) y1, ( int ) x2, ( int ) y2, dotWidth, dotSpace, connectorColour, buiCameraFrame );
			
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            //RESET PAINT COMPONENT GRAPHICS2D TRANSFORM, TO PREVENT TRANSLATION of non-selected buttons, or other 
            //objects and components currently in the graphics2d context
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            //reset stransformation so that other components are unaffected
            //restoreOldTransform ( graphics2d );   
    }
    
    //re-location after startup related accessors and mutators
        public AffineTransform getOldAffineTransform ( )
        {
            return oldAffineTransformation;
        }
        //establish old affine transform ( used in draw function )
        public void establishOldAffineTransform ( Graphics2D graphics2d )
        {
            oldAffineTransformation = graphics2d.getTransform ( );
        }
        //restore old transform so as to not affect other un-selected buttons or 
        //graphics 2d component or object currently on screem
        //only selected items must be scaled, this ensures this.
        public void restoreOldTransform ( Graphics2D graphics2d )
        {
            graphics2d.setTransform ( getOldAffineTransform ( ) );
        }
}
