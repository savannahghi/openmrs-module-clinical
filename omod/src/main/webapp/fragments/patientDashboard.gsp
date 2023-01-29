<%
	def fields = [
			[
					id: "facility",
					label: "",
					formFieldName: "facility",
					class: org.openmrs.Location
			]
	]
%>


<fieldset class="no-confirmation">
    <legend>Symptoms</legend>
    <div style="padding: 0 4px">
        <label for="symptom" class="label">Symptoms <span class="important">*</span></label>
        <input type="text" id="symptom" name="symptom" placeholder="Add Symptoms" />
        <field>
            <input type="hidden" id="symptoms-set" class="required"/>
            <span id="symptoms-lbl" class="field-error" style="display: none"></span>
        </field>
    </div>

    <div class="tasks" id="task-symptom" style="display:none;">
        <header class="tasks-header">
            <span id="title-symptom" class="tasks-title">PATIENT'S SYMPTOMS</span>
            <a class="tasks-lists"></a>
        </header>

        <div class="symptoms-qualifiers" data-bind="foreach: signs" >
            <div class="symptom-container">
                <div class="symptom-label">
                    <span class="right pointer show-qualifiers"><i class="icon-caret-down small" title="more"></i></span>
                    <span class="right pointer" data-bind="click: \$root.removeSign"><i class="icon-remove small"></i></span>
                    <span data-bind="text: label"></span>
                </div>

                <div class="qualifier-container" style="display: none;">
                    <ul class="qualifier" data-bind="foreach: qualifiers">
                        <li>
                            <span data-bind="text: label"></span>
                            <div data-bind="if: options().length >= 1">
                                <div data-bind="foreach: options" class="qualifier-option">
                                    <p class="qualifier-field">
                                        <input type="radio" data-bind="checkedValue: \$data, checked: \$parent.answer" >
                                        <label data-bind="text: label"></label>
                                    </p>
                                </div>
                            </div>
                            <div data-bind="if: options().length === 0" >
                                <p>
                                    <input type="text" data-bind="value: freeText" >
                                </p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</fieldset>
<fieldset class="no-confirmation">
    <legend>History</legend>
    <p>
        <label class="label" for="history">History of present illness</label>
        <span>
            Date of Onset<input data-bind="value: \$root.onSetDate" type="date" id="onSetDate" name="onSetDate" />
        </span>

        <textarea data-bind="value: \$root.illnessHistory" id="history" name="history" rows="10" cols="74"></textarea>
    </p>
</fieldset>
<fieldset class="no-confirmation">
    <legend>Physical Examination</legend>
    <p class="input-position-class">
        <label class="label">Physical Examination <span class="important">*</span></label>
        <field>
            <textarea data-bind="value: \$root.physicalExamination" id="examination" name="examination" rows="10" cols="74" class="required"></textarea>
            <span id="examination-lbl" class="field-error" style="display: none"></span>
        </field>
    </p>
