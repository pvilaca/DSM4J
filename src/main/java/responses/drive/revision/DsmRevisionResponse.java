package responses.drive.revision;

import java.util.List;

import responses.drive.DSMDriveFields;

public class DsmRevisionResponse {
    private Integer total;
    private List<DSMDriveFields.RevisionFile> items;

    public Integer getTotal() {
        return total;
    }


    public List<DSMDriveFields.RevisionFile> getItems() {
        return items;
    }
}
