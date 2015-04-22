package rsos.framework.exception;


public class AppException extends BaseException {
	private static final long serialVersionUID = 1L;
    private String[] details;

    public AppException(String code)
    {
        super(code);
    }

    public AppException(String code, String[] details)
    {
        super(code);
        this.details = details;
    }


	public String[] getDetails() {
		return details;
	}

	public void setDetails(String[] details) {
		this.details = details;
	}
}
