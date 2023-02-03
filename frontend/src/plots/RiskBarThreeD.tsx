import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";
import {Data} from "plotly.js";
import riskFactorEval from "../evaluation/riskFactorEval";

export default function RiskBarThreeD ({risks}: {risks: Risk[]}) {

    let dataList: Data[] = risks.map((risk) => {return ({
        type: "scatter3d",
        x: [risk.probability],
        y: [risk.frequency],
        z: [risk.healthHazard],
        name: risk.riskName.slice(0, 15),
        marker: {
            color: riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskColor.toString()
        }
    })})





    return (
            <Plot
                data={dataList}
                layout={{
                    autosize: true,
                    width: 800,
                    scene:
                        {
                            xaxis: {title : "Probability"},
                            yaxis: {title : "Frequency"},
                            zaxis: {title : "Hazard to Health"}
                        }}}
                style={{width: "45%", height: "35rem", overflowX: "scroll"}}
            />
        );
}

/*
mode: 'gauge+number',
value: 9,
title: {text: "Risk Multiplicator"},
domain: {x:[0,1], y:[0,1]},
gauge: {
    axis: {range : [null, 12], tickwidth: 2, tickcolor: "black"},
    //color needs to be adjusted to the risk factor
    bar: {color: "#525252"},
    bgcolor: "white",
    bordercolor: "gray",
    steps: [
        {range: [0, 3], color: "#9afaa7"},
        {range: [3, 5], color: "#bfd977"},
        {range: [5, 7], color: "#f0de90"},
        {range: [7, 9], color: "#FF9800"},
        {range: [9, 12], color: "#ff6d4f"}
    ]


    {
                        type: 'scatter3d',
                        x: [1],
                        y: [1],
                        z: [1],
                        name: "Something 1",
                    },
                    {
                        type: 'scatter3d',
                        x: [2],
                        y: [2],
                        z: [2],
                        name: "Something 2",
                    },

 */