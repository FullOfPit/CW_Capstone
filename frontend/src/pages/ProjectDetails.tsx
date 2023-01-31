import {useLocation} from "react-router-dom";

export default function ProjectDetails() {

    const location = useLocation();
    let idString = location.pathname.toString().split("/").pop()

    console.log(idString);

    return (
        <div>
            <h4> {`${location.pathname}`} </h4>
            <h4>Project Details Page</h4>
        </div>

    );
}