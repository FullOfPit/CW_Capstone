import Project from "../types/Project";
import Risk from "../types/Risk";

export function projectValidation(project: Project) {

    let projectValid = true;
    let projectValidationFails = [];

    if (project.projectName.length < 1) {
        projectValid = false;
        projectValidationFails.push("Missing project name!");
    } else if (project.projectId.length < 1) {
        projectValid = false;
        projectValidationFails.push("Missing project ID!");
    } else if ((Date.parse(project.plannedFinishDate as string) - Date.parse(project.plannedStartDate as string)) <= 0) {
        projectValid = false;
        projectValidationFails.push("Project finish dates have to be set at least 1 day after start dates")
    } else if (project.projectDetails.length < 1) {
        projectValid = false;
        projectValidationFails.push("Missing project description!");
    } else if (project.assessorName.length < 1) {
        projectValid = false;
        projectValidationFails.push("Missing assessor name!");
    }
    return {validation: projectValid, validationFails: projectValidationFails}
}

export function riskListValidation(risks: Risk[]) {

    let riskListValid = true;
    let riskListValidationFails = [];

    if (risks.length < 1) {
        riskListValid = false;
        riskListValidationFails.push("At least one risk factor needs to be assessed!")
    }

    return {validation: riskListValid, validationFails: riskListValidationFails};
}

export function riskValidation(risk: Risk) {

    let riskValid = true;
    let riskValidationFails = [];

    if (risk.riskName.length < 1) {
        riskValid = false;
        riskValidationFails.push("Risk factors must be named!");
    } else if (risk.riskDescription.length < 1) {
        riskValid = false;
        riskValidationFails.push("Risk factors must be described!")
    } else if (risk.riskReductionMeasures.length < 1) {
        riskValid = false;
        riskValidationFails.push("Risk factors must include deliberation about risk reduction!");
    } else if (risk.healthHazard === 0 || risk.probability === 0 || risk.frequency || 0) {
        riskValid = false;
        riskValidationFails.push("Risk factor assessment must include assessment of all three parameters!")
    }

    return {validation: riskValid, validationFails: riskValidationFails};
}
