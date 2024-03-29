(function(emrPlusConstants){
    emrPlusConstants.OTHER_SYMPTOM_ID = 1000033
    emrPlusConstants.OTHER_SYMPTOM_UID = "cb46888c-586a-4ba0-98d5-f2e7e49a60f6"
})(window.emrPlusConstants = window.emrPlusConstants || {})

function Note(noteObj) {
	var self = this;
	self.patientId = noteObj.patientId;
	self.queueId = noteObj.queueId;
	self.opdId = noteObj.opdId;
	self.opdLogId = noteObj.opdLogId;
	self.signs = ko.observableArray([]);
	self.diagnoses = ko.observableArray([]);
	self.procedures = ko.observableArray([]);
	self.investigations = ko.observableArray([]);
	self.mdtMembers = ko.observableArray([]);
	self.drugs = ko.observableArray([]);
	self.chemoPrograms = ko.observableArray([]);
	self.frequencyOpts = ko.observableArray([]);
    self.drugUnitsOptions= ko.observableArray([]);
	self.referralReasonsOptions = ko.observableArray([]);
	self.admitted = noteObj.admitted;
	self.illnessHistory = noteObj.illnessHistory;
	self.physicalExamination = noteObj.physicalExamination;
	self.otherInstructions = noteObj.otherInstructions;
	self.mdtInstructions = noteObj.mdtInstructions;
	self.mdtDiscussed = noteObj.mdtDiscussed;
	self.cancerGrading = noteObj.cancerGrading;
	self.cancerStaging = noteObj.cancerStaging;
	self.familyHistoryAnswer = noteObj.familyHistoryAnswer;
	self.lastLmp = noteObj.lastLmp;
	self.cancerType= noteObj.cancerType;
	self.relationshipToPatient = noteObj.relationshipToPatient;
	self.ageAtDiagnosis = noteObj.ageAtDiagnosis;
	self.parity = noteObj.parity;
	self.currentlyBreastFeedingAnswer = noteObj.currentlyBreastFeedingAnswer;
	self.currentContraceptiveUseAnswer = noteObj.currentContraceptiveUseAnswer;
	self.cervicalCancerScreeningAnswer = noteObj.cervicalCancerScreeningAnswer;
	self.cervicalCancerScreeningType = noteObj.cervicalCancerScreeningType;
	self.cervicalCancerScreeningDate = noteObj.cervicalCancerScreeningDate;
	self.breastCancerScreeningAnswer = noteObj.breastCancerScreeningAnswer;
	self.breastCancerScreeningType = noteObj.breastCancerScreeningType;
	self.breastCancerScreeningDate = noteObj.breastCancerScreeningDate;
	self.colorectalCancerScreeningAnswer = noteObj.colorectalCancerScreeningAnswer;
	self.colorectalCancerScreeningType = noteObj.colorectalCancerScreeningType;
	self.colorectalCancerScreeningDate = noteObj.colorectalCancerScreeningDate;
	self.prostrateCancerScreeningAnswer = noteObj.prostrateCancerScreeningAnswer;
	self.prostrateCancerScreeningType = noteObj.prostrateCancerScreeningType;
	self.prostrateCancerScreeningDate = noteObj.prostrateCancerScreeningDate;
	self.retinoblastomaState = noteObj.retinoblastomaState;
	self.cigaretteUsageAnswer = noteObj.cigaretteUsageAnswer;
	self.cigarettesPerDay = noteObj.cigarettesPerDay;
	self.yearsSmokedCigarette = noteObj.yearsSmokedCigarette;
	self.tobaccoUsageAnswer = noteObj.tobaccoUsageAnswer;
	self.alcoholUsageAnswer = noteObj.alcoholUsageAnswer;
	self.alcoholIntakeFrequency = noteObj.alcoholIntakeFrequency;
	self.physicalActivityAnswer = noteObj.physicalActivityAnswer;
	self.radiotherapyExposureAnswer = noteObj.radiotherapyExposureAnswer;
	self.comments = noteObj.comments;
	self.presentingComplains = noteObj.presentingComplains;
	self.historyOfPresentIllness = noteObj.historyOfPresentIllness;
	self.pastMedicalSurgicalHistory = noteObj.pastMedicalSurgicalHistory;
	self.cns = noteObj.cns;
	self.cvs = noteObj.cvs;
	self.rs = noteObj.rs;
	self.gus = noteObj.gus;
	self.mss = noteObj.mss;
	self.generalExamination = noteObj.generalExamination;
	self.jaundiceExamination = noteObj.jaundiceExamination;
	self.anaemiaExamination = noteObj.anaemiaExamination;
	self.cyanosisExamination = noteObj.cyanosisExamination;
	self.clubbingExamination = noteObj.clubbingExamination;
	self.oedemaExamination = noteObj.oedemaExamination;
	self.dehydrationExamination = noteObj.dehydrationExamination;
	self.palpabilityAnswer = noteObj.palpabilityAnswer;
	self.submandibularAnswer = noteObj.submandibularAnswer;
	self.submandibularComment = noteObj.submandibularComment;
	self.supraciavicularAnswer = noteObj.supraciavicularAnswer;
	self.supraciavicularComment = noteObj.supraciavicularComment;
	self.cervicalExaminationAnswer = noteObj.cervicalExaminationAnswer;
	self.cervicalExaminationComment = noteObj.cervicalExaminationComment;
	self.axillaryExaminationAnswer = noteObj.axillaryExaminationAnswer;
	self.axillaryExaminationComment = noteObj.axillaryExaminationComment;
	self.inguinalExaminationAnswer = noteObj.inguinalExaminationAnswer;
	self.inguinalExaminationComment = noteObj.inguinalExaminationComment;
	self.generalizedLymadenopathyExaminationAnswer = noteObj.generalizedLymadenopathyExaminationAnswer;
	self.generalizedLymadenopathyExaminationComment = noteObj.generalizedLymadenopathyExaminationComment;
	self.otherLymphNodeExaminationAnswer = noteObj.otherLymphNodeExaminationAnswer;
	self.otherLymphNodeExaminationComment = noteObj.otherLymphNodeExaminationComment;
	self.eyeExam = noteObj.eyeExam;
	self.neckExam = noteObj.neckExam;
	self.mouthExam = noteObj.mouthExam;
	self.earExam = noteObj.earExam;
	self.noseExam = noteObj.noseExam;
	self.throatExam = noteObj.throatExam;
	self.rsInspection = noteObj.rsInspection;
	self.rsPalpation = noteObj.rsPalpation;
	self.rsPercussion = noteObj.rsPercussion;
	self.rsAuscultation = noteObj.rsAuscultation;
	self.breastExaminationComment = noteObj.breastExaminationComment;
	self.csInspection = noteObj.csInspection;
	self.csPalpation = noteObj.csPalpation;
	self.csPercussion = noteObj.csPercussion;
	self.csAuscultation = noteObj.csAuscultation;
	self.asInspection = noteObj.asInspection;
	self.asPalpation = noteObj.asPalpation;
	self.asPercussion = noteObj.asPercussion;
	self.asAuscultation = noteObj.asAuscultation;
	self.guInspection = noteObj.guInspection;
	self.guPalpation = noteObj.guPalpation;
	self.skinInspection = noteObj.skinInspection;
	self.nHigherFunctions = noteObj.nHigherFunctions;
	self.nCranialNerves = noteObj.nCranialNerves;
	self.nHead = noteObj.nHead;
	self.nNeck = noteObj.nNeck;
	self.nSensoryLevel = noteObj.nSensoryLevel;
	self.nMusculoskeletal = noteObj.nMusculoskeletal;
	self.facility = noteObj.facility;
	self.dosage = noteObj.dosage;
	self.specify = noteObj.specify;
	self.referredTo;
	self.referralReasons;
	self.onSetDate = noteObj.onSetDate;


	self.outcome = ko.observable();
	self.referredWard = ko.observable();

	this.addSign = function(symptom) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.signs(), function(item) {
			if (symptom.id == emrPlusConstants.OTHER_SYMPTOM_ID && symptom.id === item.id && symptom.label !== item.label) {
				return false;
			} else if (symptom.id === item.id) {
				return true;
			}
		});

		if (!match) {
			this.signs.push(symptom);
		}
	};

	this.removeSign = function(symptom) {
		self.signs.remove(symptom);
		if (self.signs().length === 0) {
            jq("#symptoms-set").val('');
        }
	};

	this.addDiagnosis = function(diagnosis) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.diagnoses(), function(item) {
			return diagnosis.id === item.id;
		});

		if (!match) {
			self.diagnoses.push(diagnosis);
		}
	};

	this.removeDiagnosis = function(diagnosis) {
		self.diagnoses.remove(diagnosis);
		if (self.diagnoses().length === 0) {
            jq("#diagnosis-set").val('');
        }
	};

	this.addProcedure = function(procedure) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.procedures(), function(item) {
			return procedure.id === item.id;
		});

		if (!match) {
			self.procedures.push(procedure);
		}
	};

	this.removeProcedure = function(procedure) {
		self.procedures.remove(procedure);
		if (self.procedures().length === 0) {
            jq("#procedure-set").val('');
        }
	};

	this.addInvestigation = function(investigation) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.investigations(), function(item) {
			return investigation.id === item.id;
		});

		if (!match) {
			self.investigations.push(investigation);
		}
	};
	this.addMdtMember = function(mdtMember) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.mdtMembers(), function(item) {
			return mdtMember.label === item.label;
		});

		if (!match) {
			self.mdtMembers.push(mdtMember);
		}
	};
	this.addChemoProgram = function(chemoProgram) {
		//check if the item has already been added
		var match = ko.utils.arrayFirst(self.chemoPrograms(), function(item) {
			return chemoProgram.id === item.id;
		});

		if (!match) {
			self.chemoPrograms.push(chemoProgram);
		}
	};

	this.removeChemoProgram = function(chemoProgram) {
		self.chemoPrograms.remove(chemoProgram);
	};
	this.removeInvestigation = function(investigation) {
		self.investigations.remove(investigation);
		if (self.investigations().length === 0) {
            jq("#investigation-set").val('');
        }
	};

	self.getPrescription = function(drugName) {
		var match = ko.utils.arrayFirst(self.drugs(), function(drug) {
			return drug.name().toLowerCase() === drugName.toLowerCase();
		});
		return match;
	}

	this.addPrescription = function(prescription) {
		self.drugs.push(prescription);
	};

	this.removePrescription = function(drug) {
		self.drugs.remove(drug);
		if (self.drugs().length === 0) {
            jq("#drug-set").val('');
        }
	};

	this.refer = function(referTo) {
		self.referral(referTo);
	}

}

