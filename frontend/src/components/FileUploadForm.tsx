import {Button} from "react-bootstrap";
import React, {useState} from "react";
import "./FileUploadForm.css"
import axios from "axios";

export default function FileUploadForm() {

    const [file, setFile] = useState<File | null>(null);
    const [docPreview, setDocPreview] = useState<string | null>(null)
    const [fileNames, setFileNames] = useState<string[]>([]);

    return(
        <div>

            {docPreview && (<img src={docPreview} alt={"preview"}/>)}
            {fileNames && (<p>{fileNames}</p>)}

            <form onSubmit={async (event) => {
                event.preventDefault();

                if(file) {
                    const formData = new FormData();
                    formData.append("file", file);

                    const response = await axios.post("/api/files", formData);
                    setFileNames([...fileNames, response.data.name])
                    alert(JSON.stringify(response.data, null, 2));
                }
            }}>
                <input className={"DocumentInputField"}
                       type={"file"}
                       accept={".pdf, .docx, .jpeg"}
                       onChange={(event) => {
                           if (!event.target.files || event.target.files.length < 1) {
                               setFile(null);
                               setDocPreview(null);
                               return;
                           }
                           setFile(event.target.files[0]);

                           const reader = new FileReader();

                           reader.addEventListener("load", () => {
                               setDocPreview(reader.result as string);
                           }, false);

                           if (file) {
                               reader.readAsDataURL(file);
                           }
                       }}/>

                <Button type={"submit"}>Upload File</Button>
            </form>
        </div>
    )
}