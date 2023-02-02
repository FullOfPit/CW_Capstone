import Project from "../types/Project";
import "./ProjectSummaryCard.css"
import {Button} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function ProjectSummaryCard({
    project,
    setAllProjects
}:{
    project: Project,
    setAllProjects:(allProjects: Project[]) => void
})

{
    const navigate = useNavigate();

    const onDelete = (id: string) => {(
        (async () => {
            await axios.delete(`/api/projects/${id}`);
            const projectUpdate = await axios.get(`/api/projects/app-users/${project.createdBy}`);
            setAllProjects(projectUpdate.data);
        })())
    };

    return (
        <div className={"ProjectSummaryCard"}>
            <div className={"ProjectSummaryCardContent"}>
                <div className={"CardContentHeader"}>
                    <h4>{project.projectName}</h4>
                    <p>Project ID: {project.projectId}</p>
                    <p>Assessor: {project.assessorName}</p>
                </div>
                <div className={"CardContentDates"}>
                    <p>Planned Start Date: {project.plannedStartDate}</p>
                    <p>Planned Finish Date: {project.plannedFinishDate}</p>
                </div>
                <div>
                    <p>Flags: MUCH BUSINESS LOGIC</p>
                </div>

            </div>
            <div className={"ProjectSummaryCardButtons"}>
                <Button onClick={() => navigate(`/projectdetails/${project.id}`)}>Details</Button>
                <Button onClick={() => onDelete(project.id)}>Delete Project</Button>
            </div>
        </div>
    )

}