function Sign(signObj) {
	this.id = signObj.id;
	this.label = signObj.label;
	this.uuid = signObj.uuid;
	this.qualifiers = ko.observableArray([]);
	this.qualifiers(signObj.qualifiers);
}

function Qualifier(id, label, options, answer) {
	self = this;
	self.id = id
	self.label = label;
	self.options = ko.observableArray(options);
	self.answer = ko.observable(answer);
	self.freeText = ko.observable();
}

function Option(id, label) {
	this.id = id;
	this.label = label;

	this.display = function(data) {
		console.log(data);
	}
}

function Diagnosis(diagnosisObj) {
	this.id = diagnosisObj.id;
	this.label = diagnosisObj.label;
	this.provisional = ko.observable(diagnosisObj.provisional);
}

function Investigation(investigationObj) {
	this.id = investigationObj.id;
	this.label = investigationObj.label;
}
function MdtMember(mdtMemberObj) {
	this.label = mdtMemberObj.label;
}
function ChemoProgram(chemoProgramObj) {
	this.id = chemoProgramObj.id;
	this.label = chemoProgramObj.label;
}

function Procedure(procedureObj) {
	this.id = procedureObj.id;
	this.label = procedureObj.label;
	this.schedulable = procedureObj.schedulable;
}

function Drug() {
	var self = this;
	self.drugName = ko.observable();
	self.frequencyOpts = ko.observableArray([]);
	self.drugUnitsOptions = ko.observableArray([]);
	self.drugUnit = ko.observable();
	self.frequency = ko.observable();
	self.formulationOpts = ko.observableArray([]);
	self.formulation = ko.observable();
	self.dosage = ko.observable();
	self.comment = ko.observable();
	self.numberOfDays = ko.observable();
	self.dosageAndUnit = ko.computed(function(){
		return self.dosage() + " " + (self.drugUnit() && self.drugUnit().label);
	});
}

