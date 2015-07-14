package data.packages.UNICODE;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import javax.swing.Timer;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;


public class UNICODE_BUI_FIELD extends JPanel 
{
    //attributes
		//establish convenience pack
		private UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
        //BUI REPOSITIONING VARS
            //establish current coordinates genrated by pressed down mouse
            private Point heldMouseCoords = null;
            //establish current coordinates geenrated by mouse dragging
            private Point draggedMouseCoords = null;
			
		Graphics graphics = null;
		Graphics2D graphics2d = null;
    
        //image vars
        private ArrayList <UNICODE_BUI_CONNECTOR> buiConnectors = new ArrayList <UNICODE_BUI_CONNECTOR> ( );
        private ArrayList <UNICODE_BUI_SHAPE> buis = new ArrayList <UNICODE_BUI_SHAPE> ( );
		private ArrayList <UNICODE_BUI_DESCRIPTOR> buiDescriptors = new ArrayList <UNICODE_BUI_DESCRIPTOR> ( );
        
        //dimension
        private int buiWidth, buiHeight, spatialMultiplier;
        
        //cardinality
        private int maxBuis = 0;
        
        //randomizer
        private Random randomizer = new Random ( );
        
        //coordinate gen vars
			//prebaked limits for y upper and lower bound.
			private int prebakedLowerBoundLimitForY = 0, prebakedUpperBoundLimitForY = 0;
            //limits - y values stay in the same range, x values are still random, but still need to grow.
            private int lowerBoundLimitY, upperBoundLimitY;
            private int lowerBoundLimitX, upperBoundLimitX;
			//wave vars
			private double waveVerticalCoordinate = 0;
			private double accumilatedHorizontalWaveCoordinate = 0;
            //list generators
                //initials
                private int initialLowerBoundX = 100, initialUpperBoundX = 110;
                //lists
                private ArrayList <Integer> xCoordList = new ArrayList <Integer> ( ), yCoordList = new ArrayList <Integer> ( );
        //establish bui action dock
        private UNICODE_MenuPanel buiActionDock = null;
		private UNICODE_DonutMenuWindow buiActionWindow = null;
        
        //establish C: drive file list
        private ArrayList StreamFolderFileList = null, StreamFolderSearchNamesList = null;
        private int currentBuiIndex, currentDescriptorIndex;
        
        //animation timer
        private Timer nodeEntityMenuAnimationTimer = new Timer ( 1, new nodeEntityMenuAnimationListener ( ) );
        //scroll vars
            //timer
            private Timer scrollRightwardsTimer = new Timer ( 1, new scrollRightwardsListener ( ) ), scrollLeftwardsTimer = new Timer ( 1, new scrollLeftwardsListener ( ) );
			private Timer scrollDurationTimer = new Timer ( 1, new scrollDurationListener ( ) );
			private int maximumScrollDuration = 0; //two thousand milliesconds
			private double scrollDurationTracker = 0;
			
            //scroll value vars
                //rate
                private double scrollRate;
                //adjuster
                private int scrollRightwardsAdjuster, scrollLeftwardsAdjuster;
                //mouse wheel int controller
                private int rotationDirection;
				//scrool amount tracker
				private int scrollAmountTracker = 0;
                
        //establish name search vars
        private String searchName = "";
        
        //establish line visibility controll boolean
        private boolean lineVisibilityEnquiry = true;
        
        
        //establish field stream
        private String fieldStream = "";
        
        //establish whether this field is a home folder
        private boolean homeStreamEnquiry = false;
        private int minimumNodeFieldCardinalityBeforeChopping = 0;
		
		//establish variables so as to navigate to previous directories
			//1.establish variable to store fieldStreams
			private ArrayList <String> previousFieldStreamList = new ArrayList <String> ( );
			//2.Establish counter to keep track of the number of directory accesses.
			//this is to prevent attempt to navigate beyond the initial directory.
			private int fieldAccessCounter = 0;
			
			//establish folderIndicatorScaleFactor
			int folderIndicatorScaleFactor = 0;
			
			
		//buis description requirements
		private Color buiDescriptorBodyFolderColour = null, buiDescriptorBodyFileColour = null, buiDescriptionColour = null;
		private boolean descriptionModeEnquiry = false;
		
		//establish description view controller
		private UNICODE_DescriptionViewController descriptionViewController = null;
		
		//colour scheme variables
			//all
			private Color connectorColour = null, connectorGlowColour = null;
			//default mode
			private Color defaultModeBuiFolderColour = null, defaultModeBuiFileColour = null;
			//descriptive mode
			private Color descriptionModeBuiFolderColour = null, descriptionModeBuiFileColour = null;
			private Color descriptionModeFolderFontColour = null, descriptionModeFileFontColour = null;
			
		//searching 
		private boolean forwardNavigationActionEnquiry = false, firstLetterSearchModeEnquiry = false;
		private int searchButtonClickIndex = 0;
		
		//opacity level of bui descriptors
		private float descriptorOpacityLevel = 0.0f;
        
	//connector glow properties
	private int connectorThickness = 0, connectorGlowThickness = 0, connectorGlowQualityMultiplier = 0, connectorGlowBrightness = 0;
	
	//establish search box
	private UNICODE_MessageBoxWindow searchBox = null;
	
	//establish screen dimension
	private Dimension screenDimension = null;
	
	//establish content location indicator
	private UNICODE_ContentLocationIndicator contentLocationIndicator = null;
	
	//establish identifier to show whether description mode is enabled
	private String viewModeAnswer = "";
	
    //descriptor RESIZING VARS
			//establish timer delay value - how much time before the next enlargement occurs?
			private int descriptorResizeDelay = 1;
			
			//establish descriptor resize indices. When a button is hovered over it will equate that index to this variable,
			//then this variable will be used to enlarge that button only, when passed to the button array in
			//timer action listener
			private int resizableDescriptorIndex = -1;
			
			//establish descriptor resizing timer
			private Timer descriptorResizingTimer = new Timer ( descriptorResizeDelay, new descriptorResizeActionListening ( ) );
			
			//establish max descriptor scale - when descriptor is hovered over, how much should it scale to before stop enlarging?
			private double dimensionAlterationRate = 0.8, projectedWidthPercentageRate = 8.0;
			
			//establish font name - the name that describes the external font file used to construct descriptor labels etc
			private UNICODE_CustomFont customFont = null;
			private String fontName = "";
			//establish dir displayer
			private Color directoryDisplayerBackgroundColour = null, directoryDisplayerForegroundColour = null;
			private UNICODE_DirectoryDisplayer directoryDisplayer = null;
			private boolean directoryDisplayerVisibilityEnquiry = false;
			//establish file manager
			private UNICODE_ConfigurationManager configurationManager = null;
			
			
			//dynamic highligher
			private UNICODE_DirectoryEditor directoryEditor = null;
			private UNICODE_DynamicHiglighter dynamicHiglighter = null;
			private boolean dynamicHighlightingModeEnquiry = false;
			private ImageIcon dynamicHighlightingImageIcon = null;
			private Image dynamicHighlightingImage = null;
			private String dynamicHighlightingStream = "data/temporary/currentContext.png", dynamicHighlightingDirectory = "data/temporary/";
			private ArrayList dynamicHighlightingAffectedFileList = new ArrayList ( );
			
		//Line renderer properties colour is derived via 'connectorColour'.
		private double lineRendererEntityLength = 0, lineRendererEntitySpatialDistance = 0;
		
		//establish camera
		private UNICODE_BUI_FIELD_CAMERA buiFieldCamera = null;
		private String cameraDisplayAnswer = "";
		
		//param bubble
		private UNICODE_ParameterBubble parameterBubble = null;
		
		//establish public instance of this panel, for usage in runnables
		private JPanel runnableFieldInstance = this;
		
		//establish hgihlighter colour
		private Color highlighterColour = null, parameterBubbleColour = null;
		
