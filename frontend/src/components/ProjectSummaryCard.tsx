import Project from "../types/Project";

export default function ProjectSummaryCard({project}:{project: Project}) {

    return (
        <div>
            <p>{project.projectName}</p>
            <p>{project.projectStatus}</p>
            <p>{project.assessorName}</p>
        </div>
    )

}