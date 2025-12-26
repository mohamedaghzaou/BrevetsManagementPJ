package com.lp.brevets.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.utils.HibernateUtils;
import com.lp.brevets.models.Domaine;

public class DaoDomaine implements IDAO<Domaine> {

	@Override
	public List<Domaine> getAll() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		List<Domaine> doms = s.createQuery("from Domaine", Domaine.class).getResultList();
		t.commit();
		s.close();
		return doms;
	}

	@Override
	public Domaine getOne(int id) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Domaine d = s.get(Domaine.class, id);
		t.commit();
		s.close();
		return d;
	}

	@Override
	public boolean save(Domaine obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.save(obj);
		t.commit();
		s.close();
		return Optional.ofNullable(getOne(obj.getNum())).isPresent();
	}

	@Override
	public boolean update(Domaine obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.update(obj);
		t.commit();
		s.close();
		return true;
	}

	@Override
	public boolean delete(Domaine obj) {
		int id = obj.getNum();
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Domaine domaine = s.get(Domaine.class, id);
		s.delete(domaine);
		t.commit();
		s.close();
		return !Optional.ofNullable(getOne(id)).isPresent();
	}

	public long count() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		long count = s.createQuery("select count(d) from Domaine d", Long.class).getSingleResult();
		t.commit();
		s.close();
		return count;
	}

}

