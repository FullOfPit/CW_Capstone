import "./NewProject.css"
import "../index.css";
import {useLocation, useNavigate} from "react-router-dom";
import {Button, Form} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import Project from "../types/Project";
import axios from "axios";
import RiskSummaryCard from "../components/RiskSummaryCard";
import Risk from "../types/Risk";
import RiskDetails from "../components/RiskDetails";
import {toast, ToastContainer} from "react-toastify";
import {projectValidation, riskListValidation} from "../validation/validation";
import FileUploadForm from "../components/FileUploadForm";
import HeaderBar from "../components/HeaderBar";

const emptyProject = {
    createdBy: "",
    projectId: "",
    projectName: "",
    createdAt: "",
    plannedStartDate: new Date().toISOString().slice(0, 10),
    plannedFinishDate: new Date().toISOString().slice(0, 10),
    projectStatus: "PLANNED",
    assessorName: "",
    projectDetails: "",
    documentIds: []
}

export default function NewProject() {

    const [project, setProject] = useState<Project>({...emptyProject, "id": ""});
    const [assessmentRdy, setAssessmentRdy] = useState<boolean>(false);
    const [risks, setRisks] = useState<Risk[]>([]);
    const [riskOpen, setRiskOpen] = useState<boolean>(false);
    const [reAssessment, setReAssessment] = useState<boolean>(false);

    const navigate = useNavigate();
    const location = useLocation();

    let idString = location.pathname.toString().split("/").pop();

    useEffect(() => {(async () => {
        if (idString && idString.length === 24) {
            try {
                const projectResponse = await axios.get(`/api/projects/${idString}`);
                setProject(projectResponse.data);
                const riskList = await axios.get(`/api/risks/projects/${projectResponse.data.id}`)
                setRisks(riskList.data);
                setAssessmentRdy(true);
                setReAssessment(true)
            } catch (e) {
                console.log("Something went wrong", e)
            }
        }
    })()}, [idString])

    const editProject = (event: React.ChangeEvent<HTMLInputElement>) => {
        const name = event.target.name;
        const value = event.target.value;
        setProject({
            ...project,
            [name]: value,
        })
    };

    const onSave = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();

            let projectValid = projectValidation(project);

            if (projectValid.validation) {
                try {
                    const userId = await axios.get(`/api/app-users/me`)
                    const response = await axios.post(`/api/projects`,
                        {...project, "id":null, "createdBy": userId.data.id})
                    setProject(response.data);
                } catch (e) {
                    console.log("Error while posting the project", e)
                } finally {
                    setAssessmentRdy(true);
                }
            } else {
                projectValid.validationFails.forEach((fail) => toast.error(fail, {
                    position: "top-center",
                        autoClose: 5000,
                        hideProgressBar: true,
                        closeOnClick: false,
                        pauseOnHover: false,
                }))
            }
        })()}

    const onFinish = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();

            let projectValid = projectValidation(project);
            let riskListValid = riskListValidation(risks);

            if(projectValid.validation && riskListValid.validation) {
                try {
                    await axios.put(`/api/projects/${project.id}`, {...project})
                } catch (e) {
                    console.log("Error while posting the project", e)
                } finally {
                    navigate("/")
                }
            } else {
                projectValid.validationFails.forEach((fail) => toast.error(fail, {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: true,
                    closeOnClick: false,
                    pauseOnHover: false,
                }));
                riskListValid.validationFails.forEach((fail) => toast.error(fail, {
                    position: "top-center",
                    autoClose: 5000,
                    hideProgressBar: true,
                    closeOnClick: false,
                    pauseOnHover: false,
                }))
            }

        })()}

    const onCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();
            try {
                await axios.delete(`/api/projects/${project.id}`)
            } catch (e) {
                console.log("Error while deleting the project", e)
            } finally {
                navigate("/")
            }
        })()}

    const onDelete = (id: string) => {
        (async () => {
            try {
                await axios.delete(`/api/risks/${id}`)
                setRisks(risks.filter((risk) => risk.id !== id));
            } catch (e) {
                console.log("Error while deleting the project", e)
            }
        })()
    }

    return (
        <div>
            <HeaderBar project={project} reAssessment={reAssessment}/>
            <div className={"ScreenLimit"}>
                <div className={"NewEditProjectHead"}>
                    {reAssessment ? <h4>Project Reassessment</h4> : <h4>New Project</h4>}
                </div>

                    <Form className={"NewProjectForm"} autoComplete={"off"}>
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
                                              min={new Date().toISOString().slice(0, 10)}
                                              name={"plannedStartDate"}
                                              value={project.plannedStartDate}
                                              onInput={editProject}
                                ></Form.Control>
                            </div>
                            <div>
                                <Form.Label>Planned Finish Date</Form.Label>
                                <Form.Control placeholder={project.plannedFinishDate || "Planned Finish Date"}
                                              type={"date"}
                                              min={new Date().toISOString().slice(0, 10)}
                                              name={"plannedFinishDate"}
                                              value={project.plannedFinishDate}
                                              onInput={editProject}
                                ></Form.Control>
                            </div>
                        </Form.Group>
                        <Form.Group className={"NewProjectDescription"}>
                            <Form.Label>Project Description</Form.Label>
                            <Form.Control placeholder={project.projectDetails
                                ||
                                "Please enter specific details on your project"}
                                          name={"projectDetails"}
                                          value={project.projectDetails}
                                          as={"textarea"}
                                          onInput={editProject}
                            ></Form.Control>
                        </Form.Group>
                            <div className={"AssessorStep"}>
                                <Form.Group className={"NewProjectHead"}>
                                <Form.Label>Assessed By:</Form.Label>
                                <Form.Control placeholder={"Name of project assessor"}
                                name={"assessorName"}
                                value={project.assessorName}
                                onInput={editProject}
                                ></Form.Control>
                                </Form.Group>

                                {!assessmentRdy &&
                                    <div className={"ButtonBox"}>
                                        <Button onClick={() => navigate("/")}>Back</Button>
                                        <Button type={"submit"} onClick={(event) => onSave(event)}>Save and Next</Button>

                                    </div>
                                }
                            </div>
                    </Form>

                {assessmentRdy &&
                    <div className={"RiskAssessment"}>
                        <div className={"RiskSummaryCards"}>
                            {risks.filter((risk) => (risk.projectId === project.id))
                                .map((risk) => <RiskSummaryCard key={risk.id} risk={risk} onDelete={onDelete}/>)}

                            {!riskOpen ?
                                <Button onClick={() => {setRiskOpen(true)}}>
                                    Assess New Risk Factor</Button>
                                :
                                <RiskDetails id={project.id}
                                             setRiskOpen={setRiskOpen}
                                             setRisks={setRisks}/>
                            }
                        </div>

                        <FileUploadForm project={project} setProject={setProject} fileUploadOption={true}/>

                    </div>

                }
                {assessmentRdy &&

                    <div className={"ButtonBox"}>
                        {reAssessment ?
                            <Button onClick={() => navigate(`/projectdetails/${project.id}`)}>
                                Cancel Re-Assessment</Button>
                            :
                            <Button onClick={(event) => onCancel(event)}>Cancel Assessment</Button>}
                        <Button onClick={(event) => onFinish(event)}>Finish Assessment</Button>
                    </div>
                }
            </div>
            <ToastContainer />
        </div>
    )
}