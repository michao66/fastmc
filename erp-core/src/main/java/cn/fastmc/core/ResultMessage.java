package cn.fastmc.core;

public class ResultMessage {
	
	public ResultMessage(int status,String message){
		this.status = status;
		this.message = message;
	}
	private  int status;
	
	private String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public static ResultMessage defaultSuccessMessage(){
		return new ResultMessage(1,"操作成功");
	}
	public static ResultMessage defaultErrorMessage(){
		return new ResultMessage(-1,"操作失败");
	}

}
