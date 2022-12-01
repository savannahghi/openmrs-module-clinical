package org.openmrs.module.patientdashboardapp.fragment.controller;


import org.apache.commons.lang.StringEscapeUtils;
import org.openmrs.module.patientdashboardapp.model.Note;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
/**
 *
 */
public class ConsentFormsFragmentController {

	public void controller(FragmentConfiguration config, FragmentModel model,
			UiUtils ui) {
		config.require("patientId");
		config.require("opdId");

		Integer patientId = Integer
				.parseInt(config.get("patientId").toString());
		Integer opdId = Integer.parseInt(config.get("opdId").toString());
		Integer queueId = null;
		if (config.containsKey("queueId") && config.get("queueId") != null) {
			queueId = Integer.parseInt(config.get("queueId").toString());
		}
		Integer opdLogId = null;
		if (config.containsKey("opdLogId") && config.get("opdLogId") != null) {
			opdLogId = Integer.parseInt(config.get("opdLogId").toString());
		}

       
        
		Note note = new Note(patientId, queueId, opdId, opdLogId);
		model.addAttribute("notek", StringEscapeUtils.escapeJavaScript(SimpleObject.fromObject(note, ui, "signs.id", "signs.label", "diagnoses.id", "diagnoses.label",
						"investigations", "procedures", "patientId", "queueId","specify",
						"opdId", "opdLogId", "admitted","facility", "onSetDate", "illnessHistory","referralComments","physicalExamination", "otherInstructions").toJson()));
	}
    
}
