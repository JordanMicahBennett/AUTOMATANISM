package data.packages.UNICODE;
//author ~ Jordan Micah Bennett

import java.util.Comparator;

public class UNICODE_BuiShapeTimeWaveDescriptorComparator implements Comparator <UNICODE_BUI_DESCRIPTOR>
{
	@Override
	public int compare ( UNICODE_BUI_DESCRIPTOR previousDescriptor, UNICODE_BUI_DESCRIPTOR nextDescriptor )
	{
		return Long.valueOf ( previousDescriptor.getBodyClass ( ).getLastModifiedLongValue ( ) ).compareTo ( nextDescriptor.getBodyClass ( ).getLastModifiedLongValue ( ) );
	}
}