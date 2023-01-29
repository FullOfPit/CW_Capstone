import useAuth from "../hook/useAuth";
import {Navigate, useLocation} from "react-router-dom";
import React from "react";

export default function Authentication ({children}:{children: React.ReactNode}) {

    //TODO useContext for the distribution of the member name and id

    const location = useLocation();
    const {user, isReady} = useAuth();

    const navigate = (
        <Navigate
            to={isReady && !user ?
                `/login?redirect=${encodeURIComponent(location.pathname + location.search)}`
                : "/"}/>
    );

    if (isReady && user) {
        return <>{children}</>;
    } else if (isReady) {
        return navigate;
    } else {
        return null;
    }
}