package org.cytoscape.cyni.internal.loadtable;


import java.io.File;

import org.cytoscape.io.read.CyTableReaderManager;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;


public class LoadAttributesFileTask extends AbstractLoadAttributesTask {
	@Tunable(description="Attribute Table file", params="fileCategory=table;input=true")
	public File file;

	public LoadAttributesFileTask(final CyTableReaderManager mgr,  final CyNetworkManager netMgr, final CyTableManager tabelMgr, 
			 final CyRootNetworkManager rootNetMgr) {
		super(mgr, netMgr, tabelMgr, rootNetMgr);
	}

	/**
	 * Executes Task.
	 */
	public void run(final TaskMonitor taskMonitor) throws Exception {

		loadTable(file.getName(), file.toURI(), taskMonitor);
	}
}

