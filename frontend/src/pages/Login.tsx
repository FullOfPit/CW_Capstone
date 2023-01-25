import React, {FormEvent, useCallback, useState} from "react";
import axios from "axios";

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

    const login = useCallback(
        async (event: FormEvent<HTMLFormElement>) => {
            event.preventDefault();

            try {
                await axios.post("/api/app-user/login", null, {
                    headers: {
                        "Authorization": "Basic " + window.btoa(`${credentials.username}:${credentials.password}`)
                    }
                })
            } catch (event) {
                console.log(event)
            }
        }, [credentials])




    return (
        <div>
            <h4>Hello (Member)</h4>
            <form onSubmit={login}>
                <input placeholder={"Username"}
                       type={"text"}
                       name={"username"}
                       value={credentials.username}
                       onChange={changeCredentials}
                ></input>
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