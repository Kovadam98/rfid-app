import React from 'react';
import Component from '../models/Component';
import { filter } from '../utils/utils';
import '../styles/styles.css';

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
