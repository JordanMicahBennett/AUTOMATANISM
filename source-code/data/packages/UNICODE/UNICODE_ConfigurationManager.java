package data.packages.UNICODE; //Author(s): Jordan Micah Bennett
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Dimension;

public class UNICODE_ConfigurationManager
{
    // attributes
        //establish opacity level variables
        private ArrayList config_lines = new ArrayList ( );
        private String [ ] config_labels = 
                                            {
                                                "colour::",
												"connector-colour::",
												"connector-glow-colour::",
												"connector-thickness::",
												"connector-glow-thickness::",
												"connector-glow-quality-multiplier::",
												"connector-glow-brightness::",
												"default-mode-bui-folder-colour::",
												"default-mode-bui-file-colour::",
												"description-mode-bui-folder-colour::",
												"description-mode-bui-file-colour::",
												"description-mode-folder-font-colour::",
												"description-mode-file-font-colour::",
												"content-indicator-colour::",
												"directory-indicator-background-colour::",
												"directory-indicator-foreground-colour::",
                                                "app-opacity::",
												"bui-descriptor-opacity::",
                                                "anti-aliasing::",
												"buffer-dimension::",
												"bui-spatial-multipler::",
												"folder-indicator-multiplier::",
												"home-folder::",
												"description-view::",
												"font-name::",
												"line-rendererer-entity-length::",
												"line-rendererer-entity-spatial-distance::",
												"scroll-rate::",
												"maximum-scroll-duration(seconds)::",
												"camera-display-answer::",
												"binary-clock-panel-colour::",
												"binary-clock-base-colour::",
												"binary-clock-active-colour::",
												"binary-clock-inactive-colour::",
												"binary-clock-element-size::",
												"parameter-bubble-highlighter-colour::",
												"parameter-bubble-colour::",
												"file-age-reflection-answer::",
												"glow-span-percentile::",
												"glow-intensity-level::",
												"glow-maximum-intensity-percentile::",
												"glow-core-colour:current::",
												"glow-core-colour:security::",
												"glow-core-colour:infotainment::",
												"glow-core-colour:default::",
												"maximal-infoverse-buffer-divisor-usage-answer::",
												"maximal-infoverse-buffer-divisor::"
                                            };
                                        
        //string to colour conterter
        private UNICODE_StringToColourConverter string_to_colour_converter = new UNICODE_StringToColourConverter ( );
        
        //establish convenience pack
        private UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
		
		//establish configuration stream
		private String config_file_stream = "";
        
    //constructor
    public UNICODE_ConfigurationManager ( String config_file_stream )
    {
		this.config_file_stream = config_file_stream;
        loadConfigData ( );
    }
    
    //load config data
    public void loadConfigData ( )
    {
        //generate config lines
        try
        {
            int count = 0;
            Scanner scanner = new Scanner ( new File ( config_file_stream ) );
            while ( scanner.hasNext ( ) ) 
            {
               config_lines.add ( scanner.next ( ) );
            }
            scanner.close ( );
        }
        catch ( Exception error )
        {
        }   

    }
	
