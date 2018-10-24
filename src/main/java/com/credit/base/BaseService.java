package com.credit.base;


import java.util.List;

/**
 * Common Service
 * 
 *
 * @param <T>
 */
public interface BaseService<T> {

	int save(T t);

	int save(List<T> list);

	int update(T t);

	int delete(long id);

	int delete(List<String> ids);

	T getById(Long id);
	
	List<T> listByObj(T t);

	PageBean listPage(PageParam pageParam);

	int updateNull(T t);

}
