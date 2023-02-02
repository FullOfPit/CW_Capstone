
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import "./Menu.css"
import {useCallback} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";
import Project from "../types/Project";

export default function Menu({projects, username}:{projects: Project[], username: string}) {
    const navigate = useNavigate();
    const location = useLocation();

    const logout = useCallback(async () => {
        await axios.get("/api/app-users/logout");
        navigate("/login?redirect=" + encodeURIComponent(location.pathname + location.search));
        window.document.cookie = "";
        window.localStorage.clear();
    }, [location, navigate]);

    return (
        <Navbar variant={"light"} bg={"light"}>
            <Container fluid>
                <Navbar.Brand>Welcome Back {username}!</Navbar.Brand>
                <Nav>
                    <NavDropdown title={"Menu"} drop={"start"}>
                        <NavDropdown.Item onClick={() => navigate("/")}>Dashboard</NavDropdown.Item>
                        <NavDropdown.Item href={"/newproject"}>New Project</NavDropdown.Item>
                        <NavDropdown.Divider/>
                        <NavDropdown.Item>Projects</NavDropdown.Item>
                        {projects.map((project) => (
                            <NavDropdown.Item key={project.id}
                                              onClick={() => navigate(`/projectdetails/${project.id}`)}>
                                {project.projectId}</NavDropdown.Item>)
                        )}
                        <NavDropdown.Divider/>
                        <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>

                    </NavDropdown>
                </Nav>


            </Container>
        </Navbar>
    )

}