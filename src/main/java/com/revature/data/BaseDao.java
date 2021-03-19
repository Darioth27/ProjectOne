package com.revature.data;

import java.util.List;

public interface BaseDao<T> {
	
	public boolean insertProcedure(T t);
	public List<T> selectAll();

}
