import React from 'react';
import '../../styles/styles.css';
import { filter } from "../../utils/utils";
import Component from "../../models/production/Component";

export interface FrameProps{
    component: Component;
    freezeVirtual: boolean;
}

export default function Frame({ component, freezeVirtual }: FrameProps){
    return (
        <img
            src={component.url}
            style={{filter: filter(component.hue, component.brightness, component.saturation)}}
            alt={component.name}
            className={(component.real || freezeVirtual) ? '' : 'blink-slow'}
        />
    )
}
