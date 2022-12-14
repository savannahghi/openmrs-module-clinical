package org.openmrs.module.patientdashboardapp.fragment.controller;


import org.apache.commons.lang.StringEscapeUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.ConceptSet;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.InventoryCommonService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.InventoryDrug;
import org.openmrs.module.hospitalcore.model.InventoryDrugFormulation;
import org.openmrs.module.hospitalcore.model.Option;
import org.openmrs.module.hospitalcore.model.Referral;
import org.openmrs.module.hospitalcore.model.ReferralReasons;
import org.openmrs.module.patientdashboardapp.model.Note;
import org.openmrs.module.patientdashboardapp.model.Outcome;
import org.openmrs.module.patientdashboardapp.model.Procedure;
import org.openmrs.module.patientdashboardapp.model.Qualifier;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class ClinicalNotesFragmentController {

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

        Concept conceptFamilyHistory = Context.getConceptService().getConceptByUuid("09e2cfb7-d6cb-4275-a102-7c8b5ba52a03");
        Collection<ConceptAnswer> familyHistoryAnswers = conceptFamilyHistory.getAnswers();
        Concept conceptFamilyMember = Context.getConceptService().getConceptByUuid("1560AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> familyMemberAnswers = conceptFamilyMember.getAnswers();
        Concept conceptCurrentlyBreastFeeding = Context.getConceptService().getConceptByUuid("5632AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> currentlyBreastFeedingAnswers = conceptCurrentlyBreastFeeding.getAnswers();
        Concept conceptCurrentlyContraceptiveUse = Context.getConceptService().getConceptByUuid("1386AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> currentContraceptiveUseAnswers = conceptCurrentlyContraceptiveUse.getAnswers();
        Concept conceptCervicalCancerScreening = Context.getConceptService().getConceptByUuid("06398e78-0d3e-43d5-8017-f2fc3865e2e0");
        Collection<ConceptAnswer> cervicalCancerScreeningAnswers = conceptCervicalCancerScreening.getAnswers();
        Concept conceptColorectalCancerScreening = Context.getConceptService().getConceptByUuid("83e84962-68d2-4605-968d-0047e80b107e");
        Collection<ConceptAnswer> colorectalCancerScreeningAnswers = conceptColorectalCancerScreening.getAnswers();
        Concept conceptProstrateCancerScreening = Context.getConceptService().getConceptByUuid("ed6b0f9e-e966-4395-a727-cf1097b815c6");
        Concept conceptBreastCancerScreening = Context.getConceptService().getConceptByUuid("57053d0a-8372-4d3a-9103-1c61ec751f05");
        Collection<ConceptAnswer> breastCancerScreeningAnswers = conceptBreastCancerScreening.getAnswers();
        Collection<ConceptAnswer> prostrateCancerScreeningAnswers = conceptProstrateCancerScreening.getAnswers();
        Concept conceptRetinoblastomaSigns = Context.getConceptService().getConceptByUuid("f527f815-987c-4aaf-8a33-2ea1b36b338c");
        Collection<ConceptAnswer> retinoblastomaSigns = conceptRetinoblastomaSigns.getAnswers();
        Concept conceptCigaretteUsage = Context.getConceptService().getConceptByUuid("9e0aa689-7ab3-471e-b393-a7f321458b5c");
        Collection<ConceptAnswer> cigaretteUsage = conceptCigaretteUsage.getAnswers();
        Concept conceptTobaccoUsage = Context.getConceptService().getConceptByUuid("163731AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> tobaccoUsage = conceptTobaccoUsage.getAnswers();
        Concept conceptAlcoholUsage = Context.getConceptService().getConceptByUuid("159449AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> alcoholUsage = conceptAlcoholUsage.getAnswers();
        Concept conceptPhysicalActivity = Context.getConceptService().getConceptByUuid("159468AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> physicalActivity = conceptPhysicalActivity.getAnswers();
        Concept conceptPreviousRadioTherapyExposure = Context.getConceptService().getConceptByUuid("5e95e6ff-8ef4-46b1-80d3-f8389514c93a");
        Collection<ConceptAnswer> radiotherapyExposure= conceptPreviousRadioTherapyExposure.getAnswers();
        Concept conceptPalpablility = Context.getConceptService().getConceptByUuid("81365bc6-536b-4486-bcf5-a71af5c7c2b5");
        Collection<ConceptAnswer> palpability = conceptPalpablility.getAnswers();
        Concept conceptSubmandibularExamination = Context.getConceptService().getConceptByUuid("403a5548-2d26-45b1-b3c1-3f6b0b093ed2");
        Collection<ConceptAnswer> submandibularExamination = conceptSubmandibularExamination.getAnswers();
        Concept conceptSupraciavicularExamination = Context.getConceptService().getConceptByUuid("1315b836-a3c9-403c-9371-6ab85eb5315b");
        Collection<ConceptAnswer> supraciavicularExamination = conceptSupraciavicularExamination.getAnswers();
        Concept conceptCervicalExamination = Context.getConceptService().getConceptByUuid("3e0ac5b1-4cbe-4f5a-ac7d-3e30a55dc4fa");
        Collection<ConceptAnswer> cervicalExamination = conceptCervicalExamination.getAnswers();
        Concept conceptAxillaryExamination = Context.getConceptService().getConceptByUuid("b46aa003-ab6a-4465-b911-9e92e4a5a54f");
        Collection<ConceptAnswer> axillaryExamination = conceptAxillaryExamination.getAnswers();
        Concept conceptInguinalExamination = Context.getConceptService().getConceptByUuid("2d98fb30-18c2-4b42-90db-527ac64e01be");
        Collection<ConceptAnswer> inguinalExamination = conceptInguinalExamination.getAnswers();
        Concept conceptGeneralizedLymadenopathyExamination = Context.getConceptService().getConceptByUuid("7dfa3630-a420-4704-aae9-be3196f803ad");
        Collection<ConceptAnswer> generalizedLymadenopathyExamination = conceptGeneralizedLymadenopathyExamination.getAnswers();
        Concept conceptOtherLymphNodeExamination = Context.getConceptService().getConceptByUuid("c725329d-0ebd-42ca-9f19-6b407e551aef");
        Collection<ConceptAnswer> otherLymphNodeExamination = conceptOtherLymphNodeExamination.getAnswers();
        

        model.addAttribute("familyHistoryAnswers", familyHistoryAnswers);
        model.addAttribute("familyMemberAnswers", familyMemberAnswers);
        model.addAttribute("currentlyBreastFeedingAnswers", currentlyBreastFeedingAnswers);
        model.addAttribute("currentContraceptiveUseAnswers", currentContraceptiveUseAnswers);
        model.addAttribute("cervicalCancerScreeningAnswers", cervicalCancerScreeningAnswers);
        model.addAttribute("colorectalCancerScreeningAnswers", colorectalCancerScreeningAnswers);
        model.addAttribute("prostrateCancerScreeningAnswers", prostrateCancerScreeningAnswers);
        model.addAttribute("retinoblastomaSigns", retinoblastomaSigns);
        model.addAttribute("cigaretteUsage", cigaretteUsage);
        model.addAttribute("tobaccoUsage", tobaccoUsage);
        model.addAttribute("alcoholUsage", alcoholUsage);
        model.addAttribute("physicalActivity", physicalActivity);
        model.addAttribute("breastCancerScreeningAnswers", breastCancerScreeningAnswers);
        model.addAttribute("radiotherapyExposure", radiotherapyExposure);
        model.addAttribute("palpability", palpability);
        model.addAttribute("submandibularExamination", submandibularExamination);
        model.addAttribute("supraciavicularExamination", supraciavicularExamination);
        model.addAttribute("cervicalExamination", cervicalExamination);
        model.addAttribute("axillaryExamination", axillaryExamination);
        model.addAttribute("inguinalExamination", inguinalExamination);
        model.addAttribute("generalizedLymadenopathyExamination", generalizedLymadenopathyExamination);
        model.addAttribute("otherLymphNodeExamination", otherLymphNodeExamination);
        model.addAttribute("cancerTypes", this.getDiagnosis("cancer", ui));
		model.addAttribute("outcomeOptions", SimpleObject.fromCollection(Outcome.getAvailableOutcomes(), ui, "label", "id"));
		model.addAttribute("listOfWards", SimpleObject.fromCollection(Outcome.getInpatientWards(), ui, "label", "id"));
		model.addAttribute("internalReferralSources", SimpleObject.fromCollection(Referral.getInternalReferralOptions(), ui, "label", "id"));
        model.addAttribute("externalReferralSources", SimpleObject.fromCollection(Referral.getExternalReferralOptions(), ui, "label", "id"));
		model.addAttribute("referralReasonsSources", SimpleObject.fromCollection(ReferralReasons.getReferralReasonsOptions(), ui, "label", "id"));
		Note note = new Note(patientId, queueId, opdId, opdLogId);
		model.addAttribute("note", StringEscapeUtils.escapeJavaScript(SimpleObject.fromObject(note, ui, "signs.id", "signs.label", "diagnoses.id", "diagnoses.label",
						"investigations", "procedures", "patientId", "queueId","specify",
						"opdId", "opdLogId", "admitted","facility", "onSetDate", "illnessHistory","referralComments","physicalExamination", "otherInstructions").toJson()));
	}

    public List<SimpleObject> getQualifiers(@RequestParam("signId") Integer signId, UiUtils ui) {
    	Concept signConcept = Context.getConceptService().getConcept(signId);
    	List<Qualifier> qualifiers = new ArrayList<Qualifier>();
    	for (ConceptAnswer conceptAnswer : signConcept.getAnswers()) {
    		qualifiers.add(new Qualifier(conceptAnswer.getAnswerConcept()));
    	}
    	return SimpleObject.fromCollection(qualifiers, ui, "id", "label", "uuid", "options.id", "options.label", "options.uuid");
    }

    public List<SimpleObject> getSymptoms(@RequestParam(value="q") String name,UiUtils ui)
    {
        List<Concept> symptoms = Context.getService(PatientDashboardService.class).searchSymptom(name);

        List<SimpleObject> symptomsList = SimpleObject.fromCollection(symptoms, ui, "id", "name", "uuid");
        return symptomsList;
    }

    public static String PROPERTY_DRUGUNIT = "patientdashboard.dosingUnitConceptId";

    public List<SimpleObject> getDiagnosis(@RequestParam(value="q") String name,UiUtils ui)
    {
        List<Concept> diagnosis = Context.getService(PatientDashboardService.class).searchDiagnosis(name);

        List<SimpleObject> diagnosisList = SimpleObject.fromCollection(diagnosis, ui, "id", "name", "uuid");
        return diagnosisList;
    }
    public List<SimpleObject> getProcedures(@RequestParam(value="q") String name,UiUtils ui)
    {
        List<Concept> procedures = Context.getService(PatientDashboardService.class).searchProcedure(name);
        List<Procedure> proceduresPriority = new ArrayList<Procedure>();
        for(Concept myConcept: procedures){
            proceduresPriority.add(new Procedure(myConcept));
        }

        List<SimpleObject> proceduresList = SimpleObject.fromCollection(proceduresPriority, ui, "id", "label", "schedulable", "uuid");
        return proceduresList;
    }

    public List<SimpleObject> getInvestigations(@RequestParam(value="q") String name,UiUtils ui)
    {
        BillingService investigations = Context.getService(BillingService.class);
        List<BillableService> investigation = investigations.searchService(name);
        List<SimpleObject> investigationsList = SimpleObject.fromCollection(investigation, ui, "conceptId", "name");
        return investigationsList;
    }
    public List<SimpleObject> getDrugs(@RequestParam(value="q") String name,UiUtils ui)
    {
        List<InventoryDrug> drugs = Context.getService(PatientDashboardService.class).findDrug(name);
        List<SimpleObject> drugList = SimpleObject.fromCollection(drugs, ui, "id", "name");
        return drugList;
    }
    public List<SimpleObject> getFormulationByDrugName(@RequestParam(value="drugName") String drugName,UiUtils ui)
    {

        InventoryCommonService inventoryCommonService = (InventoryCommonService) Context.getService(InventoryCommonService.class);
        InventoryDrug drug = inventoryCommonService.getDrugByName(drugName);

        List<SimpleObject> formulationsList = null;

        if(drug != null){
            List<InventoryDrugFormulation> formulations = new ArrayList<InventoryDrugFormulation>(drug.getFormulations());
            formulationsList = SimpleObject.fromCollection(formulations, ui, "id", "name","dozage");
        }

        return formulationsList;
    }

    public List<SimpleObject> getDrugRoutes(UiUtils ui)
    {
        InventoryCommonService inventoryCommonService = Context.getService(InventoryCommonService.class);
        List<Concept> drugRoute = inventoryCommonService.getDrugRoute();
        List<SimpleObject> routesList = new ArrayList<SimpleObject>();

        if(drugRoute != null && drugRoute.size() > 0 ){
            routesList = SimpleObject.fromCollection(drugRoute, ui, "id", "name", "uuid");
        }

        return routesList;
    }

    public List<SimpleObject> getFrequencies(UiUtils uiUtils){
        InventoryCommonService inventoryCommonService = Context
                .getService(InventoryCommonService.class);
        List<Concept> drugFrequencyConcept = inventoryCommonService
                .getDrugFrequency();
        if(drugFrequencyConcept != null){
            List<SimpleObject> mydrugFrequencyObj = SimpleObject.fromCollection(drugFrequencyConcept,uiUtils, "id", "name", "uuid");
            return mydrugFrequencyObj;
        }
        else{
            return null;
        }
    }

    public List<SimpleObject> getDrugUnit(UiUtils uiUtils){
        Concept drugUnit = Context.getConceptService().getConcept(Context.getAdministrationService().getGlobalProperty(PROPERTY_DRUGUNIT));
        Collection<ConceptSet> unit = drugUnit.getConceptSets();
        List<Option> drugUnitOptions = new ArrayList<Option>();
        for (ConceptSet conceptSet: unit) {
            drugUnitOptions.add(new Option(conceptSet.getConcept()));
        }
        return SimpleObject.fromCollection(drugUnitOptions,uiUtils,"id","label", "uuid") ;
    }
}
