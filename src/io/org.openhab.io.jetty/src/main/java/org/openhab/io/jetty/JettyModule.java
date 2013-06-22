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
		super.configure(config);
		addComponent(EmbeddedServer.class);
	}

	@Override
	public void start() {
		EmbeddedServer server = getComponent(EmbeddedServer.class);
		try {
			server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		logger.info("Module {} started", getName());
	}

	@Override
	public void stop() {
		EmbeddedServer server = getComponent(EmbeddedServer.class);
		try {
			server.stopServer();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		logger.info("Module {} stopped", getName());
	}
}
