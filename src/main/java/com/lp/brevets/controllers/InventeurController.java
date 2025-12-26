package com.lp.brevets.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierEntreprise;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.models.Entreprise;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.util.Constants;

@WebServlet("/inventeurs")
public class InventeurController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMetier metier;

	public InventeurController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("destination", Constants.INVENTEUR);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/inventeur/list.jsp";

		switch (command) {
			case "list":
				metier = MetierInventeur.INSTANCE;
				request.getSession().setAttribute(Constants.INVENTEURS, metier.getAll());
				break;

			case "adding":
				page = "/WEB-INF/views/inventeur/add.jsp";

				if (request.getParameter("op") != null) {
					add(request, response);
				} else {
					metier = MetierEntreprise.INSTANCE;
					request.setAttribute(Constants.ENTREPRISES, metier.getAll());
				}
				break;
			case "updating":
				page = "/WEB-INF/views/inventeur/update.jsp";
				System.out.println("updating");

				if (request.getParameter("op") != null) {
					update(request, response);

				} else {

					int id = Integer.parseInt(request.getParameter("id"));
					metier = MetierInventeur.INSTANCE;
					request.setAttribute(Constants.INVENTEUR, metier.getOne(id));

					metier = MetierEntreprise.INSTANCE;
					request.setAttribute(Constants.ENTREPRISES, metier.getAll());

				}
				break;
			case "delete":
				delete(request, response);
				break;
		}

		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}

	private Inventeur constructInventeur(HttpServletRequest request, HttpServletResponse response) {
		Inventeur inventeur = new Inventeur();
		inventeur.setNom(request.getParameter("nom"));
		inventeur.setPrenom(request.getParameter("prenom"));
		inventeur.setAdresse(request.getParameter("adresse"));
		inventeur.setEmail(request.getParameter("email"));
		inventeur.setEntreprise(new Entreprise(Integer.parseInt(request.getParameter("entreprise"))));

		try {
			SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");

			Date dateDepot = dateformt.parse(request.getParameter("datenaiss"));
			LocalDate localdateDepot = Instant.ofEpochMilli(dateDepot.getTime()).atZone(ZoneId.systemDefault())
					.toLocalDate();
			inventeur.setDate_nais(localdateDepot);

		} catch (ParseException e) {
			System.out.println("conversion error");

		}
		return inventeur;
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			Inventeur inventeur = constructInventeur(request, response);
			inventeur.setNum(Integer.parseInt(request.getParameter("id")));
			metier = MetierInventeur.INSTANCE;
			metier.update(inventeur);
			int id = Integer.parseInt(request.getParameter("id"));
			metier = MetierInventeur.INSTANCE;
			request.setAttribute(Constants.INVENTEUR, metier.getOne(id));
			metier = MetierEntreprise.INSTANCE;
			request.setAttribute(Constants.ENTREPRISES, metier.getAll());
			request.setAttribute("status", "updated");

		} catch (Exception e) {
			request.setAttribute("status", "notUpdated");
			System.out.println("conversion error");

		}

	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {

		int id = Integer.parseInt(request.getParameter("id"));
		metier = MetierInventeur.INSTANCE;
		metier.delete(new Inventeur(id));
		request.getSession().setAttribute(Constants.INVENTEURS, metier.getAll());

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {

		Inventeur inventeur = constructInventeur(request, response);

		try {
			metier = MetierInventeur.INSTANCE;
			metier.save(inventeur);
			request.setAttribute("status", "added");

		} catch (Exception e) {
			request.setAttribute("status", "Notadde");
			System.out.println("conversion error");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

