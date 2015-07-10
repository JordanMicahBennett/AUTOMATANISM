package data.packages.UNICODE;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

public class UNICODE_BUI_SHAPE 
{
    //attributes
    private Shape buiShape = null;
    //runtime transformation requirements
    private AffineTransform oldAffineTransformation;
    private boolean relocationEnquiry = false;
    private double xRelocationCoord, yRelocationCoord;
    private UNICODE_BUI_BODY buiBody = null;
	private Color defaultModeBuiFolderColour = null, defaultModeBuiFileColour = null;
    private String buiEntityStream = null;
	private UNICODE_OuterGlow pointLight = null;
	private UNICODE_GlowIntensityAndSpanValueGenerator glowIntensityAndSpanValueGenerator = null;
	private UNICODE_BUI_FIELD buiField = null;
	private int initialX, initialY, initialWidth, initialHeight;
	private UNICODE_ConveniencePack conveniencePack = null;
	private Dimension screenDimension = null;
	private double maximalInfoverseBufferDivisor = 0.0;
	private double estimatedDecriptorBodyWidth;
	
    //constructor 0 
    public UNICODE_BUI_SHAPE ( String _buiEntityStream, String buiEntitySearchName, int x, int y, int width, int height, int buiIndex, int folderIndicatorScaleFactor, Color _defaultModeBuiFolderColour, Color _defaultModeBuiFileColour, Color _glowCoreColour, UNICODE_BUI_FIELD buiField )
    {
		//estalish conveniencePack
		conveniencePack = new UNICODE_ConveniencePack ( );
	
		//establish entity stream
		buiEntityStream = _buiEntityStream;
		
		//establish bui field
		this.buiField = buiField;
		
		//setup dimension and orientation for glow reset function
		this.initialX = x;
		this.initialY = y;
		this.initialWidth = width;
		this.initialHeight = height;
		
		//establish colour scheme
		defaultModeBuiFolderColour = _defaultModeBuiFolderColour;
		defaultModeBuiFileColour = _defaultModeBuiFileColour;
		
        buiBody = new UNICODE_BUI_BODY ( buiEntityStream, buiEntitySearchName, x, y, width, height, buiIndex, folderIndicatorScaleFactor );
        buiShape = buiBody.getBody ( );
		
		//establish glow requirements
		glowIntensityAndSpanValueGenerator = new UNICODE_GlowIntensityAndSpanValueGenerator ( "yes", 200, 1, 25 );
		pointLight = new UNICODE_OuterGlow ( buiField.getGlowCoreColour ( ), glowIntensityAndSpanValueGenerator.getSpanValue ( buiBody.getEntityFile ( ), width ), glowIntensityAndSpanValueGenerator.getIntensityValue ( buiBody.getEntityFile ( ) ), "ellipse", x, y, width, height );
    }	
	
	//constructor 1
    public UNICODE_BUI_SHAPE ( String _buiEntityStream, String buiEntitySearchName, int x, int y, int width, int height, int buiIndex, int folderIndicatorScaleFactor, Color _defaultModeBuiFolderColour, Color _defaultModeBuiFileColour, Color _glowCoreColour, UNICODE_BUI_FIELD buiField, Dimension screenDimension, double maximalInfoverseBufferDivisor )
    {
		//estalish conveniencePack
		conveniencePack = new UNICODE_ConveniencePack ( );
	
		//establish entity stream
		buiEntityStream = _buiEntityStream;
		
		//establish bui field
		this.buiField = buiField;
		
		//setup dimension and orientation for glow reset function
		this.initialX = x;
		this.initialY = y;
		this.initialWidth = width;
		this.initialHeight = height;
			
		//establish colour scheme
		defaultModeBuiFolderColour = _defaultModeBuiFolderColour;
		defaultModeBuiFileColour = _defaultModeBuiFileColour;
		
        buiBody = new UNICODE_BUI_BODY ( buiEntityStream, buiEntitySearchName, x, y, width, height, buiIndex, folderIndicatorScaleFactor );
        buiShape = buiBody.getBody ( );
		
		//establish estiimated descriptor body width
		this.screenDimension = screenDimension;
		this.maximalInfoverseBufferDivisor = maximalInfoverseBufferDivisor;
		this.estimatedDecriptorBodyWidth = conveniencePack.getByteFileSizeAsRatioOfScreenWidth ( buiBody.getEntityStream ( ), screenDimension, ( int ) maximalInfoverseBufferDivisor );
		
		//establish glow requirements
		glowIntensityAndSpanValueGenerator = new UNICODE_GlowIntensityAndSpanValueGenerator ( "yes", 200, 1, 25 );
		pointLight = new UNICODE_OuterGlow ( buiField.getGlowCoreColour ( ), glowIntensityAndSpanValueGenerator.getSpanValue ( buiBody.getEntityFile ( ), width ), glowIntensityAndSpanValueGenerator.getIntensityValue ( buiBody.getEntityFile ( ) ), "ellipse", x, y, width, height );
    }
    
    //misc
    public void drawShape ( Graphics2D graphics2d )
    {
        //DRAW SHAPE, WHILE CONTROLLING WHICH SHAPE MAY BE RELOCATED @ RUNTIME.
            //establish saved affine transformation
            establishOldAffineTransform ( graphics2d );
			  
			//set bui node colour
			if ( buiBody.representsDirectory ( buiEntityStream ) )
				graphics2d.setColor ( defaultModeBuiFolderColour );
			else 
				graphics2d.setColor ( defaultModeBuiFileColour );
			
            //translate shape
            if ( relocationEnquiry )
                graphics2d.translate ( xRelocationCoord, yRelocationCoord );
            //draw bui
            //graphics2d.fill ( buiShape );
			pointLight.draw ( graphics2d );
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            //RESET PAINT COMPONENT GRAPHICS2D TRANSFORM, TO PREVENT TRANSLATION of non-selected buttons, or other 
            //objects and components currently in the graphics2d context
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            //reset stransformation so that other components are unaffected
            restoreOldTransform ( graphics2d );   
    }
	
	public void resetGlow ( UNICODE_ConfigurationManager configurationManager )
	{
		switch ( buiField.getCoreGlowIndex ( ) )
		{
			case 0: buiField.setGlowCoreColour ( configurationManager.getSecurityGlowCoreColourFromFile ( ) ); break;
			case 1: buiField.setGlowCoreColour ( configurationManager.getInfotainmentGlowCoreColourFromFile ( ) ); break;
			case 2: buiField.setGlowCoreColour ( configurationManager.getDefaultGlowCoreColourFromFile ( ) ); break;
		}
		
		pointLight.changeColour ( buiField.getGlowCoreColour ( ) );
	}

    public Shape getShape ( )
    {
        return buiShape;
    }
	
	public double getEstimatedDescriptorBodyWidth ( )
	{
		return estimatedDecriptorBodyWidth;
	}
    
    public UNICODE_BUI_BODY getBody ( )
    {
        return buiBody;
    }
    public boolean getRelocationEnquiry ( )
    {
        return relocationEnquiry;
    }
    
    public void setRelocationEnquiry ( boolean value )
    {
        relocationEnquiry = value;
    }
    
    public void setRelocationOrientation ( double xValue, double yValue )
    {
        xRelocationCoord = xValue;
        yRelocationCoord = yValue;
    }
    
    public String getRelocationString ( )
    {
        return "" + xRelocationCoord + "," + yRelocationCoord;
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
