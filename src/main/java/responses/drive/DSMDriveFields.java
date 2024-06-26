package responses.drive;

import lombok.Data;
import lombok.Getter;
import responses.drive.ls.DSMGetInfoResponse;

public class DSMDriveFields {

	@Data
	public static class Item{
		
		private Long file_id;
		
		private Long access_time;
		private Boolean adv_shared;
		
		private Capabalities capabilities;
		
		private Long change_id;
		private Long change_time;
		private String content_snippet;
		private String content_type;
		private Long created_time;
		private Long modified_time;
		private String display_path;
		private String dsm_path;
		private Boolean encrypted;
		private String hash;
		private Owner owner;
		private String parent_id;
		private String path;
		private String permanent_link;
		private Boolean removed;
		private Long revision;
		private Boolean shared;
		private Boolean starred;
		private Long size;
		private Boolean support_remote;
		private Long sync_id;
		private Boolean sync_to_device;
		private String type;
		private String version_id;
		private String name;
		
		
	}
	
	
	public static abstract class TaggetId{
		String type;
		Object id;
		
		public TaggetId(String type, Object id) {
			this.type = type;
			this.id = id;
		}
		
		@Override
		public String toString() {
			
			return type + ":"+id;
		}
	}
	
	public static class ItemId extends TaggetId{

		public ItemId(Long itemId) {
			super("id", itemId);
		}
		
	}
	
	@Getter
	public static class Capabalities{
		private Boolean can_comment;
		private Boolean can_delete;
		private Boolean can_encrypt;
		private Boolean can_organize;
		private Boolean can_preview;
		private Boolean can_read;
		private Boolean can_rename;
		private Boolean can_share;
		private Boolean can_write;
		
	}
	
	@Getter
	public static class Owner{
		private String display_name;
		private String name;
		private String nickname;
		private Long uid;
	}
	
	
	@Getter
	public static class RevisionFile {
		private Integer   created_time;
		private String    editor_display_name;
		private String    editor_nickname;
		private String    editor_uid;
		private String    editors; 
		private String    hash; 
		private String    modified_time;
		private Integer   size;
		private Integer   version_id;

	}
}
