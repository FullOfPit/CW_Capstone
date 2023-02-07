import "./Login.css"
import Form from 'react-bootstrap/Form';
import React, {FormEvent, useCallback, useMemo, useState} from "react";
import axios from "axios";
import {useNavigate, useSearchParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import {toast, ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

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
                toast.error(
                    "Incorrect username/password!",
                    {
                        position: "top-center",
                        autoClose: 2500,
                        hideProgressBar: true,
                        closeOnClick: false,
                        pauseOnHover: false,
                    })
                setErrors((errors) => [...errors]);
            }
        }, [credentials, navigate, redirect])




    return (
        <div className={"LoginPage"}>
            <h4 className={"LoginPageTitle"}>EasyRisk - Login</h4>
            <Form onSubmit={login} className={"LoginPageInputForm"}>
                <Form.Group className={"LoginPageInputFormInputField"}>
                    <Form.Control type={"text"}
                                  placeholder={"username"}
                                  name={"username"}
                                  value={credentials.username}
                                  onChange={changeCredentials}/>
                </Form.Group>

                <Form.Group className={"LoginPageInputFormInputField"}>
                    <Form.Control type={"password"}
                                  placeholder={"password"}
                                  name={"password"}
                                  value={credentials.password}
                                  onChange={changeCredentials}/>
                </Form.Group>
                <Button type={"submit"}>Login</Button>
                <ToastContainer/>
            </Form>
            {errors.length > 0 && (
                <div>{errors.map((error) => <p key={error}> {error} </p>)}</div>
            )}
        </div>


    )
}