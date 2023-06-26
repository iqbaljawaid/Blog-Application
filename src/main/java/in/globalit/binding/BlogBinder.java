package in.globalit.binding;

import lombok.Data;

@Data
public class BlogBinder {
	
	private Integer blogId;
	private String title;
	private String shortDesc;
	
	private String longDesc;

}
