import React, { useState} from 'react';
import Frame from './Frame';
import '../../styles/styles.css';
import ComponentTypeData from "../../models/order/ComponentTypeData";
import ComponentType from "../../models/order/ComponentType";

export interface OrderProps {
    componentTypeDataArray: ComponentTypeData[];
    setComponentTypeDataArray: (componentTypeData: ComponentTypeData[]) => void;
}

export default function Order({componentTypeDataArray, setComponentTypeDataArray}: OrderProps) {
    const [coords, setCoords] = useState({x: 0, y: 0});

    function handleClick(event: any) {
        const x = event.pageX - event.target.offsetLeft;
        const y = event.pageY - event.target.offsetTop;
        setCoords( {x: x, y: y});
    }

    function update(componentType: ComponentType, colorId: number){
        const index = componentTypeDataArray.findIndex(c => c.componentType.id === componentType.id);
        componentTypeDataArray[index].colorId = colorId;
        setComponentTypeDataArray(componentTypeDataArray);
        setCoords({x:0,y:0});
    }

    return (
        <div onClick={handleClick}>
            {componentTypeDataArray.map((frame,index) =>
                <Frame
                    componentType={frame.componentType}
                    colorId={frame.colorId}
                    onUpdate={update}
                    key={index}
                    coords={coords}
                />
            )}
        </div>
    )
}
