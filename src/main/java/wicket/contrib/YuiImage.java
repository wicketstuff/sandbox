package wicket.contrib;

import java.io.Serializable;

public class YuiImage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String desc;
	
	private int sortTop;
	private int sortLeft;
	
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

	public int getSortLeft() {
		return sortLeft;
	}

	public void setSortLeft(int sortLeft) {
		this.sortLeft = sortLeft;
	}

	public int getSortTop() {
		return sortTop;
	}

	public void setSortTop(int sortTop) {
		this.sortTop = sortTop;
	}

	
	
}
