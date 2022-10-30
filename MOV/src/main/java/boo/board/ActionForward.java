package boo.board;

public class ActionForward {
	private boolean redirect = false;
	private String path = null;

//property redirect의 is 메서드
	public boolean isRedirect() {
		// 프로퍼티 타입이 boolean일 경우 get 대신 is를 앞에 붙일 수 있습니다.
		return redirect;
	}

	// property redirect의 set 메서드
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	// property path의 get 메서드
	public String getPath() {
		return path;
	}

	// property path의 get 메서드
	public void setPath(String path) {
		this.path = path;
	}
}
