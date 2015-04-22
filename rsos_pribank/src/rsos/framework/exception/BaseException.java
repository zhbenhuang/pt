package rsos.framework.exception;


public abstract class BaseException extends Exception
{
	private static final long serialVersionUID = 1L;
	protected String id;

    public BaseException(String i)
    {
        id = i;
    }

    public String getId()
    {
        return id;
    }
}
