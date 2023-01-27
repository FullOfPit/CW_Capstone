import "./NewProject.css"
import Menu from "../components/Menu";
import {useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";

type Project = {
    id: string,
    projectName: string,
    createdAt: string,
    plannedStartDate: string,
    plannedFinishDate: string,
    projectStatus: string,
    assessorName: string,
    projectDetails: string
}

export default function NewProject() {

    const navigate = useNavigate();

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <h4>New Project Page</h4>

            <Form>
                <Form.Group>
                    <Form.Label>Project Name</Form.Label>
                    <Form.Control placeholder={"Project Name"}
                                  name={"ProjectName"}></Form.Control>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Project Dates</Form.Label>
                    <div>
                        <Form.Label>Planned Start Date</Form.Label>
                        <Form.Control placeholder={"Planned Start Date"}
                                      type={"date"}></Form.Control>
                    </div>
                    <div>
                        <Form.Label>Planned Finish Date</Form.Label>
                        <Form.Control placeholder={"Planned Start Date"}
                                      type={"date"}></Form.Control>
                    </div>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Project Description</Form.Label>
                    <Form.Control placeholder={"Please enter specific details on your project"}></Form.Control>
                </Form.Group>


            </Form>

            <div>
                <div>
                    <button onClick={() => navigate("/riskdetails")}>Add</button>
                </div>
                <div>
                    <button>Cancel</button>
                    <button>Save</button>
                </div>
            </div>
        </div>
    )
}