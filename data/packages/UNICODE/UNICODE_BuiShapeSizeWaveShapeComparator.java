package data.packages.UNICODE;
//author ~ Jordan Micah Bennett

import java.util.Comparator;

public class UNICODE_BuiShapeSizeWaveShapeComparator implements Comparator <UNICODE_BUI_SHAPE>
{
	@Override
	public int compare ( UNICODE_BUI_SHAPE previousShape, UNICODE_BUI_SHAPE nextShape )
	{
		return Double.valueOf ( previousShape.getEstimatedDescriptorBodyWidth ( ) ).compareTo ( nextShape.getEstimatedDescriptorBodyWidth ( ) );
	}
}