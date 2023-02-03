import "./RiskSummaryCard.css"
import Risk from "../types/Risk";
import riskFactorEval from "../evaluation/riskFactorEval";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";

export default function RiskSummaryCard({risk, onDelete}:{risk: Risk, onDelete:(id: string) => void})

    {
    const [assessmentReady, setAssessmentReady] = useState<boolean>(false)
    let riskFactorEvaluation = riskFactorEval(risk.healthHazard, risk.probability, risk.frequency);

    const cardColor = (riskFactor: string) => {
        switch (riskFactor) {
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
        <div className={"RiskSumCard"} style={{background: cardColor(riskFactorEvaluation.riskFactor)}}>
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
                <div className={"RiskEval"}>
                    <div className={"RiskComponents"}>
                        <p>{riskFactorEvaluation.healthComponent}</p>
                        <p>{riskFactorEvaluation.probabilityComponent}</p>
                    </div>
                    <div>
                        <h6>{riskFactorEvaluation.finalEval}</h6>
                    </div>
                </div>
            }
            {risk.id &&
                <Button onClick={() => onDelete(risk.id)}>Delete</Button>
            }
        </div>
    )
    }