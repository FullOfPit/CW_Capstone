import {Route, Routes, useSearchParams} from "react-router-dom";
import Dashboard from "./Dashboard";
import NoAuth from "../components/NoAuth";
import Login from "./Login";
import NewProject from "./NewProject";
import ProjectDetails from "./ProjectDetails";
import RiskDetails from "./RiskDetails";
import React, {useMemo} from "react";
import Authentication from "../components/Authetication";

export default function Root() {

    const [searchParams] = useSearchParams();
    const redirect = useMemo(
        () => searchParams.get("redirect") || "/",
        [searchParams]
    );


    return (
        <Routes>
            <Route path={"/login"} element={
                <NoAuth redirect={redirect}>
                    <Login/>
                </NoAuth>
            }/>
            <Route path={"/"} element={
                <Authentication>
                    <Dashboard/>
                </Authentication>
            }/>

            <Route path={"/newproject"} element={<NewProject/>}></Route>
            <Route path={"/projectdetails"} element={<ProjectDetails/>}></Route>
            <Route path={"/riskdetails"} element={<RiskDetails/>}></Route>
        </Routes>
    )
}