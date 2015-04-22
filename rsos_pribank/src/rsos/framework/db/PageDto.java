package rsos.framework.db;

import java.io.Serializable;

public class PageDto implements Serializable {
    private static final long serialVersionUID = 5168938127435882976L;    
    public static final String ASC_ORDER = "ASC";    
    public static final String DESC_ORDER = "DESC";
    public static final int PAGE_SIZE = 20;
    private String orderColumn;    
    private String order = ASC_ORDER;    
    private int page;
    private int pageSize;

    public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
        return order;
    }

    /**
     * Set order type, only accept 'ASC' and 'DESC' string
     * @param order
     */
    public void setOrder(String order) {
        if (order == null) {
            this.order = ASC_ORDER;
        } else {
            order = order.trim().toUpperCase();
            if (order.equals(ASC_ORDER) || order.equals(DESC_ORDER))
                this.order = order;
        }
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }
    
}
