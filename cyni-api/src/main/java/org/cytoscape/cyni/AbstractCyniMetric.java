/*
  File: AbstractCyniMetric.java

  Copyright (c) 2006, 2010-2012, The Cytoscape Consortium (www.cytoscape.org)

  This library is free software; you can redistribute it and/or modify it
  under the terms of the GNU Lesser General Public License as published
  by the Free Software Foundation; either version 2.1 of the License, or
  any later version.

  This library is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
  MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
  documentation provided hereunder is on an "as is" basis, and the
  Institute for Systems Biology and the Whitehead Institute
  have no obligations to provide maintenance, support,
  updates, enhancements or modifications.  In no event shall the
  Institute for Systems Biology and the Whitehead Institute
  be liable to any party for direct, indirect, special,
  incidental or consequential damages, including lost profits, arising
  out of the use of this software and its documentation, even if the
  Institute for Systems Biology and the Whitehead Institute
  have been advised of the possibility of such damage.  See
  the GNU Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package org.cytoscape.cyni;

import java.util.ArrayList;
import java.util.List;



/**
 * The AbstractCyniMetrics provides a basic implementation of a cyni metric
 * TaskFactory.
 * 
 * @CyAPI.Abstract.Class
 */
public abstract class AbstractCyniMetric implements CyCyniMetric {

	private final String humanName;
	private final String computerName;
	private final ArrayList<String> types;
	

	/**
	 * The Constructor.
	 * 
	 * @param computerName
	 *            a computer readable name used to differentiate the metrics.
	 * @param humanName
	 *            a user visible name of the metric.
	 * @param types
	 *            list of types that this metric supports.
	 */
	public AbstractCyniMetric(final String computerName,final String humanName) {
		this.humanName = humanName;
		this.computerName = computerName;
		types = new ArrayList<String>();

	}
	
	/**
	 * A computer readable name used to to differentiate the metrics.
	 * 
	 * @return a computer readable name used to differentiate the metrics.
	 */
	public String getName() {
		return computerName;
	}
	
	/**
	 * Used to get the user-visible name of the cyni Metric.
	 * 
	 * @return the user-visible name of the cyni Metric.
	 */
	public String toString() {
		return humanName;
	}
	
	/**
	 * It adds a type to the list of supported types for that metric
	 * 
	 */
	protected void addType(String type){
		types.add(type);
	}
	/**
	 * The list of types of this metric
	 * 
	 * @return the list of types
	 */
	public List<String>  getTypesList() {
		return types;
	}

	/**
	 * It resets the metric and leaves the default metric's parameters
	 * 
	 */
	public void resetParameters(){
		
	}
	

}
