package rsos.framework.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface EasyBaseDao<T>  {
	
	T selectBy(Serializable id);
		
	List<T> selectAll();
	
	void insert(T t);
	
	void deleteBy(T t);
	
	void deleteBy(Collection<T> ts);
	
	void update(T t);
	
	void flush();
	
	void evict(T t);
	
	T merge(T t);
}
