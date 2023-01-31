import "./RiskSummaryCard.css"
import Risk from "../types/Risk";
import riskFactorEval from "../evaluation/riskFactorEval";
import React, {useEffect, useState} from "react";

export default function RiskSummaryCard({risk, onDelete}:{risk: Risk, onDelete:(riskId: string) => void})
{
    const [assessmentReady, setAssessmentReady] = useState<boolean>(false)
    let riskFactorEvaluation = riskFactorEval(risk.healthHazard, risk.probability, risk.frequency);

    useEffect(() => {
            if (risk.riskName !== "" &&
                risk.riskDescription !== "" &&
                risk.healthHazard !== 0 &&
                risk.probability !== 0 &&
                risk.frequency !== 0) {
                setAssessmentReady(true)
            }
        },[risk.frequency, risk.healthHazard, risk.probability, risk.riskDescription, risk.riskName])



    return (
        <div className={"RiskSumCard"}>
            {risk.id ? <button onClick={() => onDelete(risk.id)}>X</button> : null}
            <div className={"RiskBody"}>
                <div className={"RiskSumCardName"}>
                    <h5>{risk.riskName}</h5>
                </div>
                <div className={"RiskFactor"}>
                    <h5>Risk Factor</h5>
                    <h4>{assessmentReady ? riskFactorEvaluation.riskFactor : ""}</h4>
                </div>
                <div className={"RiskMetrics"}>
                    <p>{`Hazard to Health: ${risk.healthHazard}`}</p>
                    <p>{`Probability: ${risk.probability}`}</p>
                    <p>{`Frequency: ${risk.frequency}`}</p>
                </div>
            </div>
            <div className={"RiskDescription"}>
                <h6>Description of Risk Factor:</h6>
                <p>{risk.riskDescription}</p>
            </div>
            <div className={"RiskReductionMeasures"}>
                <h6>Risk Reduction Measures:</h6>
                <p>{risk.riskReductionMeasures}</p>
            </div>

                {assessmentReady &&
                    <div>
                        <div className={"RiskComponents"}>
                            <p>{riskFactorEvaluation.healthComponent}</p>
                            <p>{riskFactorEvaluation.probabilityComponent}</p>
                        </div>
                        <div className={"RiskEval"}>
                            <h6>{riskFactorEvaluation.finalEval}</h6>
                        </div>
                    </div>
                }
        </div>
    )
}