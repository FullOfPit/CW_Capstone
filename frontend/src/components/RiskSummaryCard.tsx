import "./RiskSummaryCard.css"
import Risk from "../types/Risk";
import riskFactorEval from "../evaluation/riskFactorEval";
import React, {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import Accordion from "react-bootstrap/Accordion";
import AccordionBody from "react-bootstrap/AccordionBody";
import RiskGauge from "../plots/RiskGauge";
import {BsTrash} from "react-icons/bs"

export default function RiskSummaryCard({risk, onDelete}:{risk: Risk, onDelete:(id: string) => void})

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
            <div className={"RiskBody"}>
                <div className={"RiskSumCardName"}>
                    <h5>{risk.riskName}</h5>
                </div>
                <div className={"RiskFactor"}>
                    <h5>Risk Factor</h5>
                    <h5>{assessmentReady ? riskFactorEvaluation.riskFactor : ""}</h5>
                    <div className={"RiskMetrics"}>
                        <p>{`Hazard to Health: ${risk.healthHazard}`}</p>
                        <p>{`Probability: ${risk.probability}`}</p>
                        <p>{`Frequency: ${risk.frequency}`}</p>
                    </div>
                </div>
                <div className={"RiskSummaryPlot"}>
                    <RiskGauge risk={risk}/>
                </div>

                {risk.id ?
                    <Button className={"Bin"} onClick={() => onDelete(risk.id)} variant={"outline-dark"}><BsTrash/></Button>
                    :
                    <Button className={"Bin"} variant={"outline-dark"}><BsTrash/></Button>
                }
            </div>

            <Accordion className={"RiskAccordion"} defaultActiveKey={"3"}>
                <Accordion.Item eventKey={"1"}>
                    <Accordion.Header>Risk Factor Description</Accordion.Header>
                    <AccordionBody>
                        <p>{risk.riskDescription}</p>
                    </AccordionBody>
                </Accordion.Item>
                <Accordion.Item eventKey={"2"}>
                    <Accordion.Header>Risk Reduction Measures</Accordion.Header>
                    <AccordionBody>
                        <p>{risk.riskReductionMeasures}</p>
                    </AccordionBody>
                </Accordion.Item>
                <Accordion.Item eventKey={"3"}>
                    <Accordion.Header>Risk Factor Assessment Result</Accordion.Header>
                    <div className={"RiskEval"}>
                        {assessmentReady &&
                            <AccordionBody>
                                <div className={"RiskComponents"}>
                                    <p>{riskFactorEvaluation.healthComponent}</p>
                                    <p>{riskFactorEvaluation.probabilityComponent}</p>
                                    <h6>{riskFactorEvaluation.finalEval}</h6>
                                </div>
                            </AccordionBody>
                        }
                    </div>
                </Accordion.Item>
            </Accordion>
        </div>
    )
    }