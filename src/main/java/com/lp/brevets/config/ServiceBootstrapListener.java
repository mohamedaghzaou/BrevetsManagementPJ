package com.lp.brevets.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lp.brevets.metier.MetierBrevet;
import com.lp.brevets.metier.MetierDomaine;
import com.lp.brevets.metier.MetierEntreprise;
import com.lp.brevets.metier.MetierInventeur;
import com.lp.brevets.metier.MetierInvention;
import com.lp.brevets.services.BrevetService;
import com.lp.brevets.services.DomaineService;
import com.lp.brevets.services.EntrepriseService;
import com.lp.brevets.services.InventeurService;
import com.lp.brevets.services.InventionService;
import com.lp.brevets.services.impl.BrevetServiceImpl;
import com.lp.brevets.services.impl.DomaineServiceImpl;
import com.lp.brevets.services.impl.EntrepriseServiceImpl;
import com.lp.brevets.services.impl.InventeurServiceImpl;
import com.lp.brevets.services.impl.InventionServiceImpl;

public class ServiceBootstrapListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		BrevetService brevetService = new BrevetServiceImpl(MetierBrevet.INSTANCE, MetierInventeur.INSTANCE,
				MetierInvention.INSTANCE, MetierEntreprise.INSTANCE, MetierDomaine.INSTANCE);
		InventeurService inventeurService = new InventeurServiceImpl(MetierInventeur.INSTANCE, MetierEntreprise.INSTANCE);
		InventionService inventionService = new InventionServiceImpl(MetierInvention.INSTANCE, MetierDomaine.INSTANCE);
		EntrepriseService entrepriseService = new EntrepriseServiceImpl(MetierEntreprise.INSTANCE);
		DomaineService domaineService = new DomaineServiceImpl(MetierDomaine.INSTANCE);

		ApplicationServices services = new ApplicationServices(brevetService, inventeurService, inventionService,
				entrepriseService, domaineService);
		sce.getServletContext().setAttribute(ApplicationServices.CONTEXT_KEY, services);
	}
}
