package rsos.framework.easyui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class EasyTreeMenuList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<EasyTree> treeList=new ArrayList<EasyTree>();
	
	public List<EasyTree> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<EasyTree> treeList) {
		this.treeList = treeList;
	}

	public void add(EasyTree t){
		treeList.add(t);
	}
	public String toJson(){
		return JSONArray.fromObject(treeList).toString();
	}
}
