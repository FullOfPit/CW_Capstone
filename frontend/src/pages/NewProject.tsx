import "./NewProject.css"
import Menu from "../components/Menu";
import {useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import RiskSummaryCard from "../components/RiskSummaryCard";
import React, {useState} from "react";

type Project = {
    id: string,
    createdBy: string,
    projectId: string,
    projectName: string,
    createdAt: string,
    plannedStartDate: string,
    plannedFinishDate: string,
    projectStatus: string,
    assessorName: string,
    projectDetails: string
}

const emptyProject = {
    id: "",
    createdBy: "",
    projectId: "",
    projectName: "",
    createdAt: "",
    plannedStartDate: "",
    plannedFinishDate: "",
    projectStatus: "CURRENT",
    assessorName: "",
    projectDetails: ""
}

export default function NewProject() {

    const [project, setProject] = useState<Project>(emptyProject);
    const navigate = useNavigate();

    const editProject = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setProject({
            ...project,
            [name]: value,
        })
    };

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <h4>New Project Page</h4>

            <Form>
                <Form.Group className={"NewProjectHead"}>
                    <Form.Label>Project Name:</Form.Label>
                    <Form.Control placeholder={"Project Name"}
                                  name={"projectName"}
                                  value={project.projectName}
                                  onChange={editProject}
                    ></Form.Control>
                </Form.Group>
                <Form.Group className={"NewProjectHead"}>
                    <Form.Label>Project ID:</Form.Label>
                    <Form.Control placeholder={"Project ID"}
                                  name={"projectId"}
                                  value={project.projectId}
                                  maxLength={26}
                                  onChange={editProject}
                    ></Form.Control>
                </Form.Group>
                <Form.Group className={"NewProjectDates"}>
                    <div>
                        <Form.Label>Planned Start Date</Form.Label>
                        <Form.Control placeholder={"Planned Start Date"}
                                      type={"date"}
                                      name={"plannedStartDate"}
                                      value={project.plannedStartDate}
                                      onInput={editProject}
                        ></Form.Control>
                    </div>
                    <div>
                        <Form.Label>Planned Finish Date</Form.Label>
                        <Form.Control placeholder={"Planned Start Date"}
                                      type={"date"}
                                      name={"plannedFinishDate"}
                                      value={project.plannedFinishDate}
                                      onInput={editProject}
                        ></Form.Control>
                    </div>
                </Form.Group>
                <Form.Group className={"NewProjectDescription"}>
                    <Form.Label>Project Description</Form.Label>
                    <Form.Control placeholder={"Please enter specific details on your project"}
                                  name={"projectDetails"}
                                  value={project.projectDetails}
                                  as={"textarea"}
                                  onInput={editProject}
                    ></Form.Control>
                </Form.Group>

                <div className={"RiskDetailCards"}>
                    <RiskSummaryCard projectId={"Test"} riskName={"Test"} healthHazard={1} probability={1} frequency={1}/>
                    <Button onClick={() => navigate("/riskdetails")}>Add</Button>
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
                    <Button>Cancel</Button>
                    <Button>Save</Button>
                    <>{console.log(project)}</>
                </div>

            </Form>

        </div>

    )
}