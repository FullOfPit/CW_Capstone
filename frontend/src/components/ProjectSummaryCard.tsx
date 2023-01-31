import Project from "../types/Project";
import "./ProjectSummaryCard.css"
import {Button} from "react-bootstrap";

export default function ProjectSummaryCard({project}:{project: Project}) {

    return (
        <div className={"ProjectSummaryCard"}>
            <div>
                <p>{project.projectName}</p>
                <p>{project.projectStatus}</p>
                <p>{project.assessorName}</p>
            </div>
            <div className={"ProjectSummaryCardButtons"}>
                <Button>Edit Project</Button>
                <Button>Delete Project</Button>
            </div>
        </div>
    )

}