package com.lp.brevets.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class OpenReport {

	private static final String DATABASE_NAME = "dbbrevets";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	private static final String FOLDER_NAME = "Reports";

	public static void Open(String name, ServletContext servletContext,
			HttpServletResponse response) {

		Connection conn = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + DATABASE_NAME, USERNAME, PASSWORD);
			InputStream configStream = servletContext
					.getResourceAsStream("/WEB-INF/" + FOLDER_NAME + "/" + name + ".jasper");

			JasperPrint print;
			if (configStream != null) {
				print = JasperFillManager.fillReport(configStream, null, conn);
			} else {
				// Try to compile .jrxml if .jasper is missing
				InputStream jrxmlStream = servletContext
						.getResourceAsStream("/WEB-INF/" + FOLDER_NAME + "/" + name + ".jrxml");
				if (jrxmlStream == null) {
					throw new Exception("Report file not found: " + name);
				}
				net.sf.jasperreports.engine.JasperReport jasperReport = net.sf.jasperreports.engine.JasperCompileManager
						.compileReport(jrxmlStream);
				print = JasperFillManager.fillReport(jasperReport, null, conn);
			}

			response.setContentType("application/x-download");
			response.addHeader("content-disposition", "attachment; filename=" + name + ".pdf");
			ServletOutputStream out = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(print, out);

		} catch (Exception e) {
			System.out.println("error in exporting file \n" + e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}

