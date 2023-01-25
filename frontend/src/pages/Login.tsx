import React, {FormEvent, useCallback, useMemo, useState} from "react";
import axios from "axios";
import {useLocation, useNavigate, useSearchParams} from "react-router-dom";

export default function Login () {
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    })

    const changeCredentials = useCallback(
        (event: React.ChangeEvent<HTMLInputElement>) => {
            const {name, value} = event.target;
            setCredentials({...credentials, [name]: value});
        }, [credentials, setCredentials]
    )

    const [searchParams] = useSearchParams();
    const redirect = useMemo(
        () => searchParams.get("redirect") || "/",
        [searchParams]
    );
    const navigate = useNavigate();

    const login = useCallback(
        async (event: FormEvent<HTMLFormElement>) => {
            event.preventDefault();

            try {
                await axios.post("/api/app-user/login", null, {
                    headers: {
                        "Authorization": "Basic " + window.btoa(`${credentials.username}:${credentials.password}`)
                    }
                })
                navigate(redirect);
            } catch (event) {
                console.log(event)
            }
        }, [credentials, navigate, redirect])




    return (
        <div>
            <h4>Login</h4>
            <form onSubmit={login}>
                <h4>Please enter your username</h4>
                <input placeholder={"Username"}
                       type={"text"}
                       name={"username"}
                       value={credentials.username}
                       onChange={changeCredentials}
                ></input>
                <h4>Please enter your password</h4>
                <input placeholder={"Password"}
                       type={"text"}
                       name={"password"}
                       value={credentials.password}
                       onChange={changeCredentials}
                ></input>
                <button>Login</button>
            </form>

        </div>


    )
}