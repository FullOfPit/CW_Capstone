import React from "react";
import useAuth from "../hook/useAuth";
import {Navigate} from "react-router-dom";

export default function NoAuth({
    children,
    redirect
                               }:{
    children: React.ReactNode;
    redirect: string|null
}) {

    const {user, isReady} = useAuth();


    return !isReady ? null :(
        <>
            {user ? (redirect && <Navigate to={redirect}/>) : children}
        </>
    )
}