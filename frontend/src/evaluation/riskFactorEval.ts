export default function riskFactorEval (healthHazard: number, probability: number, frequency: number) {

    const overallRiskFactor = (healthHazard: number, probability: number, frequency: number) => {

        let riskFactor = healthHazard * probability * frequency;

        if(riskFactor > 9 && healthHazard > 2) {
            return "Extreme Risk";
        } else if ((riskFactor > 7 && !(healthHazard === 2 && probability === 2 && frequency === 2))  || healthHazard >= 3) {
            return "High Risk";
        } else if (riskFactor > 5 || (healthHazard === 2 && probability === 2 && frequency === 2)) {
            return "Moderate Risk";
        } else if (riskFactor > 3) {
            return "Low Risk";
        } else if (riskFactor > 0) {
            return "Negligible Risk";
        } else {
            return "Not yet assessed"
        }
    }

    const healthHazardEval = (healthHazard: number) => {
        switch (healthHazard) {
            case 4:
                return "Danger: Risk to Life";
            case 3:
                return "Danger: Critical Risk to Health and potential Risk to Life";
            case 2:
                return "Serious Risk to Health";
            default:
                return "Minor Risk to Health";
        }
    }

    const probabilityEval = (probability: number) => {
        switch (probability) {
            case 4:
                return "Danger: Incidence Occurrence most probable";
            case 3:
                return "Danger: Incidence Occurrence likely";
            case 2:
                return "Incident Occurrence possible";
            default:
                return "Incident Occurrence unlikely";
        }
    }

    const finalRiskEval = (healthHazard: number, probability: number, frequency: number) => {
        if (healthHazard >= 3 && probability >= 3 && frequency >= 3) {
            return "Intolerable Risk. Reconsideration of any tasks entailing " +
                "this Risk Factor highly recommended. Only proceed when no alternatives have been identified.";
        } else if (healthHazard === 4 && probability >= 2 && frequency >= 2) {
            return "Dangerously high Risk to Health with considerable chance of Occurrence. " +
                "Further measures to reduce individual risk components are highly recommended. " +
                "Only proceed after reconsideration.";
        } else if ((healthHazard >= 2 && probability === 4 && frequency >= 2) || (healthHazard >= 2 && probability >= 2  && frequency >= 4)) {
            return "Dangerously high chance of an Incidence to occur. " +
                "Further measures to reduce individual risk components are highly recommended. " +
                "Only proceed after reconsideration.";
        } else if (healthHazard === 4) {
            return "Task possibly lethal. Any possibility to reduce potential impact on health and life " +
                "should be explored. Only proceed after reconsideration.";
        } else if (healthHazard >= 3) {
            return "Potentially lethal Risk. Any possibility to reduce the chance " +
                "of occurrence should be explored. Only proceed after reconsideration.";
        } else if (healthHazard >= 2 && probability >= 2 && frequency >= 2) {
            return "Considerable Risk involved. Further measures to reduce risk should be explored. " +
                "Proceed after reconsideration."
        } else if ((probability + frequency) >= 6) {
            return "Considerable chance of an Incidence to occur. Proceed with caution."
        } else {
            return "Risk factor tolerable. Proceed."
        }
    }

    const riskColorCode = (factor: string) => {
        switch (factor) {
            case "Extreme Risk":
                return "#ff6d4f";
            case "High Risk":
                return "#db9f58";
            case "Moderate Risk":
                return "#f0de90";
            case "Low Risk":
                return "#bfd977";
            case "Negligible Risk":
                return "#9afaa7";
            default:
                return "white";
        }
    }

    let evaluation = {
        riskFactor: overallRiskFactor(healthHazard, probability, frequency),
        healthComponent: healthHazardEval(healthHazard),
        probabilityComponent: probabilityEval(probability),
        finalEval: finalRiskEval(healthHazard, probability, frequency),
        riskColor: riskColorCode(overallRiskFactor(healthHazard, probability, frequency))
    }

    return (evaluation);
}