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
package org.openhab.core.events;

import org.openhab.core.types.EventType;
import org.openhab.core.types.Type;

/**
 * An event.
 * 
 * {@code Event} objects are delivered to {@code EventHandler} services which
 * subscribe to the event bus.
 * 
 * @author Jesse
 * @since 2.0.0
 */
public class Event {

	private final EventType type;

	/**
	 * The name of the item associated to this event.
	 */
	private final String item;

	/**
	 * The item state or command of this event.
	 */
	private final Type data;

	/**
	 * Constructs an event.
	 * 
	 * @param type
	 *            The type of the event.
	 * @param item
	 *            The name of the item.
	 * @param data
	 *            The state/command of the item.
	 */
	public Event(EventType type, String item, Type data) {
		this.type = type;
		this.item = item;
		this.data = data;
	}

	/**
	 * returns the name of the item
	 * 
	 * @return the name of the item
	 */
	public String getItemName() {
		return this.item;
	}

	/**
	 * Returns the type of this event.
	 * 
	 * @return The type of this event.
	 */
	public final EventType getEventType() {
		return this.type;
	}

	/**
	 * Returns the data of this event.
	 * 
	 * @return The data of this event.
	 */
	public final Type getEventData() {
		return this.data;
	}

	/**
	 * Returns the string representation of this event.
	 * 
	 * @return The string representation of this event.
	 */
	public String toString() {
		return getClass().getName() + " [type=" + type.toString() + " item=" + item + "]";
	}

}