		//establish glow requirements
		private String fileAgeReflectionAnswer = null;
		private float glowSpanPercentile = 0.0f, glowIntensityLevel = 0.0f, glowMaximumIntensityPercentile = 0.0f;
		private Color glowCoreColour = null;
		private int coreGlowIndex = 0;
		private String maximalInfoverseBufferDivisorUsageAnswer = "";
		private int maximalInfoverseBufferDivisor = 0;
		
		
		//message box settings
		private int buttonWidth, buttonHeight, arcWidth, arcHeight, axisDisplacement, lastButtonChopValue;   
		private Color backgroundColour = null, buttonOutlineColour = null, labelBackgroundColour = null, labelForegroundColour = null;
		
		
    //constructor
    public UNICODE_BUI_FIELD ( UNICODE_MessageBoxWindow _searchBox, UNICODE_ConfigurationManager _configurationManager, String _fontName, double _dimensionAlterationRate, double _projectedWidthPercentageRate, String _viewModeAnswer, int _buiWidth, int _buiHeight, int _spatialMultiplier, double _scrollRate, int _maximumScrollDuration, String _fieldStream, boolean _homeStreamEnquiry, Dimension _screenDimension, int _folderIndicatorScaleFactor, UNICODE_DescriptionViewController _descriptionViewController, Color _connectorColour, Color _connectorGlowColour, Color _defaultModeBuiFolderColour, Color _defaultModeBuiFileColour, Color _descriptionModeBuiFolderColour, Color _descriptionModeBuiFileColour, Color _descriptionModeFolderFontColour, Color _descriptionModeFileFontColour, float _descriptorOpacityLevel, int _connectorThickness, int _connectorGlowThickness, int _connectorGlowQualityMultiplier, int _connectorGlowBrightness, Color _contentLocationIndicatorColor, Color _directoryDisplayerBackgroundColour, Color _directoryDisplayerForegroundColour, int _contentLocationIndicatorSpaceSeparator, int _contentLocationIndicatorMinimumWidth, int _contentLocationIndicatorMaximumWidth, int directoryDisplayerVisualDisplayLimit, double _lineRendererEntityLength, double _lineRendererEntitySpatialDistance, String _cameraDisplayAnswer, Color _highlighterColour, Color _parameterBubbleColour, String fileAgeReflectionAnswer, float glowSpanPercentile, float glowIntensityLevel, float glowMaximumIntensityPercentile, Color _glowCoreColour, String maximalInfoverseBufferDivisorUsageAnswer, int maximalInfoverseBufferDivisor, int buttonWidth, int buttonHeight, int arcWidth, int arcHeight, int axisDisplacement, int lastButtonChopValue, Color backgroundColour, Color buttonOutlineColour, Color labelBackgroundColour, Color labelForegroundColour )
    {
		//search box
		searchBox = _searchBox;
		
		//establish file manager
		configurationManager = _configurationManager;
		
		//establish descriptor font name
		fontName = _fontName;
		customFont = new UNICODE_CustomFont ( "data/fonts/" );
		
		//establish view mode answer
		viewModeAnswer = _viewModeAnswer;
	
		//establish colour scheme vars
			//all
			connectorColour = _connectorColour;
			connectorGlowColour = _connectorGlowColour;
			//default mode
			defaultModeBuiFolderColour = _defaultModeBuiFolderColour;
			defaultModeBuiFileColour = _defaultModeBuiFileColour;
			//description mode
			descriptionModeBuiFolderColour = _descriptionModeBuiFolderColour;
			descriptionModeBuiFileColour = _descriptionModeBuiFileColour;
			descriptionModeFolderFontColour = _descriptionModeFolderFontColour;
			descriptionModeFileFontColour = _descriptionModeFileFontColour;
		
		//establish glow properties
		connectorThickness = _connectorThickness; 
		connectorGlowThickness = _connectorGlowThickness;
		connectorGlowQualityMultiplier = _connectorGlowQualityMultiplier;
		connectorGlowBrightness = _connectorGlowBrightness;
		
		//opacity level of bui descriptors
		descriptorOpacityLevel = _descriptorOpacityLevel;
		
		//establish highlighterColour
		highlighterColour = _highlighterColour;
		
		//establish parameterBubbleColour
		parameterBubbleColour = _parameterBubbleColour;
			
		//establish glow core colour
		glowCoreColour = _glowCoreColour;
		
		//establish screen dimension
		screenDimension = _screenDimension;
			
		//compute minimumNodeFieldCardinalityBeforeChopping
		minimumNodeFieldCardinalityBeforeChopping = ( ( int ) screenDimension.getWidth ( ) / 100 ) + 2;
		
		//establish description view controller
		descriptionViewController = _descriptionViewController;

        //establish bui dimension
        buiWidth = _buiWidth;
        buiHeight = _buiHeight;
		
		//establish spatial multiplier ( will directly affect the proximity latency between buis ) 
		spatialMultiplier = _spatialMultiplier;
        
        //establish field stream
        fieldStream = _fieldStream;
        
        //establish home stream enquiry
        homeStreamEnquiry = _homeStreamEnquiry;
        
        //scroll rate and max scroll duration
        scrollRate = _scrollRate;
		maximumScrollDuration = _maximumScrollDuration;
		
		//establish content location indicator
		contentLocationIndicator = new UNICODE_ContentLocationIndicator ( glowCoreColour, _contentLocationIndicatorSpaceSeparator, _contentLocationIndicatorMinimumWidth, _contentLocationIndicatorMaximumWidth, screenDimension, scrollRate );
        
        //establish home folder array list wrt generated list function
        StreamFolderFileList = generateStreamFolderDirectories ( fieldStream );
        StreamFolderSearchNamesList = generateStreamFolderSearchNames ( fieldStream );
        
        //estabish max buis
        int fileCardinality = StreamFolderFileList.size ( );
        maxBuis = fileCardinality;
        
		//establish folderIndicatorScaleFactor
		folderIndicatorScaleFactor = _folderIndicatorScaleFactor;

		//initialise previous directory as home folder
		//getPreviousFieldStreamList ( ).add ( fieldStream );

		
		//establish resizing requirements
		dimensionAlterationRate = _dimensionAlterationRate;
		projectedWidthPercentageRate = _projectedWidthPercentageRate;
		
		//establish directory displayer
			//setup colours
			directoryDisplayerBackgroundColour = _directoryDisplayerBackgroundColour;
			directoryDisplayerForegroundColour = _directoryDisplayerForegroundColour;
			//create instance
			directoryDisplayer = new UNICODE_DirectoryDisplayer ( customFont, configurationManager, 100, 100, fontName, 13.0f, this, "core:", directoryDisplayerBackgroundColour, directoryDisplayerForegroundColour, ( getContentLocationIndicatorMaxWidth ( ) * 3 ), directoryDisplayerVisualDisplayLimit );
	
		//message box universal details
		//action box details
		this.buttonWidth = buttonWidth; 
		this.buttonHeight = buttonHeight; 
		this.arcWidth = arcWidth; 
		this.arcHeight = arcHeight; 
		this.axisDisplacement = axisDisplacement; 
		this.lastButtonChopValue = lastButtonChopValue;
		this.backgroundColour = backgroundColour;
		this.buttonOutlineColour = buttonOutlineColour; 
		this.labelBackgroundColour = labelBackgroundColour;
		this.labelForegroundColour = labelForegroundColour;
	
		//establish dynamic highlighter
		directoryEditor = new UNICODE_DirectoryEditor ( );
		dynamicHiglighter = new UNICODE_DynamicHiglighter ( 5, 5, new Color ( 80, 80, 80 ), new Color ( 170, 170, 170 ), buttonWidth, buttonHeight, arcWidth, arcHeight, axisDisplacement, lastButtonChopValue, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour );
	
		//initialise line entity properties
		lineRendererEntityLength = _lineRendererEntityLength;
		lineRendererEntitySpatialDistance = _lineRendererEntitySpatialDistance;
		
		//etstablish camera display answer
		cameraDisplayAnswer = _cameraDisplayAnswer;
		
		//establish parameter bubble
		parameterBubble = new UNICODE_ParameterBubble ( 80, 200, 4, ( int ) configurationManager.getBufferDimensionFromFile ( ).getWidth ( ), this );
		
		//establish glow requirements
		this.fileAgeReflectionAnswer = fileAgeReflectionAnswer;
		this.glowSpanPercentile = glowSpanPercentile;
		this.glowIntensityLevel = glowIntensityLevel;
		this.glowMaximumIntensityPercentile = glowMaximumIntensityPercentile;
		
		//establish core glow index
		this.coreGlowIndex = 2;
		
        //add mouse listening
        addMouseMotionListener ( new mouseListening ( ) );
        addMouseListener ( new mouseListening ( ) );

        //add mouse wheel listener
        addMouseWheelListener ( new mouseWheelListenening ( ) );
        
        //set focus to this panel
        setFocusable ( true );
		
		//generate field components
		this.maximalInfoverseBufferDivisorUsageAnswer = maximalInfoverseBufferDivisorUsageAnswer;
		this.maximalInfoverseBufferDivisor = maximalInfoverseBufferDivisor;
		generateFieldComponents ( );
	}
	

        
    //paint component
    public void paintComponent ( Graphics graphics )
    {
        super.paintComponent ( graphics );
        this.graphics = graphics;
		
		graphics2d = ( Graphics2D ) graphics;
       
	    drawFieldComponents ( );
    }
    
    //get panel midline
    public int getPanelMidpointY ( )
    {
        return this.getHeight ( ) / 2 - 100;
    }
    
    //get bui list
    public ArrayList <UNICODE_BUI_SHAPE> getBuiList ( )
    {
        return buis;
    }
    
    
    public String getFieldStream ( )
    {
        return fieldStream;
    }
	
	public int getCoreGlowIndex ( )
	{
		return coreGlowIndex;
	}
	
	public int getFieldAccessCounter ( )
	{
		return fieldAccessCounter;
	}
	
