package requests.drive.ls;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.Setter;
import requests.DsmAbstractRequest;
import requests.DsmAuth;
import requests.drive.DSMDriveFieldsRequest;
import requests.drive.DSMDriveFieldsRequest.Sort;
import requests.filestation.DsmRequestParameters;
import responses.Response;
import responses.drive.DSMDriveFields.ItemId;
import responses.drive.ls.DSMDriveListResponse;

@Setter
public class DSMDriveFilesSearchRequest extends DsmAbstractRequest<DSMDriveListResponse>{

	/**
	 * Optional. Specify how many files
	 * are skipped before beginning to
	 * return listed files.
	 */
	private Integer offset;
	/**
	 * Optional. Number of files
	 * requested. 0 indicates to list all
	 * files with a given folder
	 */
	private Integer limit;
	/**
	 * Optional. Specify which file
	 * information to sort on.
	 *
	 *Options include:
	 * name: file name
	 * size: file size
	 * user: file owner
	 * group: file group
	 * mtime: last modified time
	 * atime: last access time
	 * ctime: last change time
	 * crtime: create time
	 * posix: POSIX permission
	 * type: file extension
	 */
	private List<DSMDriveFieldsRequest.Sort> sorts = new LinkedList<>();
	/**
	 * Optional. Specify to sort ascending
	 * or to sort descending
	 *
	 * Options include:
	 * asc: sort ascending
	 * desc: sort descending
	 */
	private DsmRequestParameters.SortDirection sortDirection;
	/**
	 * Optional. Given glob pattern(s) to
	 * find files whose names and
	 * extensions match a caseinsensitive glob pattern.
	 *
	 * Note:
	 * 1. If the pattern doesn’t contain
	 * any glob syntax (? and *), * of
	 * glob syntax will be added at
	 * begin and end of the string
	 * automatically for partially
	 * matching the pattern.
	 *
	 * 2. You can use commas to separate
	 * multiple glob patterns.
	 */
	private List<String> patterns = new LinkedList<>();
	/**
	 * Optional. file: only enumerate
	 * regular files; dir: only enumerate
	 * folders; all enumerate regular
	 * files and folders
	 */
	private DsmRequestParameters.FileType file_type;
	/**
	 * Optional. Folder path started with a
	 * shared folder. Return all files and
	 * sub-folders within folder_path
	 * path until goto_path path
	 * recursively.
	 */
	private String goToPath;
	/**
	 * Optional. Additional requested file
	 * information, separated by a
	 * comma. When an additional
	 * option is requested, responded
	 * objects will be provided in the
	 * specified additional option.
	 * Options include:
	 *
	 * real_path: return a real path
	 * in volume
	 *
	 * size: return file byte size
	 *
	 * owner: return information
	 * about file owner including
	 * user name, group name, UID
	 * and GID
	 *
	 * time: return information
	 * about time including last
	 * access time, last modified
	 * time, last change time and
	 * create time
	 *
	 * perm: return information
	 * about file permission
	 *
	 * mount_point_type: return a
	 * type of a virtual file system of
	 * a mount point
	 *
	 * type: return a file extension
	 */
	private List<DsmRequestParameters.Additional> additionals = new LinkedList<>();

	
	private Long start_date;
	private Long end_date;
	private Time time;
	private Long min_size;
	private Long max_size;
	private String keyword;
	private DSMDriveFieldsRequest.Location location;
	private ItemId custom_location;

	public DSMDriveFilesSearchRequest(DsmAuth auth) {
		super(auth);
		
		this.apiName = "SYNO.SynologyDrive.Files";
        this.version = 1;
        this.path = "webapi/entry.cgi";
		this.method = "search";
	}


	@Override
	public Response<DSMDriveListResponse> call() {
		Optional.ofNullable(this.offset).ifPresent(of -> addParameter("offset", String.valueOf(of)));
		Optional.ofNullable(this.limit).ifPresent(lm -> addParameter("limit", String.valueOf(lm)));
		Optional.ofNullable(this.sortDirection).ifPresent(direction -> addParameter("sort_direction", direction.name().toLowerCase()));
		Optional.ofNullable(this.goToPath).ifPresent(gtp -> addParameter("goto_path", gtp));

		Optional.ofNullable(this.keyword).ifPresent(keyword -> addParameter("keyword", this.keyword));
		
		Optional.ofNullable(this.start_date).ifPresent(start_date -> addParameter("start_date", String.valueOf(this.start_date)));
		Optional.ofNullable(this.end_date).ifPresent(end_date -> addParameter("end_date", String.valueOf(this.end_date)));
		Optional.ofNullable(this.time).ifPresent(time -> addParameter("time", time.name().toLowerCase()));
		Optional.ofNullable(this.file_type).ifPresent(time -> addParameter("file_type", file_type.name().toLowerCase()));
		
		Optional.ofNullable(this.min_size).ifPresent(time -> addParameter("min_size", String.valueOf(this.min_size)));
		Optional.ofNullable(this.max_size).ifPresent(time -> addParameter("max_size", String.valueOf(this.max_size)));
		Optional.ofNullable(this.location).ifPresent(time -> addParameter("location", location.name().toLowerCase()));
		Optional.ofNullable(this.custom_location).ifPresent(time -> addParameter("custom_location", custom_location.toString()));
		
		if(!sorts.isEmpty()) {
			addParameter("sort_by", sorts.stream().map(Sort::name).collect(Collectors.joining(",")));
		}

		if(!patterns.isEmpty()) {
			addParameter("pattern", String.join(",", patterns));
		}

		if(!additionals.isEmpty()) {
			addParameter("additional", additionals.stream().map(DsmRequestParameters.Additional::name).collect(Collectors.joining(",")));
		}

		return super.call();
	}
	
	public static enum Time{
		created_time, modified_time
	}

	@Override
	protected TypeReference getClassForMapper() {
		return new TypeReference<Response<DSMDriveListResponse>>() {};
	}
}
