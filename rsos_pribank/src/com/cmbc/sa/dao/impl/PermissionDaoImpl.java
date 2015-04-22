package com.cmbc.sa.dao.impl;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.db.EasyBaseDaoImpl;
import rsos.framework.easyui.EasyTree;
import rsos.framework.easyui.EasyTreeMenuList;
import rsos.framework.utils.StringUtil;

import com.cmbc.sa.bean.Permission;
import com.cmbc.sa.dao.PermissionDao;

public class PermissionDaoImpl extends EasyBaseDaoImpl<Permission> implements PermissionDao {	
	protected Class<Permission> getType() {
		return Permission.class;
	}

	@SuppressWarnings("unchecked")
	public EasyTreeMenuList selectNodes(final String userId,final String parentNode) {
		final EasyTreeMenuList result = new EasyTreeMenuList();
		
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {e.*},case isnull(f.permissionId,'0') when '0' then 0 else '1' end status")
					.append(" from users a join usersRole b on a.userid=b.userid ")
					.append(" join role c on b.roleid=c.roleid ")
					.append(" join rolePermission d on c.roleid=d.roleid ")
					.append(" join permission e on d.permissionid=e.permissionid ")
					.append(" left join permission f on e.permissionid =f.parentid ")
					.append(" where a.userid=:userId and e.parentId=:parentNode");
            					
                Query queryResult = session.createSQLQuery(sql.toString())
                  						.addEntity("e", Permission.class)
                  						.addScalar("status", Hibernate.STRING);
                queryResult.setString("userId", userId);
                if(StringUtil.isEmpty(parentNode)){
                	queryResult.setInteger("parentNode", GlobalConstants.ROOT_NODE);
				}else{
					queryResult.setInteger("parentNode", new Integer(parentNode));
				}
                List<Object[]> list = queryResult.list();                
				if (list != null) {
					for(Object[] obj : list){
						Permission p = (Permission)obj[0];
						EasyTree tree = new EasyTree();
						tree.setId(p.getPermissionId());
						tree.setText(p.getPermissionName());
						tree.setChecked(p.getChecked());
						tree.setIconCls(p.getImageUrl());
						tree.setParent_id(p.getParentId());
						String status = (String)obj[1];
						if("1".equals(status)){
							tree.setState(GlobalConstants.NODE_CLOSED);
						} else {
							tree.setState(GlobalConstants.NODE_OPEN);
						}
						Map<String, Object>  map = new HashMap<String, Object>();
						map.put("url", p.getUrl());
						tree.setAttributes(map);
						result.add(tree);
					}
				}
            	
				return null;
			}
		});
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Permission> selectPermissions(final String roleId,final Integer business) {
		List<Permission> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from rolePermission a join permission b on a.permissionId=b.permissionId ")
					.append(" where a.business=:business and a.roleId=:roleId ");
			
				Query queryResult = session.createSQLQuery( sql.toString() )
									.addEntity("b", Permission.class);
				queryResult.setString("roleId",roleId );
				queryResult.setInteger("business", business);
				return queryResult.list();
			}
		});
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public List<Permission> selectAllPermissions() {
		List<Permission> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from permission b ");
			
				Query queryResult = session.createSQLQuery( sql.toString() )
									.addEntity("b", Permission.class);
				return queryResult.list();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> selectPermissionsByBusiness(final int business) {
		List<Permission> result = getHibernateTemplate().execute(new HibernateCallback() {			
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				
				StringBuilder sql = new StringBuilder();
				sql.append("select distinct {b.*} from permission b where b.type=0 or b.business=:business");
			
				Query queryResult = session.createSQLQuery( sql.toString() )
									.addEntity("b", Permission.class);
				queryResult.setInteger("business", business);
				return queryResult.list();
			}
		});
		return result;
	}
	
}
