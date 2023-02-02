import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';
import {BsPlusLg} from 'react-icons/bs';
import {Card} from "react-bootstrap";
import Menu from "../components/Menu";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import Project from "../types/Project";
import axios from "axios";
import ProjectSummaryCard from "../components/ProjectSummaryCard";
import 'react-toastify/dist/ReactToastify.css';

export default function Dashboard () {

    const navigate = useNavigate();
    const [allProjects, setAllProjects] = useState<Project[]>([])
    const [username, setUsername] = useState<string>("")

    useEffect(() => {(async () => {
        try {
            const user = await axios.get("/api/app-users/me");
            const response = await axios.get(`/api/projects/app-users/${user.data.id}`)
            setUsername(user.data.username);
            setAllProjects(response.data);
        } catch (e) {
            console.log("Something went wrong", e)
        }
    })()}, [])

    return(
        <div className={"ScreenLimit"}>
            <Menu projects={allProjects} username={username}/>

            <div className={"DashboardPage"}>
                <Card className={"DashboardPageProjectCreatorCard"}>
                    <Card.Header className={"target"}>Create a new project risk assessment</Card.Header>
                    <button onClick={() => navigate("/newproject")}><BsPlusLg size={26}/></button>
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