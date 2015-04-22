package rsos.framework.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import rsos.framework.utils.StringUtil;


public abstract class EasyBaseDaoImpl<T> extends HibernateDaoSupport implements EasyBaseDao<T> {	
	
	public T selectBy(Serializable id) {
		T t = (T) getHibernateTemplate().get(getType(), id);
		return t;
	}
	
	public List<T> selectAll() {
		return getHibernateTemplate().loadAll(getType());
	}

	public void insert(T t) {
		getHibernateTemplate().saveOrUpdate(t);
	}

	public void deleteBy(T t) {
		getHibernateTemplate().delete(t);
	}

	public void deleteBy(Collection<T> ts) {
		getHibernateTemplate().deleteAll(ts);
	}

	protected abstract Class<T> getType();

	public void update(T t) {
		getHibernateTemplate().update(t);
	}
	
	public void evict(T t) {
		getHibernateTemplate().evict(t);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public T merge(T t) {
		return (T) getHibernateTemplate().merge(t);
	}
	
	public void appendOrderSql(StringBuilder sb, String orderColumn, String order){
		if (StringUtils.isNotEmpty(orderColumn)) {
			sb.append(" order by ");
			sb.append(orderColumn);			
		}
		if (StringUtils.isNotEmpty(orderColumn) && StringUtils.isNotEmpty(order)) {
			sb.append(" ");
			sb.append(order);
		}
	}
	
	public void appendOrderSql(StringBuilder sb,String prefix, String orderColumn, String order){
		if (StringUtils.isNotEmpty(orderColumn)) {
			sb.append(" order by ");
			if(prefix!=null){
				sb.append(prefix);
				sb.append(".");
			}
			sb.append(orderColumn);			
		}
		if (StringUtils.isNotEmpty(orderColumn) && StringUtils.isNotEmpty(order)) {
			sb.append(" ");
			sb.append(order);
		}
	}
	
	public Query createPagedQuery(Session session, StringBuilder sb, String sortColumn,
			int pageIndex,int pageSize){
		
		if (StringUtils.isNotEmpty(sortColumn)) {
			sb.append(" order by ");
			sb.append(sortColumn);
		}
		int startIndex = (pageIndex - 1) * pageSize;
		String sql = sb.toString();
		Query query = session.createQuery(sql);
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		return query;
	}
	
	public void setParameters(Query query, List<?> paramVals){
		for (int i = 0; i < paramVals.size(); i++) {
			query.setParameter(i, paramVals.get(i));
		}
	}
	
	public void appendLikeOrEquals(StringBuilder hql, List<Object> params, String field, Object val) {
		appendLikeOrEquals(hql, params, field, val, false);
	}

	private void appendLikeOrEquals(StringBuilder hql, List<Object> params, String field, Object val, boolean isNull) {
		if (val == null ) {
			if(isNull){
				hql.append(" and ").append(field).append(" is null ");
			}
			return;
		}		
		
		String compareSymbol = " = ";
		if (val instanceof String) {
			String strVal = val.toString();
			if (StringUtils.isEmpty(strVal)) {
				return;
			}
			if (StringUtil.isFuzzyQuery(strVal)) {
				compareSymbol = " like ";
			}
		}

		hql.append(" and ").append(field).append(compareSymbol).append(" ? ");
		params.add(val);
	}
	
	public void appendInClause(StringBuilder hql, String field, String val) {
		if (val == null || StringUtils.isEmpty(val) ) {			
			return;
		}
		
		String compareSymbol = " in ";		
		hql.append(" and ").append(field).append(compareSymbol).append("( ").append(val).append(" )");
	}
	
	private void appendOtherClause(StringBuilder hql, List<Object> params, String field, Object val,String compareSymbol) {
		if (val == null) {
			return;
		}
		if (val instanceof String) {
			String strVal = val.toString();
			if (StringUtils.isEmpty(strVal)) {
				return;
			}
		}
		hql.append(" and ").append(field).append(compareSymbol).append(" ? ");
		params.add(val);
	}

	public void appendGreaterEquals(StringBuilder hql, List<Object> params, String field, Object val) {		
		String compareSymbol = " >= ";
		appendOtherClause(hql, params, field, val, compareSymbol);
	}
	
	public void appendLowerEquals(StringBuilder hql, List<Object> params, String field, Object val) {		
		String compareSymbol = " <= ";
		appendOtherClause(hql, params, field, val, compareSymbol);
	}
	
	public void addNotEquals(StringBuilder hql, List<Object> params, String field, Object val) {		
		String compareSymbol = " <> ";
		appendOtherClause(hql, params, field, val, compareSymbol);
	}
	
	public void addEquals(StringBuilder hql, List<Object> params, String field, Object val) {		
		String compareSymbol = " = ";
		appendOtherClause(hql, params, field, val, compareSymbol);
	}
	
	/**
	 * append where if need. hasWhere is parameter,and return hasWhere.
	 */
	public Boolean appendLikeOrEquals(StringBuilder hql, List<Object> params, String field, Object val,Boolean hasWhere) {		
		return appendLikeOrEquals(hql, params, field, val, false,hasWhere);
	}

	private Boolean appendLikeOrEquals(StringBuilder hql, List<Object> params, String field, Object val, boolean isNull,Boolean hasWhere) {
		StringBuilder tSql = new StringBuilder("");
		if (val == null ) {
			if(isNull){
				tSql.append(" ").append(field).append(" is null ");
			}			
		}else{		
			String compareSymbol = " = ";
			if (val instanceof String) {
				String strVal = val.toString();
				if (!StringUtils.isEmpty(strVal)) {
					if (StringUtil.isFuzzyQuery(strVal)) {
						compareSymbol = " like ";
					}
					tSql.append(" ").append(field).append(compareSymbol).append(" ? ");
					params.add(val);
				}
			}			
		}
		if(!StringUtil.isEmpty(tSql.toString())){
			if(Boolean.FALSE.equals(hasWhere)){
				hql.append(" where ").append(tSql);
				return Boolean.TRUE;
			}else{
				hql.append(" and ").append(tSql);
				return hasWhere;
			}
		}else{
			return hasWhere;
		}
	}
	
	public Boolean appendInClause(StringBuilder hql, String field, String val,Boolean hasWhere) {
		StringBuilder tSql = new StringBuilder("");
		
		if (!(val == null || StringUtils.isEmpty(val) )) {		
			String compareSymbol = " in ";
			tSql.append(" ").append(field).append(compareSymbol).append("( ").append(val).append(" )");
		}
		
		if(!StringUtil.isEmpty(tSql.toString())){
			if(Boolean.FALSE.equals(hasWhere)){
				hql.append(" where ").append(tSql);
				return Boolean.TRUE;
			}else{
				hql.append(" and ").append(tSql);
				return hasWhere;
			}
		}else{
			return hasWhere;
		}
	}
	
	/**
	 * 增加业务条线的权限控制.
	 * @param hql
	 * @param field
	 * @param business
	 * @param hasWhere
	 * @return
	 */
	public Boolean appendBusinessPermission(StringBuilder hql, String field, Integer business,Boolean hasWhere) {
		StringBuilder tSql = new StringBuilder("");
		
		if (!(field == null || StringUtils.isEmpty(field) )) {
			if(business>0){
				String compareSymbol = " = ";
				tSql.append(" ").append(field).append(compareSymbol).append(business);
			}else{
				String compareSymbol = " <> ";
				tSql.append(" ").append(field).append(compareSymbol).append(business);
			}
			
		}
		
		if(!StringUtil.isEmpty(tSql.toString())){
			if(Boolean.FALSE.equals(hasWhere)){
				hql.append(" where ").append(tSql);
				return Boolean.TRUE;
			}else{
				hql.append(" and ").append(tSql);
				return hasWhere;
			}
		}else{
			return hasWhere;
		}
	}
	
	public void appendBusinessPermission(StringBuilder hql, String field, Integer business) {
		if (!(field == null || StringUtils.isEmpty(field) )) {
			if(business>0){
				String compareSymbol = " = ";
				hql.append(" ").append(field).append(compareSymbol).append(business);
			}else{
				String compareSymbol = " <> ";
				hql.append(" ").append(field).append(compareSymbol).append(business);
			}
			
		}
	}
	
	private Boolean appendOtherClause(StringBuilder hql, List<Object> params, String field, Object val,String compareSymbol,Boolean hasWhere) {
		StringBuilder tSql = new StringBuilder("");
		if (val != null) {
			if (val instanceof String) {
				String strVal = val.toString();
				if (!StringUtils.isEmpty(strVal)) {
					tSql.append(" ").append(field).append(compareSymbol).append(" ? ");
					params.add(val);
				}
			}
		}
		if(!StringUtil.isEmpty(tSql.toString())){
			if(Boolean.FALSE.equals(hasWhere)){
				hql.append(" where ").append(tSql);
				return Boolean.TRUE;
			}else{
				hql.append(" and ").append(tSql);
				return hasWhere;
			}
		}else{
			return hasWhere;
		}	
	}

	public Boolean appendGreaterEquals(StringBuilder hql, List<Object> params, String field, Object val,Boolean hasWhere) {		
		String compareSymbol = " >= ";
		return appendOtherClause(hql, params, field, val, compareSymbol,hasWhere);
	}
	
	public Boolean appendLowerEquals(StringBuilder hql, List<Object> params, String field, Object val,Boolean hasWhere) {		
		String compareSymbol = " <= ";
		return appendOtherClause(hql, params, field, val, compareSymbol,hasWhere);
	}
	
	public Boolean addNotEquals(StringBuilder hql, List<Object> params, String field, Object val,Boolean hasWhere) {		
		String compareSymbol = " <> ";
		return appendOtherClause(hql, params, field, val, compareSymbol,hasWhere);
	}
	
	public Boolean addEquals(StringBuilder hql, List<Object> params, String field, Object val,Boolean hasWhere) {		
		String compareSymbol = " = ";
		return appendOtherClause(hql, params, field, val, compareSymbol,hasWhere);
	}
	
	private Boolean appendHavingSumClause(StringBuilder hql, String field, String val,String compareSymbol,Boolean hasHaving) {
		StringBuilder tSql = new StringBuilder("");
		if (val != null) {
			if (!StringUtils.isEmpty(val)) {
				tSql.append(" sum(").append(field).append(")").append(compareSymbol).append(val);
			}
		}
		if(!StringUtil.isEmpty(tSql.toString())){
			if(Boolean.FALSE.equals(hasHaving)){
				hql.append(" having ").append(tSql);
				return Boolean.TRUE;
			}else{
				hql.append(" and ").append(tSql);
				return hasHaving;
			}
		}else{
			return hasHaving;
		}	
	}
	
	public Boolean appendGreaterEqualsHaving(StringBuilder hql, String field, String val,Boolean hasHaving) {		
		String compareSymbol = " >= ";
		return appendHavingSumClause(hql, field, val, compareSymbol,hasHaving);
	}
	
	public Boolean appendLowerEqualsHaving(StringBuilder hql, String field, String val,Boolean hasHaving) {		
		String compareSymbol = " <= ";
		return appendHavingSumClause(hql, field, val, compareSymbol,hasHaving);
	}
}
