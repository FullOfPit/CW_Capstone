
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import "./Menu.css"
import {useCallback} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import axios from "axios";

export default function Menu() {
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
                <Navbar.Brand>Hello Member</Navbar.Brand>
                <Nav>
                    <NavDropdown title={"Menu"}>
                        <NavDropdown.Item>Dashboard</NavDropdown.Item>
                        <NavDropdown.Item>New Project</NavDropdown.Item>
                        <NavDropdown.Divider/>
                        <NavDropdown.Item>Project</NavDropdown.Item>
                        <NavDropdown.Item>Planned Projects</NavDropdown.Item>
                        <NavDropdown.Item>Currently Ongoing Projects</NavDropdown.Item>
                        <NavDropdown.Item>Finished Projects</NavDropdown.Item>
                        <NavDropdown.Divider/>
                        <NavDropdown.Item onClick={logout}>Logout</NavDropdown.Item>

                    </NavDropdown>
                </Nav>


            </Container>
        </Navbar>
    )

}