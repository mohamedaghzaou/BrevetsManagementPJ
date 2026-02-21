package com.lp.brevets.metier;

import java.util.List;

public interface IMetier<T> {

	List<T> getAll();

	List<T> getPage(int page, int pageSize);

	long count();

	T getOne(int id);

	boolean save(T obj);

	boolean update(T obj);

	boolean delete(T obj);

}

