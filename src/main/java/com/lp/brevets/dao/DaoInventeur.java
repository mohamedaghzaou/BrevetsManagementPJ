package com.lp.brevets.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.utils.HibernateUtils;
import com.lp.brevets.models.Inventeur;

public class DaoInventeur implements IDAO<Inventeur> {

	@Override
	public List<Inventeur> getAll() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		List<Inventeur> invts = s.createQuery("from Inventeur i join fetch i.entreprise ", Inventeur.class)
				.getResultList();
		t.commit();
		s.close();
		return invts;
	}

	public long count() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		long count = s.createQuery("select count(i) from Inventeur i", Long.class).getSingleResult();
		t.commit();
		s.close();
		return count;
	}

	@Override
	public Inventeur getOne(int id) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Inventeur d = s.get(Inventeur.class, id);
		t.commit();
		s.close();
		return d;
	}

	@Override
	public boolean save(Inventeur obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.save(obj);
		t.commit();
		s.close();
		return Optional.ofNullable(getOne(obj.getNum())).isPresent();

	}

	@Override
	public boolean update(Inventeur obj) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		s.update(obj);
		t.commit();
		s.close();
		return true;
	}

	@Override
	public boolean delete(Inventeur obj) {

		int id = obj.getNum();
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Inventeur inveteurForDeleltion = s.get(Inventeur.class, id);
		s.delete(inveteurForDeleltion);
		t.commit();
		s.close();
		return !Optional.ofNullable(getOne(id)).isPresent();
	}

}

