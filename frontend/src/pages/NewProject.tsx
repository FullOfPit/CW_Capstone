import Menu from "../components/Menu";
import {useNavigate} from "react-router-dom";

export default function NewProject() {

    const navigate = useNavigate();

    return (
        <div className={"ScreenLimit"}>
            <Menu/>
            <h4>New Project Page</h4>
            <div>
                <input placeholder={"Project Name"}/>
                <input placeholder={"Planned Start Date"}/>
                <input placeholder={"Planned End Date"}/>
                <input placeholder={"Project Description"}/>
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