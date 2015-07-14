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

public class UNICODE_Glow
{
    private BufferedImage glow = null;
    private Color colour = null;
    private float glowSpan = 0, glowIntensity = 0;
    
    public UNICODE_Glow ( Color colour, float glowSpan, float glowIntensity )
    {
        this.colour = colour;
        this.glowSpan = glowSpan;
        
        if ( glowIntensity < 0 )
            glowIntensity = 1;
        else
            this.glowIntensity = glowIntensity;
    }
    
    public void draw ( Graphics2D g, String areaType, int x, int y, int inputWidth, int inputHeight )  
    {
        Area inputArea = getArea ( areaType, inputWidth, inputHeight );
        
        int width = inputArea.getBounds ( ).width + 2;
        int height = inputArea.getBounds ( ).height + 2;
        
        glow = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2 = glow.createGraphics ( );
            
        Area area1 = new Area ( new Rectangle ( 0, 0, width, height ) );
        inputArea.transform ( AffineTransform.getTranslateInstance ( 1, 1 ) );
        
        area1.subtract ( inputArea );
        
        BufferedImage tmp = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g3 = tmp.createGraphics ( );
        g3.setPaint ( new GradientPaint ( 0, 0, colour, width, height, colour ) );
        g3.fill ( area1 );
        
        GaussianFilter gf = new GaussianFilter ( glowSpan + 1 );
        glow = gf.filter ( tmp, glow );
        
        AlphaComposite ac = AlphaComposite.getInstance ( AlphaComposite.SRC_IN, ( float  ) 0.0 );
        g2.setColor ( Color.blue );
        g2.setComposite ( ac );
        g2.fill ( area1 );
        
        for ( int i = 0; i < glowIntensity; i ++ )
            g.drawImage ( glow, null, x, y );
    }
    
    public Area getArea ( String areaType, int width, int height )
    {
        Area returnValue = null;
        
        if ( areaType.equals ( "ellipse" ) )
            returnValue =  new Area ( new Ellipse2D.Double ( 0, 0, width, height ) );
            
        return returnValue;
    }
}