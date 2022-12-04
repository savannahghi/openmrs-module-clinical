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

        Concept conceptFamilyHistory = Context.getConceptService().getConceptByUuid("b576d9e5-391a-4663-a5e2-0f6e4a314af2");
        Collection<ConceptAnswer> familyHistoryAnswers = conceptFamilyHistory.getAnswers();
        Concept conceptFamilyMember = Context.getConceptService().getConceptByUuid("1560AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> familyMemberAnswers = conceptFamilyMember.getAnswers();
        Concept conceptCurrentlyBreastFeeding = Context.getConceptService().getConceptByUuid("5632AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> currentlyBreastFeedingAnswers = conceptCurrentlyBreastFeeding.getAnswers();
        Concept conceptCurrentlyContraceptiveUse = Context.getConceptService().getConceptByUuid("1386AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> currentContraceptiveUseAnswers = conceptCurrentlyContraceptiveUse.getAnswers();
        Concept conceptCervicalCancerScreening = Context.getConceptService().getConceptByUuid("06398e78-0d3e-43d5-8017-f2fc3865e2e0");
        Collection<ConceptAnswer> cervicalCancerScreeningAnswers = conceptCervicalCancerScreening.getAnswers();
        Concept conceptCervicalCancerScreeningTypes = Context.getConceptService().getConceptByUuid("163589AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> cervicalCancerScreeningTypesAnswers = conceptCervicalCancerScreeningTypes.getAnswers();
        Concept conceptBreastCancerScreening = Context.getConceptService().getConceptByUuid("e277ff6a-c014-4ce2-8c64-16aa0af3376f");
        Collection<ConceptAnswer> breastCancerScreeningAnswers = conceptBreastCancerScreening.getAnswers();
        Concept conceptBreastCancerScreeningTypes = Context.getConceptService().getConceptByUuid("5b2c1d27-59df-4361-a16c-ed60e4a2125c");
        Collection<ConceptAnswer> breastCancerScreeningTypesAnswers = conceptBreastCancerScreeningTypes.getAnswers();
        Concept conceptColorectalCancerScreening = Context.getConceptService().getConceptByUuid("d5226e81-34b3-4216-b8dd-624834005c87");
        Collection<ConceptAnswer> colorectalCancerScreeningAnswers = conceptColorectalCancerScreening.getAnswers();
        Concept conceptColorectalCancerScreeningTypes = Context.getConceptService().getConceptByUuid("ae19ee8c-d8fc-4d94-84a1-32f84a7e0fff");
        Collection<ConceptAnswer> colorectalCancerScreeningTypesAnswers = conceptColorectalCancerScreeningTypes.getAnswers();
        Concept conceptProstrateCancerScreening = Context.getConceptService().getConceptByUuid("6d40cfcc-be08-4f5a-a657-1ffeaa1a6e3c");
        Collection<ConceptAnswer> prostrateCancerScreeningAnswers = conceptProstrateCancerScreening.getAnswers();
        Concept conceptProstrateCancerScreeningTypes = Context.getConceptService().getConceptByUuid("c805a0f3-5244-4f20-ae56-57b605f7aeeb");
        Collection<ConceptAnswer> prostrateCancerScreeningTypesAnswers = conceptProstrateCancerScreeningTypes.getAnswers();
        Concept conceptRetinoblastomaSigns = Context.getConceptService().getConceptByUuid("8337f818-79eb-4360-8b3e-8f90927ddbd3");
        Collection<ConceptAnswer> retinoblastomaSigns = conceptRetinoblastomaSigns.getAnswers();
        Concept conceptCigaretteUsage = Context.getConceptService().getConceptByUuid("548e14dc-2128-434e-b365-becd398b86e4");
        Collection<ConceptAnswer> cigaretteUsage = conceptCigaretteUsage.getAnswers();
        Concept conceptTobaccoUsage = Context.getConceptService().getConceptByUuid("163731AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> tobaccoUsage = conceptTobaccoUsage.getAnswers();
        Concept conceptAlcoholUsage = Context.getConceptService().getConceptByUuid("159449AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> alcoholUsage = conceptAlcoholUsage.getAnswers();
        Concept conceptPhysicalActivity = Context.getConceptService().getConceptByUuid("159468AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Collection<ConceptAnswer> physicalActivity = conceptPhysicalActivity.getAnswers();
        Concept conceptPreviousRadioTherapyExposure = Context.getConceptService().getConceptByUuid("0693aab4-9756-454e-b693-6d4454c55043");
        Collection<ConceptAnswer> radiotherapyExposure= conceptPreviousRadioTherapyExposure.getAnswers();
        

        model.addAttribute("familyHistoryAnswers", familyHistoryAnswers);
        model.addAttribute("familyMemberAnswers", familyMemberAnswers);
        model.addAttribute("currentlyBreastFeedingAnswers", currentlyBreastFeedingAnswers);
        model.addAttribute("currentContraceptiveUseAnswers", currentContraceptiveUseAnswers);
        model.addAttribute("cervicalCancerScreeningAnswers", cervicalCancerScreeningAnswers);
        model.addAttribute("cervicalCancerScreeningTypesAnswers", cervicalCancerScreeningTypesAnswers);
        model.addAttribute("breastCancerScreeningAnswers", breastCancerScreeningAnswers);
        model.addAttribute("breastCancerScreeningTypesAnswers", breastCancerScreeningTypesAnswers);
        model.addAttribute("colorectalCancerScreeningAnswers", colorectalCancerScreeningAnswers);
        model.addAttribute("colorectalCancerScreeningTypesAnswers", colorectalCancerScreeningTypesAnswers);
        model.addAttribute("prostrateCancerScreeningAnswers", prostrateCancerScreeningAnswers);
        model.addAttribute("prostrateCancerScreeningTypesAnswers", prostrateCancerScreeningTypesAnswers);
        model.addAttribute("retinoblastomaSigns", retinoblastomaSigns);
        model.addAttribute("cigaretteUsage", cigaretteUsage);
        model.addAttribute("tobaccoUsage", tobaccoUsage);
        model.addAttribute("alcoholUsage", alcoholUsage);
        model.addAttribute("physicalActivity", physicalActivity);
        model.addAttribute("radiotherapyExposure", radiotherapyExposure);
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
