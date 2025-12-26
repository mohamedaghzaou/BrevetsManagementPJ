package com.lp.brevets.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lp.brevets.dao.DaoBrevet;
import com.lp.brevets.dao.DaoEntreprise;
import com.lp.brevets.dao.DaoInventeur;
import com.lp.brevets.dao.DaoInvention;
import com.lp.brevets.dao.DaoDomaine;
import com.lp.brevets.util.Charts;

@WebServlet("/ChartController")
public class ChartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChartController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");

		DaoEntreprise ee = new DaoEntreprise();
		DaoInvention domaine = new DaoInvention();
		DaoBrevet brevet = new DaoBrevet();
		DaoInventeur inventeur = new DaoInventeur();
		DaoDomaine daoDomaine = new DaoDomaine();

		Charts charts = new Charts(
				ee.InventionParEntreprise(),
				domaine.inoventionParDomaine(),
				brevet.count(),
				domaine.count(),
				inventeur.count(),
				ee.count(),
				daoDomaine.count());
		Gson g = new Gson();
		String jsonChart = g.toJson(charts);

		response.getWriter().println(jsonChart);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

