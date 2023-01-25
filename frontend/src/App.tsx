import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";


function App() {
  return (
      <div>
          <h4>Hello</h4>
          <BrowserRouter>
              <Routes>
                  <Route path={"/"}><Dashboard/></Route>
                  <Route path={"/login"} element={<Login/>}/>
              </Routes>
          </BrowserRouter>


      </div>


  );
}

export default App;

{
    /*
    <BrowserRouter>
              <Routes>


                  <Route path={"/"}><Dashboard/></Route>
                  <Route path={"/newproject"}><NewProject/></Route>
                  <Route path={"/projectdetails"}><ProjectDetails/></Route>
                  <Route path={"/riskdetails"}><RiskDetails/></Route>
              </Routes>
          </BrowserRouter>
     */
}
