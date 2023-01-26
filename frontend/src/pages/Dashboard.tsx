
import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';
import {BsPlusLg} from 'react-icons/bs';
import {Card} from "react-bootstrap";
import Menu from "../components/Menu";
import React from "react";

export default function Dashboard () {
    return(
        <div className={"ScreenLimit"}>
            <Menu/>
            <div className={"DashboardPage"}>

                <Accordion>
                    <Card className={"DashboardPageProjectCreatorCard"}>
                        <Card.Header>Create a new project risk assessment</Card.Header>
                        <button><BsPlusLg size={26}/></button>
                    </Card>
                </Accordion>

                <Accordion>
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