import "./NewProject.css"
import {useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import React, {useState} from "react";
import Project from "../types/Project";
import axios from "axios";

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

    const [project, setProject] = useState<Project>({...emptyProject, "id": ""});
    const [assessmentRdy, setAssessmentRdy] = useState<boolean>(false);

    const navigate = useNavigate();

    const editProject = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setProject({
            ...project,
            [name]: value,
        })
        console.log(project);
    };

    const onSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();
            try {
                const userId = await axios.get(`/api/app-users/me`)
                const response = await axios.post(`/api/projects`, {...project, "id":null, "createdBy": userId.data.id})
                setProject(response.data);
            } catch (e) {
                console.log("Error while deleting the project", e)
            } finally {
                setAssessmentRdy(true);
            }
        })()}


    return (
        <div className={"ScreenLimit"}>
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
                        <div>
                            <Form.Group className={"NewProjectHead"}>
                            <Form.Label>Assessed By:</Form.Label>
                            <Form.Control placeholder={"Name of project assessor"}
                            name={"assessorName"}
                            value={project.assessorName}
                            onInput={editProject}
                            ></Form.Control>
                            </Form.Group>

                            <div>
                                <Button onClick={() => navigate("/")}>Back</Button>
                                <Button type={"submit"} onClick={(event) => onSave(event)}>Save and Next</Button>
                            </div>
                        </div>
                </Form>

        </div>
    )
}