</fieldset>
<fieldset class="no-confirmation">
    <legend>Diagnosis</legend>
    <div>
        <h2>Patient's Diagnosis <span class="important">*</span></h2>

        <div>
            <p class="input-position-class">
                <input type="text" id="diagnosis" name="diagnosis" placeholder="Select Diagnosis" />
            </p>

            <div id="task-diagnosis" class="tasks" style="display:none;">
                <header class="tasks-header">
                    <span id="title-diagnosis" class="tasks-title">DIAGNOSIS</span>
                    <a class="tasks-lists"></a>
                </header>

                <div id="diagnosis-carrier" data-bind="foreach: diagnoses" style="margin-top: -2px">
                    <div class="diagnosis-container" style="border-top: medium none !important;">
                        <span class="right pointer" data-bind="click: \$root.removeDiagnosis"><i class="icon-remove small"></i></span>
                        <div class="diagnosis-carrier-div" style="border-width: 1px 1px 1px 10px; border-style: solid; border-color: -moz-use-text-color; padding: 0px 10px 3px;">
                            <span data-bind="text: label" style="display: block; font-weight: bold;"></span>

                            <label style="display: inline-block; font-size: 11px; padding: 0px; cursor: pointer; margin: 0px 0px 0px -5px;">
                                <input value="true"  data-bind="checked: provisional" class="chk-provisional" type="radio" style="margin-top: 3px"/>Provisional
                            </label>

                            <label style="display: inline-block; font-size: 11px; padding: 0px; cursor: pointer; margin: 0">
                                <input value="false" data-bind="checked: provisional" class="chk-final" type="radio" style="margin-top: 3px"/>Final
                            </label>
                        </div>

                    </div>
                </div>
            </div>

            <p class="input-position-class">
                <field>
                    <input type="hidden" id="diagnosis-set" class="required" />
                    <span id="diagnosis-lbl" class="field-error" style="display: none"></span>
                </field>
            </p>
        </div>
    </div>
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
    <legend>MDT</legend>
    <div class = "mdt-section">

        <div class = "mdt-row">
            <span>Was patient discussed by MDT:</span>
            <label>
                <input value="1065"   class="chk-mdt" name="chk-mdt" type="radio" />Yes
            </label>
            <label>
                <input value="1066"  class="chk-mdt" name="chk-mdt" type="radio" />No
            </label>
        </div>
        <div class = "mdt-row">
            <span style = "width: 20% !important">If yes, members present:</span>
            <input id= "mdtMembers" class = "mdtMembers" type="text" placeholder="Please Specify members present" name="specify" />
            <div style = "width: 30% !important">
                <span data-bind="foreach: mdtMembers">
                    <span data-bind="text: label"></span>
                    <span data-bind="if: (\$index() !== (\$parent.mdtMembers().length - 1))">, </span>
                </span>
            </div>
        </div>
        <p class="input-position-class">
            <label>MDT Instructions</label>
            <textarea data-bind="value: \$root.mdtInstructions" id="mdtInstructions" name="mdtInstructions" rows="6" cols="72"></textarea>
        </p>

        <div class = "mdt-row">
            <label>Cancer Grading:</label>
            <label>
                <input value="166987"   class="chk-grade"  name="chk-grade" type="radio" />Grade I
            </label>
            <label>
                <input value="166988"  class="chk-grade" name="chk-grade" type="radio" />Grade II
            </label>
            <label>
                <input value="166989"  class="chk-grade" name="chk-grade" type="radio" />Grade III
            </label>
            <label>
                <input value="1000607" class="chk-grade"  name="chk-grade" type="radio" />Grade IV
            </label>
        </div>
        <div class = "mdt-row">
            <label>Cancer Staging:</label>
            <label>
                <input value="160771"   class="chk-stage"  name="chk-stage" type="radio" />Stage 1
            </label>
            <label>
                <input value="160774"  class="chk-stage"   name="chk-stage" type="radio" />Stage 2
            </label>
            <label>
                <input value="160778"   class="chk-stage"  name="chk-stage" type="radio" />Stage 3
            </label>
            <label>
                <input value="160782"  class="chk-stage"   name="chk-stage" type="radio" />Stage 4
            </label>
        </div>


        <div class = "mdt-col">
            <label>Treatment Plan(select all that apply):</label>
            <div class = "mdt-col">
                <label>
                    <input id = "1" value="Chemotherapy"   class="chk-program" name="chk-program" type="checkbox" />Chemotherapy
                </label>
                <label>
                    <input id = "2" value="Radiotherapy"   class="chk-program" name="chk-program" type="checkbox" />Radiotherapy
                </label>
                <label>
                    <input id = "3" value="Procedure/Surgery"   class="chk-program" name="chk-program" type="checkbox" />Procedure/Surgery
                </label>
            </div>
        </div>
    <div>
</fieldset>
<fieldset class="no-confirmation">
    <legend>Outcome</legend>
    <div>
        <h2> Patient Referral</h2>

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
                    <input type="text" id="facility" placeholder="Facility Name" name="facility" data-bind="value: \$root.facility">
                    </input>
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

                        <span data-bind="if: \$data.option.id === 2 && \$root.outcome() && \$root.outcome().option.id === 2">
                            <select data-bind="options: \$root.inpatientWards, optionsText: 'label', value: admitTo" style="width: 400px !important; float: right;"></select>
                        </span>
                    </p>
                </div>
            </div>
        </div>

        <field>
            <input type="hidden" id="outcome-set" class="required" />
            <span id="outcome-lbl" class="field-error" style="display: none"></span>
        </field>

    </div>
</fieldset>