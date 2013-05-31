package org.openhab.core.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openhab.core.internal.CoreActivator.ConfigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows to manage main configurations from external files.
 * 
 * Configuration is delivered to each {@code Module} upon config file change.
 * 
 * @author Jesse
 * @since 2.0.0
 */
public class ConfigurationManager {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
	
	private static String mainConfigFolder = null;
	
	private ConfigHandler handler = null;
	
	public ConfigurationManager() {
		mainConfigFolder = getConfigFolder() + "/";
		logger.info("Main configuration directory '{}'.", mainConfigFolder);
	}
	
	public void setConfigHandler(ConfigHandler handler) {
		this.handler = handler;
	}

	public void configChanged(String module) {
		Properties configuration = null;
		try {
			configuration = getModuleConfig(module);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		handler.update(module, configuration);
	}
	
	public static Properties getModuleConfig(String module) throws IOException {
		Properties configuration = new Properties();
		String configFilePath = mainConfigFolder + module + ConfigConstants.MAIN_CONFIG_FILE_EXTENSION;
		FileInputStream in = null;
		
		try {
			in = new FileInputStream(configFilePath);
			configuration.load(in);
			logger.info("Loading configuration file '{}'.", configFilePath);
		} catch (FileNotFoundException e) {
			configuration = null;
			logger.info("Configuration file '{}' does not exist.", configFilePath);
		} catch (IOException e) {
			configuration = null;
			logger.error("Configuration file '{}' cannot be read.", configFilePath, e);
		} finally {
			if (in != null)
				in.close();
		}
		
		return configuration;
	}
	
	/**
	 * Returns the main configuration folder path name from the System property <code>configdir</code>.
	 * 
	 * @return the configuration folder path name
	 */
	public static String getConfigFolder() {
		String progArg = System.getProperty(ConfigConstants.CONFIG_DIR_PROG_ARGUMENT);
		if (progArg != null) {
			return progArg;
		} else {
			return ConfigConstants.MAIN_CONFIG_FOLDER;
		}
	}
	
}