import "./RiskDetails.css"
import Menu from "../components/Menu";
import React, {useState} from "react";
import RiskSummaryCard from "../components/RiskSummaryCard";
import {Dropdown} from "react-bootstrap";

type Risk = {
    id: string,
    projectId: string,
    riskName: string,
    riskDescription: string,
    riskReductionMeasures: string[],
    healthHazard: number,
    probability: number,
    frequency: number
}
const initialRiskState = {
    id: "",
    projectId: "",
    riskName: "",
    riskDescription: "",
    riskReductionMeasures: [],
    healthHazard: 0,
    probability: 0,
    frequency: 0
}

export default function RiskDetails() {

    const [currentRisk, setRisk] = useState<Risk>(initialRiskState)

    const editRisk = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setRisk( {
            ...currentRisk,
            [name]: value,
        })
    }

    const healthHazardModifier = (int: number) => {
        setRisk({
            ...currentRisk,
            healthHazard: int,
        })
    }

    const probabilityModifier = (int: number) => {
        setRisk( {
            ...currentRisk,
            probability: int,
        })
    }

    const frequencyModifier = (int: number) => {
        setRisk( {
            ...currentRisk,
            frequency: int,
        })
    }

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <div className={"RiskDetails"}>
                <h4>Risk Detail Page</h4>
                <div>
                    <form>
                        <div>
                            <h4>Risk factor</h4>
                            <input placeholder={"Risk Factor Name"}
                                   name={"riskName"}
                                   value={currentRisk.riskName}
                                   onInput={editRisk}></input>
                            <input placeholder={"Risk Factor description"}
                                   name={"riskDescription"}
                                   value={currentRisk.riskDescription}
                                   onChange={editRisk}></input>
                        </div>
                        <div>
                            <h4>Risk Reduction measures</h4>
                            <input placeholder={"Please enter specific details on how the " +
                                "above mentioned risk factor will be "}></input>
                        </div>
                        <div className={"RiskMetricButtonBar"}>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}>
                                    Hazard to Health
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item onClick={() => healthHazardModifier(1)}>
                                        Minor: Level 1</Dropdown.Item>
                                    <Dropdown.Item onClick={() => healthHazardModifier(2)}>
                                        Serious: Level 2</Dropdown.Item>
                                    <Dropdown.Item onClick={() => healthHazardModifier(3)}>
                                        Critical: Level 3</Dropdown.Item>
                                    <Dropdown.Item onClick={() => healthHazardModifier(4)}>
                                        Lethal: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}>
                                    Probability
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item onClick={() => probabilityModifier(1)}>
                                        Highly Unlikely: Level 1</Dropdown.Item>
                                    <Dropdown.Item onClick={() => probabilityModifier(2)}>
                                        Possible: Level 2</Dropdown.Item>
                                    <Dropdown.Item onClick={() => probabilityModifier(3)}>
                                        Likely: Level 3</Dropdown.Item>
                                    <Dropdown.Item onClick={() => probabilityModifier(4)}>
                                        Highly Likely: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                            <Dropdown>
                                <Dropdown.Toggle className={"RiskMetricsButtons"}>
                                    Frequency
                                </Dropdown.Toggle>

                                <Dropdown.Menu>
                                    <Dropdown.Item onClick={() => frequencyModifier(1)}>
                                        Once: Level 1</Dropdown.Item>
                                    <Dropdown.Item onClick={() => frequencyModifier(2)}>
                                        Rarely: Level 2</Dropdown.Item>
                                    <Dropdown.Item onClick={() => frequencyModifier(3)}>
                                        Occasionally: Level 3</Dropdown.Item>
                                    <Dropdown.Item onClick={() => frequencyModifier(4)}>
                                        Regularly: Level 4</Dropdown.Item>
                                </Dropdown.Menu>
                            </Dropdown>
                        </div>
                    </form>
                </div>
                <br></br>
                <br></br>
                <br></br>
                <RiskSummaryCard {...currentRisk}/>
            </div>
        </div>
    );
}