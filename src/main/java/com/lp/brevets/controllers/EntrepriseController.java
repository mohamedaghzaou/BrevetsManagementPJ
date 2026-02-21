package com.lp.brevets.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierEntreprise;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.util.Constants;

@WebServlet("/enterprises")
public class EntrepriseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;

	private IMetier metier = null;

	public EntrepriseController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/etrp/list.jsp";
		metier = MetierEntreprise.INSTANCE;

		switch (command) {
		case "list":
			loadEntrepriseListPage(request);

			break;

		case "adding":
			page = "/WEB-INF/views/etrp/add.jsp";

			if (request.getParameter("op") != null) {
				add(request, response);
			}
			break;
		case "updating":
			page = "/WEB-INF/views/etrp/update.jsp";
			if (request.getParameter("op") != null) {
				update(request, response);

			} else {

				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute(Constants.ENTREPRISE, metier.getOne(id));

			}
			break;
		case "delete":
			delete(request, response);
			loadEntrepriseListPage(request);
			break;
		}
		request.setAttribute("destination", Constants.ENTREPRISE);

		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		metier.delete(new Entreprise(id));

	}

	private Entreprise constructEntreprise(HttpServletRequest request, HttpServletResponse response) {
		Entreprise e = new Entreprise();

		e.setNom(request.getParameter("nom"));
		e.setActivite(request.getParameter("activite"));
		e.setCa(Double.parseDouble(request.getParameter("ca")));
		e.setVille(request.getParameter("ville"));
		return e;

	}

	private void update(HttpServletRequest request, HttpServletResponse response) {

		try {
			Entreprise e = constructEntreprise(request, response);
			int id = Integer.parseInt(request.getParameter("num"));
			e.setNum(id);
			metier.update(e);
			request.setAttribute(Constants.ENTREPRISE, e);
			request.setAttribute("status", "updated");
		} catch (Exception e) {
			request.setAttribute("status", "notupdated");
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		try {

			Entreprise e = constructEntreprise(request, response);
			metier.save(e);
			request.setAttribute("status", "added");

		} catch (Exception e) {
			request.setAttribute("status", "Notadded");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void loadEntrepriseListPage(HttpServletRequest request) {
		int requestedPage = parsePositiveInt(request.getParameter("page"), 1);

		long totalEntreprises = metier.count();
		int totalPages = (int) Math.ceil(totalEntreprises / (double) PAGE_SIZE);
		if (totalPages == 0) {
			totalPages = 1;
		}
		int currentPage = Math.min(requestedPage, totalPages);

		List<Entreprise> pageData = metier.getPage(currentPage, PAGE_SIZE);
		request.setAttribute(Constants.ENTREPRISES, pageData);
		request.getSession().setAttribute(Constants.ENTREPRISES, pageData);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.setAttribute("totalResults", totalEntreprises);
		request.setAttribute("hasPagination", totalEntreprises > PAGE_SIZE);
	}

	private int parsePositiveInt(String value, int defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		try {
			int parsed = Integer.parseInt(value);
			return parsed > 0 ? parsed : defaultValue;
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

}

