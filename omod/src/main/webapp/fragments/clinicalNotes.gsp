<%
	ui.includeCss("uicommons", "datetimepicker.css")
	ui.includeCss("ehrconfigs", "onepcssgrid.css")
	ui.includeCss("ehrconfigs", "custom.css")
	ui.includeJavascript("patientdashboardapp", "note.js")
	ui.includeJavascript("uicommons", "datetimepicker/bootstrap-datetimepicker.min.js")
	ui.includeJavascript("uicommons", "handlebars/handlebars.min.js")
	ui.includeJavascript("uicommons", "navigator/validators.js")
	ui.includeJavascript("uicommons", "navigator/navigator.js")
	ui.includeJavascript("uicommons", "navigator/navigatorHandlers.js")
	ui.includeJavascript("uicommons", "navigator/navigatorModels.js")
	ui.includeJavascript("uicommons", "navigator/navigatorTemplates.js")
	ui.includeJavascript("uicommons", "navigator/exitHandlers.js")
	ui.includeJavascript("patientdashboardapp", "knockout-3.4.0.js")

	def fields = [
			[
					id: "facility",
					label: "",
					formFieldName: "facility",
					class: org.openmrs.Location
			]
	]
%>

${ ui.includeFragment("patientdashboardapp", "patientDashboardAppScripts", [note: note, listOfWards: listOfWards, internalReferralSources: internalReferralSources, externalReferralSources: externalReferralSources, referralReasonsSources: referralReasonsSources, outcomeOptions: outcomeOptions ]) }

<style>
.dialog textarea{
	resize: none;
}
.mdt-row {
    padding: 10px;
    margin: 10px;
    display: flex;
    flex-flow: row;
    justify-content: space-between;
}

.dialog li label span {
	color: #f00;
	float: right;
	margin-right: 10px;
}
.icon-remove{
	cursor: pointer!important;
}
.diagnosis-carrier-div{
	border-width: 1px 1px 1px 10px;
	border-style: solid;
	border-color: #404040;
	padding: 0px 10px 3px;
}
#diagnosis-carrier input[type="radio"] {
	-webkit-appearance: checkbox;
	-moz-appearance: checkbox;
	-ms-appearance: checkbox;
}
#prescriptionAlert {
	text-align: center;
	border:     1px #f00 solid;
	color:      #f00;
	padding:    5px 0;
}
.alert{
	position: relative;
	padding: .75rem 1.25rem;
	margin-bottom: 1rem;
	border: 1px solid transparent;
	border-top-color: transparent;
	border-right-color: transparent;
	border-bottom-color: transparent;
	border-left-color: transparent;
	border-top-color: transparent;
	border-right-color: transparent;
	border-bottom-color: transparent;
	border-left-color: transparent;
	border-radius: .25rem;
	color: #721c24;
	background-color: #f8d7da;
	border-color: #f5c6cb;
}
.inner-date input[type="date"]{
	padding: 5px 0px !important;
	min-width: 100% !important;
	height: 27px !important;
	border: 1px solid #aaa !important;
	margin-top: 0 !important;
}
</style>

