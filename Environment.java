/*
    Author: Jordan Micah Bennett

    Title  : 'automatanism' - a simple cellular automaton emulating arbitrary life ( yet another game of life code )

    **Game rules, internet notes & background information**
    
        **Note** 
            The number of live neighbors is always based on the cells before the rule was applied. In other words, we must first find all of the cells that change before changing any of them. 
            Sounds like a job for a computer!

        **Background**
            Life was invented by the mathematician John Conway in 1970. He choose the rules carefully after trying many other possibilities, some of which caused the cells to die too fast and others 
            which caused too many cells to be born. Life balances these tendencies, making it hard to tell whether a pattern will die out completely, form a stable population, or grow forever.
            
            Life is just one example of a cellular automaton, which is any system in which rules are applied to cells and their neighbors in a regular grid.
            
            There has been much recent interest in cellular automata, a field of mathematical research. Life is one of the simplest cellular automata to have been studied, but many others have been 
            invented, often to simulate systems in the real world.
            
            In addition to the original rules, Life can be played on other kinds of grids with more complex patterns. There are rules for playing on hexagons arranged in a honeycomb pattern, and
            games where cells can have more than two states (imagine live cells with different colors).
            
            Life is probably the most often programmed computer game in existence. There are many different variations and information on the web. (See the Paul Callahan's home page for more information.)
        
        **Game rules**
            rule 0) A dead cell with exactly three live neighbors becomes a live cell ( birth ).
            rule 1) A live cell with two or three live neighbors stays alive ( survival ).
            otherwise) In all other cases, a cell dies or remains dead (overcrowding or loneliness).
*/


import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.util.ArrayList;

import data.packages.UNICODE.*;

public class Environment extends JPanel
{
    //attributes
    private ArrayList <Cell> cellCollection = null;
    private int MAXIMUM_CELLS = 0;
    private int MAXIMUM_ROWS = 0;
    private int cellSpan = 0;
    private int xCoordinate = 0, yCoordinate = 0, initialXCoordinate = 0, initialYCoordinate = 0;
    private JFrame applicationFrame = null;
    private Timer environmentClock = null;
    private Point draggedMouseCoordinates = null, heldMouseCoordinates = null;
    private Dimension applicationFrameDimension = null;
    private Color platformColour = null;
    private int ENVIRONMENTAL_CHANGE_RATE = 0;
    
