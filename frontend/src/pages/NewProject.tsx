import "./NewProject.css"
import Menu from "../components/Menu";
import {useLocation, useNavigate} from "react-router-dom";
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
    projectStatus: "CURRENT",
    assessorName: "",
    projectDetails: ""
}

export default function NewProject() {

    const [isReady, setIsReady] = useState<boolean>(false);
    const [project, setProject] = useState<Project>({...emptyProject, "id": ""});

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

    const projectCreation = (event: React.MouseEvent<HTMLButtonElement>) => {(async () => {
        event.preventDefault();
        try {
            const userId = await axios.get("/api/app-users/me");
            emptyProject.createdBy = userId.data.id;
            const response = await axios.post("/api/projects", {...emptyProject});
            navigate(`/newproject?${encodeURIComponent(response.data.id)}`);
            setProject({...response.data});
        } catch (e) {
            console.log("Error while creating a new project has occurred", e);
        } finally {
            setIsReady(true);
        }
    })()

    }

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <h4>New Project Page</h4>

            {!isReady &&
                <Button onClick={(event) => projectCreation(event)}>Create New Project File</Button>
            }


            {isReady &&

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
                        <Button onClick={() => navigate("/riskdetails")}>Assess New Risk Factor</Button>
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
                        <Button type={"submit"}>Save</Button>
                    </div>

                </Form>

            }


        </div>
    )
}