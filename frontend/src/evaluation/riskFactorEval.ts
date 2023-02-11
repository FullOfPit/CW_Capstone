export default function riskFactorEval (healthHazard: number, probability: number, frequency: number) {

    const overallRiskFactor = (healthHazard: number, probability: number, frequency: number) => {

        let riskFactor = healthHazard * probability * frequency;

        if(riskFactor > 9 && healthHazard > 3) {
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
                "this risk factor highly recommended. Only proceed when no alternatives have been identified.";

        } else if (healthHazard === 4 && probability >= 2 && frequency >= 2) {
            return "Dangerously high risk to health with considerable chance of occurrence. " +
                "Further measures to reduce individual risk components are highly recommended. " +
                "Only proceed after reconsideration.";

        } else if ((healthHazard >= 2 && probability === 4 && frequency >= 2) || (healthHazard >= 2 && probability >= 2  && frequency >= 4)) {
            return "Dangerously high chance of an incidence to occur. " +
                "Further measures to reduce individual risk components are highly recommended. " +
                "Only proceed after reconsideration.";

        } else if (healthHazard === 4 && probability > 1 && frequency > 1) {
            return "Task possibly lethal. Any possibility to reduce potential impact on health and life " +
                "and further measure to reduce the likelihood of an incident to occur should be explored. " +
                "Only proceed after reconsideration.";

        } else if (healthHazard === 4 && probability === 1 && frequency >= 2) {
            return "Task possibly lethal. Considering the assessed frequency of this task, exploration of" +
                " any measures to decouple the risk to health from other parts of this task is highly recommended. " +
                "Only proceed after reconsideration.";

        } else if (healthHazard === 4 && probability > 1 && frequency === 1) {
            return "Task possibly lethal. Considering the assessed chance of an incidence to occur, exploration of " +
                "measures to reduce the impact on health and life should be prioritised. " +
                "Only proceed after reconsideration.";

        } else if (healthHazard === 4) {
            return "Task possibly lethal. Considering the assessed overall likelihood of an incident to occur," +
                " any additional measure to reduce the potential impact on health and life should be explored. " +
                "Reconsideration highly recommended.";

        } else if (healthHazard > 2) {
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
                return "#c70039";
            case "High Risk":
                return "#ff5733";
            case "Moderate Risk":
                return "#ffc300";
            case "Low Risk":
                return "#478778";
            case "Negligible Risk":
                return "#009e60";
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

export const riskComponentColorCode = (factor: number) => {
    switch (factor) {
        case 4:
            return "#c70039";
        case 3:
            return "#ff5733";
        case 2:
            return "#ffc300";
        case 1:
            return "#009e60";
        default:
            return "white";
    }
}