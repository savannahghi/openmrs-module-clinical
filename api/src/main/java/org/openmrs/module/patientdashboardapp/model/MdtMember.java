package org.openmrs.module.patientdashboardapp.model;

import org.openmrs.*;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.LabService;
import org.openmrs.module.hospitalcore.RadiologyCoreService;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;

import java.util.*;

public class MdtMember {


	public MdtMember(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public MdtMember(){

	}

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

	private Integer id;
	private String label;
}