<div id="content">
	<form method="post" id="notes-form" class="simple-form-ui">
		<section>
			<span class="title">Patient History</span>
			<fieldset class="no-confirmation">
				<legend>Family History</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col12">
						<label for="family-members-with-cancer">Any family members with cancer?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% familyHistoryAnswers.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: familyHistoryAnswer" value="${answer.answerConcept.id}" name="familyHistoryAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						 <% } %>
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Degree of relation (List all)<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<div class="input-position-class">
                            <select id="degree-of-relation" name="degree-of-relation" autocomplete="on" data-bind="value: \$root.relationshipToPatient">
								<option value="">-- Select Relation --</option> 
								<% familyMemberAnswers.each { answer -> %>
									<option value="${answer.answerConcept.id}">${answer.answerConcept.getName()}</option>
								<% } %>
                            </select>
                        </div>
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Age at Diagnosis<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.ageAtDiagnosis" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col11">
						<label for="cancer-type">Cancer Type<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<div class="input-position-class">
                            <select id="cancer-type" name="cancer-type" autocomplete="on" data-bind="value: \$root.cancerType">
								<option value="">-- Select Cancer Type --</option> 
								<% cancerTypes.each { answer -> %>
									<option value="${answer.id}">${answer.name}</option>
								<% } %>
                            </select>
                        </div>
					</div>
				</div>
				<p>
					<input type="hidden" id="family-history-set" />
				</p>
			</fieldset>
			<% if (patient.gender == "F" && patient.age > 12){ %>
				<fieldset class="no-confirmation">
					<legend>Female History</legend>
					<div style="padding: 0 4px; margin-bottom:60px">
						<div class="col6 inner-date">
							<label for="last-lmp">Last LMP<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<input type="date" data-bind="value: \$root.lastLmp" id="last-lmp" name="last-lmp" />
						</div>
						<div class="col5">
							<label for="parity">Parity<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<input type="text" data-bind="value: \$root.parity" id="parity" name="parity" />
						</div>
					</div>
					<div>&nbsp;</div> 
					<div class="col12" style="padding: 0 4px; margin-bottom:40px">
						<div class="co12">
							<label for="currently-breastfeeding">Are you currently breastfeeding?<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
						</div>
						<div class="col12">
							<% currentlyBreastFeedingAnswers.each { answer -> %>
								<div class="radios col3">
									<label>
										<input data-bind="checked: currentlyBreastFeedingAnswer" value="${answer.answerConcept.id}" name="currentlyBreastFeedingAnswer" type="radio">
										<label>${answer.answerConcept.getName()}</label>
									</label>
								</div>
							<% } %>
						</div>
					</div>
					<div>&nbsp;</div> 
					<div style="padding: 100 4px;margin-bottom:40px">
						<div class="col12">
							<label for="currently-on-contraceptives">Are you currently using contraceptives/hormonal replacement therapy?<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
						</div>
						<div class="col12">
							<% currentContraceptiveUseAnswers.each { answer -> %>
								<div class="radios col3">
									<label>
										<input data-bind="checked: cervicalCancerScreeningAnswer" value="${answer.answerConcept.id}" name="currentContraceptiveUseAnswer" type="radio">
										<label>${answer.answerConcept.getName()}</label>
									</label>
								</div>
							<% } %>
						</div>
					</div>
					<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
						<label for="screened-before-answer-div" class="label">Have you ever been screened before for:(select all that apply) <span class="important">*</span></label>
						<div class="col12">
							<label for="screened-before-answer">Cervical Cancer:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<% cervicalCancerScreeningAnswers.each { answer -> %>
								<div class="radios col3">
									<label>
										<input data-bind="checked: cervicalCancerScreeningAnswer" value="${answer.answerConcept.id}" name="cervicalCancerScreeningAnswer" type="radio">
										<label>${answer.answerConcept.getName()}</label>
									</label>
								</div>
							<% } %>
						</div>
						<div class="col6">
							<label for="types-of-screening">Types of Screening<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<select id="type-of-cervical-cancer-screening" name="type-of-cervical-cancer-screening" autocomplete="on" data-bind="value: \$root.cervicalCancerScreeningType">
								<option value="">-- Select Relation --</option> 
								
							</select>
						</div>
						<div class="col5 inner-date">
							<label for="screened-date">Date<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<input type="date" id="cervicalCancerScreeningDate" name="cervicalCancerScreeningDate" data-bind="value: \$root.cervicalCancerScreeningDate"/>
						</div>
					</div>
					<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
						<div class="col12">
							<label for="female-screened-for-breastcancer-answer">Breast Cancer:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<% breastCancerScreeningAnswers.each { answer -> %>
								<div class="radios col3">
									<label>
										<input data-bind="checked: breastCancerScreeningAnswer" value="${answer.answerConcept.id}" name="breastCancerScreeningAnswer" type="radio">
										<label>${answer.answerConcept.getName()}</label>
									</label>
								</div>
							<% } %>
						</div>
						<div class="col6">
							<label for="types-screened-for-breastcancer">Types of Screening<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<select id="types-screened-for-breastcancer" name="types-screened-for-breastcancer" autocomplete="on" data-bind="value: \$root.breastCancerScreeningType">
								<option value="">-- Select Relation --</option> 
								
							</select>
						</div>
						<div class="col5 inner-date">
							<label for="date-screened-for-breastcancer">Date<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<input type="date" id="breastCancerScreeningDate" name="breastCancerScreeningDate" data-bind="value: \$root.breastCancerScreeningDate"/>
						</div>
					</div>
					<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
						<div class="col12">
							<label for="female-screened-colorectal-answer">Colorectal Cancer:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<% colorectalCancerScreeningAnswers.each { answer -> %>
								<div class="radios col3">
									<label>
										<input data-bind="checked: colorectalCancerScreeningAnswer" value="${answer.answerConcept.id}" name="colorectalCancerScreeningAnswer" type="radio">
										<label>${answer.answerConcept.getName()}</label>
									</label>
								</div>
							<% } %>
						</div>
						<div class="col6">
							<label for="types-female-screened-colorectalcancer">Types of Screening<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<select id="types-female-screened-colorectalcancer" name="types-female-screened-colorectalcancer" autocomplete="on" data-bind="value: \$root.colorectalCancerScreeningType">
								<option value="">-- Select Relation --</option> 
								
							</select>
						</div>
						<div class="col5 inner-date">
							<label for="date-female-screened-colorectalcancer">Date<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
							<input type="date" id="colorectalCancerScreeningDate" name="colorectalCancerScreeningDate" data-bind="value: \$root.colorectalCancerScreeningDate"/>
						</div>
					</div>
					<p>
						<input type="hidden" id="female-history-set" />
					</p>
				</fieldset>
			<% } %>
			<fieldset class="no-confirmation">
				<legend>Male History</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<label for="place-of-residence" class="label">Have you ever been screened before for:(select all that apply) <span class="important">*</span></label>
					<div class="col12">
						<label for="screened-prostrate-cancer">Prostrate Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% prostrateCancerScreeningAnswers.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: prostrateCancerScreeningAnswer" value="${answer.answerConcept.id}" name="prostrateCancerScreeningAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>
					</div>
					<div class="col6">
						<label for="types-screened-prostratecancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<select id="types-screened-prostratecancer" name="types-screened-prostratecancer" autocomplete="on" data-bind="value: \$root.prostrateCancerScreeningType">
							<option value="">-- Select Relation --</option> 
							
						</select>
					</div>
					<div class="col5 inner-date">
						<label for="date-screened-prostratecancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="prostrateCancerScreeningDate" name="prostrateCancerScreeningDate" data-bind="value: \$root.prostrateCancerScreeningDate"/>
					</div>
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col12">
						<label for="female-screened-colorectal-answer">Colorectal Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% colorectalCancerScreeningAnswers.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: colorectalCancerScreeningAnswer" value="${answer.answerConcept.id}" name="colorectalCancerScreeningAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>
					</div>
					<div class="col6">
						<label for="types-female-screened-colorectalcancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<select id="types-female-screened-colorectalcancer" name="types-female-screened-colorectalcancer" autocomplete="on" data-bind="value: \$root.colorectalCancerScreeningType">
							<option value="">-- Select Relation --</option> 
							
						</select>
					</div>
					<div class="col5 inner-date">
						<label for="date-female-screened-colorectalcancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="colorectalCancerScreeningDate" name="colorectalCancerScreeningDate" data-bind="value: \$root.colorectalCancerScreeningDate"/>
					</div>
				</div>
				<div style="padding: 100 4px;margin-bottom:40px">
					<div class="col6">
						<label for="currently-on-hormanal-therapy">Are you currently on hormonal therapy?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col5">
						<input type="text" id="currently-on-hormanal-therapy" name="currently-on-hormanal-therapy" />
					</div>
				</div>
				<div>&nbsp;</div> 
				<div class="col12" style="padding: 100 4px; padding-bottom:20px;">
					<label for="male-hormonal-therapy-type">If yes, specify type:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
					<% currentContraceptiveUseAnswers.each { answer -> %>
						<div class="radios col3">
							<label>
								<input data-bind="checked: cervicalCancerScreeningAnswer" value="${answer.answerConcept.id}" name="currentContraceptiveUseAnswer" type="radio">
								<label>${answer.answerConcept.getName()}</label>
							</label>
						</div>
					<% } %>
				</div>
				<p>
					<input type="hidden" id="male-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Child History</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="signs-of-retinoblasoma">Any signs of Retinoblastoma?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<% retinoblastomaSigns.each { answer -> %>
						<div class="radios col3">
							<label>
								<input data-bind="checked: retinoblastomaState" value="${answer.answerConcept.id}" name="retinoblastomaState" type="radio">
								<label>${answer.answerConcept.getName()}</label>
							</label>
						</div>
					<% } %>			
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Risk Factor</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col12">
						<label for="screened-prostrate-cancer">Do you smoke cigarettes?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% cigaretteUsage.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: cigaretteUsageAnswer" value="${answer.answerConcept.id}" name="cigaretteUsageAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col5">
						<label for="date-screened-prostratecancer">If yes, how many cigarrets per day?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="cigarettesPerDay" name="cigarettesPerDay" data-bind="value: \$root.cigarettesPerDay" />
					</div>
					<div class="col6">
						<label for="screened-prostrate-cancer">For how many many years?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="yearsSmokedCigarette" name="yearsSmokedCigarette" data-bind="value: \$root.yearsSmokedCigarette" />
					</div>
					<div class="col12">
						<label for="date-screened-prostratecancer">Do you use other forms of tobacco?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% tobaccoUsage.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: tobaccoUsageAnswer" value="${answer.answerConcept.id}" name="tobaccoUsageAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col12">
						<label for="screened-prostrate-cancer">Do you take alcohol?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% alcoholUsage.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: alcoholUsageAnswer" value="${answer.answerConcept.id}" name="alcoholUsageAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col5">
						<label for="alcoholIntakeFrequency">What is the frequency(bottles per day)?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="alcoholIntakeFrequency" name="alcoholIntakeFrequency" data-bind="value: \$root.alcoholIntakeFrequency" />
					</div>
					<div class="col12">
						<label for="physicalActivity">Are you physically active?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% physicalActivity.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: physicalActivityAnswer" value="${answer.answerConcept.id}" name="physicalActivityAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col12">
						<label for="types-of-screening">Previous exposure to radiation (radiotherapy?)<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% radiotherapyExposure.each { answer -> %>
							<div class="radios col3">
								<label>
									<input data-bind="checked: radiotherapyExposureAnswer" value="${answer.answerConcept.id}" name="radiotherapyExposureAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Clinicals</legend>
				<p class="input-position-class">
					<label>Presenting Complains</label>
					<textarea data-bind="value: \$root.presentingComplains" id="presentingComplains" name="presentingComplains" rows="2" cols="74"></textarea>
				</p>
				<p class="input-position-class">
					<label>History of Present Illness</label>
					<textarea data-bind="value: \$root.historyOfPresentIllness" id="historyOfPresentIllness" name="historyOfPresentIllness" rows="2" cols="74"></textarea>
				</p>
				<p class="input-position-class">
					<label>Past Medical and Surgical History</label>
					<textarea data-bind="value: \$root.pastMedicalSurgicalHistory" id="pastMedicalSurgicalHistory" name="pastMedicalSurgicalHistory" rows="2" cols="74"></textarea>
				</p>
				<label for="cns" class="label">Review of Systems</label>
				<div class="col11">
					<label for="cns">CNS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.cns" id="cns" name="cns" />
				</div>
				<div class="col11">
					<label for="cvs">CVS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.cvs" id="cvs" name="cvs" />
				</div>
				<div class="col11">
					<label for="rs">RS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.rs" id="rs" name="rs" />
				</div>
				<div class="col11">
					<label for="gus">GUS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.gus" id="gus" name="gus" />
				</div>
				<div class="col11">
					<label for="mss">MSS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.mss" id="mss" name="mss" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
		</section>
		<section>
			<span class="title">General Examination</span>
			<fieldset class="no-confirmation">
				<legend>Performance Status</legend>
				<div class="col11">
					<label for="types-of-screening">Physical Examination:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.generalExamination" id="generalExamination" name="generalExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Jaundice:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.jaundiceExamination" id="jaundiceExamination" name="jaundiceExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Anaemia:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.anaemiaExamination" id="anaemiaExamination" name="anaemiaExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Cyanosis:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.cyanosisExamination" id="cyanosisExamination" name="cyanosisExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Clubbing:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.clubbingExamination" id="clubbingExamination" name="clubbingExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Oedema:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.oedemaExamination" id="oedemaExamination" name="oedemaExamination" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Dehydration:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.dehydrationExamination" id="dehydrationExamination" name="dehydrationExamination" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Lymph Node Exam</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col10">
						<label for="family-members-with-cancer">Palpable or Non-palpable?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% palpability.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: palpabilityAnswer" value="${answer.answerConcept.id}" name="palpabilityAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
				</div>
				<label for="screened-before-answer-div">If yes, Indicate location and characteristics below:</label>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="submandibular">Submandibular<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% submandibularExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: submandibularAnswer" value="${answer.answerConcept.id}" name="submandibularAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="submandibularComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.submandibularComment" id="submandibularComment" name="submandibularComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Supraciavicular<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% supraciavicularExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: supraciavicularAnswer" value="${answer.answerConcept.id}" name="supraciavicularAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="supraciavicularComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.supraciavicularComment" id="supraciavicularComment" name="supraciavicularComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Cervical<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% cervicalExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: cervicalExaminationAnswer" value="${answer.answerConcept.id}" name="cervicalExaminationAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="cervicalExaminationComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.cervicalExaminationComment" id="cervicalExaminationComment" name="cervicalExaminationComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Axillary<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% axillaryExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: axillaryExaminationAnswer" value="${answer.answerConcept.id}" name="axillaryExaminationAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="axillaryExaminationComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.axillaryExaminationComment" id="axillaryExaminationComment" name="axillaryExaminationComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Inguinal<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% inguinalExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: inguinalExaminationAnswer" value="${answer.answerConcept.id}" name="inguinalExaminationAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="inguinalExaminationComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.inguinalExaminationComment" id="inguinalExaminationComment" name="inguinalExaminationComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Generalized Lymadenopathy<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% generalizedLymadenopathyExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: generalizedLymadenopathyExaminationAnswer" value="${answer.answerConcept.id}" name="generalizedLymadenopathyExaminationAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="generalizedLymadenopathyExaminationComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.generalizedLymadenopathyExaminationComment" id="generalizedLymadenopathyExaminationComment" name="generalizedLymadenopathyExaminationComment" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col7">
						<label for="degree-of-relation">Other<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<% otherLymphNodeExamination.each { answer -> %>
							<div class="radios col2">
								<label>
									<input data-bind="checked: otherLymphNodeExaminationAnswer" value="${answer.answerConcept.id}" name="otherLymphNodeExaminationAnswer" type="radio">
									<label>${answer.answerConcept.getName()}</label>
								</label>
							</div>
						<% } %>	
					</div>
					<div class="col4">
						<label for="otherLymphNodeExaminationComment">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" data-bind="value: \$root.otherLymphNodeExaminationComment" id="otherLymphNodeExaminationComment" name="otherLymphNodeExaminationComment" />
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>HEENT</legend>
				<div class="col11">
					<label for="eyeExam">Eyes:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.eyeExam" id="eyeExam" name="eyeExam" />
				</div>
				<div class="col11">
					<label for="neckExam">Neck:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.neckExam" id="neckExam" name="neckExam" />
				</div>
				<div class="col11">
					<label for="mouthExam">Mouth:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.mouthExam" id="mouthExam" name="mouthExam" />
				</div>
				<div class="col11">
					<label for="earExam">Ears:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.earExam" id="earExam" name="earExam" />
				</div>
				<div class="col11">
					<label for="noseExam">Nose:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.noseExam" id="noseExam" name="noseExam" />
				</div>
				<div class="col11">
					<label for="throatExam">Throat:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.throatExam" id="throatExam" name="throatExam" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Respiratory System</legend>
				<div class="col11">
					<label for="rsInspection">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.rsInspection"  id="rsInspection" name="rsInspection" />
				</div>
				<div class="col11">
					<label for="rsPalpation">Palpation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.rsPalpation" id="rsPalpation" name="rsPalpation" />
				</div>
				<div class="col11">
					<label for="rsPercussion">Percussion:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.rsPercussion" id="rsPercussion" name="rsPercussion" />
				</div>
				<div class="col11">
					<label for="rsAuscultation">Auscultation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.rsAuscultation" id="rsAuscultation" name="rsAuscultation" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Breast Examination</legend>
				<p class="input-position-class">
					<label>Comments</label>
					<textarea data-bind="value: \$root.breastExaminationComment" id="breastExaminationComment" name="breastExaminationComment" rows="2" cols="74"></textarea>
				</p>
				
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Cardiovascular System</legend>
				<div class="col11">
					<label for="csInspection">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.csInspection" id="csInspection" name="csInspection" />
				</div>
				<div class="col11">
					<label for="csPalpation">Palpation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.csPalpation" id="csPalpation" name="csPalpation" />
				</div>
				<div class="col11">
					<label for="csPercussion">Percussion:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.csPercussion" id="csPercussion" name="csPercussion" />
				</div>
				<div class="col11">
					<label for="csAuscultation">Auscultation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.csAuscultation" id="csAuscultation" name="csAuscultation" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Abdominal System</legend>
				<div class="col11">
					<label for="asInspection">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.asInspection" id="asInspection" name="asInspection" />
				</div>
				<div class="col11">
					<label for="asPalpation">Palpation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.asPalpation" id="asPalpation" name="asPalpation" />
				</div>
				<div class="col11">
					<label for="asPercussion">Percussion:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.asPercussion" id="asPercussion" name="asPercussion" />
				</div>
				<div class="col11">
					<label for="asAuscultation">Auscultation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.asAuscultation" id="asAuscultation" name="asAuscultation" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Genitourinary</legend>
				<div class="col11">
					<label for="guInspection">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.guInspection" id="guInspection" name="guInspection" />
				</div>
				<div class="col11">
					<label for="guPalpation">Palpation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.guPalpation" id="guPalpation" name="guPalpation" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Skin Exam Findings</legend>
				<div class="col11">
					<label for="skinInspection">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.skinInspection" id="skinInspection" name="skinInspection" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Neurologic</legend>
				<div class="col11">
					<label for="nHigherFunctions">Higher Functions:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nHigherFunctions" id="nHigherFunctions" name="nHigherFunctions" />
				</div>
				<div class="col11">
					<label for="nCranialNerves">Cranial Nerves:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nCranialNerves" id="nCranialNerves" name="nCranialNerves" />
				</div>
				<div class="col11">
					<label for="nHead">Head(Inspect, Palpate):<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nHead" id="nHead" name="nHead" />
				</div>
				<div class="col11">
					<label for="nNeck">Neck:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nNeck" id="nNeck" name="nNeck" />
				</div>
				<div class="col11">
					<label for="nSensoryLevel">Sensory Level:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nSensoryLevel" id="nSensoryLevel" name="nSensoryLevel" />
				</div>
				<div class="col11">
					<label for="nMusculoskeletal">Musculoskeletal:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" data-bind="value: \$root.nMusculoskeletal" id="nMusculoskeletal" name="nMusculoskeletal" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
		</section>
        <section>
            <span class="title"> Pain Assesment</span>
        </section>
		<section>
			<span class="title">Lab Tests</span>
			<fieldset class="no-confirmation">
				<legend>Full Blood Count</legend>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">HBC:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">WBC:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">ANC:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">PLT:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">MCV:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Others:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Liver Function Tests</legend>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Direct Bilirubin:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Indirect Bilirubin:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">AST:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">ALT:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Albumin:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Renal Function Tests</legend>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Sodium:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Potassium:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Chloride:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Urea:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Creatinin:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Bicarbonate:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Specialized Tests</legend>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">BMA:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Urinalysis:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">PBF:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Tumor Markers:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Immunohostpchemistry:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<p class="input-position-class col11">
					<label>Conclusion</label>
					<textarea data-bind="" id="instructions" name="instructions" rows="2" cols="74"></textarea>
				</p>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Imaging</legend>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Mammogram:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">MRI:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">CT Scan:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Ultrasound:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Echocardiography:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">Radiography (Others):<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col3">
						<label for="degree-of-relation">PET Scan:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col4 inner-date">
						<label for="age-of-diagnosis">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
					<div class="col4">
						<label for="age-of-diagnosis">Result:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div class="col6"style="padding: 0 4px; padding-bottom:20px;">
                    <label>Cancer Staging:</label>
                    <label>
                        <input value="grade-one"   name="chk-grade" type="radio" />Stage 1
                    </label>
                    <label>
                        <input value="grade_two"   name="chk-grade" type="radio" />Stage 2
                    </label>
                    <label>
                        <input value="grade-three"   name="chk-grade" type="radio" />Stage 3
                    </label>
                    <label>
                        <input value="grade_four"   name="chk-grade" type="radio" />Stage 4
                    </label>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Continuation Notes</legend>
				<div class="col11 inner-date">
						<label for="date-screened-prostratecancer">Date:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="date" id="date-screened-prostratecancer" name="date-screened-prostratecancer" placeholder="Date of Test" />
				</div>
				<div class="col11">
					<p class="input-position-class">
						<label>Notes: </label>
						<textarea id="continueation-notes" name="continuation-notes" rows="10" cols="74" class=""></textarea>
						<span id="examination-lbl" class="field-error" style="display: none"></span>
					</p>
				</div>
			</fieldset>
		</section>
		${ ui.includeFragment("patientdashboardapp", "consentForms") }
		<section>
			<span class="title">Clinical Notes</span>
			${ ui.includeFragment("patientdashboardapp", "patientDashboard") }
		</section>

		<div id="confirmation" style="width:74.6%; min-height: 400px;">
			<span id="confirmation_label" class="title">Confirmation</span>

			<div class="dashboard">
				<div class="info-section">
					<div class="info-header">
						<i class="icon-list-ul"></i>
						<h3>SUMMARY & CONFIRMATION</h3>
					</div>

					<div class="info-body">
						<table id="summaryTable">
							<tr>
								<td><span class="status active"></span>Symptoms</td>
								<td data-bind="foreach: signs">
									<span data-bind="text: label"></span>
									<span data-bind="if: (\$index() !== (\$parent.signs().length - 1))"><br/></span>
								</td>
							</tr>

							<tr>
								<td><span class="status active"></span>History</td>
								<td>N/A</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Physical Examination</td>
								<td>N/A</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Diagnosis</td>
								<td data-bind="foreach: diagnoses">
									<span data-bind="text: label"></span>
									<span data-bind="if: (\$index() !== (\$parent.diagnoses().length - 1))"><br/></span>
								</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Procedures</td>
								<td>
									<span data-bind="foreach: procedures">
										<span data-bind="text: label"></span>
										<span data-bind="if: (\$index() !== (\$parent.procedures().length - 1))"><br/></span>
									</span>
									<span data-bind="if: (procedures().length === 0)">N/A</span>
								</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Investigations</td>
								<td>
									<span data-bind="foreach: investigations">
										<span data-bind="text: label"></span>
										<span data-bind="if: (\$index() !== (\$parent.investigations().length - 1))"><br/></span>
									</span>
									<span data-bind="if: (investigations().length === 0)">N/A</span>
								</td>
							</tr>
							<tr>
								<td><span class="status active"></span>MDT Members</td>
								<td>
									<span data-bind="foreach: mdtMembers">
										<span data-bind="text: label"></span>
										<span data-bind="if: (\$index() !== (\$parent.mdtMembers().length - 1))"><br/></span>
									</span>
									<span data-bind="if: (mdtMembers().length === 0)">N/A</span>
								</td>
							</tr>
							<tr>
								<td><span class="status active"></span>Programs</td>
								<td>
									<span data-bind="foreach: chemoPrograms">
										<span data-bind="text: label"></span>
										<span data-bind="if: (\$index() !== (\$parent.chemoPrograms().length - 1))"><br/></span>
									</span>
									<span data-bind="if: (chemoPrograms().length === 0)">N/A</span>
								</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Prescriptions</td>
								<td>
									<span data-bind="foreach: drugs">
										<span data-bind="text: drugName()+' '+formulation().label"></span>
										<span data-bind="if: (\$index() !== (\$parent.drugs().length - 1))"><br/></span>
									</span>
									<span data-bind="if: (drugs().length === 0)">N/A</span>
								</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Instructions</td>
								<td>N/A</td>
							</tr>

							<tr>
								<td><span class="status active"></span>Outcome</td>
								<td>N/A</td>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<div id="confirmationQuestion">
				<p style="display: inline;">
					<button class="submitButton confirm" style="float: right">Submit</button>
				</p>

				<button id="cancelButton" class="cancel cancelButton" style="margin-left: 5px;">Cancel</button>
				<p style="display: inline">&nbsp;</p>
			</div>
		</div>
	</form>
</div>
<div id="confirmDialog" class="dialog" style="display: none;">
	<div class="dialog-header">
		<i class="icon-save"></i>

		<h3>Confirm</h3>
	</div>


	<div class="dialog-content">
		<h3>Cancelling will lead to loss of data,are you sure you want to do this?</h3>

		<span  class="button confirm right" style="float: right">Confrim</span>
		<span class="button cancel" >Cancel</span>
	</div>
</div>

<div id="prescription-dialog" class="dialog" style="display:none;">
	<div class="dialog-header">
		<i class="icon-folder-open"></i>

		<h3>Prescription</h3>
	</div>

	<div class="dialog-content">
		<ul>
			<li id="prescriptionAlert">
				<div>No batches found in Pharmacy for the Selected Drug/Formulation combination</div>
			</li>

			<li>
				<label>Drug<span>*</span></label>
				<input class="drug-name" id="drugSearch" type="text" data-bind="value: prescription.drug().drugName, valueUpdate: 'blur'">
			</li>
			<li>
				<label>Dosage<span>*</span></label>
				<input type="text" class="drug-dosage" data-bind="value: prescription.drug().dosage" style="width: 60px!important;">
				<select id="dosage-unit" class="drug-dosage-unit" data-bind="options: prescription.drug().drugUnitsOptions, value: prescription.drug().drugUnit, optionsText: 'label',  optionsCaption: 'Select Unit'" style="width: 191px!important;"></select>
			</li>

			<li>
				<label>Formulation<span>*</span></label>
				<select id="drugFormulation" class="drug-formulation" data-bind="options: prescription.drug().formulationOpts, value: prescription.drug().formulation, optionsText: 'label',  optionsCaption: 'Select Formulation'"></select>
			</li>
			<li>
				<label>Frequency<span>*</span></label>
				<select class="drug-frequency" data-bind="options: prescription.drug().frequencyOpts, value: prescription.drug().frequency, optionsText: 'label',  optionsCaption: 'Select Frequency'"></select>
			</li>

			<li>
				<label>No. 0f Days<span>*</span></label>
				<input type="text" class="drug-number-of-days" data-bind="value: prescription.drug().numberOfDays">
			</li>
			<li>
				<label>Comment</label>
				<textarea class="drug-comment" data-bind="value: prescription.drug().comment"></textarea>
			</li>
		</ul>

		<label class="button confirm right">Confirm</label>
		<label class="button cancel">Cancel</label>
	</div>
</div>

<script>
	var prescription = {drug: ko.observable(new Drug())};
	ko.applyBindings(prescription, jq("#prescription-dialog")[0]);
</script>