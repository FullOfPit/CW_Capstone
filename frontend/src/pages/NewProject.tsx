import "./NewProject.css"
import Menu from "../components/Menu";
import {useLocation, useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import React, {useCallback, useState} from "react";
import Project from "../types/Project";
import axios from "axios";
import Risk from "../types/Risk";
import RiskSummaryCard from "../components/RiskSummaryCard";
import RiskDetails from "../components/RiskDetails";

const emptyProject = {
    createdBy: "",
    projectId: "",
    projectName: "",
    createdAt: "",
    plannedStartDate: "2023-01-01",
    plannedFinishDate: "2023-01-01",
    projectStatus: "PLANNED",
    assessorName: "",
    projectDetails: ""
}

export default function NewProject() {

    const [isReady, setIsReady] = useState<boolean>(false);
    const [project, setProject] = useState<Project>({...emptyProject, "id": ""});
    const [projectSet, setProjectSet] = useState<boolean>(false);
    const [risks, setRisks] = useState<Risk[]>([]);
    const [riskOpen, setRiskOpen] = useState<boolean>(false);

    const navigate = useNavigate();
    const location = useLocation();

    const editProject = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setProject({
            ...project,
            [name]: value,
        })
        console.log(project);
        console.log(location.search);
    };
    const projectCreation = useCallback((event: React.MouseEvent<HTMLButtonElement>) => {(async () => {
        event.preventDefault();
        try {
            const userId = await axios.get("/api/app-users/me");
            const response = await axios.post("/api/projects", {...project, "createdBy": userId.data.id, "id": null});
            navigate(`/newproject?${encodeURIComponent(response.data.id)}`);
            setProject({...response.data});
            setProjectSet(true);
        } catch (e) {
            console.log("Error while creating a new project has occurred", e);
        } finally {
            setIsReady(true);

        }
    })()
    }, [navigate, project]);

    const onCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();
            try {
                navigate("/")
            } catch (e) {
                console.log("Error while deleting the project", e)
            }
        })()};

    const onSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();
            try {
                await axios.put(`/api/projects/${project.id}`, {...project})
                navigate("/")
            } catch (e) {
                console.log("Error while deleting the project", e)
            }
        })()}

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <h4>New Project</h4>

                <Form>
                    <Form.Group className={"NewProjectHead"}>
                        <Form.Label>Project Name:</Form.Label>
                        <Form.Control placeholder={project.projectName || "Project Name"}
                                      name={"projectName"}
                                      value={project.projectName}
                                      onChange={editProject}
                        ></Form.Control>
                    </Form.Group>
                    <Form.Group className={"NewProjectHead"}>
                        <Form.Label>Project ID:</Form.Label>
                        <Form.Control placeholder={project.projectId || "Project ID"}
                                      name={"projectId"}
                                      value={project.projectId}
                                      maxLength={26}
                                      onChange={editProject}></Form.Control>
                    </Form.Group>

                    <Form.Group className={"NewProjectDates"}>
                        <div>
                            <Form.Label>Planned Start Date</Form.Label>
                            <Form.Control placeholder={project.plannedStartDate || "Planned Start Date"}
                                          type={"date"}
                                          name={"plannedStartDate"}
                                          value={project.plannedStartDate}
                                          onInput={editProject}
                            ></Form.Control>
                        </div>
                        <div>
                            <Form.Label>Planned Finish Date</Form.Label>
                            <Form.Control placeholder={project.plannedFinishDate || "Planned Finish Date"}
                                          type={"date"}
                                          name={"plannedFinishDate"}
                                          value={project.plannedFinishDate}
                                          onInput={editProject}
                            ></Form.Control>
                        </div>
                    </Form.Group>
                    <Form.Group className={"NewProjectDescription"}>
                        <Form.Label>Project Description</Form.Label>
                        <Form.Control placeholder={project.projectDetails || "Please enter specific details on your project"}
                                      name={"projectDetails"}
                                      value={project.projectDetails}
                                      as={"textarea"}
                                      onInput={editProject}
                        ></Form.Control>
                    </Form.Group>

                    {!isReady &&
                        <div>
                            {!projectSet &&
                                <Button onClick={(event) => onCancel(event)}>Cancel</Button>}
                            <Button onClick={(event) => projectCreation(event)}>Assess Risks</Button>
                        </div>
                    }

                    {isReady &&

                        <div>
                            <div className={"RiskDetailCards"}>
                                {risks.filter((risk) => (risk.projectId === project.id))
                                    .map((risk) => <RiskSummaryCard key={risk.id} risk={risk}/>)}

                                {!riskOpen &&
                                    <Button onClick={() => {setRiskOpen(true)}}>
                                        Assess New Risk Factor</Button>}

                                {riskOpen &&
                                    <RiskDetails id={project.id}
                                                 setRiskOpen={setRiskOpen}
                                                 setRisks={setRisks}/>}

                            </div>

                            <Form.Group className={"NewProjectHead"}>
                            <Form.Label>Assessed By:</Form.Label>
                            <Form.Control placeholder={"Name of project assessor"}
                            name={"assessorName"}
                            value={project.assessorName}
                            onInput={editProject}
                            ></Form.Control>
                            </Form.Group>

                            <div>
                                {projectSet &&
                                    <Button onClick={() => navigate("/")}>Back</Button>}
                            <Button type={"submit"} onClick={(event) => onSave(event)}>Save</Button>
                            </div>
                        </div>
                    }
                </Form>
        </div>
    )
}