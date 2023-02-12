import "./RiskDetails.css"

import React, {useState} from "react";
import {Button, Dropdown, Form} from "react-bootstrap";
import axios from "axios";
import Risk from "../types/Risk";
import RiskSummaryCard from "./RiskSummaryCard";
import {toast} from "react-toastify";
import {riskValidation} from "../validation/validation";
import {riskComponentColorCode} from "../evaluation/riskFactorEvaluation";

export default function RiskDetails({id, setRiskOpen, setRisks}:
{id: string, setRiskOpen: (riskOpen: boolean) => void, setRisks: (risks: Risk[]) => void})
{
    const emptyRisk = {
        id: "",
        projectId: id,
        riskName: "",
        riskDescription: "",
        riskReductionMeasures: "",
        healthHazard: 0,
        probability: 0,
        frequency: 0
    }

    const [currentRisk, setRisk] = useState<Risk>(emptyRisk)

    const editRisk = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setRisk( {
            ...currentRisk,
            [name]: value,
        })
    }

    const metricModifier = (metricName: string, metricValue: number) => {
        setRisk( {
            ...currentRisk,
            [metricName]: metricValue,
        })
    }

    const saveRisk = (e: React.MouseEvent<HTMLButtonElement>) => {(async () => {
        e.preventDefault();

        if (riskValidation(currentRisk).validation) {
            try {
                await axios.post("/api/risks", {...currentRisk, "id": null});
            } catch (e) {
                console.log("Error posting new risk", e)
            } finally {
                const response = await axios.get(`/api/risks/projects/${id}`);
                setRisks(response.data)
                setRiskOpen(false);
            }
        } else {
            riskValidation(currentRisk).validationFails.forEach((fail) => toast.error((fail), {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: true,
                closeOnClick: false,
                pauseOnHover: false,
            }));
        }

    })()}
    //Dummy function to satisfy the requirement for RiskSummaryCard
    const onDelete = (id: string) => {}
    return (
        <div>
            <div>
                <RiskSummaryCard risk={currentRisk} onDelete={onDelete}/>
            </div>
            <div className={"RiskDetails"}>
                <div className={"RiskDetailsContent"}>
                    <Form autoComplete={"off"}>
                        <Form.Group className={"RiskDetailsFactor"}>
                            <Form.Label>Risk factor: </Form.Label>
                            <Form.Control className={"RiskDetailsFactorName"}
                                          placeholder={"Risk Factor Name"}
                                          name={"riskName"}
                                          value={currentRisk.riskName}
                                          maxLength={56}
                                          onInput={editRisk}></Form.Control>
                        </Form.Group>
                        <Form.Group className={"RiskDetailsTextForm"}>
                            <Form.Label>Risk Factor Description</Form.Label>
                            <Form.Control className={"RiskDetailsFactorDescription"}
                                          placeholder={"Please enter specific details on the risk you are assessing"}
                                          name={"riskDescription"}
                                          value={currentRisk.riskDescription}
                                          as={"textarea"}
                                          onInput={editRisk}></Form.Control>

                        </Form.Group>
                        <Form.Group className={"RiskDetailsTextForm"}>
                            <Form.Label>Risk Reduction measures</Form.Label>
                            <Form.Control className={"RiskDetailsTextArea"}
                                          placeholder={"Please enter specific details on how you are taking measure to reduce this risk and any potentially arising situation"}
                                          name={"riskReductionMeasures"}
                                          value={currentRisk.riskReductionMeasures}
                                          as={"textarea"}
                                          onInput={editRisk}></Form.Control>
                        </Form.Group>

                        <div className={"RiskMetricButtonBar"}>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}
                                                 style={{
                                                     backgroundColor: riskComponentColorCode(currentRisk.healthHazard),
                                                     borderColor: "black",
                                                     color: "black"
                                                 }}>
                                    Hazard to Health {currentRisk.healthHazard || ""}
                                </Dropdown.Toggle >

                                <Dropdown.Menu>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("healthHazard", 1)}>
                                        Minor: Level 1</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("healthHazard", 2)}>
                                        Serious: Level 2</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("healthHazard", 3)}>
                                        Critical: Level 3</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("healthHazard", 4)}>
                                        Lethal: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}
                                                 style={{
                                                     backgroundColor: riskComponentColorCode(currentRisk.probability),
                                                     borderColor: "black",
                                                     color: "black"
                                                 }}>
                                    Probability {currentRisk.probability || ""}
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("probability", 1)}>
                                        Highly Unlikely: Level 1</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("probability", 2)}>
                                        Possible: Level 2</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("probability", 3)}>
                                        Likely: Level 3</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("probability", 4)}>
                                        Highly Likely: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}
                                                 style={{
                                                     backgroundColor: riskComponentColorCode(currentRisk.frequency),
                                                     borderColor: "black",
                                                     color: "black"
                                                 }}>
                                    Frequency {currentRisk.frequency || ""}
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("frequency", 1)}>
                                        Once: Level 1</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("frequency", 2)}>
                                        Rarely: Level 2</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("frequency", 3)}>
                                        Occasionally: Level 3</Dropdown.Item>
                                    <Dropdown.Item
                                        onClick={() => metricModifier("frequency", 4)}>
                                        Regularly: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        </div>
                        <div className={"ButtonBox"}>
                            <Button onClick={() => setRiskOpen(false)}>Cancel</Button>
                            <Button type={"submit"} onClick={(event) => saveRisk(event)}>Save</Button>
                        </div>
                    </Form>
                </div>
            </div>

        </div>
    );

}

