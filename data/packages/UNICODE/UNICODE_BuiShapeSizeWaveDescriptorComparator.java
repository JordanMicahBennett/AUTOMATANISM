package data.packages.UNICODE;
//author ~ Jordan Micah Bennett

import java.util.Comparator;

public class UNICODE_BuiShapeSizeWaveDescriptorComparator implements Comparator <UNICODE_BUI_DESCRIPTOR>
{
	@Override
	public int compare ( UNICODE_BUI_DESCRIPTOR previousDescriptor, UNICODE_BUI_DESCRIPTOR nextDescriptor )
	{
		return Double.valueOf ( previousDescriptor.getDescriptorBodyWidth ( ) ).compareTo ( nextDescriptor.getDescriptorBodyWidth ( ) );
	}
}