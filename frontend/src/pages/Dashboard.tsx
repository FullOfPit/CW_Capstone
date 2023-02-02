import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';
import {BsPlusLg} from 'react-icons/bs';
import {Button, Card} from "react-bootstrap";
import Menu from "../components/Menu";
import React, {useCallback, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import Project from "../types/Project";
import axios from "axios";
import ProjectSummaryCard from "../components/ProjectSummaryCard";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export default function Dashboard () {

    const navigate = useNavigate();
    const [allProjects, setAllProjects] = useState<Project[]>([])

    useEffect(() => {(async () => {
        try {
            const user = await axios.get("/api/app-users/me");
            const response = await axios.get(`/api/projects/app-users/${user.data.id}`)
            setAllProjects(response.data);
        } catch (e) {
            console.log("Something went wrong", e)
        }
    })()}, [])

    const onNewProject = useCallback((event: React.MouseEvent<HTMLButtonElement>) => {(async () => {
        event.preventDefault();
        try {
            navigate(`/newproject`);
        } catch (e) {
            console.log("Error while creating a new project has occurred", e);
        }
    })()
    }, [navigate]);

    const message = () => toast("wow so easy!");


    return(
        <div className={"ScreenLimit"}>
            <Menu projects={allProjects}/>


            <Button onClick={message}>Notify!</Button>
            <ToastContainer/>


            <div className={"DashboardPage"}>
                <Card className={"DashboardPageProjectCreatorCard"}>
                    <Card.Header className={"target"}>Create a new project risk assessment</Card.Header>
                    <button onClick={(event) => onNewProject(event)}><BsPlusLg size={26}/></button>
                </Card>

                <Accordion defaultActiveKey={""} className={"ProjectAccordion"}>
                    <Accordion.Item eventKey={"1"}>
                        <Accordion.Header>View your risk assessments for planned projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "PLANNED"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project} setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"2"}>
                        <Accordion.Header>View your risk assessments for current projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "CURRENT"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project} setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"3"}>
                        <Accordion.Header>View your risk assessments for finished projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "FINISHED"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project} setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </div>
        </div>


    );
}