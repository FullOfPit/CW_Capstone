export default function riskFactorEvaluation (healthHazard: number, probability: number, frequency: number) {

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

    const finalEvalHealthStageFour = (p: number, f: number) => {
        if (p >= 3 && f >= 3) {
            return {
                overallRiskFactor: "Extreme Risk",
                factorEvaluation:  "Intolerable Risk. Tasks involving this risk factor should, if possible, " +
                    "be avoided entirely, circumvented or at least restructured. Only proceed when no alternatives have " +
                    "been identified."}

        } else if (p >= 2 && f >= 2) {
            return {
                overallRiskFactor: "Extreme Risk",
                factorEvaluation:  "Intolerable risk. Reconsideration of any tasks entailing " +
                    "this risk factor is highly recommended. Considering the assessed likelihood of an incident to occur, " +
                    "any task involving this risk factor should be restructured. " +
                    "Only proceed when no alternatives have been identified."};

        } else if (p > 2 && f === 1) {
            return {
                overallRiskFactor: "Extreme Risk",
                factorEvaluation:  "Dangerously high risk. Considering the assessed likelihood of an incidence to occur, " +
                    "the assessed task should be restructured. If restructuring is not possible, any measure that might " +
                    "reduce the impact on health should be explored. Only proceed after reconsideration."};

        } else if (p === 1 && f > 2) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation:  "Dangerously high risk. Considering the assessed frequency of this task, separation " +
                    "of harmful steps should be explored and implemented. Any measure that might reduce the impact " +
                    "on health should be explored. Only proceed after reconsideration."};

        } else if (p === 1 && f === 1) {
            return {
                overallRiskFactor: "Moderate Risk",
                factorEvaluation:  "This risk factor is assessed to be of potentially lethal risk. Considering the assessed " +
                    "unlikelihood and one-time occurrence, the overall chance of an incidence is low. However, any measure " +
                    "that could meaningfully reduce the risk to health should be implemented. Reconsideration highly " +
                    "recommended."};
        } else {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation:  "Dangerously high risk. Considering the assessed probability and frequency parameter, " +
                    "the occurrence of an incidence is low, but potentially lethal nevertheless. Any measure that might " +
                    "reduce the impact on health should be explored. Only proceed after reconsideration."};
        }
    }

    const finalEvalHealthStageThree = (p: number, f: number) => {
        if ((p * f) >= 8) {
            return {
                overallRiskFactor: "Extreme Risk",
                factorEvaluation: "Dangerously high risk. Considering the assessed likelihood of an incidence to occur, " +
                    "the assessed task should be restructured. If restructuring is not possible, any measure that might " +
                    "reduce the impact on health should be explored. Only proceed after reconsideration."};

        } else if ((p * f) >= 4) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation: "High risk of critical hazard to health. Considering the assessed overall chance " +
                    "of an incidence to occur, any measure that might reduce the impact on health should be explored " +
                    "and any that could meaningfully reduce the chance of an incidence to occur should be implemented." +
                    "Only proceed after reconsideration."};

        } else if (f >= 2) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation: "Dangerously high risk. Any task involving this risk should be restructured, " +
                    "due to the assessed likelihood of an incidence to occur. Any measure that might reduce " +
                    "the impact on health should be explored. Only proceed after reconsideration."};

        } else if (p >= 2) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation: "Dangerously high risk. Any task involving this risk should be restructured," +
                    " due to the assessed frequency of this task. Any measure that might reduce the impact on" +
                    " health should be explored. Only proceed after reconsideration."};

        } else {
            return {
                overallRiskFactor: "Moderate Risk",
                factorEvaluation: "This risk factor is assessed to be of moderate risk. Considering the potentially " +
                    "critical impact on health, any task involving this should be restructured, if possible. Particular " +
                    "emphasis should be placed on measures that reduce the hazard to health. Reconsideration recommended."};
        }
    }

    const finalEvalHealthStageTwo = (p: number, f: number) => {
        if (p >= 3 && f >= 3) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation: "Dangerously high risk of an incidence to occur. Considering the assessed potential " +
                    "to serious hazard to health, any measure that might reduce the any of the assessed risk components " +
                    "should be explored. Restructuring of any tasks involving this risk factor is recommended. Only" +
                    " proceed after reconsideration."};

        } else if ((p * f) >= 8) {
            return {
                overallRiskFactor: "High Risk",
                factorEvaluation: "High Risk of an incidence to occur with a serious risk to health. Considering the " +
                    "overall chance of an incidence to occur, any tasks involving this risk factor should be restructured" +
                    " or further measure to reduce the assessed risk components should be taken. Only proceed after " +
                    "reconsideration."};

        }  else if (p === 4) {
            return {
                overallRiskFactor: "Moderate Risk",
                factorEvaluation: "Considering the high probability of an incidence to occur and the potentially serious impact, " +
                    "exploration of further measures to reduce this risk factor is recommended, despite the one-time execution of " +
                    "this task. Reconsideration recommended."};

        } else if (f === 4) {
            return {
                overallRiskFactor: "Moderate Risk",
                factorEvaluation: "Considering the high frequency of this risk factor and potentially serious impact, " +
                    "exploration of further measures to reduce this risk factor is recommended, despite the low probability. " +
                    " Reconsideration recommended."};

        } else if ((p * f) >= 4) {
            return {
                overallRiskFactor: "Moderate Risk",
                factorEvaluation: "This risk factor is assessed to be of moderate risk. Considering the assessed probability " +
                    "and frequency, any reduction measure proposed should be further explored. Reconsideration recommended."};

        } else {
            return {
                overallRiskFactor: "Low Risk",
                factorEvaluation: "This risk factor is assessed to be of low risk. However, considering the " +
                    "potentially serious risk to health, the measures to reduce this risk factor should be emphasized " +
                    "prior to any task involved. Proceed with caution."};
        }
    }

    const finalEvalHealthStageOne = (p: number, f: number) => {
        if ((p * f) > 9) {
            return {
                overallRiskFactor: "Low Risk",
                factorEvaluation: "This risk factor is assessed to be of low risk. Considering the likelihood and frequency " +
                    "of this task, the measures to reduce this risk factor should be followed rather strictly. " +
                    "Proceed with caution."};

        } else if (p >= 2 && f >= 2) {
            return {
                overallRiskFactor: "Low Risk",
                factorEvaluation: "This risk factor is assessed to be of low risk. Proceed with caution."};

        } else {
            return {
                overallRiskFactor: "Negligible Risk",
                factorEvaluation: "This risk factor is of negligible risk. Proceed."};
        }
    }

    

    const riskEvaluation = (h: number, p: number, f: number) => {
        if (h === 4) {
            return finalEvalHealthStageFour(p, f);

        } else if (h === 3) {
            return finalEvalHealthStageThree(p, f);

        } else if (h === 2) {
            return finalEvalHealthStageTwo(p, f);

        } else {
            return finalEvalHealthStageOne(p, f)
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
        riskFactor: riskEvaluation(healthHazard, probability, frequency).overallRiskFactor,
        healthComponent: healthHazardEval(healthHazard),
        probabilityComponent: probabilityEval(probability),
        finalEval: riskEvaluation(healthHazard, probability, frequency).factorEvaluation,
        riskColor: riskColorCode(riskEvaluation(healthHazard, probability, frequency).overallRiskFactor)
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