package org.openhab.io.jetty;

import java.util.Properties;

import org.openhab.core.service.Module;
import org.openhab.io.jetty.internal.EmbeddedServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyModule extends Module {

	private static Logger logger = LoggerFactory.getLogger(JettyModule.class);

	@Override
	public void configure(Properties config) {
		addComponent("jetty", EmbeddedServer.class);
	}

	@Override
	public void updated(Properties config) {
		logger.debug("Configuration updated {}.", config.toString());
	}

	@Override
	public void start() {
		logger.info("Module '{}' has been started.", getName());
		
		EmbeddedServer server = (EmbeddedServer)getComponent("jetty");
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		logger.info("Module '{}' has been stopped.", getName());
		
		EmbeddedServer server = (EmbeddedServer)getComponent("jetty");
		try {
			server.stopServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
