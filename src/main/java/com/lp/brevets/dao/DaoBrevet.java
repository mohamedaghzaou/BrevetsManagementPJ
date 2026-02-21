package com.lp.brevets.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.utils.HibernateUtils;
import com.lp.brevets.models.Brevet;

public class DaoBrevet implements IDAO<Brevet> {

	@Override
	public List<Brevet> getAll() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		List<Brevet> brvs = s.createQuery(
				"select distinct b from Brevet b "
						+ "join fetch b.invention i "
						+ "left join fetch i.domaine d "
						+ "join fetch b.inventeur inv "
						+ "left join fetch inv.entreprise e "
						+ "order by b.num desc",
				Brevet.class)
				.getResultList();
		t.commit();
		s.close();
		return brvs;
	}

	@Override
	public List<Brevet> getPage(int page, int pageSize) {
		int safePage = Math.max(page, 1);
		int safePageSize = Math.max(pageSize, 1);
		int firstResult = (safePage - 1) * safePageSize;

		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		List<Brevet> brevets = s.createQuery(
				"select distinct b from Brevet b "
						+ "join fetch b.invention i "
						+ "left join fetch i.domaine d "
						+ "join fetch b.inventeur inv "
						+ "left join fetch inv.entreprise e "
						+ "order by b.num desc",
				Brevet.class)
				.setFirstResult(firstResult)
				.setMaxResults(safePageSize)
				.getResultList();
		t.commit();
		s.close();
		return brevets;
	}

	@Override
	public long count() {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		long count = s.createQuery("select count(b) from Brevet b", Long.class).getSingleResult();
		t.commit();
		s.close();
		return count;
	}

	public List<Brevet> search(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			String sortBy, String sortDirection) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();

		StringBuilder hql = new StringBuilder();
		hql.append("select distinct b from Brevet b ");
		appendSearchJoins(hql, true);
		Map<String, Object> parameters = new HashMap<>();
		appendSearchWhereClause(hql, parameters, keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo);
		appendOrderClause(hql, sortBy, sortDirection);

		Query<Brevet> query = s.createQuery(hql.toString(), Brevet.class);
		bindParameters(query, parameters);

		List<Brevet> brevets = query.getResultList();
		t.commit();
		s.close();
		return brevets;
	}

	public List<Brevet> searchPage(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo,
			String sortBy, String sortDirection, int page, int pageSize) {
		int safePage = Math.max(page, 1);
		int safePageSize = Math.max(pageSize, 1);
		int firstResult = (safePage - 1) * safePageSize;

		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();

		StringBuilder hql = new StringBuilder();
		hql.append("select distinct b from Brevet b ");
		appendSearchJoins(hql, true);

		Map<String, Object> parameters = new HashMap<>();
		appendSearchWhereClause(hql, parameters, keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo);
		appendOrderClause(hql, sortBy, sortDirection);

		Query<Brevet> query = s.createQuery(hql.toString(), Brevet.class);
		bindParameters(query, parameters);
		query.setFirstResult(firstResult);
		query.setMaxResults(safePageSize);

		List<Brevet> brevets = query.getResultList();
		t.commit();
		s.close();
		return brevets;
	}

	public long countSearch(String keyword, Integer inventeurId, Integer entrepriseId, Integer domaineId,
			LocalDate dateDepotFrom, LocalDate dateDepotTo, LocalDate dateValidationFrom, LocalDate dateValidationTo) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();

		StringBuilder hql = new StringBuilder();
		hql.append("select count(distinct b.num) from Brevet b ");
		appendSearchJoins(hql, false);

		Map<String, Object> parameters = new HashMap<>();
		appendSearchWhereClause(hql, parameters, keyword, inventeurId, entrepriseId, domaineId, dateDepotFrom,
				dateDepotTo, dateValidationFrom, dateValidationTo);

		Query<Long> query = s.createQuery(hql.toString(), Long.class);
		bindParameters(query, parameters);

		long count = query.getSingleResult();
		t.commit();
		s.close();
		return count;
	}

	private void appendSearchJoins(StringBuilder hql, boolean fetchAssociations) {
		if (fetchAssociations) {
			hql.append("join fetch b.invention i ");
			hql.append("left join fetch i.domaine d ");
			hql.append("join fetch b.inventeur inv ");
			hql.append("left join fetch inv.entreprise e ");
		} else {
			hql.append("join b.invention i ");
			hql.append("left join i.domaine d ");
			hql.append("join b.inventeur inv ");
			hql.append("left join inv.entreprise e ");
		}
	}

	private void appendSearchWhereClause(StringBuilder hql, Map<String, Object> parameters, String keyword,
			Integer inventeurId, Integer entrepriseId, Integer domaineId, LocalDate dateDepotFrom, LocalDate dateDepotTo,
			LocalDate dateValidationFrom, LocalDate dateValidationTo) {
		hql.append("where 1 = 1 ");

		if (keyword != null && !keyword.isBlank()) {
			hql.append("and lower(b.description) like :keyword ");
			parameters.put("keyword", "%" + keyword.toLowerCase() + "%");
		}
		if (inventeurId != null) {
			hql.append("and inv.num = :inventeurId ");
			parameters.put("inventeurId", inventeurId);
		}
		if (entrepriseId != null) {
			hql.append("and e.num = :entrepriseId ");
			parameters.put("entrepriseId", entrepriseId);
		}
		if (domaineId != null) {
			hql.append("and d.num = :domaineId ");
			parameters.put("domaineId", domaineId);
		}
		if (dateDepotFrom != null) {
			hql.append("and b.dateDepot >= :dateDepotFrom ");
			parameters.put("dateDepotFrom", dateDepotFrom);
		}
		if (dateDepotTo != null) {
			hql.append("and b.dateDepot <= :dateDepotTo ");
			parameters.put("dateDepotTo", dateDepotTo);
		}
		if (dateValidationFrom != null) {
			hql.append("and b.dateValidation >= :dateValidationFrom ");
			parameters.put("dateValidationFrom", dateValidationFrom);
		}
		if (dateValidationTo != null) {
			hql.append("and b.dateValidation <= :dateValidationTo ");
			parameters.put("dateValidationTo", dateValidationTo);
		}
	}

	private void appendOrderClause(StringBuilder hql, String sortBy, String sortDirection) {
		hql.append("order by ");
		hql.append(resolveSortField(sortBy));
		hql.append(" ");
		hql.append(resolveSortDirection(sortDirection));
	}

	private void bindParameters(Query<?> query, Map<String, Object> parameters) {
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	private String resolveSortField(String sortBy) {
		if ("dateValidation".equals(sortBy)) {
			return "b.dateValidation";
		}
		if ("description".equals(sortBy)) {
			return "b.description";
		}
		if ("inventeur".equals(sortBy)) {
			return "inv.nom";
		}
		if ("entreprise".equals(sortBy)) {
			return "e.nom";
		}
		if ("domaine".equals(sortBy)) {
			return "d.nom";
		}
		if ("num".equals(sortBy)) {
			return "b.num";
		}
		return "b.dateDepot";
	}

	private String resolveSortDirection(String sortDirection) {
		if ("asc".equalsIgnoreCase(sortDirection)) {
			return "asc";
		}
		return "desc";
	}

	@Override
	public Brevet getOne(int id) {
		Session s = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction t = s.beginTransaction();
		Brevet b = s.createQuery(
				"select b from Brevet b "
						+ "join fetch b.invention i "
						+ "join fetch b.inventeur inv "
						+ "where b.num = :id",
				Brevet.class)
				.setParameter("id", id)
				.uniqueResult();
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

