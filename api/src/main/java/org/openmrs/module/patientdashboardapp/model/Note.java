package org.openmrs.module.patientdashboardapp.model;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.LocationAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.TestOrder;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.VisitService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ehrconfigs.metadata.EhrCommonMetadata;
import org.openmrs.module.ehrconfigs.utils.EhrConfigsUtils;
import org.openmrs.module.hospitalcore.BillingConstants;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.IpdService;
import org.openmrs.module.hospitalcore.LabService;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.RadiologyCoreService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.hospitalcore.model.DepartmentConcept;
import org.openmrs.module.hospitalcore.model.IpdPatientAdmissionLog;
import org.openmrs.module.hospitalcore.model.Lab;
import org.openmrs.module.hospitalcore.model.OpdPatientQueue;
import org.openmrs.module.hospitalcore.model.OpdPatientQueueLog;
import org.openmrs.module.hospitalcore.model.OpdTestOrder;
import org.openmrs.module.hospitalcore.model.Option;
import org.openmrs.module.hospitalcore.model.RadiologyDepartment;
import org.openmrs.module.hospitalcore.model.Referral;
import org.openmrs.module.hospitalcore.model.ReferralReasons;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.patientdashboardapp.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Note {

	private static Logger logger = LoggerFactory.getLogger(Note.class);

	private static Set<Integer> collectionOfLabConceptIds = new HashSet<Integer>();
	private static Set<Integer> collectionOfRadiologyConceptIds = new HashSet<Integer>();

	private static final String YES = "1065";
	private static final int CHILDAGESEPARATOR = 5;

	static {
		List<Lab> labs = Context.getService(LabService.class).getAllLab();
		for (Lab lab : labs) {
			for (Concept labInvestigationCategoryConcept : lab.getInvestigationsToDisplay()) {
				for (ConceptAnswer labInvestigationConcept : labInvestigationCategoryConcept.getAnswers()) {
					collectionOfLabConceptIds.add(labInvestigationConcept.getAnswerConcept().getConceptId());
				}
			}
		}
		List<RadiologyDepartment> radiologyDepts = Context.getService(RadiologyCoreService.class).getAllRadiologyDepartments();
		for (RadiologyDepartment department : radiologyDepts) {
			for (Concept radiologyInvestigationCategoryConcept : department.getInvestigations()) {
				for (ConceptAnswer radiologyInvestigationConcept : radiologyInvestigationCategoryConcept.getAnswers()) {
					collectionOfRadiologyConceptIds.add(radiologyInvestigationConcept.getAnswerConcept().getConceptId());
				}
			}
		}
	}



	public Note () {
	}

	public Note(int patientId, Integer queueId, Integer opdId, Integer opdLogId) {
		super();
		this.patientId = patientId;
		this.queueId = queueId;
		this.opdId = opdId;
		this.admitted = Context.getService(IpdService.class).getAdmittedByPatientId(patientId) != null;
		this.opdLogId = opdLogId;
		this.diagnoses = Diagnosis.getPreviousDiagnoses(patientId);
		this.signs = Sign.getPreviousSigns(patientId);
		this.physicalExamination = getPreviousPhysicalExamination(patientId);
		this.illnessHistory = getPreviousIllnessHistory(patientId);
		this.onSetDate = getPreviousDateOfOnSetOfIlliness(patientId);
	}

	private int patientId;
	private Integer queueId;
	private Integer opdId;
	private boolean admitted;
	private Integer opdLogId;
	private List<Sign> signs = new ArrayList<Sign>() ;
	private List<Diagnosis> diagnoses = new ArrayList<Diagnosis>();
	private List<Investigation> investigations = new ArrayList<Investigation>();
	private List<Procedure> procedures = new ArrayList<Procedure>();
	private List<Drug> drugs = new ArrayList<Drug>();
	private Option referredTo;
	private Option referralReasons;
	private Outcome outcome;
	private String illnessHistory;
	private String facility;
	private String referralComments;
    private String specify;
	private String otherInstructions;
	private String familyHistoryAnswer;
	private String currentlyBreastFeedingAnswer;
	private String currentContraceptiveUseAnswer;
	private String cervicalCancerScreeningAnswer;
	private String cervicalCancerScreeningType;
	private String cervicalCancerScreeningDate;
	private String breastCancerScreeningAnswer;
	private String breastCancerScreeningType;
	private String breastCancerScreeningDate;
	private String colorectalCancerScreeningAnswer;
	private String colorectalCancerScreeningType;
	private String colorectalCancerScreeningDate;
	private String prostrateCancerScreeningAnswer;
	private String prostrateCancerScreeningType;
	private String prostrateCancerScreeningDate;
	private String lastLmp;
	private String parity;
	private String retinoblastomaState;
	private String relationshipToPatient;
	private String ageAtDiagnosis;
	private String cancerType;
	private String physicalExamination;
	private String cigaretteUsageAnswer;
	private String cigarettesPerDay;
	private String yearsSmokedCigarette;
	private String tobaccoUsageAnswer;
	private String alcoholUsageAnswer;
	private String alcoholIntakeFrequency;
	private String physicalActivityAnswer;
	private String radiotherapyExposureAnswer;
	private String presentingComplains; 
	private String historyOfPresentIllness;
	private String pastMedicalSurgicalHistory;
	private String cns;
	private String cvs;
	private String rs;
	private String gus;
	private String mss;
	private String generalExamination;
	private String jaundiceExamination;
	private String anaemiaExamination;
	private String cyanosisExamination;
	private String clubbingExamination;
	private String oedemaExamination;
	private String dehydrationExamination;
	private String palpabilityAnswer;
	private String submandibularAnswer;
	private String submandibularComment;
	private String supraciavicularAnswer;
	private String supraciavicularComment;
	private String cervicalExaminationAnswer;
	private String cervicalExaminationComment;
	private String axillaryExaminationAnswer;
	private String axillaryExaminationComment;
	private String inguinalExaminationAnswer;
	private String inguinalExaminationComment;
	private String generalizedLymadenopathyExaminationAnswer;
	private String generalizedLymadenopathyExaminationComment;
	private String otherLymphNodeExaminationAnswer;
	private String otherLymphNodeExaminationComment;
	private String eyeExam; 
	private String neckExam; 
	private String mouthExam; 
	private String earExam; 
	private String noseExam;
	private String throatExam;
	private String rsInspection;
	private String rsPalpation;
	private String rsPercussion;
	private String rsAuscultation;
	private String breastExaminationComment;
	private String csInspection;
	private String csPalpation;
	private String csPercussion;
	private String csAuscultation;
	private String asInspection;
	private String asPalpation;
	private String asPercussion;
	private String asAuscultation;
	private String guInspection;
	private String guPalpation;
	private String skinInspection;
	private String nHigherFunctions; 
	private String nCranialNerves; 
	private String nHead; 
	private String nNeck; 
	private String nSensoryLevel; 
	private String nMusculoskeletal;


	public String getnHigherFunctions() {
		return nHigherFunctions;
	}

	public void setnHigherFunctions(String nHigherFunctions) {
		this.nHigherFunctions = nHigherFunctions;
	}

	public String getnCranialNerves() {
		return nCranialNerves;
	}

	public void setnCranialNerves(String nCranialNerves) {
		this.nCranialNerves = nCranialNerves;
	}

	public String getnHead() {
		return nHead;
	}

	public void setnHead(String nHead) {
		this.nHead = nHead;
	}

	public String getnNeck() {
		return nNeck;
	}

	public void setnNeck(String nNeck) {
		this.nNeck = nNeck;
	}

	public String getnSensoryLevel() {
		return nSensoryLevel;
	}

	public void setnSensoryLevel(String nSensoryLevel) {
		this.nSensoryLevel = nSensoryLevel;
	}

	public String getnMusculoskeletal() {
		return nMusculoskeletal;
	}

	public void setnMusculoskeletal(String nMusculoskeletal) {
		this.nMusculoskeletal = nMusculoskeletal;
	}

	public String getSkinInspection() {
		return skinInspection;
	}

	public void setSkinInspection(String skinInspection) {
		this.skinInspection = skinInspection;
	}

	public String getGuInspection() {
		return guInspection;
	}

	public void setGuInspection(String guInspection) {
		this.guInspection = guInspection;
	}

	public String getGuPalpation() {
		return guPalpation;
	}

	public void setGuPalpation(String guPalpation) {
		this.guPalpation = guPalpation;
	}

	public String getAsInspection() {
		return asInspection;
	}

	public void setAsInspection(String asInspection) {
		this.asInspection = asInspection;
	}

	public String getAsPalpation() {
		return asPalpation;
	}

	public void setAsPalpation(String asPalpation) {
		this.asPalpation = asPalpation;
	}

	public String getAsPercussion() {
		return asPercussion;
	}

	public void setAsPercussion(String asPercussion) {
		this.asPercussion = asPercussion;
	}

	public String getAsAuscultation() {
		return asAuscultation;
	}

	public void setAsAuscultation(String asAuscultation) {
		this.asAuscultation = asAuscultation;
	}

	public String getCsInspection() {
		return csInspection;
	}

	public void setCsInspection(String csInspection) {
		this.csInspection = csInspection;
	}

	public String getCsPalpation() {
		return csPalpation;
	}

	public void setCsPalpation(String csPalpation) {
		this.csPalpation = csPalpation;
	}

	public String getCsPercussion() {
		return csPercussion;
	}

	public void setCsPercussion(String csPercussion) {
		this.csPercussion = csPercussion;
	}

	public String getCsAuscultation() {
		return csAuscultation;
	}

	public void setCsAuscultation(String csAuscultation) {
		this.csAuscultation = csAuscultation;
	}

	public String getBreastExaminationComment() {
		return breastExaminationComment;
	}

	public void setBreastExaminationComment(String breastExaminationComment) {
		this.breastExaminationComment = breastExaminationComment;
	}

	public String getRsInspection() {
		return rsInspection;
	}

	public void setRsInspection(String rsInspection) {
		this.rsInspection = rsInspection;
	}

	public String getRsPalpation() {
		return rsPalpation;
	}

	public void setRsPalpation(String rsPalpation) {
		this.rsPalpation = rsPalpation;
	}

	public String getRsPercussion() {
		return rsPercussion;
	}

	public void setRsPercussion(String rsPercussion) {
		this.rsPercussion = rsPercussion;
	}

	public String getRsAuscultation() {
		return rsAuscultation;
	}

	public void setRsAuscultation(String rsAuscultation) {
		this.rsAuscultation = rsAuscultation;
	}

	public String getEyeExam() {
		return eyeExam;
	}

	public void setEyeExam(String eyeExam) {
		this.eyeExam = eyeExam;
	}

	public String getNeckExam() {
		return neckExam;
	}

	public void setNeckExam(String neckExam) {
		this.neckExam = neckExam;
	}

	public String getMouthExam() {
		return mouthExam;
	}

	public void setMouthExam(String mouthExam) {
		this.mouthExam = mouthExam;
	}

	public String getEarExam() {
		return earExam;
	}

	public void setEarExam(String earExam) {
		this.earExam = earExam;
	}

	public String getNoseExam() {
		return noseExam;
	}

	public void setNoseExam(String noseExam) {
		this.noseExam = noseExam;
	}

	public String getThroatExam() {
		return throatExam;
	}

	public void setThroatExam(String throatExam) {
		this.throatExam = throatExam;
	}

	public String getPalpabilityAnswer() {
		return palpabilityAnswer;
	}

	public void setPalpabilityAnswer(String palpabilityAnswer) {
		this.palpabilityAnswer = palpabilityAnswer;
	}

	public String getSubmandibularAnswer() {
		return submandibularAnswer;
	}

	public void setSubmandibularAnswer(String submandibularAnswer) {
		this.submandibularAnswer = submandibularAnswer;
	}

	public String getSubmandibularComment() {
		return submandibularComment;
	}

	public void setSubmandibularComment(String submandibularComment) {
		this.submandibularComment = submandibularComment;
	}

	public String getSupraciavicularAnswer() {
		return supraciavicularAnswer;
	}

	public void setSupraciavicularAnswer(String supraciavicularAnswer) {
		this.supraciavicularAnswer = supraciavicularAnswer;
	}

	public String getSupraciavicularComment() {
		return supraciavicularComment;
	}

	public void setSupraciavicularComment(String supraciavicularComment) {
		this.supraciavicularComment = supraciavicularComment;
	}

	public String getCervicalExaminationAnswer() {
		return cervicalExaminationAnswer;
	}

	public void setCervicalExaminationAnswer(String cervicalExaminationAnswer) {
		this.cervicalExaminationAnswer = cervicalExaminationAnswer;
	}

	public String getCervicalExaminationComment() {
		return cervicalExaminationComment;
	}

	public void setCervicalExaminationComment(String cervicalExaminationComment) {
		this.cervicalExaminationComment = cervicalExaminationComment;
	}

	public String getAxillaryExaminationAnswer() {
		return axillaryExaminationAnswer;
	}

	public void setAxillaryExaminationAnswer(String axillaryExaminationAnswer) {
		this.axillaryExaminationAnswer = axillaryExaminationAnswer;
	}

	public String getAxillaryExaminationComment() {
		return axillaryExaminationComment;
	}

	public void setAxillaryExaminationComment(String axillaryExaminationComment) {
		this.axillaryExaminationComment = axillaryExaminationComment;
	}

	public String getInguinalExaminationAnswer() {
		return inguinalExaminationAnswer;
	}

	public void setInguinalExaminationAnswer(String inguinalExaminationAnswer) {
		this.inguinalExaminationAnswer = inguinalExaminationAnswer;
	}

	public String getInguinalExaminationComment() {
		return inguinalExaminationComment;
	}

	public void setInguinalExaminationComment(String inguinalExaminationComment) {
		this.inguinalExaminationComment = inguinalExaminationComment;
	}

	public String getGeneralizedLymadenopathyExaminationAnswer() {
		return generalizedLymadenopathyExaminationAnswer;
	}

	public void setGeneralizedLymadenopathyExaminationAnswer(String generalizedLymadenopathyExaminationAnswer) {
		this.generalizedLymadenopathyExaminationAnswer = generalizedLymadenopathyExaminationAnswer;
	}

	public String getGeneralizedLymadenopathyExaminationComment() {
		return generalizedLymadenopathyExaminationComment;
	}

	public void setGeneralizedLymadenopathyExaminationComment(String generalizedLymadenopathyExaminationComment) {
		this.generalizedLymadenopathyExaminationComment = generalizedLymadenopathyExaminationComment;
	}

	public String getOtherLymphNodeExaminationAnswer() {
		return otherLymphNodeExaminationAnswer;
	}

	public void setOtherLymphNodeExaminationAnswer(String otherLymphNodeExaminationAnswer) {
		this.otherLymphNodeExaminationAnswer = otherLymphNodeExaminationAnswer;
	}

	public String getOtherLymphNodeExaminationComment() {
		return otherLymphNodeExaminationComment;
	}

	public void setOtherLymphNodeExaminationComment(String otherLymphNodeExaminationComment) {
		this.otherLymphNodeExaminationComment = otherLymphNodeExaminationComment;
	}

	public String getGeneralExamination() {
		return generalExamination;
	}

	public void setGeneralExamination(String generalExamination) {
		this.generalExamination = generalExamination;
	}

	public String getJaundiceExamination() {
		return jaundiceExamination;
	}

	public void setJaundiceExamination(String jaundiceExamination) {
		this.jaundiceExamination = jaundiceExamination;
	}

	public String getAnaemiaExamination() {
		return anaemiaExamination;
	}

	public void setAnaemiaExamination(String anaemiaExamination) {
		this.anaemiaExamination = anaemiaExamination;
	}

	public String getCyanosisExamination() {
		return cyanosisExamination;
	}

	public void setCyanosisExamination(String cyanosisExamination) {
		this.cyanosisExamination = cyanosisExamination;
	}

	public String getClubbingExamination() {
		return clubbingExamination;
	}

	public void setClubbingExamination(String clubbingExamination) {
		this.clubbingExamination = clubbingExamination;
	}

	public String getOedemaExamination() {
		return oedemaExamination;
	}

	public void setOedemaExamination(String oedemaExamination) {
		this.oedemaExamination = oedemaExamination;
	}

	public String getDehydrationExamination() {
		return dehydrationExamination;
	}

	public void setDehydrationExamination(String dehydrationExamination) {
		this.dehydrationExamination = dehydrationExamination;
	}

	public String getPresentingComplains() {
		return presentingComplains;
	}

	public void setPresentingComplains(String presentingComplains) {
		this.presentingComplains = presentingComplains;
	}

	public String getHistoryOfPresentIllness() {
		return historyOfPresentIllness;
	}

	public void setHistoryOfPresentIllness(String historyOfPresentIllness) {
		this.historyOfPresentIllness = historyOfPresentIllness;
	}

	public String getPastMedicalSurgicalHistory() {
		return pastMedicalSurgicalHistory;
	}

	public void setPastMedicalSurgicalHistory(String pastMedicalSurgicalHistory) {
		this.pastMedicalSurgicalHistory = pastMedicalSurgicalHistory;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	public String getCvs() {
		return cvs;
	}

	public void setCvs(String cvs) {
		this.cvs = cvs;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getGus() {
		return gus;
	}

	public void setGus(String gus) {
		this.gus = gus;
	}

	public String getMss() {
		return mss;
	}

	public void setMss(String mss) {
		this.mss = mss;
	}

	public String getCigarettesPerDay() {
		return cigarettesPerDay;
	}

	public void setCigarettesPerDay(String cigarettesPerDay) {
		this.cigarettesPerDay = cigarettesPerDay;
	}

	public String getYearsSmokedCigarette() {
		return yearsSmokedCigarette;
	}

	public void setYearsSmokedCigarette(String yearsSmokedCigarette) {
		this.yearsSmokedCigarette = yearsSmokedCigarette;
	}

	public String getTobaccoUsageAnswer() {
		return tobaccoUsageAnswer;
	}

	public void setTobaccoUsageAnswer(String tobaccoUsageAnswer) {
		this.tobaccoUsageAnswer = tobaccoUsageAnswer;
	}

	public String getAlcoholUsageAnswer() {
		return alcoholUsageAnswer;
	}

	public void setAlcoholUsageAnswer(String alcoholUsageAnswer) {
		this.alcoholUsageAnswer = alcoholUsageAnswer;
	}

	public String getAlcoholIntakeFrequency() {
		return alcoholIntakeFrequency;
	}

	public void setAlcoholIntakeFrequency(String alcoholIntakeFrequency) {
		this.alcoholIntakeFrequency = alcoholIntakeFrequency;
	}

	public String getPhysicalActivityAnswer() {
		return physicalActivityAnswer;
	}

	public void setPhysicalActivityAnswer(String physicalActivityAnswer) {
		this.physicalActivityAnswer = physicalActivityAnswer;
	}

	public String getRadiotherapyExposureAnswer() {
		return radiotherapyExposureAnswer;
	}

	public void setRadiotherapyExposureAnswer(String radiotherapyExposureAnswer) {
		this.radiotherapyExposureAnswer = radiotherapyExposureAnswer;
	}

	public String getCigaretteUsageAnswer() {
		return cigaretteUsageAnswer;
	}

	public void setCigaretteUsageAnswer(String cigaretteUsageAnswer) {
		this.cigaretteUsageAnswer = cigaretteUsageAnswer;
	}

	public String getOnSetDate() {
		return onSetDate;
	}

	public void setOnSetDate(String onSetDate) {
		this.onSetDate = onSetDate;
	}

	private String onSetDate;

	public static String PROPERTY_FACILITY = "161562AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"; //Name of where patient was referred to

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public Integer getOpdId() {
		return opdId;
	}

	public void setOpdId(Integer opdId) {
		this.opdId = opdId;
	}

	public boolean isAdmitted() {
		return admitted;
	}

	public void setAdmitted(boolean admitted) {
		this.admitted = admitted;
	}

	public Integer getOpdLogId() {
		return opdLogId;
	}

	public void setOpdLogId(Integer opdLogId) {
		this.opdLogId = opdLogId;
	}

	public List<Sign> getSigns() {
		return signs;
	}

	public void setSigns(List<Sign> symptoms) {
		this.signs = symptoms;
	}

	public List<Diagnosis> getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(List<Diagnosis> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public List<Investigation> getInvestigations() {
		return investigations;
	}

	public void setInvestigations(List<Investigation> investigations) {
		this.investigations = investigations;
	}

	public List<Procedure> getProcedures() {
		return procedures;
	}

	public void setProcedures(List<Procedure> procedures) {
		this.procedures = procedures;
	}

	public Option getReferredTo() {
		return referredTo;
	}

	public void setReferredTo(Option referredTo) {
		this.referredTo = referredTo;
	}

	public Option getReferralReasons() {
		return referralReasons;
	}

	public void setReferralReasons(Option referralReasons) {
		this.referralReasons = referralReasons;
	}

	public Outcome getOutcome() {
		return this.outcome;
	}

	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}

	public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}

	public String getIllnessHistory() {
		return illnessHistory;
	}

	public void setIllnessHistory(String illnessHistory) {
		this.illnessHistory = illnessHistory;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getSpecify() {
		return specify;
	}

	public void setSpecify(String specify) {
		this.specify = specify;
	}

	public String getReferralComments() {
		return referralComments;
	}

	public void setReferralComments(String referralComments) {
		this.referralComments = referralComments;
	}

	public String getOtherInstructions() {
		return otherInstructions;
	}

	public void setOtherInstructions(String otherInstructions) {
		this.otherInstructions = otherInstructions;
	}

	public String getFamilyHistoryAnswer() {
		return familyHistoryAnswer;
	}

	public void setFamilyHistoryAnswer(String familyHistoryAnswer) {
		this.familyHistoryAnswer = familyHistoryAnswer;
	}
	public String getCurrentlyBreastFeedingAnswer() {
		return currentlyBreastFeedingAnswer;
	}

	public void setCurrentlyBreastFeedingAnswer(String currentlyBreastFeedingAnswer) {
		this.currentlyBreastFeedingAnswer = currentlyBreastFeedingAnswer;
	}

	public String getcurrentContraceptiveUseAnswer() {
		return currentContraceptiveUseAnswer;
	}

	public void setcurrentContraceptiveUseAnswer(String currentContraceptiveUseAnswer) {
		this.currentContraceptiveUseAnswer= currentContraceptiveUseAnswer;
	}

	public String getCervicalCancerScreeningAnswer() {
		return cervicalCancerScreeningAnswer;
	}

	public void setCervicalCancerScreeningAnswer(String cervicalCancerScreeningAnswer) {
		this.cervicalCancerScreeningAnswer= cervicalCancerScreeningAnswer;
	}
	public String getBreastCancerScreeningAnswer() {
		return breastCancerScreeningAnswer;
	}

	public void setBreastCancerScreeningAnswer(String breastCancerScreeningAnswer) {
		this.breastCancerScreeningAnswer= breastCancerScreeningAnswer;
	}

	public String getColorectalCancerScreeningAnswer() {
		return colorectalCancerScreeningAnswer;
	}

	public void setColorectalCancerScreeningAnswer(String colorectalCancerScreeningAnswer) {
		this.colorectalCancerScreeningAnswer= colorectalCancerScreeningAnswer;
	}

	public String getRetinoblastomaState() {
		return retinoblastomaState;
	}

	public void setRetinoblastomaState(String retinoblastomaState) {
		this.retinoblastomaState= retinoblastomaState;
	}

	public String getProstrateCancerScreeningAnswer() {
		return prostrateCancerScreeningAnswer;
	}

	public void setProstrateCancerScreeningAnswer(String prostrateCancerScreeningAnswer) {
		this.prostrateCancerScreeningAnswer= prostrateCancerScreeningAnswer;
	}
	
	public String getCervicalCancerScreeningType() {
		return cervicalCancerScreeningType;
	}

	public void setCervicalCancerScreeningType(String cervicalCancerScreeningType) {
		this.cervicalCancerScreeningType= cervicalCancerScreeningType;
	}

	public String getBreastCancerScreeningType() {
		return breastCancerScreeningType;
	}

	public void setBreastCancerScreeningType(String breastCancerScreeningType) {
		this.breastCancerScreeningType= breastCancerScreeningType;
	}

	public String getColorectalCancerScreeningType() {
		return colorectalCancerScreeningType;
	}

	public void setColorectalCancerScreeningType(String colorectalCancerScreeningType) {
		this.colorectalCancerScreeningType= colorectalCancerScreeningType;
	}

	public String getProstrateCancerScreeningType() {
		return prostrateCancerScreeningType;
	}

	public void setProstrateCancerScreeningType(String prostrateCancerScreeningType) {
		this.prostrateCancerScreeningType= prostrateCancerScreeningType;
	}

	public String getCervicalCancerScreeningDate() {
		return cervicalCancerScreeningDate;
	}

	public void setCervicalCancerScreeningDate(String cervicalCancerScreeningDate) {
		this.cervicalCancerScreeningDate= cervicalCancerScreeningDate;
	}

	public String getBreastCancerScreeningDate() {
		return breastCancerScreeningDate;
	}

	public void setBreastCancerScreeningDate(String breastCancerScreeningDate) {
		this.breastCancerScreeningDate= breastCancerScreeningDate;
	}

	public String getColorectalCancerScreeningDate() {
		return colorectalCancerScreeningDate;
	}

	public void setColorectalCancerScreeningDate(String colorectalCancerScreeningDate) {
		this.colorectalCancerScreeningDate= colorectalCancerScreeningDate;
	}

	public String getProstrateCancerScreeningDate() {
		return prostrateCancerScreeningDate;
	}

	public void setProstrateCancerScreeningDate(String prostrateCancerScreeningDate) {
		this.prostrateCancerScreeningDate= prostrateCancerScreeningDate;
	}

	public String getRelationshipToPatient() {
		return relationshipToPatient;
	}

	public void setRelationshipToPatient(String relationshipToPatient) {
		this.relationshipToPatient = relationshipToPatient;
	}
	public String getAgeAtDiagnosis() {
		return ageAtDiagnosis;
	}

	public void setAgeAtDiagnosis(String ageAtDiagnosis) {
		this.ageAtDiagnosis = ageAtDiagnosis;
	}
	public String getCancerType() {
		return cancerType;
	}

	public void setcCancerType(String cancerType) {
		this.cancerType = cancerType;
	}

	public String getLastLmp() {
		return lastLmp;
	}

	public void setLastLmp(String lastLmp) {
		this.lastLmp = lastLmp;
	}

	public String getParity() {
		return parity;
	}

	public void setParity(String parity) {
		this.parity = parity;
	}

	public String getPhysicalExamination() {
		return physicalExamination;
	}

	public void setPhysicalExamination(String physicalExamination) {
		this.physicalExamination = physicalExamination;
	}

	public Boolean isAdultMale(Patient patient){
		return patient.getGender().equals("M") && !isChild(patient);
	}

	public Boolean isAdultFemale(Patient patient){
		return patient.getGender().equals("F") && !isChild(patient);
	}

	public Boolean isChild(Patient patient){
		return patient.getAge()<=CHILDAGESEPARATOR;
	}

	@Transactional
	public Encounter saveInvestigations() {
		Patient patient = Context.getPatientService().getPatient(this.patientId);
		Obs obsGroup = Context.getService(HospitalCoreService.class).getObsGroupCurrentDate(patient.getPersonId());
		Encounter encounter = createEncounter(patient);
		addObs(obsGroup, encounter);
        try {
			encounter.setVisit(getLastVisitForPatient(patient));
			//save an encounter with all the other entries
			Context.getEncounterService().saveEncounter(encounter);
			saveNoteDetails(encounter);
			endEncounter(encounter);
        } catch (Exception e) {
            e.printStackTrace();
        }

		return encounter;
	}

	private Encounter createEncounter(Patient patient) {
		Encounter encounter = new Encounter();
		KenyaEmrService kenyaEmrService =Context.getService(KenyaEmrService.class);
		User user = Context.getAuthenticatedUser();
		EncounterType encounterType = Context.getEncounterService().getEncounterTypeByUuid("ba45c278-f290-11ea-9666-1b3e6e848887");
		if (this.opdLogId != null) {
			OpdPatientQueueLog opdPatientQueueLog = Context.getService(PatientQueueService.class)
					.getOpdPatientQueueLogById(opdLogId);
			IpdPatientAdmissionLog ipdPatientAdmissionLog = Context.getService(IpdService.class)
					.getIpdPatientAdmissionLog(opdPatientQueueLog);
			encounter = ipdPatientAdmissionLog.getIpdEncounter();
		} else {
			encounter.setPatient(patient);
			encounter.setCreator(user);
			encounter.setEncounterDatetime(new Date());
			encounter.setProvider(EhrConfigsUtils.getDefaultEncounterRole(), EhrConfigsUtils.getProvider(user.getPerson()));
			encounter.setEncounterType(encounterType);
			encounter.setLocation(kenyaEmrService.getDefaultLocation());
			encounter.setDateCreated(new Date());
		}
		return encounter;
	}

	private void addObs(Obs obsGroup, Encounter encounter) {
		if (StringUtils.isNotBlank(this.onSetDate)) {
			addOnSetDate(encounter, obsGroup);
		}
		if (StringUtils.isNotBlank(this.illnessHistory)) {
			addIllnessHistory(encounter, obsGroup);
		}
		
		if (StringUtils.isNotBlank(this.otherInstructions)) {
			addOtherInstructions(encounter, obsGroup);
		}

		if (StringUtils.isNotBlank(this.familyHistoryAnswer)) {
			addFamilyHistory(encounter, obsGroup);
		}
		if(isAdultFemale(encounter.getPatient())){
			addFemaleHistory(encounter, obsGroup);
		}
		if(isAdultMale(encounter.getPatient())){
			addMaleHistory(encounter, obsGroup);
		}
		if(isChild(encounter.getPatient()))
		{
			addChildHistory(encounter, obsGroup);
		}

		addRiskFactors(encounter, obsGroup);
		addClinicals(encounter, obsGroup);
		addPerformanceStatus(encounter, obsGroup);
		addLymphNodeExam(encounter, obsGroup);
		addHeentExam(encounter, obsGroup);
		addRespiratorySystemExam(encounter, obsGroup);
		addBreastExam(encounter, obsGroup);
		addCardiovascularSystemExam(encounter, obsGroup);
		addAbdominalSystemExam(encounter, obsGroup);
		addGenitoUrinarySystemExam(encounter, obsGroup);
		addSkinExam(encounter, obsGroup);
		addNeurologicExam(encounter, obsGroup);

		if (StringUtils.isNotBlank(this.facility)) {
			addFacility(encounter, obsGroup);
		}

		if(StringUtils.isNotBlank(this.physicalExamination)){
			addPhysicalExamination(encounter,obsGroup);
		}
		
		for (Sign sign : this.signs) {
			sign.addObs(encounter, obsGroup);
		}
		for (Procedure procedure : this.procedures) {
			addObsForProcedures(encounter,obsGroup, procedure);
		}

		for(Investigation investigation : this.investigations) {
			investigation.addObs(encounter,obsGroup);
		}
		
		for (Diagnosis diagnosis : this.diagnoses) {
			diagnosis.addObs(encounter, obsGroup);
		}
		
		if (referredTo != null) {
			Referral.addReferralObs(referredTo, opdId, encounter, referralComments, obsGroup);
		}

		if (referralReasons != null) {
			ReferralReasons.addReferralReasonsObs(referralReasons, specify, encounter, obsGroup);
		}
		if(this.outcome != null) {
			this.outcome.addObs(encounter, obsGroup);
		}
	}

	

	private void addFacility(Encounter encounter, Obs obsGroup) {
		Concept facilityConcept = Context.getConceptService().getConceptByUuid(PROPERTY_FACILITY);
		Obs obsFacility = new Obs();
		obsFacility.setObsGroup(obsGroup);
		obsFacility.setConcept(facilityConcept);
		Location location = Context.getLocationService().getLocation(Integer.parseInt(facility));
		String mflCode = " ";
		for (LocationAttribute locationAttribute :
				location.getAttributes()) {
			if (locationAttribute.getAttributeType().equals(Context.getLocationService().getLocationAttributeTypeByUuid("8a845a89-6aa5-4111-81d3-0af31c45c002"))){
				mflCode=locationAttribute.getValueReference();
			}
		}
		obsFacility.setValueText(location.getName()+" "+mflCode);
		obsFacility.setCreator(encounter.getCreator());
		obsFacility.setDateCreated(encounter.getDateCreated());
		obsFacility.setEncounter(encounter);
		encounter.addObs(obsFacility);
	}

	private void addIllnessHistory(Encounter encounter, Obs obsGroup) {
		Concept conceptIllnessHistory = Context.getConceptService().getConceptByUuid("1390AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptIllnessHistory == null) {
			throw new NullPointerException("Illness history concept is not defined");
		}
		Obs obsIllnessHistory = new Obs();
		obsIllnessHistory.setObsGroup(obsGroup);
		obsIllnessHistory.setConcept(conceptIllnessHistory);
		obsIllnessHistory.setValueText(this.illnessHistory);
		obsIllnessHistory.setCreator(encounter.getCreator());
		obsIllnessHistory.setDateCreated(encounter.getDateCreated());
		obsIllnessHistory.setEncounter(encounter);
		encounter.addObs(obsIllnessHistory);
	}

	private void addOnSetDate(Encounter encounter, Obs obsGroup) {
		Concept onSetConcepts = Context.getConceptService().getConceptByUuid("164428AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (onSetConcepts == null) {
			throw new NullPointerException("Date on set  concept is not defined");
		}
		Obs obsOnSetDate = new Obs();
		obsOnSetDate.setObsGroup(obsGroup);
		obsOnSetDate.setConcept(onSetConcepts);
		try {
		obsOnSetDate.setValueDatetime(Utils.getDateInddyyyymmddFromStringObject(this.onSetDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		obsOnSetDate.setCreator(encounter.getCreator());
		obsOnSetDate.setDateCreated(encounter.getDateCreated());
		obsOnSetDate.setEncounter(encounter);
		encounter.addObs(obsOnSetDate);
	}



	private void addOtherInstructions(Encounter encounter, Obs obsGroup) {
		Concept conceptOtherInstructions = Context.getConceptService().getConceptByUuid("163106AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptOtherInstructions == null) {
			throw new NullPointerException("Other instructions concept is not defined");
		}
		Obs obsOtherInstructions = new Obs();
		obsOtherInstructions.setObsGroup(obsGroup);
		obsOtherInstructions.setConcept(conceptOtherInstructions);
		obsOtherInstructions.setValueText(this.otherInstructions);
		obsOtherInstructions.setCreator(encounter.getCreator());
		obsOtherInstructions.setDateCreated(encounter.getDateCreated());
		obsOtherInstructions.setEncounter(encounter);
		encounter.addObs(obsOtherInstructions);
	}

	private void addFamilyHistory(Encounter encounter, Obs obsGroup) {
		Concept conceptFamilyHistory = Context.getConceptService().getConceptByUuid("09e2cfb7-d6cb-4275-a102-7c8b5ba52a03");
		if (conceptFamilyHistory == null) {
			throw new NullPointerException("Any family members with cancer concept is not defined");
		}
		addValueCoded(encounter, obsGroup, conceptFamilyHistory, this.familyHistoryAnswer);
		if(this.familyHistoryAnswer.equals(YES)){
			Concept conceptDetailsFamilyCancer = Context.getConceptService().getConceptByUuid("3ae2279a-e065-48a5-8cb3-ec1c1f624ecc");
			if (conceptDetailsFamilyCancer == null) {
				throw new NullPointerException("Details of Family Members With Cancer concept is not defined");
			}
			//very confuscious
			/*addValueCoded(encounter, obsGroup, conceptDetailsFamilyCancer, this.relationshipToPatient);
			addValueNumeric(encounter, obsGroup, conceptDetailsFamilyCancer, this.ageAtDiagnosis);
			addValueCoded(encounter, obsGroup, conceptDetailsFamilyCancer, this.cancerType);*/
		}
	}

	private void addFemaleHistory(Encounter encounter, Obs obsGroup) {
		Concept conceptLastMenstrualPeriod= Context.getConceptService().getConceptByUuid("1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptLastMenstrualPeriod == null) {
			throw new NullPointerException("Last Menstrual Period concept is not defined");
		}
		if (StringUtils.isNotBlank(this.lastLmp)) {
			this.addValueDate(encounter, obsGroup, conceptLastMenstrualPeriod, this.lastLmp);
		}

		Concept conceptParity= Context.getConceptService().getConceptByUuid("1053AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptParity == null) {
			throw new NullPointerException("Parity concept is not defined");
		}
		if (StringUtils.isNotBlank(this.parity)) {
			this.addValueNumeric(encounter, obsGroup, conceptParity, this.parity);
		}

		//currently breast feeding
		Concept conceptCurrentlyBreastFeeding = Context.getConceptService().getConceptByUuid("5632AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptCurrentlyBreastFeeding == null) {
			throw new NullPointerException("Current breast feeding concept is not defined");
		}
		if (StringUtils.isNotBlank(this.currentlyBreastFeedingAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptCurrentlyBreastFeeding, this.currentlyBreastFeedingAnswer);
		}

		//currently on contraceptives
		Concept conceptCurrentlyContraceptiveUse = Context.getConceptService().getConceptByUuid("1386AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptCurrentlyContraceptiveUse == null) {
			throw new NullPointerException("Current contraceptive use status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.currentContraceptiveUseAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptCurrentlyContraceptiveUse, this.currentContraceptiveUseAnswer);
		}

		//cervical cancer screening status
		Concept conceptCervicalCancerScreening= Context.getConceptService().getConceptByUuid("06398e78-0d3e-43d5-8017-f2fc3865e2e0");
		if (conceptCervicalCancerScreening == null) {
			throw new NullPointerException("Cervical cancer screening status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cervicalCancerScreeningAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptCervicalCancerScreening, this.cervicalCancerScreeningAnswer);
		}

		//cervical cancer screening type
		Concept conceptCervicalCancerScreeningType= Context.getConceptService().getConceptByUuid("163589AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptCervicalCancerScreeningType == null) {
			throw new NullPointerException("Cervical cancer screening type concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cervicalCancerScreeningType)) {
			this.addValueCoded(encounter, obsGroup, conceptCervicalCancerScreeningType, this.cervicalCancerScreeningType);
		}

		//cervical cancer screening date
		Concept conceptCervicalCancerScreeningDate= Context.getConceptService().getConceptByUuid("165429AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptCervicalCancerScreeningDate == null) {
			throw new NullPointerException("Cervical cancer screening date concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cervicalCancerScreeningDate)) {
			this.addValueDate(encounter, obsGroup, conceptCervicalCancerScreeningDate, this.cervicalCancerScreeningDate);
		}

		//breast cancer screening status
		Concept conceptBreastCancerScreening= Context.getConceptService().getConceptByUuid("57053d0a-8372-4d3a-9103-1c61ec751f05");
		if (conceptBreastCancerScreening == null) {
			throw new NullPointerException("Breast cancer screening status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.breastCancerScreeningAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptBreastCancerScreening, this.breastCancerScreeningAnswer);
		}

		//breast cancer screening type
		Concept conceptBreastCancerScreeningType= Context.getConceptService().getConceptByUuid("b83960b8-5db1-492c-9ee7-7bebae552ea4");
		if (conceptBreastCancerScreeningType == null) {
			throw new NullPointerException("Breast cancer screening type concept is not defined");
		}
		if (StringUtils.isNotBlank(this.breastCancerScreeningType)) {
			this.addValueCoded(encounter, obsGroup, conceptBreastCancerScreeningType, this.breastCancerScreeningType);
		}

		//breast cancer screening date
		Concept conceptBreastCancerScreeningDate= Context.getConceptService().getConceptByUuid("23f45ac8-613a-4ece-b3d9-ac1697acc2ab");
		if (conceptBreastCancerScreeningDate == null) {
			throw new NullPointerException("Breast cancer screening date concept is not defined");
		}
		if (StringUtils.isNotBlank(this.breastCancerScreeningDate)) {
			this.addValueDate(encounter, obsGroup, conceptBreastCancerScreeningDate, this.breastCancerScreeningDate);
		}

		//colorectal cancer screening status
		Concept conceptColorectalCancerScreening= Context.getConceptService().getConceptByUuid("83e84962-68d2-4605-968d-0047e80b107e");
		if (conceptColorectalCancerScreening == null) {
			throw new NullPointerException("Colorectal cancer screening status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.colorectalCancerScreeningAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptColorectalCancerScreening, this.colorectalCancerScreeningAnswer);
		}

		//colorectal cancer screening type
		Concept conceptColorectalCancerScreeningType= Context.getConceptService().getConceptByUuid("40e31ac1-10a3-49ec-b79b-fa9dbdafe8c0");
		if (conceptColorectalCancerScreeningType == null) {
			throw new NullPointerException("Colorectal cancer screening type concept is not defined");
		}
		if (StringUtils.isNotBlank(this.colorectalCancerScreeningType)) {
			this.addValueCoded(encounter, obsGroup, conceptColorectalCancerScreeningType, this.colorectalCancerScreeningType);
		}

		//colorectal cancer screening date
		Concept conceptColorectalCancerScreeningDate= Context.getConceptService().getConceptByUuid("23f45ac8-613a-4ece-b3d9-ac1697acc2ab");
		if (conceptColorectalCancerScreeningDate == null) {
			throw new NullPointerException("Colorectal cancer screening date concept is not defined");
		}
		if (StringUtils.isNotBlank(this.colorectalCancerScreeningDate)) {
			this.addValueDate(encounter, obsGroup, conceptColorectalCancerScreeningDate, this.colorectalCancerScreeningDate);
		}
	}

	private void addMaleHistory(Encounter encounter, Obs obsGroup) {
		//prostrate cancer screening status
		Concept conceptProstrateCancerScreening= Context.getConceptService().getConceptByUuid("68cdacd6-9fb9-4713-9cdc-8b590e152fc0");
		if (conceptProstrateCancerScreening == null) {
			throw new NullPointerException("Prostrate cancer screening status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.prostrateCancerScreeningAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptProstrateCancerScreening, this.prostrateCancerScreeningAnswer);
		}

		//prostrate cancer screening type
		/*Concept conceptProstrateCancerScreeningType= Context.getConceptService().getConceptByUuid("d5226e81-34b3-4216-b8dd-624834005c87");
		if (conceptProstrateCancerScreeningType == null) {
			throw new NullPointerException("Prostrate cancer screening type concept is not defined");
		}
		if (StringUtils.isNotBlank(this.prostrateCancerScreeningType)) {
			this.addValueCoded(encounter, obsGroup, conceptProstrateCancerScreeningType, this.prostrateCancerScreeningType);
		}*/

		//prostrate cancer screening date
		Concept conceptProstrateCancerScreeningDate= Context.getConceptService().getConceptByUuid("165429AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptProstrateCancerScreeningDate == null) {
			throw new NullPointerException("Prostrate cancer screening date concept is not defined");
		}
		if (StringUtils.isNotBlank(this.prostrateCancerScreeningDate)) {
			this.addValueDate(encounter, obsGroup, conceptProstrateCancerScreeningDate, this.prostrateCancerScreeningDate);
		}
	}

	private void addChildHistory(Encounter encounter, Obs obsGroup) {
		//retinoblatoma state
		Concept conceptRetinoBlastomaState= Context.getConceptService().getConceptByUuid("f527f815-987c-4aaf-8a33-2ea1b36b338c");
		if (conceptRetinoBlastomaState == null) {
			throw new NullPointerException("Retinoblastoma status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.retinoblastomaState)) {
			this.addValueCoded(encounter, obsGroup, conceptRetinoBlastomaState, this.retinoblastomaState);
		}
	}

	private void addRiskFactors(Encounter encounter, Obs obsGroup) {
		//cigarette usage state
		Concept conceptCigaretteUsage= Context.getConceptService().getConceptByUuid("9e0aa689-7ab3-471e-b393-a7f321458b5c");
		if (conceptCigaretteUsage == null) {
			throw new NullPointerException("Cigarette usage status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cigaretteUsageAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptCigaretteUsage, this.cigaretteUsageAnswer);
		}

		//number of cigaretts per day
		Concept conceptCigarettesPerDay= Context.getConceptService().getConceptByUuid("165595AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptCigarettesPerDay == null) {
			throw new NullPointerException("Number of cigarettes smoked per day concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cigarettesPerDay)) {
			this.addValueNumeric(encounter, obsGroup, conceptCigarettesPerDay, this.cigarettesPerDay);
		}

		//number of years smoked cigarette
		Concept conceptYearsSmoked= Context.getConceptService().getConceptByUuid("443b4338-16cd-43e8-910d-d1d9b22860c4");
		if (conceptYearsSmoked == null) {
			throw new NullPointerException("Number of years smoked concept is not defined");
		}
		if (StringUtils.isNotBlank(this.yearsSmokedCigarette)) {
			this.addValueNumeric(encounter, obsGroup, conceptYearsSmoked, this.yearsSmokedCigarette);
		}

		//tobacco usage state
		Concept conceptTobaccoUsage= Context.getConceptService().getConceptByUuid("163731AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptTobaccoUsage == null) {
			throw new NullPointerException("Tobacco usage status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.tobaccoUsageAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptTobaccoUsage, this.tobaccoUsageAnswer);
		}

		//alcohol usage state
		Concept conceptAlcoholUsage= Context.getConceptService().getConceptByUuid("159449AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptAlcoholUsage == null) {
			throw new NullPointerException("Alcohol usage status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.alcoholUsageAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptAlcoholUsage, this.alcoholUsageAnswer);
		}

		//number of botlles per day
		Concept conceptAlcoholIntake= Context.getConceptService().getConceptByUuid("166678AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptAlcoholIntake== null) {
			throw new NullPointerException("Number of alcohol bottles per day concept is not defined");
		}
		if (StringUtils.isNotBlank(this.alcoholIntakeFrequency)) {
			this.addValueNumeric(encounter, obsGroup, conceptAlcoholIntake, this.alcoholIntakeFrequency);
		}

		//physical activity state
		Concept conceptPhysicalActivity = Context.getConceptService().getConceptByUuid("159468AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptPhysicalActivity == null) {
			throw new NullPointerException("Physical activity status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.physicalActivityAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptPhysicalActivity, this.physicalActivityAnswer);
		}

		//physical activity state
		Concept conceptRadiotherapyExposure = Context.getConceptService().getConceptByUuid("5e95e6ff-8ef4-46b1-80d3-f8389514c93a");
		if (conceptRadiotherapyExposure == null) {
			throw new NullPointerException("Previous radiotherapy exposure status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.radiotherapyExposureAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptRadiotherapyExposure, this.radiotherapyExposureAnswer);
		}
	}

	private void addClinicals(Encounter encounter, Obs obsGroup) {
		//PresentingComplains
		Concept conceptPresentingComplains = Context.getConceptService().getConceptByUuid("160531AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptPresentingComplains== null) {
			throw new NullPointerException("PresentingComplains concept is not defined");
		}
		if (StringUtils.isNotBlank(this.presentingComplains)) {
			this.addValueText(encounter, obsGroup, conceptPresentingComplains, this.presentingComplains);
		}

		//HistoryOfPresentIllness
		Concept conceptHistoryOfPresentIllness= Context.getConceptService().getConceptByUuid("1390AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptHistoryOfPresentIllness== null) {
			throw new NullPointerException("HistoryOfPresentIllness concept is not defined");
		}
		if (StringUtils.isNotBlank(this.historyOfPresentIllness)) {
			this.addValueText(encounter, obsGroup, conceptHistoryOfPresentIllness, this.historyOfPresentIllness);
		}

		//PastMedicalSurgicalHistory
		Concept conceptPastMedicalSurgicalHistory= Context.getConceptService().getConceptByUuid("160221AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptPastMedicalSurgicalHistory== null) {
			throw new NullPointerException("PastMedicalSurgicalHistory concept is not defined");
		}
		if (StringUtils.isNotBlank(this.pastMedicalSurgicalHistory)) {
			this.addValueText(encounter, obsGroup, conceptPastMedicalSurgicalHistory, this.pastMedicalSurgicalHistory);
		}

		//Cns
		Concept conceptCns= Context.getConceptService().getConceptByUuid("9217661f-89b9-4727-b633-6fbb7f3a85a1");
		if (conceptCns== null) {
			throw new NullPointerException("Cns concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cns)) {
			this.addValueText(encounter, obsGroup, conceptCns, this.cns);
		}

		//Cvs
		Concept conceptCvs= Context.getConceptService().getConceptByUuid("7ef86399-9afe-4977-b320-3ca9763b39fa");
		if (conceptCvs== null) {
			throw new NullPointerException("Cvs concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cvs)) {
			this.addValueText(encounter, obsGroup, conceptCvs, this.cvs);
		}

		//Rs
		Concept conceptRs= Context.getConceptService().getConceptByUuid("f157b780-5007-48b6-8e20-af91a0a61fb0");
		if (conceptRs== null) {
			throw new NullPointerException("Rs concept is not defined");
		}
		if (StringUtils.isNotBlank(this.rs)) {
			this.addValueText(encounter, obsGroup, conceptRs, this.rs);
		}

		//Gus
		Concept conceptGus= Context.getConceptService().getConceptByUuid("2389453e-e4bd-4ec9-9764-620daeb52dbb");
		if (conceptGus== null) {
			throw new NullPointerException("Gus concept is not defined");
		}
		if (StringUtils.isNotBlank(this.gus)) {
			this.addValueText(encounter, obsGroup, conceptGus, this.gus);
		}

		//Mss
		Concept conceptMss= Context.getConceptService().getConceptByUuid("cb2cfd51-3886-4430-a0df-0390fca9a2cd");
		if (conceptMss== null) {
			throw new NullPointerException("Mss concept is not defined");
		}
		if (StringUtils.isNotBlank(this.mss)) {
			this.addValueText(encounter, obsGroup, conceptMss, this.mss);
		}
	}

	private void addPerformanceStatus(Encounter encounter, Obs obsGroup) {
		//GeneralExamination
		Concept conceptGeneralExamination= Context.getConceptService().getConceptByUuid("163042AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptGeneralExamination== null) {
			throw new NullPointerException("GeneralExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.generalExamination)) {
			this.addValueText(encounter, obsGroup, conceptGeneralExamination, this.generalExamination);
		}
		//JaundiceExamination
		Concept conceptJaundiceExamination= Context.getConceptService().getConceptByUuid("db614112-db2c-4102-b1e1-8cd6b2852587");
		if (conceptJaundiceExamination== null) {
			throw new NullPointerException("JaundiceExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.jaundiceExamination)) {
			this.addValueText(encounter, obsGroup, conceptJaundiceExamination, this.jaundiceExamination);
		}

		//AnaemiaExamination
		Concept conceptAnaemiaExamination= Context.getConceptService().getConceptByUuid("865a5ce7-4206-4939-99a6-f27fb74818fc");
		if (conceptAnaemiaExamination== null) {
			throw new NullPointerException("AnaemiaExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.anaemiaExamination)) {
			this.addValueText(encounter, obsGroup, conceptAnaemiaExamination, this.anaemiaExamination);
		}

		//CyanosisExamination
		Concept conceptCyanosisExamination= Context.getConceptService().getConceptByUuid("71a508f9-866c-41fb-8da7-efa84df6c29e");
		if (conceptCyanosisExamination== null) {
			throw new NullPointerException("CyanosisExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cyanosisExamination)) {
			this.addValueText(encounter, obsGroup, conceptCyanosisExamination, this.cyanosisExamination);
		}

		//ClubbingExamination
		Concept conceptClubbingExamination= Context.getConceptService().getConceptByUuid("eda93f9b-7f98-432f-b9d7-bd56343def46");
		if (conceptClubbingExamination== null) {
			throw new NullPointerException("ClubbingExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.clubbingExamination)) {
			this.addValueText(encounter, obsGroup, conceptClubbingExamination, this.clubbingExamination);
		}

		//OedemaExamination
		Concept conceptOedemaExamination= Context.getConceptService().getConceptByUuid("872ab30f-1924-4b2d-bba6-e7af9e1b711a");
		if (conceptOedemaExamination== null) {
			throw new NullPointerException("OedemaExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.oedemaExamination)) {
			this.addValueText(encounter, obsGroup, conceptOedemaExamination, this.oedemaExamination);
		}

		//DehydrationExamination
		Concept conceptDehydrationExamination= Context.getConceptService().getConceptByUuid("a504fcd1-2aad-403b-9424-727a1d590609");
		if (conceptDehydrationExamination== null) {
			throw new NullPointerException("DehydrationExamination concept is not defined");
		}
		if (StringUtils.isNotBlank(this.dehydrationExamination)) {
			this.addValueText(encounter, obsGroup, conceptDehydrationExamination, this.dehydrationExamination);
		}
	}

	private void addLymphNodeExam(Encounter encounter, Obs obsGroup) {
		//PalpabilityAnswer
		Concept conceptPalpabilityAnswer = Context.getConceptService().getConceptByUuid("81365bc6-536b-4486-bcf5-a71af5c7c2b5");
		if (conceptPalpabilityAnswer == null) {
			throw new NullPointerException("PalpabilityAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.palpabilityAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptPalpabilityAnswer, this.palpabilityAnswer);
		}

		//SubmandibularAnswer
		Concept conceptSubmandibularAnswer = Context.getConceptService().getConceptByUuid("403a5548-2d26-45b1-b3c1-3f6b0b093ed2");
		if (conceptSubmandibularAnswer == null) {
			throw new NullPointerException("SubmandibularAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.submandibularAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptSubmandibularAnswer, this.submandibularAnswer);
		}

		//SubmandibularComment
		Concept conceptSubmandibularComment = Context.getConceptService().getConceptByUuid("d18a67ce-648f-4aa3-9ff1-107b028aa054");
		if (conceptSubmandibularComment == null) {
			throw new NullPointerException("SubmandibularComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.submandibularComment)) {
			this.addValueText(encounter, obsGroup, conceptSubmandibularComment, this.submandibularComment);
		}

		//SupraciavicularAnswer
		Concept conceptSupraciavicularAnswer = Context.getConceptService().getConceptByUuid("1315b836-a3c9-403c-9371-6ab85eb5315b");
		if (conceptSupraciavicularAnswer == null) {
			throw new NullPointerException("SupraciavicularAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.supraciavicularAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptSupraciavicularAnswer, this.supraciavicularAnswer);
		}

		//SupraciavicularComment
		Concept conceptSupraciavicularComment= Context.getConceptService().getConceptByUuid("df3fba00-680c-4380-90b5-72420dc5edd1");
		if (conceptSupraciavicularComment== null) {
			throw new NullPointerException("SupraciavicularComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.supraciavicularComment)) {
			this.addValueText(encounter, obsGroup, conceptSupraciavicularComment, this.supraciavicularComment);
		}

		//CervicalExaminationAnswer
		Concept conceptCervicalExaminationAnswer = Context.getConceptService().getConceptByUuid("3e0ac5b1-4cbe-4f5a-ac7d-3e30a55dc4fa");
		if (conceptCervicalExaminationAnswer == null) {
			throw new NullPointerException("CervicalExaminationAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cervicalExaminationAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptCervicalExaminationAnswer, this.cervicalExaminationAnswer);
		}

		//CervicalExaminationComment
		Concept conceptCervicalExaminationComment = Context.getConceptService().getConceptByUuid("81bc9ccd-8fdf-40cd-9c93-e34d50a6c8b2");
		if (conceptCervicalExaminationComment == null) {
			throw new NullPointerException("CervicalExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.cervicalExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptCervicalExaminationComment, this.cervicalExaminationComment);
		}

		//AxillaryExaminationAnswer
		Concept conceptAxillaryExaminationAnswer = Context.getConceptService().getConceptByUuid("b46aa003-ab6a-4465-b911-9e92e4a5a54f");
		if (conceptAxillaryExaminationAnswer == null) {
			throw new NullPointerException("AxillaryExaminationAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.axillaryExaminationAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptAxillaryExaminationAnswer, this.axillaryExaminationAnswer);
		}

		//AxillaryExaminationComment
		Concept conceptAxillaryExaminationComment = Context.getConceptService().getConceptByUuid("37a73fee-57db-445f-b047-fc703fb73c1a");
		if (conceptAxillaryExaminationComment == null) {
			throw new NullPointerException("AxillaryExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.axillaryExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptAxillaryExaminationComment, this.axillaryExaminationComment);
		}

		//InguinalExaminationAnswer
		Concept conceptInguinalExaminationAnswer = Context.getConceptService().getConceptByUuid("2d98fb30-18c2-4b42-90db-527ac64e01be");
		if (conceptInguinalExaminationAnswer == null) {
			throw new NullPointerException("InguinalExaminationAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.inguinalExaminationAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptInguinalExaminationAnswer, this.inguinalExaminationAnswer);
		}

		//InguinalExaminationComment
		Concept conceptInguinalExaminationComment = Context.getConceptService().getConceptByUuid("22e51d0d-0184-44e1-9fb0-0ac310d09dfd");
		if (conceptInguinalExaminationComment == null) {
			throw new NullPointerException("InguinalExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.inguinalExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptInguinalExaminationComment, this.inguinalExaminationComment);
		}

		//GeneralizedLymadenopathyExaminationAnswer
		Concept conceptGeneralizedLymadenopathyExaminationAnswer = Context.getConceptService().getConceptByUuid("7dfa3630-a420-4704-aae9-be3196f803ad");
		if (conceptGeneralizedLymadenopathyExaminationAnswer == null) {
			throw new NullPointerException("GeneralizedLymadenopathyExaminationAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.generalizedLymadenopathyExaminationAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptGeneralizedLymadenopathyExaminationAnswer, this.generalizedLymadenopathyExaminationAnswer);
		}

		//GeneralizedLymadenopathyExaminationComment
		Concept conceptGeneralizedLymadenopathyExaminationComment = Context.getConceptService().getConceptByUuid("9756934b-1c2b-4d6d-916d-85b8fcfa89cc");
		if (conceptGeneralizedLymadenopathyExaminationComment == null) {
			throw new NullPointerException("GeneralizedLymadenopathyExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.generalizedLymadenopathyExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptGeneralizedLymadenopathyExaminationComment, this.generalizedLymadenopathyExaminationComment);
		}

		//OtherLymphNodeExaminationAnswer
		Concept conceptOtherLymphNodeExaminationAnswer = Context.getConceptService().getConceptByUuid("c725329d-0ebd-42ca-9f19-6b407e551aef");
		if (conceptOtherLymphNodeExaminationAnswer == null) {
			throw new NullPointerException("OtherLymphNodeExaminationAnswer status concept is not defined");
		}
		if (StringUtils.isNotBlank(this.otherLymphNodeExaminationAnswer)) {
			this.addValueCoded(encounter, obsGroup, conceptOtherLymphNodeExaminationAnswer, this.otherLymphNodeExaminationAnswer);
		}

		//OtherLymphNodeExaminationComment
		Concept conceptOtherLymphNodeExaminationComment = Context.getConceptService().getConceptByUuid("183f9497-b72e-4ebe-ae17-5dcf110ff3b6");
		if (conceptOtherLymphNodeExaminationComment == null) {
			throw new NullPointerException("OtherLymphNodeExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.otherLymphNodeExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptOtherLymphNodeExaminationComment, this.otherLymphNodeExaminationComment);
		}
	}
	private void addHeentExam(Encounter encounter, Obs obsGroup) {
		//EyeExam
		Concept conceptEyeExam = Context.getConceptService().getConceptByUuid("166441AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptEyeExam == null) {
			throw new NullPointerException("EyeExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.eyeExam)) {
			this.addValueText(encounter, obsGroup, conceptEyeExam, this.eyeExam);
		}
		//NeckExam
		Concept conceptNeckExam = Context.getConceptService().getConceptByUuid("165983AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptNeckExam == null) {
			throw new NullPointerException("NeckExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.neckExam)) {
			this.addValueText(encounter, obsGroup, conceptNeckExam, this.neckExam);
		}
		//MouthExam
		Concept conceptMouthExam = Context.getConceptService().getConceptByUuid("ea536fb3-cfd0-4928-8cbd-8210559d2a35");
		if (conceptMouthExam == null) {
			throw new NullPointerException("MouthExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.mouthExam)) {
			this.addValueText(encounter, obsGroup, conceptMouthExam, this.mouthExam);
		}
		//EarExam
		Concept conceptEarExam = Context.getConceptService().getConceptByUuid("462bfb69-b97e-425c-b387-ddd3a5c8653c");
		if (conceptEarExam == null) {
			throw new NullPointerException("EarExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.earExam)) {
			this.addValueText(encounter, obsGroup, conceptEarExam, this.earExam);
		}
		//NoseExam
		Concept conceptNoseExam = Context.getConceptService().getConceptByUuid("462bfb69-b97e-425c-b387-ddd3a5c8653c");
		if (conceptNoseExam == null) {
			throw new NullPointerException("NoseExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.noseExam)) {
			this.addValueText(encounter, obsGroup, conceptNoseExam, this.noseExam);
		}
		//ThroatExam
		Concept conceptThroatExam = Context.getConceptService().getConceptByUuid("462bfb69-b97e-425c-b387-ddd3a5c8653c");
		if (conceptThroatExam == null) {
			throw new NullPointerException("ThroatExam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.throatExam)) {
			this.addValueText(encounter, obsGroup, conceptThroatExam, this.throatExam);
		}
	}

	private void addRespiratorySystemExam(Encounter encounter, Obs obsGroup) {
		//RsInspection
		Concept conceptRsInspection = Context.getConceptService().getConceptByUuid("32882ce7-19ca-4b2c-8175-a5f188618391");
		if (conceptRsInspection == null) {
			throw new NullPointerException("RsInspection concept is not defined");
		}
		if (StringUtils.isNotBlank(this.rsInspection)) {
			this.addValueText(encounter, obsGroup, conceptRsInspection, this.rsInspection);
		}
		//RsPalpation
		Concept conceptRsPalpation = Context.getConceptService().getConceptByUuid("e65bb895-b31e-4eb7-ada6-2ffa9fff5dfe");
		if (conceptRsPalpation == null) {
			throw new NullPointerException("RsPalpation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.rsPalpation)) {
			this.addValueText(encounter, obsGroup, conceptRsPalpation, this.rsPalpation);
		}
		//RsPercussion
		Concept conceptRsPercussion = Context.getConceptService().getConceptByUuid("8e06aad9-5180-4054-8c7d-2e83dbcdcc0a");
		if (conceptRsPercussion == null) {
			throw new NullPointerException("RsPercussion concept is not defined");
		}
		if (StringUtils.isNotBlank(this.rsPercussion)) {
			this.addValueText(encounter, obsGroup, conceptRsPercussion, this.rsPercussion);
		}
		//RsAuscultation
		Concept conceptRsAuscultation = Context.getConceptService().getConceptByUuid("77116c15-8dde-446a-adca-e489f5c47199");
		if (conceptRsAuscultation == null) {
			throw new NullPointerException("RsAuscultation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.rsAuscultation)) {
			this.addValueText(encounter, obsGroup, conceptRsAuscultation, this.rsAuscultation);
		}
	}

	private void addBreastExam(Encounter encounter, Obs obsGroup) {
		//BreastExaminationComment
		Concept conceptBreastExaminationComment = Context.getConceptService().getConceptByUuid("162825AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptBreastExaminationComment == null) {
			throw new NullPointerException("BreastExaminationComment concept is not defined");
		}
		if (StringUtils.isNotBlank(this.breastExaminationComment)) {
			this.addValueText(encounter, obsGroup, conceptBreastExaminationComment, this.breastExaminationComment);
		}
	}

	private void addCardiovascularSystemExam(Encounter encounter, Obs obsGroup) {
		//CsInspection
		Concept conceptCsInspection = Context.getConceptService().getConceptByUuid("086d1dcf-7d0d-4197-a917-de9819da8689");
		if (conceptCsInspection == null) {
			throw new NullPointerException("CsInspection concept is not defined");
		}
		if (StringUtils.isNotBlank(this.csInspection)) {
			this.addValueText(encounter, obsGroup, conceptCsInspection, this.csInspection);
		}
		//CsPalpation
		Concept conceptCsPalpation = Context.getConceptService().getConceptByUuid("c5d196c2-bd78-4539-a686-f66cef7176dd");
		if (conceptCsPalpation == null) {
			throw new NullPointerException("CsPalpation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.csPalpation)) {
			this.addValueText(encounter, obsGroup, conceptCsPalpation, this.csPalpation);
		}
		//CsPercussion
		Concept conceptCsPercussion = Context.getConceptService().getConceptByUuid("29185559-4336-4383-8681-f74e11d7756e");
		if (conceptCsPercussion == null) {
			throw new NullPointerException("CsPercussion concept is not defined");
		}
		if (StringUtils.isNotBlank(this.csPercussion)) {
			this.addValueText(encounter, obsGroup, conceptCsPercussion, this.csPercussion);
		}
		//CsAuscultation
		Concept conceptCsAuscultation = Context.getConceptService().getConceptByUuid("faa3255f-0f56-44b6-908b-da0002efebf1");
		if (conceptCsAuscultation == null) {
			throw new NullPointerException("CsAuscultation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.csAuscultation)) {
			this.addValueText(encounter, obsGroup, conceptCsAuscultation, this.csAuscultation);
		}
	}

	private void addAbdominalSystemExam(Encounter encounter, Obs obsGroup) {
		//AsInspection
		Concept conceptAsInspection = Context.getConceptService().getConceptByUuid("02e44cf4-37dd-4f03-90f0-40fff4485218");
		if (conceptAsInspection == null) {
			throw new NullPointerException("AsInspection concept is not defined");
		}
		if (StringUtils.isNotBlank(this.asInspection)) {
			this.addValueText(encounter, obsGroup, conceptAsInspection, this.asInspection);
		}
		//AsPalpation
		Concept conceptAsPalpation = Context.getConceptService().getConceptByUuid("b20b1889-a85b-40ac-92cd-06f03dba052b");
		if (conceptAsPalpation == null) {
			throw new NullPointerException("AsPalpation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.asPalpation)) {
			this.addValueText(encounter, obsGroup, conceptAsPalpation, this.asPalpation);
		}
		//AsPercussion
		Concept conceptAsPercussion = Context.getConceptService().getConceptByUuid("e90091ea-2065-4cc9-a42a-e490a2aedd53");
		if (conceptAsPercussion == null) {
			throw new NullPointerException("AsPercussion concept is not defined");
		}
		if (StringUtils.isNotBlank(this.asPercussion)) {
			this.addValueText(encounter, obsGroup, conceptAsPercussion, this.asPercussion);
		}
		//AsAuscultation
		Concept conceptAsAuscultation = Context.getConceptService().getConceptByUuid("2ddd4147-c7df-4510-b9b6-5cce3bef9d3b");
		if (conceptAsAuscultation == null) {
			throw new NullPointerException("AsAuscultation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.asAuscultation)) {
			this.addValueText(encounter, obsGroup, conceptAsAuscultation, this.asAuscultation);
		}
	}

	private void addGenitoUrinarySystemExam(Encounter encounter, Obs obsGroup) {
		//GuInspection
		Concept conceptGuInspection = Context.getConceptService().getConceptByUuid("72b0296f-e6c4-4eed-a407-1229b76441db");
		if (conceptGuInspection == null) {
			throw new NullPointerException("GuInspection concept is not defined");
		}
		if (StringUtils.isNotBlank(this.guInspection)) {
			this.addValueText(encounter, obsGroup, conceptGuInspection, this.guInspection);
		}
		//GuPalpation
		Concept conceptGuPalpation = Context.getConceptService().getConceptByUuid("aa5d8054-49a0-4039-85f6-d09b94fd33cb");
		if (conceptGuPalpation == null) {
			throw new NullPointerException("GuPalpation concept is not defined");
		}
		if (StringUtils.isNotBlank(this.guPalpation)) {
			this.addValueText(encounter, obsGroup, conceptGuPalpation, this.guPalpation);
		}
	}

	private void addSkinExam(Encounter encounter, Obs obsGroup) {
		//SkinInspection
		Concept conceptSkinInspection = Context.getConceptService().getConceptByUuid("568e6f9c-8349-4dde-b6fb-a2d70a988d59");
		if (conceptSkinInspection == null) {
			throw new NullPointerException("SkinInspection concept is not defined");
		}
		if (StringUtils.isNotBlank(this.skinInspection)) {
			this.addValueText(encounter, obsGroup, conceptSkinInspection, this.skinInspection);
		}

	}

	private void addNeurologicExam(Encounter encounter, Obs obsGroup) {
		//NHigherFunctions
		Concept conceptNHigherFunctions = Context.getConceptService().getConceptByUuid("86ef4a3e-d861-4444-9205-31426144789a");
		if (conceptNHigherFunctions == null) {
			throw new NullPointerException("Neurologic HigherFunctions Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nHigherFunctions)) {
			this.addValueText(encounter, obsGroup, conceptNHigherFunctions, this.nHigherFunctions);
		}

		//NCranialNerves
		Concept conceptNCranialNerves = Context.getConceptService().getConceptByUuid("cc5d131a-a18b-40a2-a874-43bf33601236");
		if (conceptNCranialNerves == null) {
			throw new NullPointerException("Neurologic CranialNerves Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nCranialNerves)) {
			this.addValueText(encounter, obsGroup, conceptNCranialNerves, this.nCranialNerves);
		}

		//NHead
		Concept conceptNHead = Context.getConceptService().getConceptByUuid("973addba-1854-43e5-b512-e4921f252416");
		if (conceptNHead == null) {
			throw new NullPointerException("Neurologic Head Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nHead)) {
			this.addValueText(encounter, obsGroup, conceptNHead, this.nHead);
		}

		//NNeck
		Concept conceptNNeck = Context.getConceptService().getConceptByUuid("68461a2a-eea6-4103-ace7-ab78c6693e27");
		if (conceptNNeck == null) {
			throw new NullPointerException("Neurologic Neck Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nNeck)) {
			this.addValueText(encounter, obsGroup, conceptNNeck, this.nNeck);
		}

		//NSensoryLevel
		Concept conceptNSensoryLevel = Context.getConceptService().getConceptByUuid("b8bd3aff-dedb-4e16-9b6d-a75ba5695696");
		if (conceptNSensoryLevel == null) {
			throw new NullPointerException("Neurologic SensoryLevel Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nSensoryLevel)) {
			this.addValueText(encounter, obsGroup, conceptNSensoryLevel, this.nSensoryLevel);
		}

		//NMusculoskeletal
		Concept conceptNMusculoskeletal = Context.getConceptService().getConceptByUuid("f46ac8ad-3ec7-44ba-9040-c2c75291379e");
		if (conceptNMusculoskeletal == null) {
			throw new NullPointerException("Neurologic Musculoskeletal Exam concept is not defined");
		}
		if (StringUtils.isNotBlank(this.nMusculoskeletal)) {
			this.addValueText(encounter, obsGroup, conceptNMusculoskeletal, this.nMusculoskeletal);
		}
	}

	private void addValueCoded(Encounter encounter, Obs obsGroup, Concept concept, String value)
	{
		Obs obs = new Obs();
		obs.setObsGroup(obsGroup);
		obs.setConcept(concept);
		Concept answerConcept = Context.getConceptService().getConcept(value);
		if (answerConcept == null) {
			throw new NullPointerException("Error concept for  id: "+value+" was not found");
		}
		obs.setValueCoded(answerConcept);
		obs.setCreator(encounter.getCreator());
		obs.setDateCreated(encounter.getDateCreated());
		obs.setEncounter(encounter);
		encounter.addObs(obs);
	}

	private void addValueText(Encounter encounter, Obs obsGroup, Concept concept, String value)
	{
		Obs obs = new Obs();
		obs.setObsGroup(obsGroup);
		obs.setConcept(concept);
		obs.setValueText(value);
		obs.setCreator(encounter.getCreator());
		obs.setDateCreated(encounter.getDateCreated());
		obs.setEncounter(encounter);
		encounter.addObs(obs);
	}

	private void addValueNumeric(Encounter encounter, Obs obsGroup, Concept concept, String value)
	{
		Obs obs = new Obs();
		obs.setObsGroup(obsGroup);
		obs.setConcept(concept);
		obs.setValueNumeric(Double.parseDouble(value));
		obs.setCreator(encounter.getCreator());
		obs.setDateCreated(encounter.getDateCreated());
		obs.setEncounter(encounter);
		encounter.addObs(obs);
	}

	private void addValueDate(Encounter encounter, Obs obsGroup, Concept concept, String date){
		Obs obs = new Obs();
		obs.setObsGroup(obsGroup);
		obs.setConcept(concept);
		try {
			obs.setValueDate(Utils.getDateInddyyyymmddFromStringObject(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		obs.setCreator(encounter.getCreator());
		obs.setDateCreated(encounter.getDateCreated());
		obs.setEncounter(encounter);
		encounter.addObs(obs);
	}

	public void addPhysicalExamination(Encounter encounter, Obs obsGroup)
	{
		Concept conceptPhysicalExamination = Context.getConceptService().getConceptByUuid("1391AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (conceptPhysicalExamination == null) {
			throw new NullPointerException("Physical examination concept is not defined");
		}
		Obs obsPhysicalExamination = new Obs();
		obsPhysicalExamination.setObsGroup(obsGroup);
		obsPhysicalExamination.setConcept(conceptPhysicalExamination);
		obsPhysicalExamination.setValueText(this.physicalExamination);
		obsPhysicalExamination.setCreator(encounter.getCreator());
		obsPhysicalExamination.setDateCreated(encounter.getDateCreated());
		obsPhysicalExamination.setEncounter(encounter);
		encounter.addObs(obsPhysicalExamination);
	}

	private void saveNoteDetails(Encounter encounter) {
		for (Drug drug : this.drugs) {
			String referralWardName = Context.getService(PatientQueueService.class).getOpdPatientQueueById(this.queueId)
					.getOpdConceptName();
			drug.save(encounter, referralWardName);
		}
		for (Investigation investigation : this.investigations) {
			String departmentName = Context.getConceptService().getConcept(this.opdId).getName().toString();
			try {
				saveInvestigations(encounter, departmentName, investigation);
			} catch (Exception e) {
				logger.error("Error saving investigation {}({}): {}", new Object[] { investigation.getId(), investigation.getLabel(), e.getMessage() });
			}
		}
		for(Procedure procedure : this.procedures) {
			String departmentName = Context.getConceptService().getConcept(this.opdId).getName().toString();
			try {
				saveProcedures(encounter, departmentName, procedure);
			}
			catch (Exception e) {
				logger.error("Error saving procedure {}({}): {}", new Object[] { procedure.getId(), procedure.getLabel(), e.getMessage() });
			}
		}
		for(Sign sign: this.signs) {
			sign.save(encounter);
		}
		if (this.outcome != null) {
			this.outcome.save(encounter);
		}
	}
	
	private void endEncounter(Encounter encounter) {
		PatientQueueService queueService = Context.getService(PatientQueueService.class);
		if (this.queueId != null) {
			OpdPatientQueue queue = queueService.getOpdPatientQueueById(this.queueId);
			OpdPatientQueueLog queueLog = new OpdPatientQueueLog();
			queueLog.setOpdConcept(queue.getOpdConcept());
			queueLog.setOpdConceptName(queue.getOpdConceptName());
			queueLog.setPatient(queue.getPatient());
			queueLog.setCreatedOn(queue.getCreatedOn());
			queueLog.setPatientIdentifier(queue.getPatientIdentifier());
			queueLog.setPatientName(queue.getPatientName());
			queueLog.setReferralConcept(queue.getReferralConcept());
			queueLog.setReferralConceptName(queue.getReferralConceptName());
			queueLog.setSex(queue.getSex());
			queueLog.setUser(Context.getAuthenticatedUser());
			queueLog.setStatus("processed");
			queueLog.setBirthDate(encounter.getPatient().getBirthdate());
			queueLog.setEncounter(encounter);
			queueLog.setCategory(queue.getCategory());
			queueLog.setVisitStatus(queue.getVisitStatus());
			queueLog.setTriageDataId(queue.getTriageDataId());

			queueService.saveOpdPatientQueueLog(queueLog);
			queueService.deleteOpdPatientQueue(queue);
		}
	}
	private String getPreviousPhysicalExamination(int patientId){
		String previousPhysicalExamination = "";
		Patient patient = Context.getPatientService().getPatient(patientId);
		PatientQueueService queueService = Context.getService(PatientQueueService.class);
		Concept conceptPhysicalExamination = Context.getConceptService().getConceptByUuid("1391AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Encounter physicalExaminationEncounter = queueService.getLastOPDEncounter(patient);

		if(physicalExaminationEncounter!=null) {

			Set<Obs> allPhysicalExaminationEncounterObs = physicalExaminationEncounter.getAllObs();

			for (Obs ob : allPhysicalExaminationEncounterObs) {
				if (ob.getConcept().equals(conceptPhysicalExamination)) {
					previousPhysicalExamination = ob.getValueText();
				}
			}
		}

		return  previousPhysicalExamination;
	}

    private String getPreviousIllnessHistory(int patientId){
        String previousIllnessHistory = "";
        Patient patient = Context.getPatientService().getPatient(patientId);
        PatientQueueService queueService = Context.getService(PatientQueueService.class);
        Concept conceptPreviousIllnessHistory = Context.getConceptService().getConceptByUuid("1390AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        Encounter previousIllnessHistoryEncounter = queueService.getLastOPDEncounter(patient);
        if (previousIllnessHistoryEncounter != null){
            Set<Obs> allPreviousIllnessHistoryObs = previousIllnessHistoryEncounter.getAllObs();

            for (Obs obs :allPreviousIllnessHistoryObs){
                if (obs.getConcept().equals(conceptPreviousIllnessHistory )){
                    previousIllnessHistory = obs.getValueText();
                }
            }
        }

        return previousIllnessHistory;
    }

    private String getPreviousDateOfOnSetOfIlliness(int patientId){
		String previousOnSetDate = "";
		Patient patient = Context.getPatientService().getPatient(patientId);
		PatientQueueService queueService = Context.getService(PatientQueueService.class);
		Concept onSetConcepts = Context.getConceptService().getConceptByUuid("164428AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Encounter previousOnSetDateEncounter = queueService.getLastOPDEncounter(patient);
		if(previousOnSetDateEncounter != null) {
			Set<Obs> allPreviousObs = previousOnSetDateEncounter.getAllObs();
			for(Obs obs :allPreviousObs) {
				if (obs.getConcept().equals(onSetConcepts )){
					previousOnSetDate = Utils.getDateAsString(obs.getValueDatetime(), "dd/MM/yyyy");
				}
			}
		}
		return previousOnSetDate;

	}

	public void saveInvestigations(Encounter encounter, String departmentName, Investigation investigation) throws Exception {
		Concept investigationConcept = Context.getConceptService().getConceptByUuid("0179f241-8c1d-47c1-8128-841f6508e251");
		if (investigationConcept == null) {
			throw new Exception("Investigation concept null");
		}
		BillableService billableService = Context.getService(BillingService.class).getServiceByConceptId(investigation.getId());
		OpdTestOrder opdTestOrder = new OpdTestOrder();
		opdTestOrder.setPatient(encounter.getPatient());
		opdTestOrder.setEncounter(encounter);
		opdTestOrder.setConcept(investigationConcept);
		opdTestOrder.setTypeConcept(DepartmentConcept.TYPES[2]);
		opdTestOrder.setValueCoded(Context.getConceptService().getConcept(investigation.getId()));
		opdTestOrder.setCreator(encounter.getCreator());
		opdTestOrder.setCreatedOn(encounter.getDateCreated());
		opdTestOrder.setBillableService(billableService);
		opdTestOrder.setScheduleDate(encounter.getDateCreated());
		opdTestOrder.setFromDept(departmentName);
		if (billableService.getPrice() != null && billableService.getPrice().compareTo(BigDecimal.ZERO) == 0) {
			opdTestOrder.setBillingStatus(1);
		}

		PersonAttributeType patientCategoryAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(
				"09cd268a-f0f5-11ea-99a8-b3467ddbf779");
		PersonAttributeType payingCategoryAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(
				"972a32aa-6159-11eb-bc2d-9785fed39154");

		PersonAttribute patientCategoryAttribute = encounter.getPatient().getAttribute(patientCategoryAttributeType);
		PersonAttribute payingCategoryAttribute = encounter.getPatient().getAttribute(payingCategoryAttributeType);

		if((patientCategoryAttribute != null && patientCategoryAttribute.getValue().equals("Non paying")) ||
				(payingCategoryAttribute != null && payingCategoryAttribute.getValue().equals("NHIF patient"))) {
			opdTestOrder.setBillingStatus(1);
		}

		opdTestOrder = Context.getService(PatientDashboardService.class).saveOrUpdateOpdOrder(opdTestOrder);

		processInvestigationsForBillingFree(opdTestOrder, encounter.getLocation());
	}

	private void processInvestigationsForBillingFree(OpdTestOrder opdTestOrder, Location encounterLocation) {
		String radiologyClass = "8caa332c-efe4-4025-8b18-3398328e1323";
		String labSet = "8d492026-c2cc-11de-8d13-0010c6dffd0f";
		String test = "8d4907b2-c2cc-11de-8d13-0010c6dffd0f";

		if(opdTestOrder.getBillingStatus() == 1) {
			if (opdTestOrder.getValueCoded().getConceptClass().getUuid().equals(labSet) || opdTestOrder.getValueCoded().getConceptClass().getUuid().equals(test)) {
				EncounterType labEncounterType = Context.getEncounterService().getEncounterTypeByUuid(EhrCommonMetadata._EhrEncounterTypes.LABENCOUNTER);
				Encounter encounter = getInvestigationEncounter(opdTestOrder,
						encounterLocation, labEncounterType);

				String labOrderTypeId = Context.getAdministrationService().getGlobalProperty(BillingConstants.GLOBAL_PROPRETY_LAB_ORDER_TYPE);
				generateInvestigationOrder(opdTestOrder, encounter, labOrderTypeId);
				Context.getEncounterService().saveEncounter(encounter);
			}

			if (opdTestOrder.getValueCoded().getConceptClass().getUuid().equals(radiologyClass)) {
				EncounterType radiologyEncounterType = Context.getEncounterService().getEncounterTypeByUuid(EhrCommonMetadata._EhrEncounterTypes.RADIOLOGYENCOUNTER);
				Encounter encounter = getInvestigationEncounter(opdTestOrder,
						encounterLocation, radiologyEncounterType);

				String labOrderTypeId = Context.getAdministrationService().getGlobalProperty(BillingConstants.GLOBAL_PROPRETY_RADIOLOGY_ORDER_TYPE);
				generateInvestigationOrder(opdTestOrder, encounter, labOrderTypeId);
				Context.getEncounterService().saveEncounter(encounter);
			}
		}

	}

	private Encounter getInvestigationEncounter(OpdTestOrder opdTestOrder,
												Location encounterLocation, EncounterType encounterType) {
		List<Encounter> investigationEncounters = Context.getEncounterService().getEncounters(opdTestOrder.getPatient(), null, opdTestOrder.getCreatedOn(), null, null, Arrays.asList(encounterType), null, null, null, false);
		Encounter encounter = null;
		if (investigationEncounters.size() > 0) {
			encounter = investigationEncounters.get(0);
		} else {
			encounter = new Encounter();
			encounter.setCreator(opdTestOrder.getCreator());
			encounter.setLocation(encounterLocation);
			encounter.setDateCreated(opdTestOrder.getCreatedOn());
			encounter.setEncounterDatetime(opdTestOrder.getCreatedOn());
			encounter.setEncounterType(encounterType);
			encounter.setPatient(opdTestOrder.getPatient());
			encounter.setProvider(EhrConfigsUtils.getDefaultEncounterRole(), EhrConfigsUtils.getProvider(opdTestOrder.getCreator().getPerson()));
		}
		return encounter;
	}

	private void generateInvestigationOrder(OpdTestOrder opdTestOrder,
											Encounter encounter, String orderTypeId) {
		Order order = new TestOrder();
		order.setConcept(opdTestOrder.getValueCoded());
		order.setCreator(opdTestOrder.getCreator());
		order.setDateCreated(opdTestOrder.getCreatedOn());
		order.setOrderer(EhrConfigsUtils.getProvider(opdTestOrder.getCreator().getPerson()));
		order.setPatient(opdTestOrder.getPatient());
		order.setDateActivated(new Date());
		order.setAccessionNumber("0");
		order.setOrderType(Context.getOrderService().getOrderTypeByUuid("52a447d3-a64a-11e3-9aeb-50e549534c5e"));
		order.setCareSetting(Context.getOrderService().getCareSettingByUuid("6f0c9a92-6f24-11e3-af88-005056821db0"));
		order.setEncounter(encounter);
		encounter.addOrder(order);
	}

	private void saveProcedures(Encounter encounter, String departmentName, Procedure procedure) throws Exception {
		Concept procedureConcept = Context.getConceptService().getConceptByUuid("1651AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		if (procedureConcept == null) {
			throw new Exception("Post for procedure concept null");
		}
		BillableService billableService = Context.getService(BillingService.class).getServiceByConceptId(procedure.getId());
		OpdTestOrder opdTestOrder = new OpdTestOrder();
		opdTestOrder.setPatient(encounter.getPatient());
		opdTestOrder.setEncounter(encounter);
		opdTestOrder.setConcept(procedureConcept);
		opdTestOrder.setTypeConcept(DepartmentConcept.TYPES[1]);
		opdTestOrder.setValueCoded(Context.getConceptService().getConcept(procedure.getId()));
		opdTestOrder.setCreator(encounter.getCreator());
		opdTestOrder.setCreatedOn(encounter.getDateCreated());
		opdTestOrder.setBillableService(billableService);
		opdTestOrder.setScheduleDate(encounter.getDateCreated());
		opdTestOrder.setFromDept(departmentName);
		if (billableService.getPrice() != null && billableService.getPrice().compareTo(BigDecimal.ZERO) == 0) {
			opdTestOrder.setBillingStatus(1);
		}
		PersonAttributeType patientCategoryAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(
				"09cd268a-f0f5-11ea-99a8-b3467ddbf779");
		PersonAttributeType payingCategoryAttributeType = Context.getPersonService().getPersonAttributeTypeByUuid(
				"972a32aa-6159-11eb-bc2d-9785fed39154");

		PersonAttribute patientCategoryAttribute = encounter.getPatient().getAttribute(patientCategoryAttributeType);
		PersonAttribute payingCategoryAttribute = encounter.getPatient().getAttribute(payingCategoryAttributeType);

		if((patientCategoryAttribute != null && patientCategoryAttribute.getValue().equals("Non paying")) ||
				(payingCategoryAttribute != null && payingCategoryAttribute.getValue().equals("NHIF patient"))) {
			opdTestOrder.setBillingStatus(1);
		}

		Context.getService(PatientDashboardService.class).saveOrUpdateOpdOrder(opdTestOrder);
	}

	private void addObsForProcedures(Encounter encounter, Obs obsGroup, Procedure procedure) {
		Obs obsProcedure = new Obs();
		obsProcedure.setObsGroup(obsGroup);
		obsProcedure.setConcept(Context.getConceptService().getConceptByUuid("1651AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		obsProcedure.setValueCoded(Context.getConceptService().getConcept(procedure.getId()));
		obsProcedure.setCreator(encounter.getCreator());
		obsProcedure.setDateCreated(encounter.getDateCreated());
		obsProcedure.setEncounter(encounter);
		obsProcedure.setPerson(encounter.getPatient());
		encounter.addObs(obsProcedure);
	}

	private Visit getLastVisitForPatient(Patient patient) {
		VisitService visitService = Context.getVisitService();
        List<Visit> activeVisitsByPatient = visitService.getActiveVisitsByPatient(patient);
        if (activeVisitsByPatient == null){
			try {
				throw new Exception("patient not checked-in");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (activeVisitsByPatient.size()>0) {
            return activeVisitsByPatient.get(0);
        }
        return null;
	}
}
