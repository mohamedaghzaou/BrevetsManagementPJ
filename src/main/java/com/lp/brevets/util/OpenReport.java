package com.lp.brevets.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class OpenReport {

	private static final String DEFAULT_DATABASE_NAME = "dbbrevets";
	private static final String DEFAULT_USERNAME = "root";
	private static final String DEFAULT_PASSWORD = "password";
	private static final String FOLDER_NAME = "Reports";
	private static final Set<String> ALLOWED_REPORTS = Set.of("RptBrevet", "RptDomaine", "RptEntreprise",
			"RptInventeur", "RptInvention");

	public static void Open(String name, ServletContext servletContext, HttpServletResponse response) {
		String reportName = sanitizeReportName(name);
		if (reportName == null) {
			sendError(response, HttpServletResponse.SC_NOT_FOUND, "Rapport introuvable.");
			return;
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(buildJdbcUrl(), resolveUsername(), resolvePassword())) {
				JasperPrint print = loadReport(reportName, servletContext, conn);
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + ".pdf\"");
				try (ServletOutputStream out = response.getOutputStream()) {
					JasperExportManager.exportReportToPdfStream(print, out);
				}
			}
		} catch (Exception ex) {
			servletContext.log("Error while exporting report: " + reportName, ex);
			sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Erreur lors de la generation du rapport.");
		}
	}

	private static JasperPrint loadReport(String reportName, ServletContext servletContext, Connection conn)
			throws Exception {
		String basePath = "/WEB-INF/" + FOLDER_NAME + "/" + reportName;
		try (InputStream compiledStream = servletContext.getResourceAsStream(basePath + ".jasper")) {
			if (compiledStream != null) {
				return JasperFillManager.fillReport(compiledStream, null, conn);
			}
		}
		try (InputStream jrxmlStream = servletContext.getResourceAsStream(basePath + ".jrxml")) {
			if (jrxmlStream == null) {
				throw new IllegalArgumentException("Report file not found: " + reportName);
			}
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
			return JasperFillManager.fillReport(jasperReport, null, conn);
		}
	}

	private static String sanitizeReportName(String name) {
		if (name == null) {
			return null;
		}
		String trimmed = name.trim();
		return ALLOWED_REPORTS.contains(trimmed) ? trimmed : null;
	}

	private static String buildJdbcUrl() {
		String databaseName = resolveConfig("BREVETS_DB_NAME", "brevets.db.name", DEFAULT_DATABASE_NAME);
		return "jdbc:mysql://localhost/" + databaseName;
	}

	private static String resolveUsername() {
		return resolveConfig("BREVETS_DB_USER", "brevets.db.user", DEFAULT_USERNAME);
	}

	private static String resolvePassword() {
		return resolveConfig("BREVETS_DB_PASSWORD", "brevets.db.password", DEFAULT_PASSWORD);
	}

	private static String resolveConfig(String envKey, String systemPropertyKey, String fallbackValue) {
		String propertyValue = System.getProperty(systemPropertyKey);
		if (propertyValue != null && !propertyValue.isBlank()) {
			return propertyValue;
		}
		String envValue = System.getenv(envKey);
		if (envValue != null && !envValue.isBlank()) {
			return envValue;
		}
		return fallbackValue;
	}

	private static void sendError(HttpServletResponse response, int statusCode, String message) {
		if (response.isCommitted()) {
			return;
		}
		try {
			response.sendError(statusCode, message);
		} catch (IOException ignored) {
		}
	}
}