    //constructor
    public Environment ( JFrame applicationFrame, Dimension applicationFrameDimension )
    {
        //estanlish application frame
        this.applicationFrame = applicationFrame;
        this.applicationFrameDimension = applicationFrameDimension;
              
        //establish cells
        MAXIMUM_CELLS = 70000;
        MAXIMUM_ROWS = 200;
        ENVIRONMENTAL_CHANGE_RATE = 1;
        cellSpan = 2;
        cellCollection = new ArrayList <Cell> ( );
        int initialXCoordinate = ( int ) ( ( applicationFrameDimension.getWidth ( ) / 2 ) - ( ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) * cellSpan ) / 2 ) );
        int initialYCoordinate = ( int ) ( ( applicationFrameDimension.getHeight ( ) / 2 ) - ( MAXIMUM_ROWS * cellSpan ) / 2 );
        int xCordinate = initialXCoordinate, yCoordinate = initialYCoordinate;
        
        platformColour = new Color ( 245, 245, 245 );
        setBackground ( platformColour );
        
        for ( int i = 0; i < MAXIMUM_CELLS; i ++ )
        {
            if ( i % ( MAXIMUM_CELLS / MAXIMUM_ROWS ) == 0 )
            {
                xCoordinate = initialXCoordinate;
                yCoordinate += cellSpan;
            }
                
            cellCollection.add ( new Cell ( platformColour, new Color ( 20, 20, 20 ), CellState.DEAD, new Point ( xCoordinate, yCoordinate ), new Dimension ( cellSpan, cellSpan ) ) );
            
            xCoordinate += cellSpan;
        }
        

        //establish environment clock
        environmentClock = new Timer ( ENVIRONMENTAL_CHANGE_RATE, new environmentActionContainer ( ) );
        
        //establish mouse listeners
        addMouseMotionListener ( new mouseListening ( ) );
        addMouseListener ( new mouseListening ( ) );
        addMouseWheelListener ( new mouseWheelListening ( ) );
        //enableDenseInitialEnvironmentalChange ( 20 ); //start with dense field of cells
    }
    
    //establish cell relationship rules
    public void enableEnvironmentalChange ( )
    {
        for ( int i = 0; i < MAXIMUM_CELLS; i ++ )
        {
            // conway's rules
            // rule 0) A dead cell with exactly three live neighbors becomes a live cell ( birth ).
            if ( cellCollection.get ( i ).isDead ( ) )
                if ( evaluateSourroundings ( i ) == 3 ) 
                    cellCollection.get ( i ).enliven ( );
                    
            // rule 1) A live cell with two or three live neighbors stays alive ( survival ).
            if ( cellCollection.get ( i ).isAlive ( ) )
                if ( ( evaluateSourroundings ( i ) == 2 ) || ( evaluateSourroundings ( i ) == 3 ) ) 
                    cellCollection.get ( i ).enliven ( );      
                    
            // otherwise) In all other cases, a cell dies or remains dead (overcrowding or loneliness).
            else 
                cellCollection.get ( i ).kill ( );
        }
    }
    
    public void enableDenseInitialEnvironmentalChange ( int cycleCardinality )
    {
        for ( int n = 0; n < cycleCardinality; n ++ )
        {
            //start of by manually inserting live cells
            for ( int i = 0; i < ( new java.util.Random ( ).nextInt ( MAXIMUM_CELLS ) + 1 ); i ++ )
            {
                int randomIndex = new java.util.Random ( ).nextInt ( MAXIMUM_CELLS ) + 1;
                cellCollection.get ( randomIndex ).enliven ( );   
            }
        }
    }
    
    //essentially, cell painting
    public void enableSparseInitialEnvironmentalChange ( MouseEvent mouseEvent )
    {
        //start of by manually inserting live cells
        for ( int i = 0; i < MAXIMUM_CELLS; i ++ )
            if ( cellCollection.get ( i ).getBody ( ).contains ( ( int ) mouseEvent.getX ( ), ( int ) mouseEvent.getY ( ) ) )
                cellCollection.get ( i ).enliven ( );   
    }
  
    public int evaluateSourroundings ( int explorationIndex )
    {
        int returnValue = 0;
        
        //[TOP:ci-1.ri-1, ci.ri-1, ci.ri-1] [CURRENT:ci-1.ri, ci+1.ri] [BOTTOM:ci-1.ri+1, ci.ri+1, ci.ri+1], WHERE i is index, c=current, and r=row
        //evaluate above row [if the top left, top middle and or top right cell's index is : (>-1) && (immediately above current index) && (represents an alive cell)]
        returnValue = ( /*>-1*/ explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) > -1 ) && ( ( cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) ).isAlive ( ) ) ? returnValue + 1 : returnValue;          
        returnValue = ( /*>-1*/ explorationIndex - ( MAXIMUM_CELLS / MAXIMUM_ROWS ) > -1 ) && ( ( cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex - ( MAXIMUM_CELLS / MAXIMUM_ROWS ) ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex - ( MAXIMUM_CELLS / MAXIMUM_ROWS ) ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        returnValue = ( /*>-1*/ explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) > -1 ) && ( ( cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex - ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        //evaluate current row [if the left, and or right cell's index is : (>-1,<MAXIMUM_CELLS) && (immediately beside current index) && (represents an alive cell)]
        returnValue = ( /*>-1*/ explorationIndex - 1 > -1 ) && ( ( cellCollection.get ( explorationIndex ).getBody ( ).getX ( ) - cellCollection.get ( explorationIndex - 1 ).getBody ( ).getX ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex - 1 ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        returnValue = ( /*<MAXIMUM_CELLS*/ explorationIndex + 1 < MAXIMUM_CELLS ) && ( ( cellCollection.get ( explorationIndex + 1 ).getBody ( ).getX ( ) - cellCollection.get ( explorationIndex ).getBody ( ).getX ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex + 1 ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        //evaluate below row [if the bottom left, bottom middle and or bottom right cell's index is : (<MAXIMUM_CELLS) && (immediately above current index) && (represents an alive cell)]
        returnValue = ( /*<MAXIMUM_CELLS*/ explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) < MAXIMUM_CELLS ) && ( ( cellCollection.get ( explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) - 1 ) ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        returnValue = ( explorationIndex + ( MAXIMUM_CELLS / MAXIMUM_ROWS ) < MAXIMUM_CELLS ) && ( ( cellCollection.get ( explorationIndex + ( MAXIMUM_CELLS / MAXIMUM_ROWS ) ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex + ( MAXIMUM_CELLS / MAXIMUM_ROWS ) ).isAlive ( ) ) ? returnValue + 1 : returnValue; 
        returnValue = ( explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) < MAXIMUM_CELLS ) && ( ( cellCollection.get ( explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) ).getBody ( ).getY ( ) - cellCollection.get ( explorationIndex ).getBody ( ).getY ( ) ) == cellSpan ) && ( cellCollection.get ( explorationIndex + ( ( MAXIMUM_CELLS / MAXIMUM_ROWS ) + 1 ) ).isAlive ( ) ) ? returnValue + 1 : returnValue;
 
        return returnValue;
    }
            
    //methods
    //miscallaneous
    public void paintComponent ( Graphics graphics )
    {
        super.paintComponent ( graphics );
        Graphics2D graphics2d = ( Graphics2D ) graphics;
        
        //draw grid of cells
        for ( int i = 0; i < MAXIMUM_CELLS; i ++ )
        {
            cellCollection.get ( i ).draw ( graphics2d );
        }
    }
    
    
    private class environmentActionContainer implements ActionListener 
    {
        public void actionPerformed ( ActionEvent actionEvent )
        {
             enableEnvironmentalChange ( );
             repaint ( );
        }
    }
    
    private class mouseListening implements MouseMotionListener, MouseListener
    {
        public void mouseMoved ( MouseEvent mouseEvent )
        {
        }
        public void mouseDragged ( MouseEvent mouseEvent )
        {
            draggedMouseCoordinates = mouseEvent.getLocationOnScreen ( );
            
            //allow the frame to be dragged along the screen edge space (corners) that the life grid does not
            //encompass
            if 
            (
                ( ( mouseEvent.getX ( ) < initialXCoordinate ) || ( mouseEvent.getX ( ) > initialXCoordinate + ( MAXIMUM_CELLS / MAXIMUM_ROWS * cellSpan ) ) )
                ||
                ( ( mouseEvent.getY ( ) < initialYCoordinate ) || ( mouseEvent.getY ( ) > initialYCoordinate + ( MAXIMUM_ROWS * cellSpan ) ) )
            )
                applicationFrame.setLocation ( ( int ) ( draggedMouseCoordinates.getX ( ) - heldMouseCoordinates.getX ( ) ), ( int ) ( draggedMouseCoordinates.getY ( ) - heldMouseCoordinates.getY ( ) ) );
            else //otherwise always enable cell painting
                enableSparseInitialEnvironmentalChange ( mouseEvent );
            
            repaint ( );
        }
        public void mouseEntered ( MouseEvent mouseEvent )
        {
        }
        public void mouseExited ( MouseEvent mouseEvent )
        {
        }    
        public void mouseClicked ( MouseEvent mouseEvent )
        {
            enableSparseInitialEnvironmentalChange ( mouseEvent );
           
            repaint ( );
        }
        public void mousePressed ( MouseEvent mouseEvent )
        {
            heldMouseCoordinates = mouseEvent.getPoint ( );
        }
        public void mouseReleased ( MouseEvent mouseEvent )
        {
        }
    }
    
    
    private class mouseWheelListening implements java.awt.event.MouseWheelListener
    {
        public void mouseWheelMoved ( java.awt.event.MouseWheelEvent mouseWheelEvent )
        {
            int rotationDirection = mouseWheelEvent.getWheelRotation ( );
            
            if ( rotationDirection > 0 )
                environmentClock.stop ( );
            else
                environmentClock.start ( );
            repaint ( );
        }
    }
}