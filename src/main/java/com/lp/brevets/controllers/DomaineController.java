package com.lp.brevets.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lp.brevets.metier.IMetier;
import com.lp.brevets.metier.MetierDomaine;
import com.lp.brevets.models.Domaine;
import com.lp.brevets.util.Constants;

@WebServlet("/domaines")
public class DomaineController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int PAGE_SIZE = 10;

    private IMetier<Domaine> metier = MetierDomaine.INSTANCE;

    public DomaineController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("destination", Constants.DOMAINE);
        String command = request.getParameter("mode") == null ? "list" : request.getParameter("mode");
        System.out.println("DomaineController command: " + command);

        String page = "/WEB-INF/views/domaine/list.jsp";

        switch (command) {
            case "list":
                loadDomaineListPage(request);
                break;
            case "adding":
                page = "/WEB-INF/views/domaine/add.jsp";
                if (request.getParameter("op") != null) {
                    add(request, response);
                }
                break;
            case "updating":
                page = "/WEB-INF/views/domaine/update.jsp";
                if (request.getParameter("op") != null) {
                    update(request, response);
                } else {
                    int id = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute(Constants.DOMAINE, metier.getOne(id));
                }
                break;
            case "delete":
                System.out.println("Deleting domaine with id: " + request.getParameter("id"));
                delete(request, response);
                loadDomaineListPage(request);
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
        metier.delete(new Domaine(id));
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        String nom = request.getParameter("nom");
        Domaine d = new Domaine();
        d.setNom(nom);
        metier.save(d);
        request.setAttribute("status", "added");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        int num = Integer.parseInt(request.getParameter("num"));
        String nom = request.getParameter("nom");
        Domaine d = new Domaine(num, nom);
        metier.update(d);
        request.setAttribute(Constants.DOMAINE, metier.getOne(num));
        request.setAttribute("status", "updated");
    }

    private void loadDomaineListPage(HttpServletRequest request) {
        int requestedPage = parsePositiveInt(request.getParameter("page"), 1);

        long totalDomaines = metier.count();
        int totalPages = (int) Math.ceil(totalDomaines / (double) PAGE_SIZE);
        if (totalPages == 0) {
            totalPages = 1;
        }
        int currentPage = Math.min(requestedPage, totalPages);

        List<Domaine> pageData = metier.getPage(currentPage, PAGE_SIZE);
        request.setAttribute(Constants.DOMAINES, pageData);
        request.getSession().setAttribute(Constants.DOMAINES, pageData);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", PAGE_SIZE);
        request.setAttribute("totalResults", totalDomaines);
        request.setAttribute("hasPagination", totalDomaines > PAGE_SIZE);
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

