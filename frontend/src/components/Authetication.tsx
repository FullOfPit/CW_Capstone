import useAuth from "../hook/useAuth";
import {Navigate, useLocation} from "react-router-dom";
import React from "react";

export default function Authentication ({children}:{children: React.ReactNode}) {

    const location = useLocation();
    const {user, isReady} = useAuth();

    const navigate = (
        <Navigate
            to={isReady && !user ?
                `/login?redirect=${encodeURIComponent(location.pathname + location.search)}`
                : "/"}/>
    );

    return !isReady ? null : user ? <>{children}</> : navigate;


}