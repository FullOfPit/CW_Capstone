import {Route, Routes, useSearchParams} from "react-router-dom";
import Dashboard from "./Dashboard";
import NoAuth from "../components/NoAuth";
import Login from "./Login";
import NewProject from "./NewProject";
import ProjectDetails from "./ProjectDetails";
import RiskDetails from "./RiskDetails";
import React, {useMemo} from "react";

export default function Root() {

    const [searchParams] = useSearchParams();
    const redirect = useMemo(
        () => searchParams.get("redirect") || "/",
        [searchParams]
    );


    return (
        <Routes>
            <Route path={"/"} element={<Dashboard/>}/>
            <Route path={"/login"} element={
                //<NoAuth redirect={redirect}>
                    <Login/>
                //</NoAuth>
            }/>
            <Route path={"/newproject"} element={<NewProject/>}></Route>
            <Route path={"/projectdetails"} element={<ProjectDetails/>}></Route>
            <Route path={"/riskdetails"} element={<RiskDetails/>}></Route>
        </Routes>
    )
}