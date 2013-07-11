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
package org.openhab.core.persist.internal;

import org.openhab.core.items.Item;
import org.openhab.core.persist.PersistenceService;
import org.openhab.core.model.Model;
import org.openhab.core.model.ModelRepository;
import org.openhab.core.model.persist.PersistenceConfiguration;
import org.openhab.core.model.persist.PersistenceModel;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of Quartz {@link Job}-Interface. It takes a PersistenceModel and a CronStrategy,
 * scans through the relevant configurations and persists the concerned items.
 * 
 * @author Kai Kreuzer
 * @since 1.0.0
 */
public class PersistItemsJob implements Job {

	private static final Logger logger = LoggerFactory.getLogger(PersistItemsJob.class);
		
	public static final String JOB_DATA_PERSISTMODEL = "model";
	public static final String JOB_DATA_STRATEGYNAME = "strategy";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String modelName = (String) context.getJobDetail().getJobDataMap().get(JOB_DATA_PERSISTMODEL);				
		String strategyName = (String) context.getJobDetail().getJobDataMap().get(JOB_DATA_STRATEGYNAME);
		
		PersistenceManager persistenceManager = PersistenceManager.getInstance();
		if(persistenceManager!=null) {
			ModelRepository modelRepository = persistenceManager.modelRepository;
			PersistenceService persistenceService = persistenceManager.persistenceServices.get(modelName);
			
			if(modelRepository!=null && persistenceService!=null) {
				Model model = modelRepository.getModel(modelName + ".persist");
				if (model instanceof PersistenceModel) {
					PersistenceModel persistModel = (PersistenceModel) model;
					for(PersistenceConfiguration config : persistModel.getConfigs().getConfigs()) {
						if(hasStrategy(persistModel, config, strategyName)) {
							for(Item item : persistenceManager.getAllItems(config)) {
								long startTime = System.currentTimeMillis();
								persistenceService.store(item, config.getAlias());
								logger.trace("Storing item '{}' with persistence service '{}' took {}ms",
										new Object[] { item.getName(), modelName, System.currentTimeMillis() - startTime});
							}
						}
					}
				} else {
					logger.debug("Persistence file '{}' does not exist", modelName);
				}
			}
		} else {
			logger.warn("Persistence manager is not available!");
		}
	}

	private boolean hasStrategy(PersistenceModel persistModel, PersistenceConfiguration config, String strategyName) {
		// check if the strategy is directly defined on the config
		for(String strategy : config.getStrategies()) {
			if(strategy.equals(strategyName)) 
				return true;
		}
		return false;
	}

}