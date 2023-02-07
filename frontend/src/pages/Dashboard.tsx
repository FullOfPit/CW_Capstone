import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';
import {Card} from "react-bootstrap";
import Menu from "../components/Menu";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import Project from "../types/Project";
import axios from "axios";
import ProjectSummaryCard from "../components/ProjectSummaryCard";
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer} from "react-toastify";
export default function Dashboard () {

    const navigate = useNavigate();
    const [allProjects, setAllProjects] = useState<Project[]>([])
    const [username, setUsername] = useState<string>("")

    useEffect(() => {
        (async () => {
            try {
                const user = await axios.get("/api/app-users/me");
                const response = await axios.get(`/api/projects/app-users/${user.data.id}`)
                setUsername(user.data.username);
                setAllProjects(response.data);
            } catch (e) {
                console.log("Something went wrong", e)
            }
        })()
    }, [])

    return (
        <div>
            <Menu projects={allProjects} username={username}/>

            <div className={"DashboardPage"}>
                <Card className={"DashboardPageProjectCreatorCard"}>
                    <Card.Header className={"target"} onClick={() => navigate("/newproject")}>
                        New Project Risk Assessment</Card.Header>
                </Card>

                <Accordion defaultActiveKey={"2"} className={"ProjectAccordion"}>
                    <Accordion.Item eventKey={"1"}>
                        <Accordion.Header>Planned Projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "PLANNED"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project}
                                                                      setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"2"}>
                        <Accordion.Header>Current Projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "CURRENT"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project}
                                                                      setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"3"}>
                        <Accordion.Header>Finished Projects</Accordion.Header>
                        <Accordion.Body>
                            {allProjects.filter((project) => (project.projectStatus === "FINISHED"))
                                .map((project) => <ProjectSummaryCard key={project.id} project={project}
                                                                      setAllProjects={setAllProjects}/>)}
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
                <ToastContainer limit={1}/>
            </div>
        </div>
    );
}