package cn.fastmc.viewconfig.components;




public class TableHead implements Comparable<TableHead>{
	private String field;
	
	private String title;
	private String listAlign = "left";
	
	private boolean hidden;
	private String body;
	
	private String format;
	private Integer order ;
	private String type;
	

	private int  width = 100;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public TableHead(){
		listAlign = "left";
		type = "string";
		hidden = false;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public int compareTo(TableHead o) {
	     return order.compareTo(o.getOrder());
	}
	public String getListAlign() {
		return listAlign;
	}

	public void setListAlign(String listAlign) {
		this.listAlign = listAlign;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
