import {Button} from "react-bootstrap";
import React, {useState} from "react";
import "./FileUploadForm.css"
import axios from "axios";
import Project from "../types/Project";

export default function FileUploadForm({project, setProject}: {project: Project, setProject: (project: Project) => void}) {

    const [file, setFile] = useState<File | null>(null);
    const [uploadedFiles, setUploadedFiles] = useState<{id: string, name: string, createdBy: string}[]>([]);

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
                setUploadedFiles(
                    [...uploadedFiles, {
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
            setUploadedFiles([...uploadedFiles.filter((file) => file.id !== id)]);
        }) ()
    }

    return(
        <div className={"FileUploadForm"}>

            <h6>Additional Documents</h6>

            <div className={"UploadedFiles"}>
                {uploadedFiles &&
                    uploadedFiles.map((file) => <div key={file.id} className={"FileContainer"}>
                            <h6>{file.name}</h6>
                            <Button onClick={(event) => onFileDelete(event, file.id)}>Delete</Button>
                    </div>)
                }
            </div>


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
        </div>
    )
}