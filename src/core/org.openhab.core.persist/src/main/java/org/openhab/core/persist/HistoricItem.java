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
package org.openhab.core.persist;

import java.util.Date;

import org.openhab.core.types.State;

/**
 * This interface is used by persistence services to represent an item
 * with a certain state at a given point in time.
 * 
 * <p>Note that this interface does not extend {@link Item} as the persistence
 * services could not provide an implementation that correctly implement
 * getAcceptedXTypes() and getGroupNames().</p>
 * 
 * @author Kai Kreuzer
 * @since 1.0.0
 */
public interface HistoricItem {

	/**
	 * returns the timestamp of the persisted item
	 * 
	 * @return the timestamp of the item
	 */
	Date getTimestamp();

	/**
	 * returns the current state of the item
	 * 
	 * @return the current state
	 */
	public State getState();

	/**
	 * returns the name of the item
	 * 
	 * @return the name of the item
	 */
	public String getName();

}