	public UNICODE_ContentLocationIndicator getContentLocationIndicator ( )
	{
		return contentLocationIndicator;
	}
	
	public double getScrollRate ( )
	{
		return scrollRate;
	}
	
	public UNICODE_DirectoryDisplayer getDirectoryDisplayer ( )
	{
		return directoryDisplayer;
	}
	
	public int getMaxBubblesForCurrentlyViewableField ( )
	{
        // //reset bounds according to how many nodes where found, with respect to the spatial order in which they were born.
		// if ( maxBuis < minimumNodeFieldCardinalityBeforeChopping )
			// setBounds ( getX ( ), getY ( ), getWidth ( ) + minimumNodeFieldCardinalityBeforeChopping, getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.
		// else
			// setBounds ( getX ( ), getY ( ), ( maxBuis * ( buiWidth * maxBuis ) ), getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.
		return 0;
	}
	
	public void setFieldAccessCounter ( int value )
	{
		fieldAccessCounter = value;
	}
	
	public void incFieldAccessCounter ( )
	{
		fieldAccessCounter ++;
	}
	
	public void setContentLocationIndicatorColour ( Color value )
	{
		contentLocationIndicator.changeColour ( value );
	}
	
	public void incCoreGlowIndex ( )
	{
		if ( coreGlowIndex < 3 )
			coreGlowIndex ++;
		else
			coreGlowIndex = 0;
	}
	
	public void decCoreGlowIndex ( )
	{
		if ( coreGlowIndex > -1 )
			coreGlowIndex --;
		else
			coreGlowIndex = 2;
	}
	
	public void decFieldAccessCounter ( )
	{
		fieldAccessCounter --;
	}	
	
	public ArrayList getPreviousFieldStreamList ( )
	{
		return previousFieldStreamList;
	}
	
	public void growPreviousFieldStreamList ( String value )
	{
		previousFieldStreamList.add ( value );
	}
	
	public void shrinkPreviousFieldStreamList ( )
	{
		previousFieldStreamList.remove ( previousFieldStreamList.size ( ) - 1 );
	}
	
    public void setFieldStream ( String value )
    {
        fieldStream = value;
    }
	
	public void setCoreGlowIndex ( int value )
	{
		coreGlowIndex = value;
	}
	
    public boolean getHomeStreamEnquiry ( )
    {
        return homeStreamEnquiry;
    }
	
	public boolean getDescriptionModeEnquiry ( )
	{
		return descriptionModeEnquiry;
	}
	
	public ArrayList <UNICODE_BUI_DESCRIPTOR> getDescriptorList ( )
	{
		return buiDescriptors;
	}
	
	public void setDescriptionModeEnquiry ( boolean value )
	{
		descriptionModeEnquiry = value;
	}
	
	public boolean getStreamEmptyEnquiry ( String value )
	{
		return conveniencePack.getDirectoryCardinality ( value ) > 0;
	}
	
	public ArrayList getStreamFolderFileList ( )
	{
		return StreamFolderFileList;
	}
	
	public String getHomeStream ( )
	{
		return fieldStream;
	}
	
	public int getSearchButtonClickIndex ( )
	{
		return searchButtonClickIndex;
	}
	
	public void resetSearchButtonClickIndex ( )
	{
		searchButtonClickIndex = 0;
	}
	
	public void setSearchButtonClickIndex ( int value )
	{
		searchButtonClickIndex = value;
	}
	
	public void incSearchButtonClickIndex ( )
	{
		searchButtonClickIndex ++;
	}
	
	public String getViewModeAnswer ( )
	{
		return viewModeAnswer;
	}
	
	public Color getGlowCoreColour ( )
	{
		return glowCoreColour;
	}
	
	public void setViewModeAnswer ( String value )
	{
		viewModeAnswer = value;
		descriptionViewController.setValue ( value );
		configurationManager.updateDescriptionViewController ( descriptionViewController );
	}
	
	public void setGlowCoreColour ( Color value )
	{
		glowCoreColour = value;
	}
	
	//display directory displayer
	public void showDirectoryDisplayer ( )
	{
		directoryDisplayer.show ( false, 0 );
	}

	//if forwardNaviagationEnquiry is supplied false, it is to be assumed that
	//the user is trying to return to previous directory.
	//certain params are left blank based on the type of mutation required.
	//eg.commencingLetter and keyWOrds left blank in navigation mutation
	public void reInitializeField ( String value, boolean forwardNavigationEnquiry )
	{
		if ( getStreamEmptyEnquiry ( value ) ) //if folder is not empty.
		{
				//the user may either forward or backward navigate.
				//this counter will be used to eventually prevent attempt to naviaget backwards, 
				//when there exists no directory to act upon. So, this counter will increment if forward,
				//decrement if backward.
				if ( forwardNavigationEnquiry )
				{
					//store fieldStream as previous stream before mutating currentFieldStream
					growPreviousFieldStreamList ( getFieldStream ( ) );
					//increment fieldAccessCounter
					incFieldAccessCounter ( );
				}
			//reset main stream
			setFieldStream ( value );
			//reset list of entities streams
			StreamFolderFileList = generateStreamFolderDirectories ( value ); 
			//reset list of entities stream names
			StreamFolderSearchNamesList = generateStreamFolderSearchNames ( value );
			
			//reset max bui value
			maxBuis = StreamFolderFileList.size ( ); //reset max buis
			//reset dimension lists
			xCoordList.clear ( );
			yCoordList.clear ( );
			//reset buis and connectors
			buis.clear ( );
			buiConnectors.clear ( );
			//reset descriptors list
			buiDescriptors.clear ( );
			//regenrate field components
			generateFieldComponents ( );
		}
		else
			new UNICODE_MessageBoxWindow ( true, "Directory is empty!", 0.9f, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour, true, "data/images/all/message box/okay/","rr", buttonWidth, buttonHeight, arcWidth, arcHeight, axisDisplacement, lastButtonChopValue  );
	}
	
	//field mutation for search result generation
	public void reInitializeFieldSearchModeA ( String value, String commencingLetter )
	{
		//reset list of entities streams
		StreamFolderFileList = generateStreamFolderDirectoriesSearchModeA ( value, commencingLetter ); 
		//reset list of entities stream names
		StreamFolderSearchNamesList = generateStreamFolderSearchNamesSearchModeA ( value, commencingLetter );
				
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//regenrate field components
		generateFieldComponents ( );
	}
	
	//field mutation for search result generation
	public void reInitializeFieldSearchModeB ( String value, String keyWord )
	{
		//reset list of entities streams
		StreamFolderFileList = generateStreamFolderDirectoriesSearchModeB ( value, keyWord ); 
		//reset list of entities stream names
		StreamFolderSearchNamesList = generateStreamFolderSearchNamesSearchModeB ( value, keyWord );
				
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//regenrate field components
		generateFieldComponents ( );
	}
	
	//field mutation for search result generation
	public void reInitializeFieldSearchModeC ( String value )
	{
		//reset list of entities streams
		StreamFolderFileList = generateStreamFolderDirectoriesSearchModeC ( value ); 
		//reset list of entities stream names
		StreamFolderSearchNamesList = generateStreamFolderSearchNamesSearchModeC ( value );
				
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//regenrate field components
		generateFieldComponents ( );
	}
	
	//field mutation for search result generation
	public void reInitializeFieldSearchModeD ( String value )
	{
		//reset list of entities streams
		StreamFolderFileList = generateStreamFolderDirectoriesSearchModeD ( value ); 
		//reset list of entities stream names
		StreamFolderSearchNamesList = generateStreamFolderSearchNamesSearchModeD ( value );
				
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//regenrate field components
		generateFieldComponents ( );
	}
	
	//field mutation for dynamic highlighting mutation result
	public void reInitializeFieldSearchModeE ( String value, ArrayList ignoranceList )
	{
		//reset list of entities streams
		StreamFolderFileList = generateStreamFolderDirectoriesE ( value, ignoranceList ); 
		//reset list of entities stream names
		StreamFolderSearchNamesList = generateStreamFolderSearchNamesE ( value, ignoranceList );
				
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//regenrate field components
		generateFieldComponents ( );
	}
	
	//time wave field mutation
	public void initializeTimeWaveField ( )
	{		
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//reset horizontal factor ( vertical doesn't require any, since it already reset continuously )
		accumilatedHorizontalWaveCoordinate = 0;
		//regenrate field components
		generateTimeWaveFieldComponents ( );
	}
	
	//size wave field mutation
	public void initializeSizeWaveField ( )
	{		
		//reset max bui value
		maxBuis = StreamFolderFileList.size ( ); //reset max buis
		//reset dimension lists
		xCoordList.clear ( );
		yCoordList.clear ( );
		//reset buis and connectors
		buis.clear ( );
		buiConnectors.clear ( );
		//reset descriptors list
		buiDescriptors.clear ( );
		//reset horizontal factor ( vertical doesn't require any, since it already reset continuously )
		accumilatedHorizontalWaveCoordinate = 0;
		//regenrate field components
		generateSizeWaveFieldComponents ( );
	}
	
