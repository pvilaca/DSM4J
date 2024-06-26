package responses.drive.ls;

import java.util.List;

import lombok.Data;
import responses.drive.DSMDriveFields;

@Data
public class DSMDriveListResponse{

	List<DSMDriveFields.Item> items;
	Integer total;
}
