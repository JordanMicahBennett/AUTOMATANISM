package data.packages.UNICODE;
//author ~ Jordan Micah Bennett

import java.util.Comparator;

public class UNICODE_BuiShapeTimeWaveShapeComparator implements Comparator <UNICODE_BUI_SHAPE>
{
	@Override
	public int compare ( UNICODE_BUI_SHAPE previousShape, UNICODE_BUI_SHAPE nextShape )
	{
		return Long.valueOf ( previousShape.getBody ( ).getLastModifiedLongValue ( ) ).compareTo ( nextShape.getBody ( ).getLastModifiedLongValue ( ) );
	}
}