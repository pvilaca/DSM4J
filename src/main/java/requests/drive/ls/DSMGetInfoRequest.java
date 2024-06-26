package requests.drive.ls;

import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import exeptions.DsmListFolderException;
import requests.DsmAbstractRequest;
import requests.DsmAuth;
import responses.Response;
import responses.drive.ls.DSMGetInfoResponse;

public class DSMGetInfoRequest extends DsmAbstractRequest<DSMGetInfoResponse>{

	/**
     * Path for a file or folder to get info.
     */
	private String path_item;
	
	public DSMGetInfoRequest(DsmAuth auth) {
		super(auth);

		this.apiName = "SYNO.SynologyDrive.Files";
        this.version = 1;
        this.method = "get";
        this.path = "webapi/entry.cgi";
	}

	@Override
	protected TypeReference getClassForMapper() {
		return new TypeReference<Response<DSMGetInfoResponse>>() {};
	}

	public String getPathItem() {
		return path_item;
	}
	
	public void setPathItem(String path) {
		this.path_item = path;
	}

	@Override
	public Response<DSMGetInfoResponse> call() {
		addParameter("path", escape(Optional.ofNullable(this.path_item).orElseThrow(() -> new DsmListFolderException("the path can not be null"))));
		
		return super.call();
	}
}
