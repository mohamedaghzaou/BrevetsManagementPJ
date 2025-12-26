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
import com.lp.brevets.metier.MetierBrevet;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.metier.MetierInvention;
import com.lp.brevets.models.Brevet;
import com.lp.brevets.models.Inventeur;
import com.lp.brevets.models.Invention;
import com.lp.brevets.util.Constants;

@WebServlet("/brevets")
public class BrevetsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IMetier metier;

	public BrevetsController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("destination", Constants.BREVETS);
		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");

		String page = "/WEB-INF/views/brevets/list.jsp";

		switch (command) {
			case "list":
				metier = MetierBrevet.INSTANCE;

				request.getSession().setAttribute(Constants.BREVETS, metier.getAll());

				break;
			case "adding":
				page = "/WEB-INF/views/brevets/add.jsp";

				if (request.getParameter("op") != null) {
					add(request, response);
				} else {
					metier = MetierInventeur.INSTANCE;
					request.setAttribute(Constants.INVENTEURS, metier.getAll());
					metier = MetierInvention.INSTANCE;
					request.setAttribute(Constants.INVENTIONS, metier.getAll());
				}

				break;
			case "updating":
				page = "/WEB-INF/views/brevets/update.jsp";

				if (request.getParameter("op") != null) {
					update(request, response);

				} else {

					int id = Integer.parseInt(request.getParameter("id"));
					metier = MetierBrevet.INSTANCE;
					request.setAttribute(Constants.BREVET, metier.getOne(id));

					metier = MetierInventeur.INSTANCE;
					request.setAttribute(Constants.INVENTEURS, metier.getAll());

					metier = MetierInvention.INSTANCE;
					request.setAttribute(Constants.INVENTIONS, metier.getAll());

				}

				break;
			case "delete":

				delete(request, response);
				break;
		}
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {

		int id = Integer.parseInt(request.getParameter("id"));
		metier = MetierBrevet.INSTANCE;
		metier.delete(new Brevet(id));
		request.getSession().setAttribute(Constants.BREVETS, metier.getAll());

	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			metier = MetierBrevet.INSTANCE;
			Brevet v = constructBrevet(request, response);
			metier.save(v);
			request.setAttribute("status", "added");
		}

		catch (Exception eX) {
			request.setAttribute("status", "Notadde");

			System.out.println("conversion error");
		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		metier = MetierBrevet.INSTANCE;

		Brevet v = constructBrevet(request, response);
		v.setNum(Integer.parseInt(request.getParameter("num")));

		try {
			metier = MetierBrevet.INSTANCE;
			metier.update(v);
			int id = Integer.parseInt(request.getParameter("num"));
			metier = MetierBrevet.INSTANCE;
			request.setAttribute(Constants.BREVET, metier.getOne(id));
			metier = MetierInventeur.INSTANCE;
			request.setAttribute(Constants.INVENTEURS, metier.getAll());
			metier = MetierInvention.INSTANCE;
			request.setAttribute(Constants.INVENTIONS, metier.getAll());
			request.setAttribute("status", "updated");

		} catch (Exception eX) {
			request.setAttribute("status", "notUpdated");

			System.out.println("conversion error");
		}

	}

	private Brevet constructBrevet(HttpServletRequest request, HttpServletResponse response) {

		Brevet v = new Brevet();
		v.setInvention(new Invention(Integer.parseInt(request.getParameter("invention"))));

		v.setInventeur(new Inventeur(Integer.parseInt(request.getParameter("inventeur"))));
		v.setDescription(request.getParameter("description"));

		try {
			SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");

			Date dateDepot = dateformt.parse(request.getParameter("datedepot"));
			LocalDate localdateDepot = Instant.ofEpochMilli(dateDepot.getTime()).atZone(ZoneId.systemDefault())
					.toLocalDate();

			v.setDateDepot(localdateDepot);

			Date datevalidation = dateformt.parse(request.getParameter("datevalidation"));
			LocalDate localdatevalidation = Instant.ofEpochMilli(datevalidation.getTime())
					.atZone(ZoneId.systemDefault()).toLocalDate();

			v.setDateValidation(localdatevalidation);
		} catch (ParseException e) {
			System.out.println("conversion error");

		}
		return v;
	}

}

