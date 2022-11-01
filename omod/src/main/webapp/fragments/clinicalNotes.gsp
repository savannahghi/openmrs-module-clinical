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
</style>

<div id="content">
	<form method="post" id="notes-form" class="simple-form-ui">
		<section>
			<span class="title">Clinical Notes</span>
			<fieldset class="no-confirmation">
				<legend>Family History</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="family-members-with-cancer">Any family members with cancer?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="family-members-with-cancer" name="family-members-with-cancer" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Degree of relation (List all)<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Age at Diagnosis<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col11">
						<label for="cancer-type">Cancer Type<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="cancer-type" name="cancer-type" placeholder="Cancer Type" />
					</div>
				</div>
				<p>
					<input type="hidden" id="family-history-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Female History</legend>
				<div style="padding: 0 4px; margin-bottom:60px">
					<div class="col6">
						<label for="last-lmp">Last LMP<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="last-lmp" name="last-lmp" placeholder="Last LMP" />
					</div>
					<div class="col5">
						<label for="parity">Parity<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="parity" name="parity" placeholder="Parity" />
					</div>
				</div>
				<div>&nbsp;</div> 
				<div style="padding: 0 4px; margin-bottom:40px">
					<div class="col6">
						<label for="currently-breastfeeding">Are you currently breastfeeding?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col5">
						<input type="text" id="currently-breastfeeding" name="currently-breastfeeding" />
					</div>
				</div>
				<div>&nbsp;</div> 
				<div style="padding: 100 4px;margin-bottom:40px">
					<div class="col6">
						<label for="currently-on-contraceptives">Are you currently using contraceptives/hormonal replacement therapy?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
					</div>
					<div class="col5">
						<input type="text" id="currently-on-contraceptives" name="currently-on-contraceptives" />
					</div>
				</div>
				<div>&nbsp;</div> 
				<div class="col11" style="padding: 100 4px; padding-bottom:20px;">
					<label for="using-contraceptive-answer">If yes, specify type:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
					<input type="text" id="using-contraceptive-answer" name="using-contraceptive-answer" />
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<label for="screened-before-answer-div" class="label">Have you ever been screened before for:(select all that apply) <span class="important">*</span></label>
					<div class="col6">
						<label for="screened-before-answer">Cervical Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-before-answer" name="screened-before-answer" />
					</div>
					<div class="col5">
						<label for="screened-date">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-date" name="screened-date" placeholder="Date of Test" />
					</div>
					<div class="col11">
						<label for="types-of-screening">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-of-screening" name="types-of-screening" />
					</div>
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="female-screened-for-breastcancer-answer">Breast Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="female-screened-for-breast-cancer-answer" name="female-screened-for-breast-cancer-answer" />
					</div>
					<div class="col5">
						<label for="date-screened-for-breastcancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-screened-for-breastcancer" name="date-screened-for-breastcancer" placeholder="Date of Test" />
					</div>
					<div class="col11">
						<label for="types-screened-for-breastcancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-screened-for-breastcancer" name="types-screened-for-breastcancer" />
					</div>
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="female-screened-colorectal-answer">Colorectal Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="female-screened-colorectal-answer" name="female-screened-colorectal-answer" />
					</div>
					<div class="col5">
						<label for="date-female-screened-colorectalcancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-female-screened-colorectalcancer" name="date-female-screened-colorectalcancer" placeholder="Date of Test" />
					</div>
					<div class="col11">
						<label for="types-female-screened-colorectalcancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-female-screened-colorectalcancer" name="types-female-screened-colorectalcancer" />
					</div>
				</div>
				<p>
					<input type="hidden" id="female-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Male History</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<label for="place-of-residence" class="label">Have you ever been screened before for:(select all that apply) <span class="important">*</span></label>
					<div class="col6">
						<label for="screened-prostrate-cancer">Prostrate Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-prostrate-cancer" name="screened-prostrate-cancer" />
					</div>
					<div class="col5">
						<label for="date-screened-prostratecancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-screened-prostratecancer" name="date-screened-prostratecancer" placeholder="Date of Test" />
					</div>
					<div class="col11">
						<label for="types-screened-prostratecancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-screened-prostratecancer" name="types-screened-prostratecancer" />
					</div>
				</div>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="male-screened-colorectal">Colorectal Cancer:<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="male-screened-colorectal" name="male-screened-colorectal" />
					</div>
					<div class="col5">
						<label for="date-male-screened-colorectalcancer">Date<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-male-screened-colorectalcancer" name="date-male-screened-colorectalcancer" placeholder="Date of Test" />
					</div>
					<div class="col11">
						<label for="types-male-screened-colorectalcancer">Types of Screening<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-male-screened-colorectalcancer" name="types-male-screened-colorectalcancer" />
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
				<div class="col11" style="padding: 100 4px; padding-bottom:20px;">
					<label for="male-hormonal-therapy-type">If yes, specify type:<span style="color: #f00 !important;
							padding-left: 5px;"></span></label>
					<input type="text" id="male-hormonal-therapy-type" name="male-hormonal-therapy-type" />
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
					<div class="col6">						
						<input type="text" id="signs-of-retinoblasoma" name="signs-of-retinoblasoma" />
					</div>				
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Risk Factor</legend>
				<div class="col12" style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="screened-prostrate-cancer">Do you some cigarettes?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-prostrate-cancer" name="screened-prostrate-cancer" />
					</div>
					<div class="col5">
						<label for="date-screened-prostratecancer">If yes, how many cigarrets per day?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-screened-prostratecancer" name="date-screened-prostratecancer" placeholder="Date of Test" />
					</div>
					<div class="col6">
						<label for="screened-prostrate-cancer">For how many many years?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-prostrate-cancer" name="screened-prostrate-cancer" />
					</div>
					<div class="col5">
						<label for="date-screened-prostratecancer">Do you use other forms of tobacco?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-screened-prostratecancer" name="date-screened-prostratecancer" placeholder="Date of Test" />
					</div>
					<div class="col6">
						<label for="screened-prostrate-cancer">Do you take alcohol?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-prostrate-cancer" name="screened-prostrate-cancer" />
					</div>
					<div class="col5">
						<label for="date-screened-prostratecancer">What is the frequency?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="date-screened-prostratecancer" name="date-screened-prostratecancer" placeholder="Date of Test" />
					</div>
					<div class="col6">
						<label for="screened-prostrate-cancer">Are you physically active?<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="screened-prostrate-cancer" name="screened-prostrate-cancer" />
					</div>
					<div class="col11">
						<label for="types-of-screening">Previous exposure to radiation (radiotherapy?)<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="types-of-screening" name="types-of-screening" />
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
					<textarea data-bind="" id="instructions" name="instructions" rows="2" cols="74"></textarea>
				</p>
				<p class="input-position-class">
					<label>History of Present Illness</label>
					<textarea data-bind="" id="instructions" name="instructions" rows="2" cols="74"></textarea>
				</p>
				<p class="input-position-class">
					<label>Past Medical and Surgical History</label>
					<textarea data-bind="" id="instructions" name="instructions" rows="2" cols="74"></textarea>
				</p>
				<label for="screened-before-answer-div" class="label">Review of Systems</label>
				<div class="col11">
					<label for="types-of-screening">CNS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">CNS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">CVS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">RS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">GUS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">MSS:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Performance Status</legend>
				<div class="col11">
					<label for="types-of-screening">General Examination:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Jaundice:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Anaemia:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Cyanosis:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Clubbing:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Oedema:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Dehydration:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
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
						<input type="text" id="family-members-with-cancer" name="family-members-with-cancer" />
					</div>
				</div>
				<label for="screened-before-answer-div">If yes, Indicate location and characteristics below:</label>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Submandibular<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Supraciavicular<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Cervical<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Axillary<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Inguinal<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Generalized Lymadenopathy<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<div style="padding: 0 4px; padding-bottom:20px;">
					<div class="col6">
						<label for="degree-of-relation">Other<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="degree-of-relation" name="degree-of-relation" />
					</div>
					<div class="col5">
						<label for="age-of-diagnosis">Comments<span style="color: #f00 !important;
						padding-left: 5px;"></span></label>
						<input type="text" id="age-of-diagnosis" name="age-of-diagnosis" />
					</div>
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>HEENT</legend>
				<div class="col11">
					<label for="types-of-screening">Eyes:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Neck:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Mouth:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Ears:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Nose:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Throat:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Respiratory System</legend>
				<div class="col11">
					<label for="types-of-screening">Inspection:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Palpation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Percussion:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<div class="col11">
					<label for="types-of-screening">Auscultation:<span style="color: #f00 !important;
					padding-left: 5px;"></span></label>
					<input type="text" id="types-of-screening" name="types-of-screening" />
				</div>
				<p>
					<input type="hidden" id="child-history-set" />
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Investigations</legend>
				<div>
					<div class="input-position-class">
						<label class="label" for="investigation">Investigation:</label>
						<input type="text" id="investigation" name="investigation" />
					</div>

					<div id="task-investigation" class="tasks" style="display:none;">
						<header class="tasks-header">
							<span id="title-investigation" class="tasks-title">INVESTIGATION</span>
							<a class="tasks-lists"></a>
						</header>

						<div data-bind="foreach: investigations">
							<div class="investigation-container">
								<span class="right pointer" data-bind="click: \$root.removeInvestigation"><i class="icon-remove small"></i></span>
								<p data-bind="text: label"></p>
							</div>
						</div>
					</div>
					<div style="display:none">
						<p><input type="text" ></p>
					</div>
					<p>
						<input type="hidden" id="investigation-set" />
					</p>
				</div>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Prescription</legend>
				<div>
					<div style="display:none">
						<p><input type="text"></p>
					</div>
					<h2>Prescribe Medicine</h2>
					<table id="addDrugsTable">
						<thead>
						<tr>
							<th>Drug Name</th>
							<th>Dosage</th>
							<th>Formulation</th>
							<th>Frequency</th>
							<th>Days</th>
							<th>Comments</th>
							<th></th>
						</tr>
						</thead>
						<tbody data-bind="foreach: drugs">
						<tr>
							<td data-bind="text: drugName"></td>
							<td data-bind="text: dosageAndUnit" ></td>
							<td data-bind="text: formulation().label"></td>
							<td data-bind="text: frequency().label"></td>
							<td data-bind="text: numberOfDays"></td>
							<td data-bind="text: comment"></td>
							<td>
								<a href="#" title="Remove">
									<i data-bind="click: \$root.removePrescription" class="icon-remove small" style="color: red" ></i>
								</a>
								<!-- <a href="#"><i class="icon-edit small"></i></a> -->
							</td>
						</tr>
						</tbody>
					</table>
					<br/>
					<button id="add-prescription">Add</button>
				</div>
				<p>
					<input type="hidden" id="drug-set" />
				</p>
			</fieldset>
			<fieldset class="no-confirmation">
				<legend>Other Instructions</legend>
				<p class="input-position-class">
					<label class="label">Other Instructions</label>
					<textarea data-bind="value: \$root.otherInstructions" id="instructions" name="instructions" rows="10" cols="74"></textarea>
				</p>
			</fieldset>

			<fieldset class="no-confirmation">
				<legend>Outcome</legend>
				<div>
					<div class="onerow" style="padding-top:2px;">
						<h2>What is the outcome of this visit? <span class="important">*</span></h2>
						<div data-bind="foreach: availableOutcomes" class="outcomes-container">
							<div data-bind="if: !(\$root.admitted !== false && \$data.id !== 2)">
								<p class="outcome">
									<label style="display: inline-block;">
										<input type="radio" name="outcome" data-bind="click: updateOutcome"/>
										<span data-bind="text: option.label" style="color:#000; font-size: 1em; cursor: pointer"></span>
									</label>
									<span data-bind="if: \$data.option.id === 1 && \$root.outcome() && \$root.outcome().option.id === 1">
										<span id="follow-up-date" class="date" style="float: right;">
											<input data-bind="value : followUpDate" style="width: 378px;" class="required">
											<span class="add-on"><i class="icon-calendar small"></i></span>
										</span>
									</span>
								</p>
							</div>
						</div>
						<span data-bind="if: \$root.outcome() && \$root.outcome().option.id ===6">
							<h2>Referral information</h2>

							<div class="onerow">
								<div class="col4"><label for="internalReferral"						>Referral Available</label></div>
								<div class="col4"><label for="internalReferral" id="refTitle"		>Internal Referral</label></div>
								<div class="col4 last"><label for="internalReferral" id="facTitle"	>Facility</label></div>
							</div>

							<div class="onerow">
								<div class="col4">
									<div class="input-position-class">
										<select id="availableReferral" name="availableReferral">
											<option value="0">Select Option</option>
											<option value="1">Internal Referral</option>
											<option value="2">External Referral</option>
										</select>
									</div>
								</div>

								<div class="col4">
									<div class="input-position-class">
										<select id="internalReferral" name="internalReferral" data-bind="options: \$root.internalReferralOptions, optionsText: 'label', value: \$root.referredTo, optionsCaption: 'Please select...'">
										</select>

										<select id="externalReferral" name="externalReferral" onchange="loadExternalReferralCases();" data-bind="options: \$root.externalReferralOptions, optionsText: 'label', value: \$root.referredTo, optionsCaption: 'Please select...'">
										</select>
									</div>
								</div>

								<div class="col4 last">
									<div class="input-position-class">
										<field>
											<% fields.each { %>
											${ ui.includeFragment("kenyaui", "widget/labeledField", it) }
											<% } %>
										</field>
									</div>
								</div>
							</div>

							<div class="onerow" style="padding-top:2px;" id="refReason1">
								<div class="col4">
									<label for="referralReasons" style="margin-top:20px;">Referral Reasons</label>
								</div>

								<label id="specify-lbl" for="specify" style="margin-top:20px;">If Other, Please Specify</label>
							</div>

							<div class="onerow" style="padding-top:2px;" id="refReason2">
								<div class="col4">
									<select id="referralReasons" name="referralReasons" data-bind="options: \$root.referralReasonsOptions, optionsText: 'label', value: \$root.referralReasons, optionsCaption: 'Please select...'" style="margin-top: 5px;">
									</select>
								</div>

								<div class="col4 last" style="width: 65%;">
									<input type="text" id="specify" placeholder="Please Specify" name="specify" data-bind="value: \$root.specify"/>
								</div>
							</div>

							<div class="onerow" style="padding-top:2px;" id="refReason3">
								<label for="referralComments" style="margin-top:20px;">Comments</label>
								<textarea type="text" id="referralComments"   name="referralComments" data-bind="value: \$root.referralComments" placeholder="COMMENTS"  style="height: 80px; width: 650px;"></textarea>
							</div>
						</span>

					</div>

					<field>
						<input type="hidden" id="outcome-set" class="required" />
						<span id="outcome-lbl" class="field-error" style="display: none"></span>
					</field>

				</div>
			</fieldset>
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