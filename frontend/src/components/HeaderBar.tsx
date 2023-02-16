import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";
import {useNavigate} from "react-router-dom";
import Project from "../types/Project";
import logo from "../EasyRiskLogoSmall.png";
import React from "react";


export default function HeaderBar ({project, reAssessment}:{project: Project | null, reAssessment: boolean
}) {

    const navigate = useNavigate();

    return (
        <div>
            <Navbar variant={"dark"} bg={"primary"} className={"MenuBar"}>
                <img className={"LogoSmall"} src={logo} alt={"Easy Risk Logo"}/>
                <Container fluid>
                    {!reAssessment && project ?
                        <Navbar.Brand>Project Details: {project.projectId}</Navbar.Brand>
                        :
                        <Navbar.Brand>Assessment Page</Navbar.Brand>
                    }
                    <Nav>
                        <NavDropdown title={"Menu"} drop={"start"}>
                            <NavDropdown.Item onClick={() => navigate("/")}>Dashboard</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Container>
            </Navbar>

        </div>
    )
}