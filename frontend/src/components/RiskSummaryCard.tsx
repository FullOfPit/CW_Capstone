import "./RiskSummaryCard.css"
import Risk from "../types/Risk";
import riskFactorEval from "../evaluation/riskFactorEval";

export default function RiskSummaryCard({risk}:{risk: Risk})
{
    let riskFactorEvalutaion = riskFactorEval(risk.healthHazard, risk.probability, risk.frequency);

    return (
        <div className={"RiskSumCard"}>
            <div className={"RiskBody"}>
                <div className={"RiskSumCardName"}>
                    <h5>{risk.riskName}</h5>
                </div>
                <div className={"RiskFactor"}>
                    <h5>Risk Factor</h5>
                    <h4>{riskFactorEvalutaion.riskFactor}</h4>
                </div>
                <div className={"RiskMetrics"}>
                    <p>{`Hazard to Health: ${risk.healthHazard}`}</p>
                    <p>{`Probability: ${risk.probability}`}</p>
                    <p>{`Frequency: ${risk.frequency}`}</p>
                </div>
            </div>
            <div className={"RiskComponents"}>
                <p>{riskFactorEvalutaion.healthComponent}</p>
                <p>{riskFactorEvalutaion.probabilityComponent}</p>
            </div>
            <div className={"RiskEval"}>
                <h6>{riskFactorEvalutaion.finalEval}</h6>
            </div>
        </div>
    )
}