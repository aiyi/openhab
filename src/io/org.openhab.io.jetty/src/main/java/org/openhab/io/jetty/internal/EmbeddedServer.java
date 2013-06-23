package org.openhab.io.jetty.internal;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EmbeddedServer {

	private static Logger logger = LoggerFactory.getLogger(EmbeddedServer.class);

	private static Server server;
	
	public void startServer() throws Exception {
		logger.info("Starting EmbeddedServer...");

		String jettyHome = System.getProperty("jetty.home");
		if (jettyHome == null)
			jettyHome = ".";
		
		Resource jettyXml = Resource.newResource(jettyHome + "/conf/jetty/jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(jettyXml.getInputStream());
		server = (Server) configuration.configure();

		server.start();

		logger.info("EmbeddedServer started");
	}

	public void stopServer() throws Exception {
		logger.info("Stopping EmbeddedServer...");
		server.stop();
		logger.info("EmbeddedServer Stopped");
	}
}
