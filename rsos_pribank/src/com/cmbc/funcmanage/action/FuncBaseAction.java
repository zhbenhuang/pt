package com.cmbc.funcmanage.action;

import com.cmbc.funcmanage.service.ContractService;
import com.cmbc.funcmanage.service.ProductRedemptionIntervalService;
import com.cmbc.funcmanage.service.ProductService;
import com.cmbc.funcmanage.service.TagService;

import rsos.framework.struts2.BaseAction;

public class FuncBaseAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ProductService productService;
	protected ContractService contractService;
	protected TagService tagService;
	protected ProductRedemptionIntervalService productRedemptionIntervalService;
	
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
	public void setProductRedemptionIntervalService(
			ProductRedemptionIntervalService productRedemptionIntervalService) {
		this.productRedemptionIntervalService = productRedemptionIntervalService;
	}

}
