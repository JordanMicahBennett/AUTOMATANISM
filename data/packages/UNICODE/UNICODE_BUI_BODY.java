package data.packages.UNICODE;
import java.awt.geom.Ellipse2D;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import javax.swing.JPanel;
import java.util.Date;

public class UNICODE_BUI_BODY 
{
    //attributes
    private Ellipse2D buiBody = null;
    private String entityStream, entitySearchName;
    private double x, y, width, height;
    private boolean directoryEnquiry = false; //will be used to determine whether the current UNICODE_BUI_BODY represents an entity that is a directory. If not, it is assumed that it represnets a file instead.
    private int entityIndex;
    private UNICODE_ConveniencePack conveniencePack = new UNICODE_ConveniencePack ( );
    private UNICODE_DirectoryEditor directoryEditor = new UNICODE_DirectoryEditor ( );
	private int folderIndicatorScaleFactor = 0;
    
    //constructor
    public UNICODE_BUI_BODY ( String _entityStream, String _entitySearchName, double _x, double _y, double _width, double _height, int _entityIndex, int _folderIndicatorScaleFactor )
    {
        //establish entity stream
        entityStream = _entityStream;
        
        //establish entity index
        entityIndex = _entityIndex;
        
        //establish entity directory name
        entitySearchName = _entitySearchName;
		
		//establish folderIndicatorScaleFactor - folders shapes are bigger than files. this dictates the disparity, which is to say it controlls by how much folder shapes will be bigger.
		folderIndicatorScaleFactor = _folderIndicatorScaleFactor;
        
        //establish orientation
        x = _x;
        y = _y;
        
        //establish dimension
		if ( representsDirectory ( getEntityStream ( ) ) )
		{
			width = _width + folderIndicatorScaleFactor;
			height = _height + folderIndicatorScaleFactor;
		}
		else
		{
			width = _width;
			height = _height;
		}
			
        
        //establish UNICODE_BUI_BODY
        buiBody = new Ellipse2D.Double ( x, y, width, height );
    }
    
    
    //accessors
    public Ellipse2D getBody ( )
    {
        return buiBody;
    }
    
    public String getEntityStream ( )
    {
        return entityStream + "/";
    }
	
    public String getWindowsEntityStream ( )
    {
        return entityStream;
    }
	
	public File getEntityFile ( )
	{
		return new File ( getEntityStream ( ) );
	}
    public String getEntityFileName ( )
    {
        return getEntityFile ( ).getName ( );
    }    
	
    public String getEntitySearchName ( )
    {
        return entitySearchName;
    }    
    
    public int getEntityIndex ( )
    {
        return entityIndex;
    }
    
    public boolean representsDirectory ( String stream )
    {
        return new File ( stream ).isDirectory ( );
    }
    
    //actions
        //0.open folder
        public void openRepresentedWindowsVersion ( )
        {
			directoryEditor.openFolder ( getEntityStream ( ) );
        }
		
		//the 'fieldGenerationMethod' varibale determines how bui field will be mutated:
        public void openRepresentedBuiVersion ( JPanel masterPanel, UNICODE_BUI_FIELD buiField, String _streamFolder, boolean forwardNavigationEnquiry )
        {
			//if node represents a folder, then hide current bui field, then add new bui field with folder enties
			if ( representsDirectory ( _streamFolder ) )
			{
				buiField.reInitializeField ( _streamFolder, forwardNavigationEnquiry );
				buiField.repaint ( );
				masterPanel.repaint ( );
			}
			//otherwise its a file, herego, use default windows file opener. Windows will handle this automaitically
			else
				openRepresentedWindowsVersion ( );
		}
		
		//mutates the bui field, but rather than accoutning for navigation, it accounts for search results
		//based on a letter supplied as 'commencingLetter'
        public void openRepresentedBuiVersionSearchModeA ( JPanel masterPanel, UNICODE_BUI_FIELD buiField, String buiFieldStream, String commencingLetter )
        {
			buiField.reInitializeFieldSearchModeA ( buiFieldStream, commencingLetter );
			buiField.repaint ( );
			masterPanel.repaint ( );
		}
		
