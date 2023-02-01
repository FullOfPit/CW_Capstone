import Project from "../types/Project";

export default function RiskAssessmentPage({project}:{project: Project}) {

    return (
        <div>
            <h4>Project ID {project.projectId}</h4>
            <h6>Project Description {project.projectDetails}</h6>

        </div>
    )

}