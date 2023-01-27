import {Route, Routes, useSearchParams} from "react-router-dom";
import Dashboard from "./Dashboard";
import NoAuth from "../components/NoAuth";
import Login from "./Login";
import NewProject from "./NewProject";
import ProjectDetails from "./ProjectDetails";
import RiskDetails from "./RiskDetails";
import React, {useMemo} from "react";
import Authentication from "../components/Authentication";

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
            <Route path={"/newproject"} element={
                <Authentication>
                    <NewProject/>
                </Authentication>
            }/>
            <Route path={"/projectdetails"} element={
                <Authentication>
                    <ProjectDetails/>
                </Authentication>
            }/>
            <Route path={"/riskdetails"} element={
                <Authentication>
                    <RiskDetails/>
                </Authentication>
            }/>
        </Routes>
    )
}