		//mutates the bui field, but rather than accoutning for navigation, it accounts for search results
		//based on a keyword supplied as 'keyWord'
        public void openRepresentedBuiVersionSearchModeB ( JPanel masterPanel, UNICODE_BUI_FIELD buiField, String buiFieldStream, String keyWord )
        {
			buiField.reInitializeFieldSearchModeB ( buiFieldStream, keyWord );
			buiField.repaint ( );
			masterPanel.repaint ( );
		}
		
		//mutates the bui field, but rather than accoutning for navigation, it accounts for search results
		//based on the fact that we only want to return folder entities
        public void openRepresentedBuiVersionSearchModeC ( JPanel masterPanel, UNICODE_BUI_FIELD buiField, String buiFieldStream )
        {
			buiField.reInitializeFieldSearchModeC ( buiFieldStream );
			buiField.repaint ( );
			masterPanel.repaint ( );
		}
		
		//mutates the bui field, but rather than accoutning for navigation, it accounts for search results
		//based on the fact that we only want to return file entities
        public void openRepresentedBuiVersionSearchModeD ( JPanel masterPanel, UNICODE_BUI_FIELD buiField, String buiFieldStream )
        {
			buiField.reInitializeFieldSearchModeD ( buiFieldStream );
			buiField.repaint ( );
			masterPanel.repaint ( );
		}
        
        //1.ATTRIBUTES/PROPERTIS
        //attributes
            //get name
            public String getRepresentedName ( )
            {
                return new File ( getEntityStream ( ) ).getName ( );
            }
            //get type
            public String getRepresentedType ( String stream )
            {
                String returnValue = "";
                
                if ( representsDirectory ( stream ) )
                    returnValue = "Folder";
                else
                    returnValue = new MimetypesFileTypeMap ( ).getContentType ( new File ( getEntityStream ( ) ) );
                
                return returnValue;
            }
            //get size
            public String getRepresentedSize ( )
            {
                double bytes = new File ( getEntityStream ( ) ).length ( );
                double kilobytes = ( bytes / 1024 );
                double megabytes = ( kilobytes / 1024 );
                
                return "" + kilobytes + "Kb or " + bytes + " bytes";
            }
            //get long size
            public String getRepresentedLongSize ( )
            {
                double bytes = new File ( getEntityStream ( ) ).length ( );
                double kilobytes = ( bytes / 1024 );
                double megabytes = ( kilobytes / 1024 );
                
                return "" + kilobytes + "Kb or " + bytes + " bytes";
            }
            //get last modified
            public String getRepresentedLastModified ( )
            {
                return "" + new Date ( new File ( getEntityStream ( ) ).lastModified ( ) );
            }
            //get whether entity is hidden
            public String getRepresentedHiddenEnquiry ( )
            {
                return "" + new File ( getEntityStream ( ) ).isHidden ( );
            }
			
			public long getLastModifiedLongValue ( )
			{
				return new File ( getEntityStream ( ) ).lastModified ( );
			}
        
        //2.copy
        public void copyRepresented ( )
        {
        }

        //3.rename
        public void renameRepresented ( String newName )
        {
            String oldFileName = new File ( getEntityStream ( ) ).getName ( );
            new File ( getEntityStream ( ) ).renameTo ( conveniencePack.makeFile ( getEntityStream ( ).replace ( oldFileName, newName ) ) );
        }
        //4.delete
        public void deleteRepresented ( )
        {
            //check if a folder is being deleted, if so, use directory editor 
            //for deletion, else use simple delete method on file wrt to stream.
            if ( representsDirectory ( getEntityStream ( ) ) )
                directoryEditor.removeFolder ( conveniencePack.makeFile ( getEntityStream ( ) ) );
            else
                new File ( getEntityStream ( ) ).delete ( );
        }
        //5.set read only
        public void setRepresentedReadOnly ( )
        {
            new File ( getEntityStream ( ) ).setReadOnly ( );
        } 
}
