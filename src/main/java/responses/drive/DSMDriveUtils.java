package responses.drive;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import responses.drive.DSMDriveFields.Item;
import responses.drive.DSMDriveFields.ItemId;

public class DSMDriveUtils {

//	static 
//	static {
//		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
//	}
	
	public static ItemId itemId(Item item) {
		return new ItemId(item.getFile_id());
	}
	
	
	public static Date time(Long timeInSeconds) {

		return new Date(timeInSeconds*1000);
	}
}
