import React from 'react';
import './App.css';
import {BrowserRouter} from "react-router-dom";
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