	public void drawFieldComponents ( )
	{  
		if ( dynamicHighlightingModeEnquiry )
			graphics2d.drawImage ( dynamicHighlightingImage, scrollAmountTracker, 0, this );
                    
        //draw bui entities
		buiFieldCamera.enableBuiComponentRendering ( graphics, graphics2d, this, maxBuis, lineVisibilityEnquiry, lineRendererEntityLength, lineRendererEntitySpatialDistance, descriptionViewController, buis, buiDescriptors, buiConnectors, rotationDirection, scrollAmountTracker );
		
					
			//since the bui action dock is now on a window rather than a panel, it no longer binds its orientation to the parent panel, since it's a JFrame.
			//So only when scrolling occurs, (mouse rotation mutation) should we consider the new location of the parent bui field panel, and adjust the location of the 
			//bui window then.
			if ( ( rotationDirection > 0 ) && ( getX ( ) > 0 ) )
			{	
				int panelScrollRightwardsIncrementAmount = 0;
				
				if ( conveniencePack.getIntegerIncrementEnquiry ( getX ( ), getWidth ( ) ) )
					panelScrollRightwardsIncrementAmount += getX ( );

                UNICODE_BUI_SHAPE buiShape = buis.get ( currentBuiIndex );
                UNICODE_BUI_BODY buiBody = buiShape.getBody ( );
				buiActionWindow.setBounds ( ( int ) ( ( buiBody.getBody ( ).getX ( ) - 110/2 ) + buiBody.getBody ( ).getWidth ( )/2 ) + ( int ) panelScrollRightwardsIncrementAmount, ( int ) ( ( buiBody.getBody ( ).getY ( ) - 110/2 ) + buiBody.getBody ( ).getHeight ( )/2 ), 110, 110 );
			}
			else if ( ( rotationDirection < 0 ) && ( getX ( ) < 0 ) )
			{			
				int panelScrollLeftwardsIncrementAmount = 0;
				
				if ( conveniencePack.getIntegerDecrementEnquiry ( getX ( ), -getWidth ( ) ) )
					panelScrollLeftwardsIncrementAmount -= getX ( );
				
                UNICODE_BUI_SHAPE buiShape = buis.get ( currentBuiIndex );
                UNICODE_BUI_BODY buiBody = buiShape.getBody ( );
				buiActionWindow.setBounds ( ( int ) ( ( buiBody.getBody ( ).getX ( ) - 110/2 ) + buiBody.getBody ( ).getWidth ( )/2 ) - ( int ) panelScrollLeftwardsIncrementAmount, ( int ) ( ( buiBody.getBody ( ).getY ( ) - 110/2 ) + buiBody.getBody ( ).getHeight ( )/2 ), 110, 110 );
			}				
			
		buiFieldCamera.draw ( graphics2d, Color.lightGray );
		
		contentLocationIndicator.draw ( graphics2d );
		dynamicHiglighter.draw ( graphics, graphics2d );
		parameterBubble.draw ( graphics2d, parameterBubbleColour );
		graphics2d.dispose ( );
	}
	
