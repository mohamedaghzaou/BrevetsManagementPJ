package com.lp.brevets.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierDomaine;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.metier.MetierInvention;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.models.Invention;
import com.lp.brevets.util.Constants;

@WebServlet("/inventions")
public class InventionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IMetier metier = null;

	public InventionController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
		String page = "/WEB-INF/views/invention/list.jsp";
		request.setAttribute("destination", Constants.INVENTIONS);

		switch (command) {
		case "list":
			metier = MetierInvention.INSTANCE;
			request.getSession().setAttribute(Constants.INVENTIONS, metier.getAll());
			break;

		case "adding":
			page = "/WEB-INF/views/invention/add.jsp";

			if (request.getParameter("op") != null) {
				add(request, response);
			} else {
				metier = MetierDomaine.INSTANCE;
				request.setAttribute("domaines", metier.getAll());
			}
			break;
		case "updating":
			page = "/WEB-INF/views/invention/update.jsp";

			if (request.getParameter("op") != null) {
				update(request, response);
			} else {
				int id = Integer.parseInt(request.getParameter("id"));

				metier = MetierInvention.INSTANCE;
				request.setAttribute("invention", metier.getOne(id));
				metier = MetierDomaine.INSTANCE;
				request.setAttribute("domaines", metier.getAll());

			}
			break;
		case "delete":
			delete(request, response);
			break;
		}

		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}

	private void update(HttpServletRequest request, HttpServletResponse response) {

		try {

			metier = MetierInvention.INSTANCE;
			int id = Integer.parseInt(request.getParameter("id"));

			Invention invention = constructInvention(request, response);
			invention.setNum(id);
			metier.update(invention);
			request.setAttribute("invention", metier.getOne(id));
			metier = MetierDomaine.INSTANCE;
			request.setAttribute("domaines", metier.getAll());

			request.setAttribute("status", "updated");
		} catch (Exception e) {

			request.setAttribute("status", "Notupdated");

		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {

		try {
			metier = MetierInvention.INSTANCE;

			Invention invention = constructInvention(request, response);
			metier.save(invention);
			request.setAttribute("status", "added");
			request.getSession().setAttribute(Constants.INVENTIONS, metier.getAll());
			metier = MetierDomaine.INSTANCE;
			request.setAttribute("domaines", metier.getAll());

		} catch (Exception e) {
			request.setAttribute("status", "Notadded");

		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		metier = MetierInvention.INSTANCE;
		metier.delete(new Invention(id));
		request.getSession().setAttribute(Constants.INVENTIONS, metier.getAll());

	}

	private Invention constructInvention(HttpServletRequest request, HttpServletResponse response) {
		Invention invention = new Invention();
		invention.setDescriptif(request.getParameter("description"));
		invention.setResume(request.getParameter("resume"));
		invention.setDomaine(new Domaine(Integer.parseInt(request.getParameter("domaine"))));

		return invention;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

