
import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';
import {BsPlusLg} from 'react-icons/bs';
import {Card} from "react-bootstrap";
import Menu from "../components/Menu";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function Dashboard () {

    const [accordionKey, SetAccordionKey] = useState<string>("")
    const navigate = useNavigate();

    return(
        <div className={"ScreenLimit"}>
            <Menu/>
            <div className={"DashboardPage"}>
                <Card className={"DashboardPageProjectCreatorCard"}>
                    <Card.Header className={"target"}>Create a new project risk assessment</Card.Header>
                    <button onClick={() => navigate("/newproject")}><BsPlusLg size={26}/></button>
                </Card>

                <Accordion defaultActiveKey={accordionKey} className={"ProjectAccordion"}>
                    <Accordion.Item eventKey={"1"}>
                        <Accordion.Header>View your risk assessments for planned projects</Accordion.Header>
                        <Accordion.Body>
                            {"ProjectOverviewComponent needs to be here"}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"2"}>
                        <Accordion.Header>View your risk assessments for current projects</Accordion.Header>
                        <Accordion.Body>
                            {"ProjectOverviewComponent needs to be here"}
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey={"3"}>
                        <Accordion.Header>View your risk assessments for finished projects</Accordion.Header>
                        <Accordion.Body>
                            {"ProjectOverviewComponent needs to be here"}
                        </Accordion.Body>
                    </Accordion.Item>
                </Accordion>
            </div>
        </div>


    );
}