package org.openhab.io.jetty.internal;

import java.util.Collection;
import java.util.Map;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.openhab.core.service.ServletProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EmbeddedServer {

	private static Logger logger = LoggerFactory.getLogger(EmbeddedServer.class);

	private static Server server;
	
	Collection<ServletProvider> servletProviders;

	public EmbeddedServer(Collection<ServletProvider> servletProviders) {
		this.servletProviders = servletProviders;
	}
	
	public void startServer() throws Exception {
		logger.info("Starting EmbeddedServer...");

		String jettyHome = System.getProperty("jetty.home");
		if (jettyHome == null)
			jettyHome = ".";
		
		Resource jettyXml = Resource.newResource(jettyHome + "/conf/jetty/jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(jettyXml.getInputStream());
		server = (Server) configuration.configure();
		
		ServletContextHandler contextHandler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		
		for (ServletProvider provider : servletProviders) {
			ServletHolder holder = new ServletHolder(provider.getServletClass());
			for (Map.Entry<String, String> entry : provider.getInitParams().entrySet())
			    holder.setInitParameter(entry.getKey(), entry.getValue());
			contextHandler.addServlet(holder, provider.getPathSpec());
		}
		
		server.start();

		logger.info("EmbeddedServer started");
	}

	public void stopServer() throws Exception {
		logger.info("Stopping EmbeddedServer...");
		server.stop();
		logger.info("EmbeddedServer Stopped");
	}
}
