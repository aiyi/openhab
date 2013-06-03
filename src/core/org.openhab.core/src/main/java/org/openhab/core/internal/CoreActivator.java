/**
 * openHAB, the open Home Automation Bus.
 * Copyright (C) 2010-2013, openHAB.org <admin@openhab.org>
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 * Additional permission under GNU GPL version 3 section 7
 *
 * If you modify this Program, or any covered work, by linking or
 * combining it with Eclipse (or a modified version of that library),
 * containing parts covered by the terms of the Eclipse Public License
 * (EPL), the licensors of this Program grant you additional permission
 * to convey the resulting work.
 */
package org.openhab.core.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Handler;

import org.openhab.core.config.ConfigManager;
import org.openhab.core.service.Module;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * @author Jesse
 * 
 * @since 2.0.0
 */
public class CoreActivator {

	private static Logger logger = LoggerFactory.getLogger(CoreActivator.class);

	private static MutablePicoContainer container = new DefaultPicoContainer(new Caching());

	private static Map<String, Module> moduleMap = new HashMap<String, Module>();

	private static ConfigManager configManager = new ConfigManager(
			new CoreActivator().new ConfigHandler());

	public static void main(String[] args) {
		try {
			loadModules();
			start();
			configManager.activate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadModules() {
		ServiceLoader<Module> moduleLoader = ServiceLoader.load(Module.class);

		try {
			Iterator<Module> modIterator = moduleLoader.iterator();

			while (modIterator.hasNext()) {
				Module module = modIterator.next();
				String name = module.getClass().getPackage().getName();
				name = name.substring(name.lastIndexOf('.') + 1);
				
				module.setName(name);
				module.setContainer(container);
				moduleMap.put(name, module);
				logger.info("Loading module '{}'.", name);
			}
		} catch (ServiceConfigurationError serviceError) {
			serviceError.printStackTrace();
		}
	}

	public static void start() throws Exception {
		for (Module module : moduleMap.values()) {
			Properties configuration = ConfigManager.getModuleConfig(module.getName());
			module.configure(configuration);
			module.start();
		}

		logger.info("openHAB runtime has been started.");

		java.util.logging.Logger rootLogger = java.util.logging.LogManager.getLogManager().getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		for (Handler handler : handlers) {
			rootLogger.removeHandler(handler);
		}
		SLF4JBridgeHandler.install();
	}

	public static void stop() throws Exception {
		logger.info("openHAB runtime has been terminated.");
	}

	public class ConfigHandler {

		public void update(String moduleName, Properties configuration) {
			Module module = moduleMap.get(moduleName);
			if (module != null) {
				module.updated(configuration);
			}
			else {
				logger.info("Wrong configuration file name, module '{}' does not exist.", moduleName);
			}
		}
	}

}
