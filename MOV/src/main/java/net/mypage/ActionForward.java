package net.mypage;

public class ActionForward {
	private boolean redirect = false;
	private String path = null;
	
	//property redirect의 is메소드 
	public boolean isRedirect() {
		//프로퍼티 타입이 boolean일 경우 get 대신 is를 앞에 붙일 수 있습니다.
		return redirect;
	}
	//property redirect의 set메소드 
	public void setRedirect(boolean b) {
		redirect = b;
	}
	//property getPath의 get메소드 
	public String getPath() {
		return path;
	}
	//property getPath의 set메소드 
	public void setPath(String string) {
		path = string;
	}
}
