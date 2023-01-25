import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import NewProject from "./pages/NewProject";
import ProjectDetails from "./pages/ProjectDetails";
import RiskDetails from "./pages/RiskDetails";


function App() {
  return (
      <div>
          <BrowserRouter>
              <Routes>
                  <Route path={"/"} element={<Dashboard/>}/>
                  <Route path={"/login"} element={<Login/>}/>
                  <Route path={"/newproject"} element={<NewProject/>}></Route>
                  <Route path={"/projectdetails"} element={<ProjectDetails/>}></Route>
                  <Route path={"/riskdetails"} element={<RiskDetails/>}></Route>
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
