package data.packages.UNICODE;

//original author Dan From <dan.from@gmail.com>
//modified by : Jordan Micah Bennett
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import data.packages.JHLABS.GaussianFilter; 

public class UNICODE_OuterGlow
{
    //establish descriptors
    private Color colour = null;
    private float glowImageSpan = 0, glowImageIntensity = 0;
    
    //establish graphics components
    private Graphics2D graphicsContextI = null, graphicsContextII = null;
    private GaussianFilter gaussianFilter = null;
    private AlphaComposite alphaCompositor = null;
    
    //establish bodies
        //establish buffered images
        private BufferedImage glowImage = null, temporaryGlowImage = null;
        //establish java area to encompass glow volume
            //( area descriptors )
            private String areaType;
            private int areaX, areaY, subsequentAreaWidth, subsequentAreaHeight;
            private int irradiance, irradianceTransformWidthSpace, irradianceTransformHeightSpace;
            //( physical area )
            private Area defaultArea = null, subsequentArea = null;
            
    
    public UNICODE_OuterGlow ( Color colour, float glowImageSpan, float glowImageIntensity, String areaType, int areaX, int areaY, int inputWidth, int inputHeight )
    {
        //establish descriptors
        this.colour = colour;
        this.glowImageSpan = glowImageSpan;
        
        if ( glowImageIntensity < 0 )
            glowImageIntensity = 1;
        else
            this.glowImageIntensity = glowImageIntensity;            
            
        //added by Jordan Micah Bennett - IRRADIANCE is the variable I introduced freshly, to recognize new space for outward span.
        //This will allow for the production of values, that will yield space for glow growth.
        this.irradiance = inputWidth * 2;            
        this.irradianceTransformWidthSpace = irradiance / 2;
        this.irradianceTransformHeightSpace = irradiance / 2;
        
        //establish area descriptors
        this.areaType = areaType;
        this.areaX = areaX - irradianceTransformWidthSpace;
        this.areaY = areaY - irradianceTransformHeightSpace;
        
        
        //establish area
            //establish default area, wrt area type, and input dimensions. This area will determine this class' area dimensions.
            defaultArea = getArea ( areaType, inputWidth, inputHeight );

            //establish required dimensions
            this.subsequentAreaWidth = defaultArea.getBounds ( ).width + irradiance;
            this.subsequentAreaHeight = defaultArea.getBounds ( ).height + irradiance;
            
        //establish glow image, wrt subsequent dimension, based on defaultArea dimensions.
        glowImage = new BufferedImage ( subsequentAreaWidth, subsequentAreaHeight, BufferedImage.TYPE_INT_ARGB );
        
        //establish subsequent area, wrt subsequent dimensions
        this.subsequentArea = getArea ( areaType, subsequentAreaWidth, subsequentAreaHeight );

        
        //establish the order of defaultArea, together with the subsequentArea.
        //That is, adjust these areas to prepare adequately for the achievement of the desired glow effect.
        //Ergo, we modify our subsequentArea ( subsequent area ), in terms of a transformed defaultArea.
        defaultArea.transform ( AffineTransform.getTranslateInstance ( irradianceTransformWidthSpace, irradianceTransformHeightSpace ) );
        //subsequentArea.subtract ( defaultArea );
        
        //establish temporary glow image wrt subsequentArea dimensions.
        temporaryGlowImage = new BufferedImage ( subsequentAreaWidth, subsequentAreaHeight, BufferedImage.TYPE_INT_ARGB );
        
        //establish graphics contexts wrt to glow images.
        graphicsContextI = glowImage.createGraphics ( );
        graphicsContextII = temporaryGlowImage.createGraphics ( );
        graphicsContextII.setColor ( Color.red );
        //establish the context's ( that pertains to default context ) properties; setup and define its view content.
        graphicsContextII.setPaint ( new GradientPaint ( 0, 0, colour, subsequentAreaWidth, subsequentAreaHeight, colour ) );
        
        
        graphicsContextII.fill ( defaultArea );
        
        //establish gaussian filter, which is a requirment of our final glow image. (here is where the glows span is determined)
        gaussianFilter = new GaussianFilter ( glowImageSpan + 1 );
        
        //establish glow image wrt gaussianFilter aformentioned
        glowImage = gaussianFilter.filter ( temporaryGlowImage, glowImage );
        
        //establish compositor for final blending. 
        alphaCompositor = AlphaComposite.getInstance ( AlphaComposite.SRC_IN, ( float  ) 0.0 );
        
        //finally, establish components of subsquent nature; inclusive of subsquent graphics context, and the area prescribed with blended glow data outline-subsequentArea.
        //graphicsContextI.setColor ( Color.white );
        graphicsContextI.setComposite ( alphaCompositor );
    }
    
    public void draw ( Graphics2D g )  
    {
        for ( int i = 0; i < glowImageIntensity; i ++ )
            g.drawImage ( glowImage, null, areaX, areaY );
    }
	
	public void changeColour ( Color value )
	{
		colour = value;
        //establish the context's ( that pertains to default context ) properties; setup and define its view content.
        graphicsContextII.setPaint ( new GradientPaint ( 0, 0, colour, subsequentAreaWidth, subsequentAreaHeight, colour ) );
        
        graphicsContextII.fill ( defaultArea );
        
        //establish gaussian filter, which is a requirment of our final glow image. (here is where the glows span is determined)
        gaussianFilter = new GaussianFilter ( glowImageSpan + 1 );
        
        //establish glow image wrt gaussianFilter aformentioned
        glowImage = gaussianFilter.filter ( temporaryGlowImage, glowImage );
        
        //establish compositor for final blending. 
        alphaCompositor = AlphaComposite.getInstance ( AlphaComposite.SRC_IN, ( float  ) 0.0 );
        
        //finally, establish components of subsquent nature; inclusive of subsquent graphics context, and the area prescribed with blended glow data outline-subsequentArea.
        //graphicsContextI.setColor ( Color.white );
        graphicsContextI.setComposite ( alphaCompositor );
	}
	
	
    
    public Area getArea ( String areaType, int width, int height )
    {
        Area returnValue = null;
        
        if ( areaType.equals ( "ellipse" ) )
            returnValue =  new Area ( new Ellipse2D.Double ( 0, 0, width, height ) );
            
        if ( areaType.equals ( "rectangle" ) )
            returnValue =  new Area ( new Rectangle ( 0, 0, width, height ) );
            
        return returnValue;
    }
}