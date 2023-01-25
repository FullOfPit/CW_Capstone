import React, {useMemo} from 'react';
import './App.css';
import {BrowserRouter, Route, Routes, useSearchParams} from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import NewProject from "./pages/NewProject";
import ProjectDetails from "./pages/ProjectDetails";
import RiskDetails from "./pages/RiskDetails";
import NoAuth from "./components/NoAuth";
import Root from "./pages/Root";


function App() {



  return (
      <div>
          <BrowserRouter>
              <Root/>
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
