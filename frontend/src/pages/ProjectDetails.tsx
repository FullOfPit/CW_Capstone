import "./ProjectDetails.css";
import "../index.css";
import {useLocation, useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import Project from "../types/Project";
import Risk from "../types/Risk";
import axios from "axios";
import {Button} from "react-bootstrap";
import RiskSummaryCard from "../components/RiskSummaryCard";
import RiskBarTwoD from "../plots/RiskBarTwoD";
import RiskBarThreeD from "../plots/RiskBarThreeD";
import FileUploadForm from "../components/FileUploadForm";
import Accordion from "react-bootstrap/Accordion";
import AccordionBody from "react-bootstrap/AccordionBody";
import HeaderBar from "../components/HeaderBar";

export default function ProjectDetails() {

    const [project, setProject] = useState<Project|null>(null);
    const [riskList, setRiskList] = useState<Risk[]|null>(null);
    const [isReady, setIsReady] = useState<boolean>(false)

    const location = useLocation();
    let idString = location.pathname.toString().split("/").pop()

    const navigate = useNavigate();

    useEffect(() => {(async () => {
        try {
            const projectResponse = await axios.get(`/api/projects/${idString}`);
            setProject(projectResponse.data);
            const riskList = await axios.get(`/api/risks/projects/${projectResponse.data.id}`)
            setRiskList(riskList.data);
        } catch (e) {
            console.log("Something went wrong", e)
        } finally {
            setIsReady(true);
        }
    })()}, [idString])

    const onDelete = (id: string) => {
        (async () => {
            try {
                await axios.delete(`/api/risks/${id}`);
                riskList && setRiskList(riskList.filter((risk) => risk.id !== id));
            } catch (e) {
                console.log("Error while deleting the project", e);
            }
        })()
    }

    return (
        <div>
            {
                (isReady && project && riskList)
                    ?
                <div>
                <HeaderBar project={project} reAssessment={false}/>
                <div className={"ScreenLimit"}>
                    <header>
                        <h4>{project.projectName}</h4>
                    </header>
                    <div className={"ProjectContent"}>
                        <div className={"ProjectData"}>
                            <table>
                                <tbody>
                                <tr>
                                    <td className={"ProjectDataTable"}><h5>Project ID:</h5></td>
                                    <td className={"ProjectDataTable"}><h5>{project.projectId}</h5></td>
                                </tr>
                                <tr>
                                    <td className={"ProjectDataTable"}><h5>Assessor: </h5></td>
                                    <td className={"ProjectDataTable"}><h5>{project.assessorName}</h5></td>
                                </tr>
                                </tbody>
                            </table>
                            <table>
                                <tbody>
                                <tr>
                                    <td className={"ProjectDataTable"}><h5>Project Status:</h5></td>
                                    <td className={"ProjectDataTable"}><h5>{project.projectStatus}</h5></td>
                                </tr>
                                <tr>
                                    <td className={"ProjectDataTableLeft"}><h5>Start Date:</h5></td>
                                    <td><h5>{project.plannedStartDate}</h5></td>
                                </tr>
                                <tr>
                                    <td className={"ProjectDataTableLeft"}><h5>Finish Date:</h5></td>
                                    <td><h5>{project.plannedFinishDate}</h5></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <Accordion className={"ProjectDetailsAccordion"} defaultActiveKey={"1"}>
                        <Accordion.Item eventKey={"1"}>
                            <Accordion.Header>Project Details</Accordion.Header>
                            <AccordionBody>
                                <div className={"ProjectDescription"}>
                                    <p>{project.projectDetails}</p>
                                </div>
                            </AccordionBody>
                        </Accordion.Item>
                        <Accordion.Item eventKey={"2"}>
                            <Accordion.Header>Risk Factors</Accordion.Header>
                            <Accordion.Body className={"RiskSummaryCards"}>
                                {riskList.map((risk) => <RiskSummaryCard key={risk.id} risk={risk} onDelete={onDelete}/>)}
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey={"3"}>
                            <Accordion.Header>Additional Documents</Accordion.Header>
                            <Accordion.Body>
                                <FileUploadForm project={project} setProject={setProject} fileUploadOption={false}/>
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>


                    <div className={"ProjectPlots"}>
                        <RiskBarTwoD risks={riskList}/>
                        <RiskBarThreeD risks={riskList}/>
                    </div>

                    <div className={"ButtonBox"}>
                        <Button onClick={() => navigate("/")}>Back</Button>
                        <Button onClick={() => navigate(`/newproject/${project.id}`)}>Re-Assess this Project</Button>
                    </div>

                </div>
                </div>
                :
                <div>Something went wrong</div>

            }
        </div>
    )
}