package exception;

public class LibException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private String msg;
	private int state;
//	public String getMsg() {
//		return msg;
//	}
	public int getState() {
		return state;
	}
//	public LibException(int state,String msg) {
//		super();
//		this.state = state;
//		this.msg = msg;
//	}
	public LibException(int state,String message) {
		super(message);
		this.state = state;
	}
	

	
}
