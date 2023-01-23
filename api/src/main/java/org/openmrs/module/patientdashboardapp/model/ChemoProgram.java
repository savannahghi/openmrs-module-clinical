package org.openmrs.module.patientdashboardapp.model;

import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.api.context.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChemoProgram {
	public ChemoProgram(){

	}

	public ChemoProgram(Integer id, String label) {
		this(id, label, null);
	}

	public ChemoProgram(Integer id, String label, String uuid) {
		this.id = id;
		this.label = label;
	}


	private Integer id;
	private String label;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
