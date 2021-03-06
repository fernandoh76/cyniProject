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
package fr.systemsbiology.cyni.internal.inductionAlgorithms.K2Algorithm;

import java.io.IOException;
import java.util.*;

import org.cytoscape.work.util.*;
import org.cytoscape.model.CyTable;
import fr.systemsbiology.cyni.*;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;

public class K2InductionContext extends CyniAlgorithmContext implements TunableValidator {
	
	@Tunable(description="Max. number of parents", gravity=1.0)
	public int maxNumParents = 5;
	@Tunable(description="Output Only Nodes with Edges", gravity=2.0)
	public boolean removeNodes = false;
	
	@Tunable(description="Row order", groups="Row order options", gravity=3.0)
	public ListSingleSelection<String> ordering = new ListSingleSelection<String>("Default Cytoscape Order", "Random Order", "Use Column");
	@Tunable(description="Column to order rows",dependsOn="ordering=Use Column", groups="Row order options", gravity=4.0)
	public ListSingleSelection<String> selectedColumn;
	
	@Tunable(description="Use selected nodes only", groups="Parameters if a network associated to table data", gravity=5.0)
	public boolean selectedOnly = false;
	
	public ListSingleSelection<CyCyniMetric> measures;
	@Tunable(description="Metric", gravity=6.0)
	public ListSingleSelection<CyCyniMetric> getMeasures()
	{
		return measures;
	}
	public void setMeasures(ListSingleSelection<CyCyniMetric> mes)
	{
		measures = mes;
	}
	
	public ListMultipleSelection<String> attributeList;
	@Tunable(description="Data Attributes", groups="Sources for Network Inference",listenForChange={"Measures"}, gravity=7.0)
	public ListMultipleSelection<String> getAttributeList()
	{
		List<String>  tagList ;
		if(measures.getPossibleValues().size()==0)
		{
			attributeList = new  ListMultipleSelection<String>("No sources available");
		}
		else
		{
			tagList = measures.getSelectedValue().getTagsList();
			if(tagList.contains(CyniMetricTags.INPUT_NUMBERS.toString())  &&  !currentType.matches(CyniMetricTags.INPUT_NUMBERS.toString()))
			{
				attributes = getAllAttributesNumbers(selectedTable);
				attributeList = new  ListMultipleSelection<String>(attributes);
				currentType = CyniMetricTags.INPUT_NUMBERS.toString();
			}
			else
			{
				if(tagList.contains(CyniMetricTags.INPUT_STRINGS.toString())  &&  !currentType.matches(CyniMetricTags.INPUT_STRINGS.toString()))
				{
					attributes = getAllAttributesStrings(selectedTable);
					attributeList = new  ListMultipleSelection<String>(attributes);
					List<String> temp = new ArrayList<String>( attributes);
					temp.remove(selectedTable.getPrimaryKey().getName());
					if(!temp.isEmpty())
						attributeList.setSelectedValues(temp);
					currentType =  CyniMetricTags.INPUT_STRINGS.toString();
				}
				else
				{
					if(currentType.isEmpty())
						attributeList = new  ListMultipleSelection<String>("No sources available");
				}
			}
		}
		
		return attributeList;
	}
	public void setAttributeList(ListMultipleSelection<String> input)
	{
		attributeList = input;
	}
	
	private List<String> attributes;
	private CyTable selectedTable;
	private String currentType ;

	public K2InductionContext(boolean supportsSelectedOnly, CyTable table,  ArrayList<CyCyniMetric> metrics, CyCyniMetricsManager metricsManager) {
		super(supportsSelectedOnly);
		selectedTable = table;
		currentType = "";
		ArrayList<String> tempList = new ArrayList<String>(getAllAttributesNumbers(selectedTable));
		tempList.addAll(getAllAttributesStrings(selectedTable));
		
		attributes = getAllAttributesStrings(selectedTable);
		if(attributes.size() > 0)
		{
			attributeList = new  ListMultipleSelection<String>(attributes);
			List<String> temp = new ArrayList<String>( attributes);
			temp.remove(table.getPrimaryKey().getName());
			if(!temp.isEmpty())
				attributeList.setSelectedValues(temp);
		}
		else
		{
			attributeList = new  ListMultipleSelection<String>("No sources available");
		}
		
		if(metrics.size() > 0)
		{
			measures = new  ListSingleSelection<CyCyniMetric>(metrics);
			measures.setSelectedValue(metricsManager.getDefaultCyniMetric());
		}
		else
		{
			measures = new  ListSingleSelection<CyCyniMetric>();
		}
		selectedColumn = new  ListSingleSelection<String>(tempList);
		
	}
	
	@Override
	public ValidationState getValidationState(final Appendable errMsg) {
		setSelectedOnly(selectedOnly);
		if (maxNumParents > 0 && !attributeList.getPossibleValues().get(0).matches("No sources available") && measures.getPossibleValues().size()>0 && attributeList.getSelectedValues().size() >0)
			return ValidationState.OK;
		else {
			try {
				if (maxNumParents <= 0)
					errMsg.append("Number of parents needs to be greater than 0!!!!\n");
				if(measures.getPossibleValues().size()==0)
					errMsg.append("There are no available metrics for this algorithm!\n");
				if(attributeList.getSelectedValues().size() == 0)
					errMsg.append("There are no source attributes selected to apply the Inference Algorithm. Please, select them.\n");
				else if(attributeList.getSelectedValues().get(0).matches("No sources available"))
					errMsg.append("There are no source attributes available to apply the Inference Algorithm.\n");
			} catch (IOException e) {
				e.printStackTrace();
				return ValidationState.INVALID;
			}
			return ValidationState.INVALID;
		}
	}
}
