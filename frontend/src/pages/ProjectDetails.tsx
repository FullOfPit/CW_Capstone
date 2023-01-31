import {useLocation} from "react-router-dom";
import React, {useEffect, useState} from "react";
import Project from "../types/Project";
import Risk from "../types/Risk";
import axios from "axios";


export default function ProjectDetails() {

    const [project, setProject] = useState<Project>();
    const [riskList, setRiskList] = useState<Risk[]>();
    const [isReady, setIsReady] = useState<boolean>(false)

    const location = useLocation();
    let idString = location.pathname.toString().split("/").pop()

    useEffect(() => {(async () => {
        try {
            const projectResponse = await axios.get(`/api/projects/${idString}`);
            setProject(projectResponse.data);
            const riskList = await axios.post(`/api/risks/${projectResponse.data.id}`)
            setRiskList(riskList.data);
        } catch (e) {
            console.log("Something went wrong", e)
        } finally {
            setIsReady(true);
        }
    })()}, [idString])

    return (
        <div>
            {isReady &&

                <h4>{idString}</h4>

            }
        </div>
    )
}