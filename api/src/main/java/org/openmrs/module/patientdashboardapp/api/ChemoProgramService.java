package org.openmrs.module.patientdashboardapp.api;

import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.patientdashboardapp.model.ChemoProgram;
import org.openmrs.ui.framework.SimpleObject;

import java.util.Date;

public interface ChemoProgramService extends OpenmrsService {
    boolean enrolledInProgram(Patient patient, ChemoProgram program);
    SimpleObject enrollInProgram(Patient patient, Date dateEnrolled, ChemoProgram program);
}
