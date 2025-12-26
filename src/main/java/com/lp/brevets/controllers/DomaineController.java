package com.lp.brevets.controllers;

import java.io.IOException;

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
                request.getSession().setAttribute(Constants.DOMAINES, metier.getAll());
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
        request.getSession().setAttribute(Constants.DOMAINES, metier.getAll());
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
}

