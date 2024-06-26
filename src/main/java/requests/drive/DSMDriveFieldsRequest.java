package requests.drive;

public class DSMDriveFieldsRequest {

	
	public static enum Location {
		mydrive,
		team_folders,
		shared_with_me,
		shared_with_others,
		starred,
		custom
	}
	
	
	public enum Sort {
		modified_time,
		created_time,
		crtime,
		name,
		type,
		owner,
		size
    }
}
