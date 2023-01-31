export default function riskFactorEval (healthHazard: number, probability: number, frequency: number) {

    let evaluation = {
        riskFactor: "",
        healthComponent: "",
        probabilityComponent: "",
        finalEval: ""
    }

    let riskFactor = healthHazard * probability * frequency;

    if(riskFactor > 9 && healthHazard > 1) {
        evaluation.riskFactor = "Extreme Risk";
    } else if (riskFactor > 7) {
        evaluation.riskFactor = "High Risk";
    } else if (riskFactor > 5) {
        evaluation.riskFactor = "Moderate Risk";
    } else if (riskFactor > 3) {
        evaluation.riskFactor = "Low Risk";
    } else {
        evaluation.riskFactor = "Negligible Risk";
    }

    switch (healthHazard) {
        case 4:
            evaluation.healthComponent = "Danger: Risk to Life";
            break;
        case 3:
            evaluation.healthComponent = "Danger: Critical Risk to Health and potential Risk to Life";
            break;
        case 2:
            evaluation.healthComponent = "Serious Risk to Health";
            break;
        default:
            evaluation.healthComponent = "Minor Risk to Health";
    }

    switch (probability) {
        case 4:
            evaluation.probabilityComponent = "Danger: Incident Occurrence most probable";
            break;
        case 3:
            evaluation.probabilityComponent = "Danger: Incident Occurrence likely";
            break;
        case 2:
            evaluation.probabilityComponent = "Incident Occurrence possible";
            break;
        default:
            evaluation.probabilityComponent = "Incident Occurrence unlikely";
    }

    if (healthHazard >= 3 && probability >= 3 && frequency >= 3) {
        evaluation.finalEval = "Intolerable Risk. Reconsideration of any tasks entailing " +
            "this Risk Factor highly recommended. Only proceed when no alternatives have been identified.";
    } else if (healthHazard === 4 && probability >= 2 && frequency >= 2) {
        evaluation.finalEval = "Dangerously high Risk to Health with considerable chance of Occurrence. " +
            "Further measures to reduce individual risk components are highly recommended. " +
            "Only proceed after reconsideration.";
    } else if ((healthHazard >= 2 && probability === 4 && frequency >= 2) || (healthHazard >= 2 && probability >= 2  && frequency >= 4)) {
        evaluation.finalEval = "Dangerously high chance of an Incidence to occur. " +
            "Further measures to reduce individual risk components are highly recommended. " +
            "Only proceed after reconsideration.";
    } else if (healthHazard === 4) {
        evaluation.finalEval = "Task possibly lethal. Any possibility to reduce the chance " +
            "of occurrence should be explored. Only proceed after reconsideration.";
    } else if (healthHazard >= 3) {
        evaluation.finalEval = "Potentially lethal Risk. Any possibility to reduce the chance " +
            "of occurrence should be explored. Only proceed after reconsideration.";
    } else if (healthHazard >= 2 && probability >= 2 && frequency >= 2) {
        evaluation.finalEval = "Considerable Risk involved. Further measures to reduce risk should be explored. " +
            "Proceed after reconsideration."
    } else if ((probability + frequency) >= 6) {
        evaluation.finalEval = "Considerable chance of an Incidence to occur. Proceed with caution."
    } else {
        evaluation.finalEval = "Risk factor tolerable. Proceed."
    }

    return (evaluation);
}