package requests.drive.ls;

import requests.DsmAuth;

public class DSMDriveFilesListAncestorRequest extends DSMDriveFilesRequest{

	/**
     * Path for a file or folder to get info.
     */
	
	
	public DSMDriveFilesListAncestorRequest(DsmAuth auth) {
		super(auth);
        this.method = "list_ancestor";
	}


}