function Frequency(freqObj) {
	this.id = freqObj.id;
	this.label = freqObj.label;
}

function Formulation(formulationObj) {
	this.id = formulationObj.id;
	this.label = formulationObj.label;
}

function DrugUnit(unitObj) {
	this.id = unitObj.id;
	this.label = unitObj.label;
}

function Outcome(outcomeObj) {
	this.option = new Option(outcomeObj.id,outcomeObj.label);
	this.followUpDate = outcomeObj.followUpDate;
	this.admitTo = outcomeObj.admitTo;

	this.updateOutcome = function(data) {
		note.outcome(this);
		jq("#outcome-set").val("outcome set");
		
		var outcomes = note.outcome().option.label
		
		if (jq('#availableReferral').val() > 0 ){			
			outcomes = outcomes+' ('+ jq("#availableReferral option:selected").text()+')'
		}
		
		jq('#summaryTable tr:eq(8) td:eq(1)').text(outcomes);
		return true;
	}
}


function Referral(referralObj) {
	this.id = referralObj.id;
	this.label = referralObj.label;
	this.referral;
}

if (!Array.prototype.find) {
	Array.prototype.find = function(predicate) {
		if (this === null) {
			throw new TypeError(
					'Array.prototype.find called on null or undefined');
		}
		if (typeof predicate !== 'function') {
			throw new TypeError('predicate must be a function');
		}
		var list = Object(this);
		var length = list.length >>> 0;
		var thisArg = arguments[1];
		var value;

		for (var i = 0; i < length; i++) {
			value = list[i];
			if (predicate.call(thisArg, value, i, list)) {
				return value;
			}
		}
		return undefined;
	};
}

function jsonEscape(str)  {
	return str.replace(/\t/g, "\\t").replace(/\r?\n/g, "\\r\\n");
}
