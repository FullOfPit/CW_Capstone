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
            === "Moderate Risk").length;

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
                {type: 'bar', x: [1], y: [numberNegligible], name: "Negligible Risk", marker: {color: "#009e60"}},
                {type: 'bar', x: [2], y: [numberLow], name: "Low Risk", marker: {color: "#478778"}},
                {type: 'bar', x: [3], y: [numberMedium], name: "Moderate Risk", marker: {color: "#ffc300"}},
                {type: 'bar', x: [4], y: [numberHigh], name: "High Risk", marker: {color: "#ff5733"}},
                {type: 'bar', x: [5], y: [numberExtreme], name: "Extreme Risk", marker: {color: "#c70039"}}
            ]}
            layout={{title: 'Risk Factor Distribution'}}
            style={{width: "45%", height: "35rem"}}
        />
    );
}
