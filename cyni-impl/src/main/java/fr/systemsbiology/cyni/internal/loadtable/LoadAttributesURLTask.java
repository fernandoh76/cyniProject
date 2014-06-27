/*
 * #%L
 * Cyni Implementation (cyni-impl)
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2006 - 2013 The Cytoscape Consortium
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 2.1 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package fr.systemsbiology.cyni.internal.loadtable;


import java.net.URL;

import org.cytoscape.io.read.CyTableReaderManager;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;


public class LoadAttributesURLTask extends AbstractLoadAttributesTask {
	
	@Tunable(description="Attribute Table URL", params="fileCategory=table;input=true")
	public URL url;

	public LoadAttributesURLTask(final CyTableReaderManager mgr,  final CyNetworkManager netMgr, final CyTableManager tableMgr, 
			 final CyRootNetworkManager rootNetMgr) {
		super(mgr, netMgr, tableMgr,  rootNetMgr);
	}

	/**
	 * Executes Task.
	 */
	public void run(final TaskMonitor taskMonitor) throws Exception {

		loadTable(url.toString(), url.toURI(), taskMonitor);
	}
}

