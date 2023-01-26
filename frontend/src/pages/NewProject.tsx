import Menu from "../components/Menu";
import RiskSummaryCard from "../components/RiskSummaryCard";

export default function NewProject() {


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
                    <RiskSummaryCard/>
                    <button>Add</button>
                </div>

                <div>
                    <button>Cancel</button>
                    <button>Save</button>
                </div>





            </div>


        </div>





    )
}