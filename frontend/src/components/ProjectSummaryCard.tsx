import Project from "../types/Project";
import "./ProjectSummaryCard.css"
import {Button} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

export default function ProjectSummaryCard({
    project,
    setAllProjects
}:{
    project: Project,
    setAllProjects:(allProjects: Project[]) => void
})

{
    const navigate = useNavigate();

    const onDelete = (id: string) => {
        toast.warning(
            <div>
                "Are you sure you wish to delete this project?"
                <div>
                    <Button variant={"outline-danger"}
                            onClick={() => {
                                ((async () => {
                                    await axios.delete(`/api/projects/${id}`);
                                    const projectUpdate = await axios.get(`/api/projects/app-users/${project.createdBy}`);
                                    setAllProjects(projectUpdate.data);
                                })())
                            }}
                            className={"DeleteConfirmation"}>
                        Confirm</Button>
                </div>
            </div>,
            {position: "top-center",
                autoClose: false,
                hideProgressBar: true,
                closeOnClick: false,
                pauseOnHover: false,
            },
        );
    }

    return (
        <div className={"ProjectSummaryCard"}>
            <div className={"ProjectSummaryCardContent"}>
                <div className={"CardContentHeader"}>
                    <h4>{project.projectName}</h4>
                    <p><strong>Project ID:</strong> {project.projectId}</p>
                    <p><strong>Assessor:</strong> {project.assessorName}</p>
                </div>
                <div className={"CardContentDates"}>
                    <p><strong>Start Date:</strong> {project.plannedStartDate}</p>
                    <p><strong>Finish Date:</strong> {project.plannedFinishDate}</p>
                </div>
                <div>
                    <p></p>
                </div>

            </div>
            <div className={"ProjectSummaryCardButtons"}>
                <Button onClick={() => navigate(`/projectdetails/${project.id}`)}>Details</Button>
                <Button onClick={() => onDelete(project.id)}>Delete Project</Button>
            </div>
        </div>
    )

}