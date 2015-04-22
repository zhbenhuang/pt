package rsos.framework.exception;


public class NoRollbackAppException extends BaseException {
	private static final long serialVersionUID = 1L;
    private String[] details=null;

    public NoRollbackAppException(String code)
    {
        super(code);
    }

    public NoRollbackAppException(String code, String[] details)
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
