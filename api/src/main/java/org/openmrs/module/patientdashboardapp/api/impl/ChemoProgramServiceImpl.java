package org.openmrs.module.patientdashboardapp.api.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.patientdashboardapp.PatientDashboardAppConstants;
import org.openmrs.module.patientdashboardapp.api.ChemoProgramService;
import org.openmrs.module.patientdashboardapp.api.db.TriageDAO;
import org.openmrs.ui.framework.SimpleObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChemoProgramServiceImpl extends BaseOpenmrsService implements ChemoProgramService {
    protected final Log log = LogFactory.getLog(getClass());
    DateFormat ymdDf = new SimpleDateFormat("yyyy-MM-dd");
    private TriageDAO triageDAO;

    private static final int MAX_ANC_DURATION = 9;

    private static final int MAX_PNC_DURATION = 9;

    private static final int MAX_CWC_DURATION = 9;
    @Override
    public boolean enrolledInProgram(Patient patient, String program) {
        Program patientProgram = null;

        if(program.equals("Chemotherapy")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.ANC_PROGRAM);
        }
        if(program.equals("Radiotherapy")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.PNC_PROGRAM);
        }
        if(program.equals("Procedure/Surgery")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.CWC_PROGRAM);
        }
        return patientEnrolledInProgram(patient,patientProgram);
    }

    public boolean patientEnrolledInProgram(Patient patient, Program program) {
        Calendar minEnrollmentDate = Calendar.getInstance();
        Calendar minCompletionDate = Calendar.getInstance();

        minCompletionDate.add(Calendar.DAY_OF_MONTH, 1);

        if (program.getName().toLowerCase().contains("antenatal") || program.getName().toLowerCase().contains("anc")) {
            minEnrollmentDate.add(Calendar.MONTH, -MAX_ANC_DURATION);
        } else if (program.getName().toLowerCase().contains("postnatal") || program.getName().toLowerCase().contains("pnc")) {
            minEnrollmentDate.add(Calendar.MONTH, -MAX_PNC_DURATION);
        } else {
            minEnrollmentDate.add(Calendar.YEAR, -MAX_CWC_DURATION);
        }

        List<PatientProgram> ancPatientPrograms = Context.getProgramWorkflowService().getPatientPrograms(patient, program,
                minEnrollmentDate.getTime(), null, minCompletionDate.getTime(), null, false);
        if (ancPatientPrograms.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public SimpleObject enrollInProgram(Patient patient, Date dateEnrolled, String program) {
        Program patientProgram = null;

        if(program.equals("Chemotherapy")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.ANC_PROGRAM);
        }
        if(program.equals("Radiotherapy")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.PNC_PROGRAM);
        }
        if(program.equals("Procedure/Surgery")){
            patientProgram = Context.getProgramWorkflowService().getProgramByUuid(PatientDashboardAppConstants.CWC_PROGRAM);
        }
        if (!enrolledInProgram(patient,program)) {
            PatientProgram pProgram = new PatientProgram();
            pProgram.setPatient(patient);
            pProgram.setProgram(patientProgram);
            pProgram.setDateEnrolled(dateEnrolled);
            //TODO Add creator
            Context.getProgramWorkflowService().savePatientProgram(pProgram);
            return SimpleObject.create("status", "success", "message", "Patient enrolled in  successfully");
        } else {
            return SimpleObject.create("status", "error", "message", "Patient already enrolled in  program");
        }
    }

    public TriageDAO getTriageDAO() {
        return triageDAO;
    }

    public void setTriageDAO(TriageDAO triageDAO) {
        this.triageDAO = triageDAO;
    }
}
