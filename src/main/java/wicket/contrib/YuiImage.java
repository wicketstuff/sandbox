package wicket.contrib;

import java.io.Serializable;

public class YuiImage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String desc;
	
	public YuiImage(String fileName){
		this.fileName= fileName;
	}
	
	public YuiImage(String fileName, String desc){
		this.fileName= fileName;
		this.desc= desc;
	}

	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName= fileName;
	}
	
	public String getDesc(){
		return desc;
	}
	
	public void setDesc(String desc){
		this.desc= desc;
	}
}