    //get colour from file, so program can know what colour to start with 
    public Color getColourFromFile ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 0 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get all mode colour 0
    public Color getConnectorColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 1 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get all mode colour 1
    public Color getConnectorGlowColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 2 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //getConnectorThickness 
    public int getConnectorThicknessFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 3 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }		
    //getConnectorGlowhickness 
    public int getConnectorGlowThicknessFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 4 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }			
    //getConnectorThickness 
    public int getConnectorGlowQualityMultipler ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 5 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }		
    //getConnectorGlowhickness 
    public int getConnectorGlowBrightness ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 6 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }			
		
												
    //get default mode colour 1
    public Color getDefaultModeBuiFolderColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 7 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get default mode colour 2
    public Color getDefaultModeBuiFileColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 8 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get description mode colour 1
    public Color getDescriptionModeBuiFolderColour  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 9 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get description mode colour 2
    public Color getDescriptionModeBuiFileColour  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 10 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get description mode colour 3
    public Color getDescriptionModeFolderFontColour  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 11 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get description mode colour 4
    public Color getDescriptionModeFileFontColour  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 12 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get content indicator colour
    public Color getContentLocationIndicatorColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 13 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get directory-indicator-background-colour
    public Color getDirectoryIndicatorBackgroundColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 14 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
    //get directory-indicator-foreground-colour
    public Color getDirectoryIndicatorForegroundColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 15 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get opacity from file, so program can know what opacity to start with
    public float getOpacityFromFile ( )
    {
        float opacity = 0.0f;
        opacity = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 16 ), "", "::", 2 ) [ 1 ] );
        return opacity;
    }
	
    //get getBuiDescriptorOpacityFromFile from file, so program can know what opacity to start with
    public float getBuiDescriptorOpacityFromFile ( )
    {
        float opacity = 0.0f;
        opacity = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 17 ), "", "::", 2 ) [ 1 ] );
        return opacity;
    }
	
    //get anti alias value
    public String getAntiAliasingStateFromFile ( )
    {
        String alias_state = null;
        
        alias_state = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 18 ), "", "::", 2 ) [ 1 ];
        
        return alias_state;
    }
    //get buffer dimension from file
    public Dimension getBufferDimensionFromFile ( )
    {
		Dimension returnValue = null;
		
        String bufferDimensionString = bufferDimensionString = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 19 ), "", "::", 2 ) [ 1 ];
		
		int width = Integer.parseInt ( conveniencePack.getDelimitedArray ( bufferDimensionString, "", ",", 2 ) [ 0 ] );
		int height = Integer.parseInt ( conveniencePack.getDelimitedArray ( bufferDimensionString, "", ",", 2 ) [ 1 ] );
		
		returnValue = new Dimension ( width, height );
		
		return returnValue;
	}
    //getNodeSpatialMuliplierFromFile
    public int getNodeSpatialMuliplierFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 20 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
    //getFolderIndicatorMultiplier
    public int getFolderIndicatorMultiplier ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 21 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
    //getHomeFolderString from file
    public String getHomeFolderStringFromFile ( )
    {
        String value = null;
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 22 ), "", "::", 2 ) [ 1 ];
        
        return value;
    }
	
    //getDescriptionViewAnswerFromFile from file
    public String getDescriptionViewAnswerFromFile ( )
    {
        String value = null;
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 23 ), "", "::", 2 ) [ 1 ];
        
        return value;
    }
	
    //getFontName  from file
    public String getFontNameFromFile ( )
    {
        String value = null;
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 24 ), "", "::", 2 ) [ 1 ];
        
        return value;
    }
	
    //getLineRendererEntityLength 
    public double getLineRendererEntityLength ( )
    {
        double value = 0;
        
        value = Double.parseDouble ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 25 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }		
    //getLineRendererEntitySpatialDistance 
    public double getLineRendererEntitySpatialDistance ( )
    {
        double value = 0;
        
        value = Double.parseDouble ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 26 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }		
	
    //getScrollRate from file
    public int getScrollRateFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 27 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
    //getMaximumScrollDuration from file
    public int getMaximumScrollDurationFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 28 ), "", "::", 2 ) [ 1 ] );
        
        return conveniencePack.getMillisecondsFromSeconds ( value );
    }
	
    //getCameraDisplayAnswerFromFile
    public String getCameraDisplayAnswerFromFile ( )
    {
        String value = null;
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 29 ), "", "::", 2 ) [ 1 ];
        
        return value;
    }
												
    //get getBinaryClockPanelColour from file
    public Color getBinaryClockPanelColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 30 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }			
    //get getBinaryClockBaseColour from file
    public Color getBinaryClockBaseColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 31 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }				
    //get getBinaryClockActiveColour from file
    public Color getBinaryClockActiveColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 32 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }			
    //get getBinaryClockActiveColour from file
    public Color getBinaryClockInactiveColour ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 33 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }		

    //getBinaryClockElementSize from file
    public int getBinaryClockElementSizeFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 34 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
    //get getParameterBubbleHighlighterColour from file
    public Color getParameterBubbleHighlighterColourFromFile ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 35 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get getParameterBubbleColour from file
    public Color getParameterBubbleColourFromFile ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 36 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
	//get file-age-reflection
	public String getFileAgeReflectionAnswerFromFile ( )
	{
        String value = "";
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 37 ), "", "::", 2 ) [ 1 ];
        
        return value;
	}
	
	
	//get glow-span-percentile
    public float getGlowSpanPercentileFromFile ( )
    {
        float value = 0;
        
        value = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 38 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
	//get glow-intensity-level
    public float getGlowIntensityLevelFromFile ( )
    {
        float value = 0;
        
        value = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 39 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
	//get glow-maximum-intensity-percentile
    public float getGlowMaximumIntensityPercentileFromFile ( )
    {
        float value = 0;
        
        value = Float.parseFloat ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 40 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }
	
    //get glow core colour from file
    public Color getCurrentGlowCoreColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 41 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get secure glow core colour from file
    public Color getSecurityGlowCoreColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 42 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get secure glow core colour from file
    public Color getInfotainmentGlowCoreColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 43 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	
    //get default glow core colour from file
    public Color getDefaultGlowCoreColourFromFile  ( )
    {
        Color colour = null;
        
        //convert array list index 0, colour config line to a string separated by spaces
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 44 ), "", "::", 2 ) [ 1 ], "", ",", 3 );
        String colour_string = rgb_array [ 0 ] + " " + rgb_array [ 1 ] + " " + rgb_array [ 2 ];
        
        //convert the string into a fucking colour 
        colour = string_to_colour_converter.getColourFromString ( colour_string.replace ( "null", "" ) );

        //return the fucking colour
        return colour;
    }
	//get getMaximalInfoverseBufferDivisorUsageAnswer from file.
	public String getMaximalInfoverseBufferDivisorUsageAnswerFromFile ( )
	{
        String value = "";
        
        value = conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 45 ), "", "::", 2 ) [ 1 ];
        
        return value;
	}
	
    //getMaximalInfoverseBufferDivisor
    public int getMaximalInfoverseBufferDivisorFromFile ( )
    {
        int value = 0;
        
        value = Integer.parseInt ( "" + conveniencePack.getDelimitedArray ( ( String ) config_lines.get ( 46 ), "", "::", 2 ) [ 1 ] );
        
        return value;
    }		
	
    //update colour config:: takes a colour, and extracts rgb integer components, 
    //and prints them to the colour config file.
    public void updateColour ( Color colour )
    {
        //establish output colour config ( what bushman gui kit config reconizes as colour data at parsing )
        String [ ] rgb_array = conveniencePack.getDelimitedArray ( string_to_colour_converter.getRGBString ( colour ), "", " ", 3 );
        String output_colour = rgb_array [ 0 ] + "," + rgb_array [ 1 ] + "," + rgb_array [ 2 ];
        //set colour config index to new colour
        config_lines.set ( 0, config_labels [ 0 ] + output_colour );
        //print the config array list contents
        updateConfigFile ( );
    }
	
	
    //update opacity
    public void updateOpacity ( UNICODE_OpacityController opacity_manager )
    {
        //set opacity config index to new opacity level
        config_lines.set ( 16, config_labels [ 16 ] + opacity_manager.getOpacLevel ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    //update bui descriptor opacity
    public void updateDescriptorOpacity ( UNICODE_BuiDescriptorOpacityController opacity_manager )
    {
        //set opacity config index to new opacity level
        config_lines.set ( 17, config_labels [ 17 ] + opacity_manager.getOpacLevel ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
    //update anti - alias file
    public void updateAntiAliasing ( UNICODE_AntiAliasingController anti_alias_manager )
    {
        //set anti - aliasing config index to new anti - aliasing state answer
        config_lines.set ( 18, config_labels [ 18 ] + anti_alias_manager.getRenderingAnswer ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }
	
    //update buffer dimension 
    public void updateBufferDimensionController ( UNICODE_BufferDimensionController bufferDimensionController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 19, config_labels [ 19 ] + bufferDimensionController.getBufferDimensionString ( ) );
        //print the config array list contents
        updateConfigFile ( );
	}
	
    //update node spatial multiplier controller
    public void updateNodeSpatialMultiplierController ( UNICODE_NodeSpatialMultiplierController nodeSpatialMultiplierController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 20, config_labels [ 20 ] + nodeSpatialMultiplierController.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }	
	
    //update node spatial multiplier controller
    public void updateFolderIndicatorMultiplier ( UNICODE_FolderIndicatorMultiplierController folderIndicatorMultiplierController  )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 21, config_labels [ 21 ] + folderIndicatorMultiplierController.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }	
	
	
    //update home folder 
    public void updateHomeFolderController ( UNICODE_HomeFolderController homeFolderController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 22, config_labels [ 22 ] + homeFolderController.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }	
	
	
    //update description view
    public void updateDescriptionViewController ( UNICODE_DescriptionViewController descriptionViewController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 23, config_labels [ 23 ] + descriptionViewController.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }	 
	
    //update core colour controller
    public void updateCoreCurrentGlowColourController ( UNICODE_CoreCurrentGlowColourController coreCurrentGlowColourController )
    {
        //set Method Package Regex Usage Answer
        config_lines.set ( 41, config_labels [ 41 ] + coreCurrentGlowColourController.getValue ( ) );
        //print the config array list contents
        updateConfigFile ( );
    }	
	
	
    public void updateConfigFile ( )
    {
        try
        {
            PrintWriter pw = new PrintWriter ( new FileWriter ( config_file_stream ) );
            for ( int configs = 0; configs < config_lines.size ( ); configs ++ )
                pw.println ( config_lines.get ( configs ) );
            pw.close ( );
        }
        catch ( Exception error )
        {
        }  
    }
	
	public void defineLabels ( String label_string, String delimiter )
	{
		config_labels = conveniencePack.makeArray ( label_string, delimiter );
	}
	
	public String [ ] getConfigLabels ( )
	{
		return config_labels;
	}
}
