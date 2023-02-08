import {Button} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import "./FileUploadForm.css"
import axios from "axios";
import Project from "../types/Project";

export default function FileUploadForm({project, setProject, fileUploadOption}: {project: Project, setProject: (project: Project) => void, fileUploadOption: boolean}) {

    const [file, setFile] = useState<File | null>(null);
    const [uploadedFilesMetadata, setUploadedFilesMetadata] = useState<{id: string, name: string, createdBy: string}[]>([]);

    useEffect(() => {(async () => {
        try {
            const response = await axios.get(`/api/files/projects/${project.id}/metadata`);
            console.log(response.data);
            setUploadedFilesMetadata(response.data);
        } catch (e) {
            console.log("Something went wrong", e)
        }
    })()}, [project.id])

    const onFileUpload = (event: React.MouseEvent<HTMLButtonElement>) => {
        (async () => {
            event.preventDefault();
            if (file) {
                const formData = new FormData();
                formData.append("file", file);
                console.log(file);

                const response = await axios.post(`/api/files/${project.id}`, formData);
                project.documentIds.push(response.data.id)
                setProject({
                    ...project,
                    documentIds: project.documentIds
                })
                setUploadedFilesMetadata(
                    [...uploadedFilesMetadata, {
                        id: response.data.id,
                        name: response.data.name,
                        createdBy: response.data.createdBy
                    }])
                alert(JSON.stringify(response.data, null, 2));
            }
        }) ()
    }

    const onFileDelete = (event: React.MouseEvent<HTMLButtonElement>, id: string) => {
        (async () => {
            event.preventDefault();
            await axios.delete(`/api/files/${id}`);
            setProject({
                ...project,
                documentIds: project.documentIds.filter((documentId) => documentId !== id),
            })
            setUploadedFilesMetadata([...uploadedFilesMetadata.filter((file) => file.id !== id)]);
        }) ()
    }

    return(
        <div className={"FileUploadForm"}>

            <h6>Additional Documents</h6>

            <div className={"UploadedFiles"}>
                {uploadedFilesMetadata &&
                    uploadedFilesMetadata.map((file) =>
                        <div key={file.id} className={"FileContainer"}>
                            <h6><a href={`${process.env.REACT_APP_TEST}/api/files/${file.id}`}
                                   target={"_blank"}>{file.name}</a></h6>
                            <Button onClick={(event) => onFileDelete(event, file.id)}>Delete</Button>
                        </div>)
                }
            </div>

            {fileUploadOption &&
                <form>
                    <input className={"DocumentInputField"}
                           type={"file"}
                           accept={".pdf, .docx"}
                           onChange={(event) => {
                               if (!event.target.files || event.target.files.length < 1) {
                                   setFile(null);
                                   return;
                               }
                               setFile(event.target.files[0]);
                           }}/>

                    <Button type={"submit"} onClick={(event) => onFileUpload(event)}>Upload File</Button>
                </form>
            }



        </div>
    )
}