	public void generateFieldComponents ( )
	{
		//establish prebaked y midpoint values.
		prebakedLowerBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) + 100 );
		prebakedUpperBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) - 100 );
				
		buiFieldCamera = new UNICODE_BUI_FIELD_CAMERA ( 60, prebakedLowerBoundLimitForY, prebakedUpperBoundLimitForY, configurationManager, cameraDisplayAnswer );
		
        //reset bounds according to how many nodes where found, with respect to the spatial order in which they were born.
		if ( maxBuis < minimumNodeFieldCardinalityBeforeChopping )
			setBounds ( getX ( ), getY ( ), getWidth ( ) + minimumNodeFieldCardinalityBeforeChopping, getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.
		else
			setBounds ( getX ( ), getY ( ), ( maxBuis * ( buiWidth * maxBuis ) ), getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.


        //compute y limits
            //compute lowerBoundLimitY
            lowerBoundLimitY = prebakedLowerBoundLimitForY;
            //compute upperBoundLimitY
            upperBoundLimitY = prebakedUpperBoundLimitForY;
        //compute x limits
            //compute lowerBoundLimitX
            lowerBoundLimitX = initialLowerBoundX;
            //compute upperBoundLimitX
            upperBoundLimitX = initialUpperBoundX;
            
        //generate y coords
        for ( int i = 0; i < maxBuis; i ++ )
        {
            int y = randomizer.nextInt ( lowerBoundLimitY ) + upperBoundLimitY;
            yCoordList.add ( y );
        }
            
        //generat x coords
        for ( int i = 0; i < maxBuis; i ++ )
        {
            int x = randomizer.nextInt ( lowerBoundLimitX ) + upperBoundLimitX;
            
            xCoordList.add ( x );
            
            lowerBoundLimitX += buiWidth * spatialMultiplier; //change the lower bound by increasing its value by a multiple of an bui's width * 10.
            upperBoundLimitX += ( buiWidth * spatialMultiplier ); //change the upper bound by the same value.
        }
        
        //generate bui list
        for ( int i = 0; i < maxBuis; i ++ )
            buis.add ( new UNICODE_BUI_SHAPE ( ( String ) StreamFolderFileList.get ( i ), ( String ) StreamFolderSearchNamesList.get ( i ), xCoordList.get ( i ), yCoordList.get ( i ), buiWidth, buiHeight, i, folderIndicatorScaleFactor, defaultModeBuiFolderColour, defaultModeBuiFileColour, glowCoreColour, this ) );
        
        //generate bui connectors
        for ( int i = 0; i < maxBuis; i ++ )
            if ( ( i + 1 ) < maxBuis )
                buiConnectors.add ( new UNICODE_BUI_CONNECTOR ( xCoordList.get ( i ), yCoordList.get ( i ), xCoordList.get ( i + 1 ), yCoordList.get ( i + 1 ), buis, descriptionViewController.getValue ( ), connectorColour, connectorGlowColour, connectorThickness, connectorGlowThickness, connectorGlowQualityMultiplier, connectorGlowBrightness ) );
        
		//generate bui descriptions       
		for ( int i = 0; i < maxBuis; i ++ )
			buiDescriptors.add ( new UNICODE_BUI_DESCRIPTOR ( buis.get ( i ).getBody ( ), customFont, fontName, descriptionModeBuiFolderColour, descriptionModeBuiFileColour, descriptionModeFolderFontColour, descriptionModeFileFontColour, descriptorOpacityLevel, dimensionAlterationRate, projectedWidthPercentageRate, highlighterColour, configurationManager.getCurrentGlowCoreColourFromFile ( ), this, fileAgeReflectionAnswer, glowSpanPercentile, glowIntensityLevel, glowMaximumIntensityPercentile, screenDimension, maximalInfoverseBufferDivisorUsageAnswer, maximalInfoverseBufferDivisor ) );
	}
	
	public void generateTimeWaveFieldComponents ( )
	{
		//establish prebaked y midpoint values.
		prebakedLowerBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) + 100 );
		prebakedUpperBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) - 100 );
				
		buiFieldCamera = new UNICODE_BUI_FIELD_CAMERA ( 60, prebakedLowerBoundLimitForY, prebakedUpperBoundLimitForY, configurationManager, cameraDisplayAnswer );
		
        //reset bounds according to how many nodes where found, with respect to the spatial order in which they were born.
		if ( maxBuis < minimumNodeFieldCardinalityBeforeChopping )
			setBounds ( getX ( ), getY ( ), getWidth ( ) + minimumNodeFieldCardinalityBeforeChopping, getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.
		else
			setBounds ( getX ( ), getY ( ), ( maxBuis * ( buiWidth * maxBuis ) ), getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.


        //compute y limits
            //compute lowerBoundLimitY
            lowerBoundLimitY = prebakedLowerBoundLimitForY;
            //compute upperBoundLimitY
            upperBoundLimitY = prebakedUpperBoundLimitForY;
        //compute x limits
            //compute lowerBoundLimitX
            lowerBoundLimitX = initialLowerBoundX;
            //compute upperBoundLimitX
            upperBoundLimitX = initialUpperBoundX;
            
        //generate y coord
		waveVerticalCoordinate = screenDimension.getHeight ( ) / 2;
            
        //generat x coords //maximalInfoverseBufferDivisor
        for ( int i = 0; i < maxBuis; i ++ )
        {
			accumilatedHorizontalWaveCoordinate += ( screenDimension.getWidth ( ) / maximalInfoverseBufferDivisor ) / 4;
            
            xCoordList.add ( ( int ) accumilatedHorizontalWaveCoordinate );
        }
        
        //generate bui list
        for ( int i = 0; i < maxBuis; i ++ )
            buis.add ( new UNICODE_BUI_SHAPE ( ( String ) StreamFolderFileList.get ( i ), ( String ) StreamFolderSearchNamesList.get ( i ), xCoordList.get ( i ), ( int ) waveVerticalCoordinate, buiWidth, buiHeight, i, folderIndicatorScaleFactor, defaultModeBuiFolderColour, defaultModeBuiFileColour, glowCoreColour, this ) );
        
		//sort bui shape list
		Collections.sort ( buis, new UNICODE_BuiShapeTimeWaveShapeComparator ( ) );
		
        //generate bui connectors
        for ( int i = 0; i < maxBuis; i ++ )
            if ( ( i + 1 ) < maxBuis )
                buiConnectors.add ( new UNICODE_BUI_CONNECTOR ( xCoordList.get ( i ), ( int ) waveVerticalCoordinate, ( int ) xCoordList.get ( i + 1 ), ( int ) waveVerticalCoordinate, buis, descriptionViewController.getValue ( ), connectorColour, connectorGlowColour, connectorThickness, connectorGlowThickness, connectorGlowQualityMultiplier, connectorGlowBrightness ) );
        
		
		//generate bui descriptions       
		for ( int i = 0; i < maxBuis; i ++ )
			buiDescriptors.add ( new UNICODE_BUI_DESCRIPTOR ( buis.get ( i ).getBody ( ), customFont, fontName, descriptionModeBuiFolderColour, descriptionModeBuiFileColour, descriptionModeFolderFontColour, descriptionModeFileFontColour, descriptorOpacityLevel, dimensionAlterationRate, projectedWidthPercentageRate, highlighterColour, configurationManager.getCurrentGlowCoreColourFromFile ( ), this, fileAgeReflectionAnswer, glowSpanPercentile, glowIntensityLevel, glowMaximumIntensityPercentile, screenDimension, maximalInfoverseBufferDivisorUsageAnswer, maximalInfoverseBufferDivisor ) );
	
		//sort bui descriptor list
		Collections.sort ( buiDescriptors, new UNICODE_BuiShapeTimeWaveDescriptorComparator ( ) );
	}
	
	public void generateSizeWaveFieldComponents ( )
	{
		//establish prebaked y midpoint values.
		prebakedLowerBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) + 100 );
		prebakedUpperBoundLimitForY = ( int ) ( ( ( screenDimension.getHeight ( ) / 2 ) - 100 ) - 100 );
				
		buiFieldCamera = new UNICODE_BUI_FIELD_CAMERA ( 60, prebakedLowerBoundLimitForY, prebakedUpperBoundLimitForY, configurationManager, cameraDisplayAnswer );
		
        //reset bounds according to how many nodes where found, with respect to the spatial order in which they were born.
		if ( maxBuis < minimumNodeFieldCardinalityBeforeChopping )
			setBounds ( getX ( ), getY ( ), getWidth ( ) + minimumNodeFieldCardinalityBeforeChopping, getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.
		else
			setBounds ( getX ( ), getY ( ), ( maxBuis * ( buiWidth * maxBuis ) ), getHeight ( ) ); //AN APPROPRIATE FORMULAE FOR EXTENDING THE FRAME'S WIDTH, WITH RESPECT TO THE NEW NODES FOUND.


        //compute y limits
            //compute lowerBoundLimitY
            lowerBoundLimitY = prebakedLowerBoundLimitForY;
            //compute upperBoundLimitY
            upperBoundLimitY = prebakedUpperBoundLimitForY;
        //compute x limits
            //compute lowerBoundLimitX
            lowerBoundLimitX = initialLowerBoundX;
            //compute upperBoundLimitX
            upperBoundLimitX = initialUpperBoundX;
            
        //generate y coord
		waveVerticalCoordinate = screenDimension.getHeight ( ) / 2;
            
        //generat x coords //maximalInfoverseBufferDivisor
        for ( int i = 0; i < maxBuis; i ++ )
        {
			accumilatedHorizontalWaveCoordinate += ( screenDimension.getWidth ( ) / maximalInfoverseBufferDivisor ) / 4;
            
            xCoordList.add ( ( int ) accumilatedHorizontalWaveCoordinate );
        }
        
        //generate bui list
        for ( int i = 0; i < maxBuis; i ++ )
            buis.add ( new UNICODE_BUI_SHAPE ( ( String ) StreamFolderFileList.get ( i ), ( String ) StreamFolderSearchNamesList.get ( i ), xCoordList.get ( i ), ( int ) waveVerticalCoordinate, buiWidth, buiHeight, i, folderIndicatorScaleFactor, defaultModeBuiFolderColour, defaultModeBuiFileColour, glowCoreColour, this, screenDimension, maximalInfoverseBufferDivisor ) );
        
		//sort bui shape list
		Collections.sort ( buis, new UNICODE_BuiShapeSizeWaveShapeComparator ( ) );
			
        //generate bui connectors
        for ( int i = 0; i < maxBuis; i ++ )
            if ( ( i + 1 ) < maxBuis )
                buiConnectors.add ( new UNICODE_BUI_CONNECTOR ( xCoordList.get ( i ), ( int ) waveVerticalCoordinate, ( int ) xCoordList.get ( i + 1 ), ( int ) waveVerticalCoordinate, buis, descriptionViewController.getValue ( ), connectorColour, connectorGlowColour, connectorThickness, connectorGlowThickness, connectorGlowQualityMultiplier, connectorGlowBrightness ) );
        
		
		//generate bui descriptions       
		for ( int i = 0; i < maxBuis; i ++ )
			buiDescriptors.add ( new UNICODE_BUI_DESCRIPTOR ( buis.get ( i ).getBody ( ), customFont, fontName, descriptionModeBuiFolderColour, descriptionModeBuiFileColour, descriptionModeFolderFontColour, descriptionModeFileFontColour, descriptorOpacityLevel, dimensionAlterationRate, projectedWidthPercentageRate, highlighterColour, configurationManager.getCurrentGlowCoreColourFromFile ( ), this, fileAgeReflectionAnswer, glowSpanPercentile, glowIntensityLevel, glowMaximumIntensityPercentile, screenDimension, maximalInfoverseBufferDivisorUsageAnswer, maximalInfoverseBufferDivisor ) );
	
		//sort bui descriptor list
		Collections.sort ( buiDescriptors, new UNICODE_BuiShapeSizeWaveDescriptorComparator ( ) );
	}
    
    //mouse listening
    private class mouseListening implements MouseListener, MouseMotionListener
    { 
        public void mouseClicked ( MouseEvent mEvent )
        { 
			if ( !parameterBubble.getParameterBubbleEnableEnquiry ( ) )
			{
				for ( int i = 0; i < maxBuis; i ++ )
				{
					UNICODE_BUI_SHAPE buiShape = buis.get ( i );
					UNICODE_BUI_BODY buiBody = buiShape.getBody ( );  
					UNICODE_BUI_DESCRIPTOR descriptorShapes = buiDescriptors.get ( i );
					
					if ( getViewModeAnswer ( ).equals ( "off" ) )
					{
						if ( buiShape.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
						{
							buiActionDock.audioPlayer.playAudio ( "enter.wav" );
							buiActionDock.setVisible ( true );
							buiActionWindow.setBounds ( ( int ) ( ( buiBody.getBody ( ).getX ( ) - 110/2 ) + buiBody.getBody ( ).getWidth ( )/2 ), ( int ) ( ( buiBody.getBody ( ).getY ( ) - 110/2 ) + buiBody.getBody ( ).getHeight ( )/2 ), 110, 110 );
							
							if ( buiBody.representsDirectory ( buiBody.getEntityStream ( ) ) )
								buiActionDock.setBackground ( descriptionModeBuiFolderColour );
							else
								buiActionDock.setBackground ( descriptionModeBuiFileColour );
							
							enableBuiActionDonutWindow ( );
							currentBuiIndex = buiBody.getEntityIndex ( );
							nodeEntityMenuAnimationTimer.start ( );
						}
					}
					else if ( getViewModeAnswer ( ).equals ( "on" ) )
					{	
						if ( descriptorShapes.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
						{
							UNICODE_BUI_SHAPE buiShapeAtSelectedDescriptorShape = buis.get ( i );
							UNICODE_BUI_BODY buiBodyAtSelectedDescriptorShape = buiShapeAtSelectedDescriptorShape.getBody ( );  
						
							buiActionDock.audioPlayer.playAudio ( "enter.wav" );
							buiActionDock.setVisible ( true );
							buiActionWindow.setBounds ( ( int ) ( ( buiBodyAtSelectedDescriptorShape.getBody ( ).getX ( ) - 110/2 ) + buiBodyAtSelectedDescriptorShape.getBody ( ).getWidth ( )/2 ), ( int ) ( ( buiBodyAtSelectedDescriptorShape.getBody ( ).getY ( ) - 110/2 ) + buiBodyAtSelectedDescriptorShape.getBody ( ).getHeight ( )/2 ), 110, 110 );
							
							if ( buiBodyAtSelectedDescriptorShape.representsDirectory ( buiBodyAtSelectedDescriptorShape.getEntityStream ( ) ) )
								buiActionDock.setBackground ( descriptionModeBuiFolderColour );
							else
								buiActionDock.setBackground ( descriptionModeBuiFileColour );
								
							enableBuiActionDonutWindow ( );
							currentBuiIndex = buiBody.getEntityIndex ( );
							nodeEntityMenuAnimationTimer.start ( );
						}
					}
				}
					
				dynamicHiglighter.toggleContactEnquiryStateAtMousePress ( mEvent, maxBuis, buis, buiDescriptors );
			}          
			else
			{
				parameterBubble.enableMouseClickRespose ( );
			}
			repaint ( );
        }

        public void mouseReleased ( MouseEvent mEvent )
        {
			if ( !parameterBubble.getParameterBubbleEnableEnquiry ( ) )
			{
				dynamicHiglighter.toggleContactEnquiryStateAtMouseRelease ( mEvent, maxBuis, buis, buiDescriptors );
				dynamicHiglighter.establishMouseReleasedEvent ( getDescriptorCollectionSelectedActionThreads ( ) );
				disableDynamicHighlightingPerformanceMode ( );	
			}
        }
        
        public void mouseEntered ( MouseEvent mEvent )
        {
        }  
        
        public void mouseExited ( MouseEvent mEvent )
        {
        }
     
        public void mousePressed ( MouseEvent mEvent )
        {
			if ( !parameterBubble.getParameterBubbleEnableEnquiry ( ) )
			{		
				//establish mouse pressed coords
				heldMouseCoords = mEvent.getPoint ( );

				dynamicHiglighter.establishMousePressedEvent ( mEvent );
				enableDynamicHighlightingPerformanceMode ( );	
				
				repaint ( );
			}
        }
        
        public void mouseDragged ( MouseEvent mEvent )
        {         
            // for ( int i = 0; i < maxBuis; i ++ )
            // {
                // UNICODE_BUI_SHAPE buiShape = buis.get ( i );
                // if ( buiShape.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
                // {  
                    // //establish dragged mouse coordinates
                    // draggedMouseCoords = mEvent.getLocationOnScreen ( );   
            
                    // double relocationX = draggedMouseCoords.getX ( ) - heldMouseCoords.getX ( );
                    // double relocationY = draggedMouseCoords.getY ( ) - heldMouseCoords.getY ( );
                    
                    // //buiShape.setRelocationEnquiry ( true );
                    // //buiShape.setRelocationOrientation ( relocationX, relocationY );
                // }
            // }
			if ( !parameterBubble.getParameterBubbleEnableEnquiry ( ) )
			{
				dynamicHiglighter.establishMouseDraggedEvent ( mEvent, scrollAmountTracker );
				enableDynamicHighlightingPerformanceMode ( );		
			
				repaint ( );
			}
        }
        public void mouseMoved ( MouseEvent mEvent )
        {
			parameterBubble.enableMouseMovementResponse ( mEvent );
			
			if ( parameterBubble.getParameterBubbleEnableEnquiry ( ) )
				parameterBubble.updateParameterBubbleLocation ( mEvent );
			else
			{
				//search box aider
				for ( int i = 0; i < maxBuis; i ++ )
				{
					UNICODE_BUI_SHAPE buiShape = buis.get ( i );
					UNICODE_BUI_BODY buiBody = buiShape.getBody ( );  
					UNICODE_BUI_DESCRIPTOR descriptorShape = buiDescriptors.get ( i );
					
					if ( getViewModeAnswer ( ).equals ( "off" ) )
					{
						if ( buiShape.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
						{
							if ( buiShape.getBody ( ).representsDirectory ( buiShape.getBody ( ).getEntityStream ( ) ) )
							{
								searchBox.getPanel ( ).getTextField ( ).setText ( "infoverses:" );
							}
							else
							{
								searchBox.getPanel ( ).getTextField ( ).setText ( "infowaves:" );
							}
						}
					}
					else if ( getViewModeAnswer ( ).equals ( "on" ) )
					{	
						if ( descriptorShape.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
						{
							if ( buiShape.getBody ( ).representsDirectory ( buiShape.getBody ( ).getEntityStream ( ) ) )
							{
								searchBox.getPanel ( ).getTextField ( ).setText ( "infoverses:" );
							}
							else
							{
								searchBox.getPanel ( ).getTextField ( ).setText ( "infowaves:" );
							}
						}
					}
				}
				
				///////////////////////////////////////////////
				//resizing response generation
				///////////////////////////////////////////////
				for ( int descriptors = 0; descriptors < buiDescriptors.size ( ); descriptors ++ )
				{
					UNICODE_BUI_DESCRIPTOR descriptorShape = buiDescriptors.get ( descriptors );
					
					if ( descriptorShape.getShape ( ).contains ( mEvent.getX ( ), mEvent.getY ( ) ) )
					{
						descriptorResizingTimer.start ( );
						//place a break after getting button index
						//otherwise the timer will only be called when
						//the descriptors has reached the end of its value in its for loop
						//instead of the value index we need to resize the appropriate button
						currentDescriptorIndex = descriptors;
						buiDescriptors.get ( descriptors ).setResizeEnquiry ( true );
						resizableDescriptorIndex = descriptors; break;//tell the resize timer which button to resize
					}
					else
					{
						descriptorResizingTimer.stop ( ); //stop resize timer
						buiDescriptors.get ( descriptors ).setResizeEnquiry ( false );
						buiDescriptors.get ( descriptors ).resetDescriptor ( );
					}
				}

			}
			
            repaint ( );
        }
    }
    
    //FOR BUI FIELD NAVIGATION
		//generate arraylist that contains each C:Drive dir.
		public ArrayList generateStreamFolderDirectories ( String _fieldStream )
		{
			ArrayList value = new ArrayList ( );
			
			File directory = new File ( _fieldStream );
			String [ ] fileList = directory.list ( );
			
			for ( int i = 0; i < fileList.length; i ++ )
				value.add ( _fieldStream + fileList [ i ] );
				
			return value;
		}
		//generate arraylist that contains each search name. (what the user will search for via each bui node.
		public ArrayList generateStreamFolderSearchNames ( String _fieldStream )
		{
			ArrayList value = new ArrayList ( );
			
			File directory = new File ( _fieldStream );
			String [ ] fileList = directory.list ( );
			
			for ( int i = 0; i < fileList.length; i ++ )
				value.add ( fileList [ i ] );
				
			return value;
		}
	
	//FOR BUI FIELD SEARCHING..
		//A.SEARCH NAMES THAT BEGIN WITH 'USER' SPECIFIED 'LETTER', WE WILL REGENERATE THE BUI FIELD WITH THOSE ONLY. 
			//generate arraylist that contains each C:Drive dir, only if the file/folder begins with supplied letter
			public ArrayList generateStreamFolderDirectoriesSearchModeA ( String _fieldStream, String commencingLetter )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					//only build the array list, with respect to the number of names that begin with the commencinLetter
					if ( fileList [ i ].startsWith ( commencingLetter ) )
						value.add ( _fieldStream + fileList [ i ] );
					
				return value;
			}
			
			//generate arraylist that contains each search name. (what the user will search for via each bui node.
			//,Ofcourse, only if the file/folder begins with supplied letter
			public ArrayList generateStreamFolderSearchNamesSearchModeA ( String _fieldStream, String commencingLetter )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					//only build the array list, with respect to the number of names that begin with the commencinLetter
					if ( fileList [ i ].startsWith ( commencingLetter ) )
						value.add ( fileList [ i ] );
					
				return value;
			}
			
		//B.SEARCH NAMES THAT BEGIN WITH 'USER' SPECIFIED 'KEYWORD', WE WILL REGENERATE THE BUI FIELD WITH THOSE ONLY. 
			//generate arraylist that contains each C:Drive dir, only if the file/folder contains with the keyword
			public ArrayList generateStreamFolderDirectoriesSearchModeB ( String _fieldStream, String keyword )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
				{
					String fullString = fileList [ i ];
					String formattedFullString = fullString.toLowerCase ( ), formattedSearchString = keyword.toLowerCase ( );
					
					//only build the array list, with respect to the number of names that contain the keyword
					if ( conveniencePack.stringSubsetEnquiry ( formattedFullString, formattedSearchString ) )
						value.add ( _fieldStream + fullString );
				}
				return value;
			}
			
			//generate arraylist that contains each search name. (what the user will search for via each bui node.
			//,Ofcourse, only if the file/folder contains the supplied keyword
			public ArrayList generateStreamFolderSearchNamesSearchModeB ( String _fieldStream, String keyword )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
				{
					String fullString = fileList [ i ];
					String formattedFullString = fullString.toLowerCase ( ), formattedSearchString = keyword.toLowerCase ( );
					
					//only build the array list, with respect to the number of names that contain the keyword
					if ( conveniencePack.stringSubsetEnquiry ( formattedFullString, formattedSearchString ) )
						value.add ( fullString );
				}
				return value;
			}
			
		//C.RETURN ENTIES THAT REPRESENT FOLDERS ONLY
			//generate arraylist that contains each C:Drive dir, only if the entity represents a folder
			public ArrayList generateStreamFolderDirectoriesSearchModeC ( String _fieldStream )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( new File ( _fieldStream + fileList [ i ] ).isDirectory ( ) ) //add to list only if this is a directory
						value.add ( _fieldStream + fileList [ i ] );
					
				return value;
			}
			
			//generate arraylist, only if the entity represents a folder
			public ArrayList generateStreamFolderSearchNamesSearchModeC ( String _fieldStream )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( new File ( _fieldStream + fileList [ i ] ).isDirectory ( ) ) //add to list only if this is a directory
						value.add ( fileList [ i ] );
					
				return value;
			}
			
		//D.RETURN ENTIES THAT DON'T REPRESENT FOLDERS, but rather files.
			//generate arraylist that contains each C:Drive dir, only if the entity represents a file
			public ArrayList generateStreamFolderDirectoriesSearchModeD ( String _fieldStream )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( !new File ( _fieldStream + fileList [ i ] ).isDirectory ( ) ) //add to list only if this is a directory
						value.add ( _fieldStream + fileList [ i ] );
					
				return value;
			}
			
			//generate arraylist, only if the entity represents a folder
			public ArrayList generateStreamFolderSearchNamesSearchModeD ( String _fieldStream )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( !new File ( _fieldStream + fileList [ i ] ).isDirectory ( ) ) //add to list only if this is a directory
						value.add ( fileList [ i ] );
					
				return value;
			}
			String test = "";
		//E.RETURN ENTIES THAT DON'T INCLUDE FILE NAMES IN DELETION/COPY/CUT ACTION LIST.
			//generate arraylist that contains each C:Drive dir.
			public ArrayList generateStreamFolderDirectoriesE ( String _fieldStream, ArrayList ignoranceList  )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( !fileList [ i ].equals ( ignoranceList.get ( i ) ) )
						value.add ( _fieldStream + fileList [ i ] );
						

				return value;
			}
			//generate arraylist that contains each search name. (what the user will search for via each bui node.
			public ArrayList generateStreamFolderSearchNamesE ( String _fieldStream, ArrayList ignoranceList )
			{
				ArrayList value = new ArrayList ( );
				
				File directory = new File ( _fieldStream );
				String [ ] fileList = directory.list ( );
				
				for ( int i = 0; i < fileList.length; i ++ )
					if ( !fileList [ i ].equals ( ignoranceList.get ( i ) ) )
						value.add ( fileList [ i ] );
					
				return value;
			}
    
    //generate target bui index
    public int getTargetBuiIndex ( String searchName )
    {
        int value = 0;
        for ( int i = 0; i < StreamFolderSearchNamesList.size ( ); i ++ )
            if ( StreamFolderSearchNamesList.get ( i ).equals ( searchName ) )
                value = i; 
        return value;
    }
	
	public int getContentLocationIndicatorMaxWidth ( )
	{
		return ( int ) contentLocationIndicator.getMaxWidth ( );
	}
	
    //line visibility controller
    public boolean getLineVisibilityEnquiry ( )
    {
        return lineVisibilityEnquiry;
    }
	
	public boolean getDirectoryDisplayerVisibilityEnquiry ( )
	{
		return directoryDisplayerVisibilityEnquiry;
	}
	
    public void setLineVisibilityEnquiry ( boolean value )
    {
        lineVisibilityEnquiry = value;
    }
    
    public void toggleLineVisibilityControl ( )
    {
        if ( getLineVisibilityEnquiry ( ) )
            setLineVisibilityEnquiry ( false );
        else if ( !getLineVisibilityEnquiry ( ) )
            setLineVisibilityEnquiry ( true );
			
		repaint ( );
    }
	
	public void setDirectoryDisplayerVisibilityEnquiry ( boolean value )
	{
		directoryDisplayerVisibilityEnquiry = value;
	}
	
    public void toggleDirectoryDisplayerVisibilityControl ( )
    {
        if ( getDirectoryDisplayerVisibilityEnquiry ( ) )
            directoryDisplayer.hide ( );
        else if ( !getDirectoryDisplayerVisibilityEnquiry ( ) )
            directoryDisplayer.show ( false, 0 );
			
		repaint ( );
	}
	
	public void updateDirectoryDisplayer ( )
	{
		directoryDisplayer.update ( );
	}
	
    public void toggleDescriptionModeControl ( UNICODE_AudioPlayer audioPlayer, String successAudioStream, String failureAudioStream, UNICODE_ConfigurationManager configurationManager )
    {
        if ( configurationManager.getDescriptionViewAnswerFromFile ( ).equals ( "on" ) )
		{
            descriptionViewController.setValue ( "off" );
			audioPlayer.playAudio ( failureAudioStream );
		}
        else if ( configurationManager.getDescriptionViewAnswerFromFile ( ).equals ( "off" ) )
		{
			descriptionViewController.setValue ( "on" );
			audioPlayer.playAudio ( successAudioStream );
		}
		
		configurationManager.updateDescriptionViewController ( descriptionViewController );
		setViewModeAnswer ( configurationManager.getDescriptionViewAnswerFromFile ( ) );
			
		repaint ( );
    }
 
	
 
    //dock animation functions
        //dock animation listener
        private class nodeEntityMenuAnimationListener implements ActionListener
        {
            public void actionPerformed ( ActionEvent actionEvent )
            {
                buiActionDock.performAxisAnimation ( 70, 180, 2.4f, 0.756f, 0.3f, "clockwise", nodeEntityMenuAnimationTimer );
                repaint ( );
            }
        }    
        public UNICODE_MenuPanel getBuiActionDock ( )
        {
            return buiActionDock;
        }
        public UNICODE_DonutMenuWindow getBuiActionWindow ( )
        {
            return buiActionWindow;
        }
        //mutators
        public void setBuiActionDock ( UNICODE_MenuPanel _buiActionDock )
        {
            buiActionDock = _buiActionDock;
        }
		public void setBuiActionWindow ( UNICODE_DonutMenuWindow _buiActionWindow )
		{
			buiActionWindow = _buiActionWindow;
		}
		public void disableBuiActionDonutWindow ( )
		{
			buiActionWindow.setVisible ( false );
		}
		public void enableBuiActionDonutWindow ( )
		{
			buiActionWindow.setVisible ( true );
		}
        //accesor for current bui index
        public int getCurrentBuiIndex ( )
        {
            return currentBuiIndex;
        }
		public boolean getFirstLetterSearchModeEnquiry ( )
		{
			return firstLetterSearchModeEnquiry;
		}
		public void setFirstLetterSearchModeEnquiry ( boolean value )
		{
			firstLetterSearchModeEnquiry = value;
		}
		public boolean getForwardNavigationActionEnquiry ( )
		{
			return forwardNavigationActionEnquiry;
		}
		public void setForwardNavigationActionEnquiry ( boolean value )
		{
			forwardNavigationActionEnquiry = value;
		}
    
    //scroll functions
        //mouse wheel controller - user driven
        private class mouseWheelListenening implements MouseWheelListener
        {
            public void mouseWheelMoved ( MouseWheelEvent mouseWheelEvent )
            {
                UNICODE_BUI_SHAPE buiShape = buis.get ( currentBuiIndex );
                UNICODE_BUI_BODY buiBody = buiShape.getBody ( );
			
                rotationDirection = mouseWheelEvent.getWheelRotation ( );
                
				if ( !parameterBubble.getParameterBubbleEnableEnquiry ( ) )
				{
					if ( rotationDirection > 0 )
						slide ( "leftwards" );
					else if ( rotationDirection < 0 )
						slide ( "rightwards" );
				}
				else
					parameterBubble.updateParameterBubbleSize ( rotationDirection, 10 );
					
                repaint ( );
            }
        }
        
        //scroll action listeners - non - user driven
            //mutators
            public void setSearchName ( String value )
            {
                searchName = value;
            }
        
            //leftwards
            private class scrollRightwardsListener implements ActionListener
            {
                public void actionPerformed ( ActionEvent actionEvent ) 
                {
					if ( scrollDurationTracker < maximumScrollDuration )
					{
						if ( getX ( ) > - getLastDescriptorLocation ( ) )
						{
							UNICODE_BUI_SHAPE buiShape = buis.get ( currentBuiIndex );
							UNICODE_BUI_BODY buiBody = buiShape.getBody ( );

							scrollLeftwardsAdjuster = getX ( ) - ( int )  scrollRate;
							setBounds ( scrollLeftwardsAdjuster, getY ( ), getWidth ( ), getHeight ( ) );
							contentLocationIndicator.performAnimation ( "rightwards" );
							directoryDisplayer.provideRightwardStabilization ( );
							scrollAmountTracker += scrollRate;
						}
					}
					else
						scrollRightwardsTimer.stop ( );
                }
            }
            //rightwards
            private class scrollLeftwardsListener implements ActionListener
            {
                public void actionPerformed ( ActionEvent actionEvent ) 
                {
					if ( scrollDurationTracker < maximumScrollDuration )
					{
						if ( getX ( ) < getFirstDescriptorLocation ( ) )
						{
							UNICODE_BUI_SHAPE buiShape = buis.get ( currentBuiIndex );
							UNICODE_BUI_BODY buiBody = buiShape.getBody ( );
							
							scrollRightwardsAdjuster = getX ( ) + ( int ) scrollRate;
							setBounds ( scrollRightwardsAdjuster, getY ( ), getWidth ( ), getHeight ( ) );
							contentLocationIndicator.performAnimation ( "leftwards" );
							directoryDisplayer.provideLeftwardStabilization ( );	
							scrollAmountTracker -= scrollRate;
						}
					}
					else
						scrollLeftwardsTimer.stop ( );
                }
            }
			
			private class scrollDurationListener implements ActionListener
            {
                public void actionPerformed ( ActionEvent actionEvent ) 
                {
					scrollDurationTracker += 1000;
                }
            }
            //funtion to perfom node search by name supplied via button dock 0,'s text field.
            public void performNameSearch ( String targetFolderName )
            {
                //set search name wrt user specified value
                setSearchName ( targetFolderName );
            
                UNICODE_BUI_SHAPE buiShape = buis.get ( getTargetBuiIndex ( searchName ) );
                UNICODE_BUI_BODY buiBody = buiShape.getBody ( );
                int buiBodyX = ( int ) buiBody.getBody ( ).getX ( );
                
                //start appropriate scroll timers.
                if ( scrollRightwardsAdjuster < buiBodyX )
                    scrollRightwardsTimer.start ( );
//                 else if ( scrollLeftwardsAdjuster > bui.getBui ( ).getX ( ) )
//                     scrollLeftwardsTimer.start ( );
            }
			
    //button resize action listeenr
    private class descriptorResizeActionListening implements ActionListener
    {
        public void actionPerformed ( ActionEvent aEvent )
        {
            establishDescriptorVisualResponseActionListenerComponent ( );
            repaint ( );
        }
    }	
	
	//establish descriptor visual response action listener component
	public void establishDescriptorVisualResponseActionListenerComponent ( )
	{
		if ( buiDescriptors.get ( resizableDescriptorIndex ).getDimensionAlterationRate ( ) <= buiDescriptors.get ( resizableDescriptorIndex ).getProjectedWidth ( ) )
			buiDescriptors.get ( currentDescriptorIndex ).performGeometricalMutation ( );
	}
	
	public void performNodeDeletionViaDynamicHighlighting ( )
	{
		for ( int i = 0; i < buis.size ( ); i ++ )
			if ( dynamicHiglighter.getStrokeEntailPointEnquiry ( ( int ) buis.get ( i ).getBody ( ).getBody ( ).getX ( ), ( int ) buis.get ( i ).getBody ( ).getBody ( ).getY ( ) ) )
				dynamicHighlightingAffectedFileList.add ( buis.get ( i ).getBody ( ).getEntityStream ( ) );
		reInitializeFieldSearchModeE ( fieldStream, dynamicHighlightingAffectedFileList );
	}
	
	public Thread [ ] getDescriptorCollectionSelectedActionThreads ( )
	{
		Thread [ ] value = new Thread [ 4 ];	
		
		Thread Thread_0 = new Thread
		(
		   new Runnable ( )
		   {
			   public void run ( )
			   {
					new UNICODE_MessageBoxWindow ( true, "file(s) successfully copied!", 0.9f, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour , true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 8 );    
					dynamicHiglighter.reset ( );
					parameterBubble.reset ( runnableFieldInstance );
			   }
		   }
		);
		Thread Thread_1 = new Thread
		(
			new Runnable ( )
			{
				public void run ( )
				{
					new UNICODE_MessageBoxWindow ( true, "file(s) successfully cut!", 0.9f, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour , true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 8 );    
					dynamicHiglighter.reset ( );
					parameterBubble.reset ( runnableFieldInstance );
				}
			}
		);
		Thread Thread_2 = new Thread
		(
			new Runnable ( )
			{
				public void run ( )
				{
					Thread fileDeletionThread = new Thread
					(
						new Runnable ( )
						{
							public void run ( )
							{
								//performNodeDeletionViaDynamicHighlighting ( );
								new UNICODE_MessageBoxWindow ( true, "file(s) successfully deleted!", 0.9f, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour , true, "data/images/all/message box/okay/","rr", 20, 20, 10, 10, 0, 8 );   
							}
						}
					);
					
					new UNICODE_MessageBoxWindow ( true, fileDeletionThread, "are you certain?", 0.9f, backgroundColour, buttonOutlineColour, labelBackgroundColour, labelForegroundColour , true, "data/images/all/message box/okay - threaded/","rr", 20, 20, 10, 10, 0, 8 );   
					dynamicHiglighter.reset ( );
					parameterBubble.reset ( runnableFieldInstance );
				}
			}
		);
		Thread Thread_3 = new Thread
		(
		   new Runnable ( )
		   {
			   public void run ( )
			   {
					dynamicHiglighter.reset ( );
					parameterBubble.reset ( runnableFieldInstance );
			   }
		   }
		);
		
		value [ 0 ] = Thread_0;
		value [ 1 ] = Thread_1;
		value [ 2 ] = Thread_2;
		value [ 3 ] = Thread_3;
		return value;
	}
	
	public void enableDynamicHighlightingPerformanceMode ( )
	{
		//we only want to create bg when file doesn't exist.
		//Otherwise the puspose of this mode would be defated, sontantly generating 
		//bcrkground images when we don't need to.
		if ( !new File ( dynamicHighlightingStream ).exists ( ) ) 
		{
			new File ( dynamicHighlightingDirectory ).mkdir ( );
			conveniencePack.saveScreen ( dynamicHighlightingStream );
			dynamicHighlightingImageIcon = new ImageIcon ( dynamicHighlightingStream );
			dynamicHighlightingImage = dynamicHighlightingImageIcon.getImage ( );
			dynamicHighlightingImage = createImage ( dynamicHighlightingImage.getSource ( ) );
			dynamicHighlightingModeEnquiry = true;
		}
	}
	public void disableDynamicHighlightingPerformanceMode ( )
	{
		if ( new File ( dynamicHighlightingStream ).exists ( ) ) 
		{
			directoryEditor.removeFolder ( new File ( dynamicHighlightingDirectory ) );
			dynamicHighlightingModeEnquiry = false;
		}
	}
	
	public void resetScrollDuration ( )
	{
		scrollDurationTimer.stop ( );
		scrollDurationTracker = 0;
		scrollDurationTimer.start ( );
	}
	
	public void slide ( String direction )
	{
		if ( direction.equals ( "rightwards" ) )
		{
			resetScrollDuration ( );
			scrollLeftwardsTimer.stop ( );
			scrollRightwardsTimer.start ( );
		}
		else if ( direction.equals ( "leftwards" ) )
		{
			resetScrollDuration ( );
			scrollRightwardsTimer.stop ( );
			scrollLeftwardsTimer.start ( );
		}
		repaint ( );
	}
	
	//with occupied by bui descriptors
	public int getPreciseWidth ( )
	{
		int returnValue = 0;
		
		Rectangle rectangle = null;
	
		//get the position of the last descriptor.
		UNICODE_BUI_DESCRIPTOR descriptorShape = buiDescriptors.get ( maxBuis - 1 );
				
		//create a rectangle wrt to data derived above
		rectangle = new Rectangle ( 0, 0, ( int ) ( descriptorShape.getBody ( ).getX ( ) + descriptorShape.getBody ( ).getWidth ( ) ), 1 );
		
		//establish returnValue
		returnValue = ( int ) rectangle.getWidth ( );
		
		return returnValue;
	}
	
	public int getLastDescriptorLocation ( )
	{
		int returnValue = 0;
		
		//get the position of the last descriptor.
		UNICODE_BUI_DESCRIPTOR descriptorShape = buiDescriptors.get ( maxBuis - 1 );
		
		//establish returnValue
		returnValue = ( int ) ( descriptorShape.getBody ( ).getX ( ) + descriptorShape.getBody ( ).getWidth ( ) );
		
		return returnValue;
	}
	
	public int getFirstDescriptorLocation ( )
	{
		int returnValue = 0;
		
		//get the position of the last descriptor.
		UNICODE_BUI_DESCRIPTOR descriptorShape = buiDescriptors.get ( 0 );
		
		//establish returnValue
		returnValue = ( int ) ( descriptorShape.getBody ( ).getX ( ) );
		
		return returnValue;
	}	

	public void toggleParameterBubbleEnableEnquiry ( )
	{
		parameterBubble.toggleParameterBubbleEnableEnquiry ( this, getDescriptorCollectionSelectedActionThreads ( ) );
	}
}
