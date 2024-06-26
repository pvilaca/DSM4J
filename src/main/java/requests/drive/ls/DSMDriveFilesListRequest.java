package requests.drive.ls;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Setter;
import requests.DsmAuth;
import requests.filestation.DsmRequestParameters;
import responses.Response;
import responses.drive.ls.DSMDriveListResponse;

@Setter
public class DSMDriveFilesListRequest extends DSMDriveFilesRequest{

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
	private List<DsmRequestParameters.Sort> sorts = new LinkedList<>();
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
	private DsmRequestParameters.FileType fileType;
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


	public DSMDriveFilesListRequest(DsmAuth auth) {
		super(auth);
		this.method = "list";
	}


	@Override
	public Response<DSMDriveListResponse> call() {
		Optional.ofNullable(this.offset).ifPresent(of -> addParameter("offset", String.valueOf(of)));
		Optional.ofNullable(this.limit).ifPresent(lm -> addParameter("limit", String.valueOf(lm)));
		Optional.ofNullable(this.sortDirection).ifPresent(direction -> addParameter("sort_direction", direction.name()));
		Optional.ofNullable(this.fileType).ifPresent(type -> addParameter("filetype", type.name()));
		Optional.ofNullable(this.goToPath).ifPresent(gtp -> addParameter("goto_path", gtp));

		if(!sorts.isEmpty()) {
			addParameter("sort_by", sorts.stream().map(DsmRequestParameters.Sort::name).collect(Collectors.joining(",")));
		}

		if(!patterns.isEmpty()) {
			addParameter("pattern", String.join(",", patterns));
		}

		if(!additionals.isEmpty()) {
			addParameter("additional", additionals.stream().map(DsmRequestParameters.Additional::name).collect(Collectors.joining(",")));
		}

		return super.call();
	}
}
