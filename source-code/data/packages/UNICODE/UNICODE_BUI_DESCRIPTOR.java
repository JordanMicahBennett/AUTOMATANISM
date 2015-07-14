package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.awt.geom.Ellipse2D;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.ArrayList;

import data.packages.UNICORTEX.*;

public class UNICODE_BUI_DESCRIPTOR
{
	//attributes
	private UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );	
	private Shape buiDescriptorShape = null;
	private Ellipse2D buiDescriptorBody = null;
	private Ellipse2D buiDescriptorBodyEmbracer = null;
	private String buiDescriptionText = "";
	private Color descriptionModeBuiFolderColour = null, descriptionModeBuiFileColour = null;
	private Color descriptionModeFolderFontColour = null, descriptionModeFileFontColour = null;
	private Color descriptionModeAIColour = null;
	private double descriptorBodyWidth, descriptorBodyHeight, initialDescriptorBodyWidth, initialDescriptorBodyHeight;
	private int mainTextWidth, mainTextHeight, extensionTextWidth, extensionTextHeight, descriptorTagSeparatorWidth;
	private UNICODE_BUI_BODY buiBodyClass = null;
	private String buiEntityStream = null, fileExtensionTag = null, descriptorTagSeparator = null;
	private float descriptorOpacityLevel = 0.0f;
	
        //establish resizing requirements
            //establish scale
            private double dimensionAlterationRate = 1.2, originalDimensionAlterationRate = 1.2;
            //establish scale limits
            private double projectedWidth = 0.0;
			//bui orientation and dimension
			double buiX, buiY, buiWidth, buiHeight;
			private boolean resizeEnquiry = false;
	//font
	private UNICODE_CustomFont customFont = null;
	private String fontName = "";

	//establish line style
	private Stroke descriptorEmbracerLineStyle = null, defaultLineStyle = null;
	
	//establish selection enquiry
	private boolean selectionEnquiry = false;
	
	//establish screen Dimension 
	private Dimension screenDimension = null;
	
	//establish colour
	private Color highlighterColour = null;
	
	//establish spining animation requirements
		//old transformation data
		private UNICODE_RotationManager rotationManager = null;
		private Timer embracerAnimationTimer = new Timer ( 1, new embracerAnimationListening ( ) );
		private UNICODE_BUI_FIELD buiField = null;

	//establish glow feature
	private UNICODE_Glow glow = null;
	private UNICODE_GlowIntensityAndSpanValueGenerator glowIntensityAndSpanValueGenerator = null;
	private String fileAgeReflectionAnswer = null;
	private float glowSpanPercentile = 0.0f, glowIntensityLevel = 0.0f, glowMaximumIntensityPercentile = 0.0f;
	
	//unicortex file extension system
    private UNICORTEX_InforverseExtensionCollectionLoader inforverseExtensionCollectionLoader = new UNICORTEX_InforverseExtensionCollectionLoader ( );
    private ArrayList <String> wordExtensionList = inforverseExtensionCollectionLoader.getWordExtensions ( );
    private ArrayList <String> audioExtensionList = inforverseExtensionCollectionLoader.getAudioExtensions ( );
    private ArrayList <String> videoExtensionList = inforverseExtensionCollectionLoader.getVideoExtensions ( );
    private ArrayList <String> executableExtensionList = inforverseExtensionCollectionLoader.getExecutableExtensions ( );	
	
	//establish maximalInfoverseBufferDivisorUsage Answer and maximalInfoverseBufferDivisor
	private String maximalInfoverseBufferDivisorUsageAnswer = "";
	private int maximalInfoverseBufferDivisor = 0;
	
	//constructor
	public UNICODE_BUI_DESCRIPTOR ( UNICODE_BUI_BODY _buiBodyClass, UNICODE_CustomFont _customFont, String _fontName, Color _descriptionModeBuiFolderColour, Color _descriptionModeBuiFileColour, Color _descriptionModeFolderFontColour, Color _descriptionModeFileFontColour, float _descriptorOpacityLevel, double _dimensionAlterationRate, double projectedWidthPercentageRate, Color highlighterColour, Color _descriptionModeAIColour, UNICODE_BUI_FIELD buiField, String fileAgeReflectionAnswer, float glowSpanPercentile, float glowIntensityLevel, float glowMaximumIntensityPercentile, Dimension screenDimension, String maximalInfoverseBufferDivisorUsageAnswer, int maximalInfoverseBufferDivisor )
	{
		//establish font stream
		customFont = _customFont;
		fontName = _fontName;
		
		//establish screen dimension
		this.screenDimension = screenDimension;
		
		//establish ai colour
		descriptionModeAIColour = _descriptionModeAIColour;
		
		//establish rotation maanager
		this.rotationManager = new UNICODE_RotationManager ( );
		
		//establish opacity level
		descriptorOpacityLevel = _descriptorOpacityLevel;
		
		//establish maximalInfoverseBufferDivisor
		this.maximalInfoverseBufferDivisorUsageAnswer = maximalInfoverseBufferDivisorUsageAnswer;
		this.maximalInfoverseBufferDivisor = maximalInfoverseBufferDivisor;
		
		//establish bui body
		buiBodyClass = _buiBodyClass;
		
		//establish enity stream
		buiEntityStream = buiBodyClass.getEntityStream ( );
		//String refinedExtractedExtension = buiEntityStream.replace ( "/", "" );
		//fileExtensionTag = inforverseExtensionCollectionLoader.getUniCortexFileExtension ( buiEntityStream, wordExtensionList, audioExtensionList, videoExtensionList, executableExtensionList );
		fileExtensionTag = ( conveniencePack.getFileExtension ( buiEntityStream, "verse" ) + "~" ).replace ( "/", "" );
		
		//establish bui dimensions
		buiX = buiBodyClass.getBody ( ).getX ( );
		buiY = buiBodyClass.getBody ( ).getY ( );
		buiWidth = buiBodyClass.getBody ( ).getWidth ( );
		buiHeight = buiBodyClass.getBody ( ).getHeight ( );
	
		//establish bui description text
		buiDescriptionText = conveniencePack.getFileNameWithoutExtension ( buiBodyClass.getEntityStream ( ) );
		
		//compute text dimension based on text
		extensionTextWidth = ( int ) conveniencePack.getDisplayWidthFromString ( fileExtensionTag, 16 );
		extensionTextHeight = ( int ) conveniencePack.getDisplayHeightFromString ( fileExtensionTag, 16 );
		mainTextWidth = ( int ) conveniencePack.getDisplayWidthFromString ( buiDescriptionText, 16 );
		mainTextHeight = ( int ) conveniencePack.getDisplayHeightFromString ( buiDescriptionText, 16 );
		
		//establish body dimension
		if ( maximalInfoverseBufferDivisorUsageAnswer.equals ( "yes" ) )
			descriptorBodyWidth = conveniencePack.getByteFileSizeAsRatioOfScreenWidth ( buiEntityStream, screenDimension, maximalInfoverseBufferDivisor );
		else if ( maximalInfoverseBufferDivisorUsageAnswer.equals ( "no" ) )
			descriptorBodyWidth = extensionTextWidth + mainTextWidth;
			
		descriptorBodyHeight = descriptorBodyWidth;
		//save dimensions
		initialDescriptorBodyWidth = descriptorBodyWidth;
		initialDescriptorBodyHeight = descriptorBodyHeight;
		
		//establish bui descriptor shape - formulae to center a child component on parent, given that the child was already established, and rather adopted by a parent = cX - pW/2 + cW/2
		buiDescriptorBody = new Ellipse2D.Double ( buiX - descriptorBodyWidth/2 + buiWidth/2, buiY - descriptorBodyHeight/2 + buiHeight/2, descriptorBodyWidth, descriptorBodyHeight );
		buiDescriptorBodyEmbracer = new Ellipse2D.Double ( buiX - descriptorBodyWidth/2 + buiWidth/2, buiY - descriptorBodyHeight/2 + buiHeight/2, descriptorBodyWidth + 4, descriptorBodyHeight + 4 );
		
		//establish bui descriptor shape with respect to body
		buiDescriptorShape = buiDescriptorBody;
		
		//establish colours
		descriptionModeBuiFolderColour = _descriptionModeBuiFolderColour;
		descriptionModeBuiFileColour = _descriptionModeBuiFileColour;
		descriptionModeFolderFontColour = _descriptionModeFolderFontColour;
		descriptionModeFileFontColour = _descriptionModeFileFontColour;
		
		//supply dimension alteration rate
		dimensionAlterationRate = _dimensionAlterationRate;
			//projectedWidthPercentageRate - indicates the rate of increase, of how much the width should ultimately grow to.
		projectedWidth = descriptorBodyWidth + ( projectedWidthPercentageRate * descriptorBodyWidth ); //compute projected width per shape; a precursor value is used to produce a mutated, bigger width.
	
		this.descriptorEmbracerLineStyle = new BasicStroke ( 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float [ ] { 2, 4 }, 0 );
		this.defaultLineStyle = new BasicStroke ( 1 );
		this.highlighterColour = highlighterColour;
		
		//embracer anim requirements
		this.embracerAnimationTimer = new Timer ( 10, new embracerAnimationListening ( ) );
		this.buiField = buiField;
		
		
		
		//establish bui glow feature
			//required vars
			this.fileAgeReflectionAnswer = fileAgeReflectionAnswer;
			this.glowSpanPercentile = glowSpanPercentile;
			this.glowIntensityLevel = glowIntensityLevel;
			this.glowMaximumIntensityPercentile = glowMaximumIntensityPercentile;
			//span & intensity generator
			glowIntensityAndSpanValueGenerator = new UNICODE_GlowIntensityAndSpanValueGenerator ( fileAgeReflectionAnswer, glowSpanPercentile, glowIntensityLevel, glowMaximumIntensityPercentile );
			//glow wrt folder & files
			if ( buiBodyClass.representsDirectory ( buiEntityStream ) )
				glow = new UNICODE_Glow ( descriptionModeBuiFolderColour, glowIntensityAndSpanValueGenerator.getSpanValue ( buiBodyClass.getEntityFile ( ), buiDescriptorBody.getWidth ( ) ), glowIntensityAndSpanValueGenerator.getIntensityValue ( buiBodyClass.getEntityFile ( ) ) );	
			else
				glow = new UNICODE_Glow ( descriptionModeBuiFileColour, glowIntensityAndSpanValueGenerator.getSpanValue ( buiBodyClass.getEntityFile ( ), buiDescriptorBody.getWidth ( ) ), glowIntensityAndSpanValueGenerator.getIntensityValue ( buiBodyClass.getEntityFile ( ) ) );

	}
	
	//accessors
	public Shape getShape ( )
	{
		return buiDescriptorShape;
	}
	
	public double getDescriptorBodyWidth ( )
	{
		return descriptorBodyWidth;
	}
	
	public Ellipse2D getBody ( )
	{
		return buiDescriptorBody;
	}
	
	public UNICODE_BUI_BODY getBodyClass ( )
	{
		return buiBodyClass;
	}


	
	//accessors
		//resizing requirements
		public double getDimensionAlterationRate ( )
		{
			return dimensionAlterationRate;
		}
		public double getOriginalDimensionAlterationRate ( )
		{
			return originalDimensionAlterationRate;
		}
		public double getProjectedWidth ( ) 
		{
			return projectedWidth;
		}
		public boolean getResizeEnquiry ( )
		{
			return resizeEnquiry;
		}

	//mutators
		//dynamic mutators
			//resizing requirements
			public void incDimensionAlterationRate ( double value )
			{
				dimensionAlterationRate += value;
			}
			public void decDimensionAlterationRate ( double value )
			{
				dimensionAlterationRate -= value;
			}
 
		//static mutators
			//resizing requiremnts
			public void setDimensionAlterationRate ( double value )
			{
				dimensionAlterationRate = value;
			}
			public void setProjectedWidth ( double value ) 
			{
				projectedWidth = value;
			}
			public void resetDimensionAlterationRate ( )
			{
				dimensionAlterationRate = originalDimensionAlterationRate;
			}
			public void setResizeEnquiry ( boolean value )
			{
				resizeEnquiry = value;
			}
        
			
	public void drawBuiDescriptor ( Graphics graphics, Graphics2D graphics2d, JPanel pane )
	{	
		//save old transformation
		rotationManager.saveOldTransform ( graphics2d );
		
		//establish shape
			//colour the shape
			if ( buiBodyClass.representsDirectory ( buiEntityStream ) )
				glow.draw ( graphics2d, "ellipse", ( int ) buiDescriptorBody.getX ( ), ( int ) buiDescriptorBody.getY ( ), ( int ) buiDescriptorBody.getWidth ( ), ( int ) buiDescriptorBody.getHeight ( ) );  
			else
				glow.draw ( graphics2d, "ellipse", ( int ) buiDescriptorBody.getX ( ), ( int ) buiDescriptorBody.getY ( ), ( int ) buiDescriptorBody.getWidth ( ), ( int ) buiDescriptorBody.getHeight ( ) );  
	

			/*[OLD DESCRIPTOR]
			//the following two lines:
			//allows for the generation of translucent effects on the fly.
			//Attained via: http://www.java-gaming.org/index.php/topic,1546.
			System.setProperty ( "sun.java2d.translaccel", "true" );
			System.setProperty ( "sun.java2d.ddforcevram", "true" ); 
			
			graphics2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, descriptorOpacityLevel ) ); //set alpha composite of descriptor 
			
			//force graphics context to render lines normally..since the process above renders it specially wrt descriptorEmbracerLineStyle
			graphics2d.setStroke ( defaultLineStyle );	
			
			graphics2d.fill ( buiDescriptorBody ); //draw the shape

			graphics2d.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER, 1.0f ) ); //reset alpha composite of descriptor, so after-occuring components are unscaved
			*/
			
		//establish description (extension + file name)
			//draw and colour file extension tag
			graphics2d.setColor ( descriptionModeAIColour );
			if ( resizeEnquiry )
			{
				customFont.write ( graphics, fileExtensionTag, ( int ) ( buiDescriptorBody.getX ( ) + ( ( descriptorBodyWidth + ( dimensionAlterationRate + ( mainTextWidth / 2 ) ) ) / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( ( descriptorBodyHeight + dimensionAlterationRate ) / 2 ) ), 12.0f, fontName );
				//graphics2d.drawString ( buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + ( ( descriptorBodyWidth + ( dimensionAlterationRate + ( mainTextWidth / 2 ) ) ) / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( ( descriptorBodyHeight + dimensionAlterationRate ) / 2 ) ) );
			}
			else	
			{
				customFont.write ( graphics, fileExtensionTag, ( int ) ( buiDescriptorBody.getX ( ) + ( descriptorBodyWidth / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( descriptorBodyHeight / 2 ) ), 12.0f, fontName );
				//graphics2d.drawString ( buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + ( descriptorBodyWidth / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( descriptorBodyHeight / 2 ) ) );
			}
			
			//draw and colour file description tag
			if ( buiBodyClass.representsDirectory ( buiEntityStream ) )
				graphics2d.setColor ( descriptionModeFolderFontColour );
			else
				graphics2d.setColor ( descriptionModeFileFontColour );
				
			//draw the description
			if ( resizeEnquiry )
			{
				customFont.write ( graphics, buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + extensionTextWidth + ( ( descriptorBodyWidth + ( dimensionAlterationRate + ( mainTextWidth / 2 ) ) ) / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( ( descriptorBodyHeight + dimensionAlterationRate ) / 2 ) ), 12.0f, fontName );
				//graphics2d.drawString ( buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + ( ( descriptorBodyWidth + ( dimensionAlterationRate + ( mainTextWidth / 2 ) ) ) / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( ( descriptorBodyHeight + dimensionAlterationRate ) / 2 ) ) );
			}
			else	
			{
				customFont.write ( graphics, buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + extensionTextWidth + ( descriptorBodyWidth / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( descriptorBodyHeight / 2 ) ), 12.0f, fontName );
				//graphics2d.drawString ( buiDescriptionText, ( int ) ( buiDescriptorBody.getX ( ) + ( descriptorBodyWidth / mainTextHeight ) ), ( int ) ( buiDescriptorBody.getY ( ) + ( descriptorBodyHeight / 2 ) ) );
			}
			
		
		if ( getSelectionEnquiry ( ) )
		{
			graphics2d.setColor ( highlighterColour );
			graphics2d.setStroke ( descriptorEmbracerLineStyle );
			
			//apply rotation weh rotation bool is set true
			if ( rotationManager.getRotationEnquiry ( ) )
			{
				rotationManager.toggleTransformation ( graphics2d, ( int ) buiDescriptorBodyEmbracer.getX ( ), ( int ) buiDescriptorBodyEmbracer.getWidth ( ), ( int ) buiDescriptorBodyEmbracer.getY ( ), ( int ) buiDescriptorBodyEmbracer.getHeight ( ) );
					
				graphics2d.draw ( buiDescriptorBodyEmbracer ); //draw the shape
			}
		}
		
		//restore old affine transform
		rotationManager.restoreOldTransform ( graphics2d );
	}
	
	public void mutateGeometricalDimension ( )
	{
		//establish bui descriptor shape - formulae to center a child component on parent, given that the child was already established, and rather adopted by a parent = cX - pW/2 + cW/2
		buiDescriptorBody = new Ellipse2D.Double ( ( buiX - descriptorBodyWidth/2 + buiWidth/2 ) - dimensionAlterationRate/2, ( buiY - descriptorBodyHeight/2 + buiHeight/2 ) - dimensionAlterationRate/2, descriptorBodyWidth + dimensionAlterationRate, descriptorBodyHeight + dimensionAlterationRate );
		
		//establish bui descriptor shape with respect to body
		buiDescriptorShape = buiDescriptorBody;
	}
	
	public void resetGeometricalDimension ( )
	{
		//establish bui descriptor shape - formulae to center a child component on parent, given that the child was already established, and rather adopted by a parent = cX - pW/2 + cW/2
		buiDescriptorBody = new Ellipse2D.Double ( buiX - descriptorBodyWidth/2 + buiWidth/2, buiY - descriptorBodyHeight/2 + buiHeight/2, initialDescriptorBodyWidth, initialDescriptorBodyHeight );
		
		//establish bui descriptor shape with respect to body
		buiDescriptorShape = buiDescriptorBody;
	}
	
	public void performGeometricalMutation ( )
	{
		incDimensionAlterationRate ( dimensionAlterationRate );	
		mutateGeometricalDimension ( );
	}	
	
	public void resetDescriptor ( )
	{
		resetGeometricalDimension ( );
		resetDimensionAlterationRate ( );
	}
	
	public void setSelectionEnquiry ( boolean value )
	{
		selectionEnquiry = value;
	}
	
	public boolean getSelectionEnquiry ( )
	{
		return selectionEnquiry;
	}
	
	public void startEmbracerAnimation ( )
	{
		embracerAnimationTimer.start ( );
	}
	
	public void stopEmbracerAnimation ( )
	{
		embracerAnimationTimer.stop ( );
	}
	
	private class embracerAnimationListening implements ActionListener 
	{
		public void actionPerformed ( ActionEvent actionEvent )
		{
			rotationManager.toggleAction ( 0.0224f, "clockwise" );
			buiField.repaint ( );
		}
	}
}
