type Project = {
    id: string,
    createdBy: string,
    projectId: string,
    projectName: string,
    createdAt: string,
    plannedStartDate?: string,
    plannedFinishDate?: string,
    projectStatus: string,
    assessorName: string,
    projectDetails: string,
    documentIds: string[],
}

export default Project;