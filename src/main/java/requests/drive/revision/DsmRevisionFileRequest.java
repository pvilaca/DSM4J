package requests.drive.revision;

import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import exeptions.DsmListFolderException;
import requests.DsmAbstractRequest;
import requests.DsmAuth;
import responses.Response;
import responses.drive.revision.DsmRevisionResponse;

public class DsmRevisionFileRequest extends DsmAbstractRequest<DsmRevisionResponse> {

    /**
     * A file
     */
    private String file;

    public DsmRevisionFileRequest(DsmAuth auth) {
        super(auth);
        this.apiName = "SYNO.SynologyDrive.Revisions";
        this.version = 1;
        this.method = "list";
        this.path = "webapi/query.cgi";
    }

    @Override
    protected TypeReference getClassForMapper() {
        return new TypeReference<Response<DsmRevisionResponse>>() {};
    }

    /**
     * A listed folder path started with a
     * shared folder.
     * @param file the root path
     * @return DsmRevisionFileRequest
     */
	public DsmRevisionFileRequest setFile(String file) {
        this.file = file;
        return this;
    }

   

    @Override
    public Response<DsmRevisionResponse> call() {
        addParameter("path", escape(Optional.ofNullable(this.file).orElseThrow(() -> new DsmListFolderException("the root folder path can not be null"))));

        return super.call();
    }
}
