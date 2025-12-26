package com.lp.brevets.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.utils.HibernateUtils;
import com.lp.brevets.models.Brevet;

public class DaoBrevet implements IDAO<Brevet> {

	@Override
	public List<Brevet> getAll() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		List<Brevet> brvs = s
				.createQuery("select b from Brevet b  join fetch b.invention  join fetch b.inventeur", Brevet.class)
				.getResultList();
		t.commit();
		s.close();
		return brvs;
	}

	public long count() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		long count = s.createQuery("select count(b) from Brevet b", Long.class).getSingleResult();
		t.commit();
		s.close();
		return count;
	}

	@Override
	public Brevet getOne(int id) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Brevet b = s.get(Brevet.class, id);
		t.commit();
		s.close();
		return b;
	}

	@Override
	public boolean save(Brevet obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.save(obj);
		t.commit();
		s.close();
		return Optional.ofNullable(getOne(obj.getNum())).isPresent();
	}

	@Override
	public boolean update(Brevet obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.update(obj);
		t.commit();
		s.close();
		return true;
	}

	@Override
	public boolean delete(Brevet obj) {
		int id = obj.getNum();
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Brevet brevet = s.get(Brevet.class, id);
		s.delete(brevet);
		t.commit();
		s.close();
		return !Optional.ofNullable(getOne(id)).isPresent();
	}

}

