import Logout from "../components/Logout";
import "./Dashboard.css";
import Accordion from 'react-bootstrap/Accordion';


export default function Dashboard () {
    return(
        <div className={"DashboardPage"}>
            <h4>Hello --Member--</h4>
            <div><h4>Create a new project risk assessment</h4></div>
            <Accordion>
                <Accordion.Item eventKey={"1"}>
                    <Accordion.Header>View your risk assessments for planned projects</Accordion.Header>
                    <Accordion.Body></Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey={"2"}>
                    <Accordion.Header>View your risk assessments for current projects</Accordion.Header>
                    <Accordion.Body></Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey={"3"}>
                    <Accordion.Header>View your risk assessments for finished projects</Accordion.Header>
                    <Accordion.Body></Accordion.Body>
                </Accordion.Item>
            </Accordion>
            <Logout/>
        </div>

    );
}