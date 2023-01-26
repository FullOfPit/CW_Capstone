import "./Login.css"

import React, {FormEvent, useCallback, useMemo, useState} from "react";
import axios from "axios";
import {useNavigate, useSearchParams} from "react-router-dom";

export default function Login () {
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    })

    const [errors, setErrors] = useState<string[]>([]);

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

            setErrors([]);

            try {
                await axios.post("/api/app-users/login", null, {
                    headers: {
                        "Authorization": "Basic " + window.btoa(`${credentials.username}:${credentials.password}`)
                    }
                })
                navigate(redirect);
            } catch (error) {
                console.log(error);
                setErrors((errors) => [...errors]);

            }
        }, [credentials, navigate, redirect])




    return (
        <div className={"LoginPage"}>
            <h4 className={"LoginPageTitle"}>EasyRisk - Login</h4>

            <form onSubmit={login} className={"LoginPageInputForm"}>
                <h4>Please enter your username below</h4>
                <input placeholder={"Username"}
                       type={"text"}
                       name={"username"}
                       value={credentials.username}
                       onChange={changeCredentials}
                ></input>
                <h4>Please enter your password below</h4>
                <input placeholder={"Password"}
                       type={"text"}
                       name={"password"}
                       value={credentials.password}
                       onChange={changeCredentials}
                ></input>
                <button className={"ButtonGeneral"}>Login</button>
            </form>

            {errors.length > 0 && (
                <div>{errors.map((error) => <p key={error}> {error} </p>)}</div>
            )}

        </div>


    )
}