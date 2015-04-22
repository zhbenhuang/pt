package rsos.framework.easyui;

import java.io.Serializable;

public class EasyToolBar implements Serializable {
	private static final long serialVersionUID = 1L;
	private String text;
	private String iconCls;
	private String handler;
	public EasyToolBar() {
	}
	public EasyToolBar(String text, String iconCls, String handler) {
		this.text = text;
		this.iconCls = iconCls;
		this.handler = handler;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}

}
