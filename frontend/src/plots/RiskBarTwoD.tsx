import React from 'react';
import Plot from 'react-plotly.js';
import Risk from "../types/Risk";
import riskFactorEval from "../evaluation/riskFactorEval";

export default function RiskBarTwoD({risks}: {risks: Risk[]}) {

    let numberNegligible = risks.filter(
        (risk) => riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskFactor
            === "Negligible Risk").length;

    let numberLow = risks.filter(
        (risk) => riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskFactor
            === "Low Risk").length;

    let numberMedium = risks.filter(
        (risk) => riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskFactor
            === "Medium Risk").length;

    let numberHigh = risks.filter(
        (risk) => riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskFactor
            === "High Risk").length;

    let numberExtreme = risks.filter(
        (risk) => riskFactorEval(risk.healthHazard, risk.probability, risk.frequency).riskFactor
            === "Extreme Risk").length;

    return (
        <Plot
            data={
            [
                {type: 'bar', x: [1], y: [numberNegligible], name: "Negligible Risk", marker: {color: "#9afaa7"}},
                {type: 'bar', x: [2], y: [numberLow], name: "Low Risk", marker: {color: "#bfd977"}},
                {type: 'bar', x: [3], y: [numberMedium], name: "Medium Risk", marker: {color: "#f0de90"}},
                {type: 'bar', x: [4], y: [numberHigh], name: "High Risk", marker: {color: "#db9f58"}},
                {type: 'bar', x: [5], y: [numberExtreme], name: "Extreme Risk", marker: {color: "#ff6d4f"}}
            ]}
            layout={{title: 'A Fancy Plot'}}
            style={{width: "45%", height: "35rem", overflowX: "scroll"}}
        />
    );
}
