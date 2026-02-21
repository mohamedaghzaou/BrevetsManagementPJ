package com.lp.brevets.config;

import javax.servlet.ServletContext;

import com.lp.brevets.services.BrevetService;
import com.lp.brevets.services.DomaineService;
import com.lp.brevets.services.EntrepriseService;
import com.lp.brevets.services.InventeurService;
import com.lp.brevets.services.InventionService;

public class ApplicationServices {
	public static final String CONTEXT_KEY = ApplicationServices.class.getName();

	private final BrevetService brevetService;
	private final InventeurService inventeurService;
	private final InventionService inventionService;
	private final EntrepriseService entrepriseService;
	private final DomaineService domaineService;

	public ApplicationServices(BrevetService brevetService, InventeurService inventeurService,
			InventionService inventionService, EntrepriseService entrepriseService, DomaineService domaineService) {
		this.brevetService = brevetService;
		this.inventeurService = inventeurService;
		this.inventionService = inventionService;
		this.entrepriseService = entrepriseService;
		this.domaineService = domaineService;
	}

	public BrevetService getBrevetService() {
		return brevetService;
	}

	public InventeurService getInventeurService() {
		return inventeurService;
	}

	public InventionService getInventionService() {
		return inventionService;
	}

	public EntrepriseService getEntrepriseService() {
		return entrepriseService;
	}

	public DomaineService getDomaineService() {
		return domaineService;
	}

	public static ApplicationServices from(ServletContext servletContext) {
		Object services = servletContext.getAttribute(CONTEXT_KEY);
		if (!(services instanceof ApplicationServices)) {
			throw new IllegalStateException("Application services are not initialized.");
		}
		return (ApplicationServices) services;
	}
}
