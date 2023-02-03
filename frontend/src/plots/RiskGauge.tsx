import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";

export default function RiskGauge({risk}: {risk: Risk}) {

    let riskFactor = risk.healthHazard * risk.probability * risk.frequency;

        return (
            <Plot
                data={[
                    {
                        type: 'indicator',
                        mode: 'gauge+number',
                        value: riskFactor,
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
                        }
                    },
                ]}
                layout={{margin: {t:50, r:25, l:25, b:25},title: 'HH x P x F'}}
                style={{width: "15rem", height: "15rem", overflowX: "scroll"}}
            />
        );
}