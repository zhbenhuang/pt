package rsos.framework.easyui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EasyTree implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id ;
	private String text ;
	private String iconCls ;
	private int checked ;
	private String parent_id ;
	private Map<String, Object> attributes = new HashMap<String, Object>();
	private String state ;
	
	
	public EasyTree(String id, String text, String iconCls, int checked,
			String parentId, Map<String, Object> attributes, String state) {
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
		this.checked = checked;
		parent_id = parentId;
		this.attributes = attributes;
		this.state = state;
	}
	public EasyTree() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
		
		
}
