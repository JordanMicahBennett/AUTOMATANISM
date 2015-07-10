//author: jordan micah bennett
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;

public class Cell 
{
    //attributes
    private Color currentColour = null, unconceivedStateColour = null, deadStateColour = null, aliveStateColour = null;
    private CellState cellState = null;
    private Rectangle cellBody = null;
    private Point orientation = null;
    private Dimension dimension = null;
    
    //constructor
    public Cell ( Color deadStateColour, Color aliveStateColour, CellState cellState, Point orientation, Dimension dimension )
    {
        this.currentColour = deadStateColour;
        this.unconceivedStateColour = deadStateColour;
        this.deadStateColour = deadStateColour;
        this.aliveStateColour = aliveStateColour;
        this.cellState = cellState;
        this.orientation = orientation;
        this.dimension = dimension;
        cellBody = new Rectangle ( ( int ) orientation.getX ( ), ( int ) orientation.getY ( ), ( int ) dimension.getWidth ( ), ( int ) dimension.getHeight ( ) );
    }
    
    //methods
    //accessors
    public boolean isAlive ( )
    {
        return cellState == CellState.ALIVE;
    }
    public boolean isDead ( )
    {
        return cellState == CellState.DEAD;
    }
    public Rectangle getBody ( )
    {
        return cellBody;
    }
    //mutators
    public void kill ( )
    {
        cellState = CellState.DEAD;
        setColor ( cellState );
    }
    public void enliven ( )
    {
        cellState = CellState.ALIVE;
        setColor ( cellState );
    }

    public void setColor ( CellState cellState )
    {
        switch ( cellState )
        {
            case DEAD:
            {
                currentColour = deadStateColour;
            }
            break;
            case ALIVE:
            {
                currentColour = aliveStateColour;
            }
        }
    }
    //miscallaneous
    public void draw ( Graphics2D graphics2d)
    { 
        graphics2d.setColor ( currentColour );
        graphics2d.fill ( cellBody );
    }
}