import React, { useEffect, useState } from 'react';
import { filter } from '../../utils/utils';
import '../../styles/styles.css';
import ComponentType from "../../models/order/ComponentType";

export interface FrameProps{
    componentType: ComponentType;
    colorId: number;
    onUpdate: (componentType: ComponentType, id: number) => void;
    coords: { x: number, y: number };
}

export default function Frame({componentType,colorId,onUpdate,coords}: FrameProps){
    const colorType = componentType.colorTypes.filter(c => c.id === colorId)[0];
    const imageRef = React.createRef<HTMLImageElement>();
    const [canvas,setCanvas] = useState(document.createElement('canvas'));

    useEffect(() => {
        canvas.width = imageRef.current!.width;
        canvas.height = imageRef.current!.height;
        canvas.getContext('2d')!.drawImage(imageRef.current!,0,0,canvas.width,canvas.height);
        setCanvas(canvas);
    },[canvas, imageRef])


    useEffect(() =>{
        const isTransparent = canvas
            .getContext('2d')!
            .getImageData(coords.x,coords.y,1,1)
            .data[3] < 127;
        if(!isTransparent){
            const colorTypes = componentType.colorTypes;
            const newIndex = (colorTypes.indexOf(colorType)! + 1) % colorTypes.length;
            const newId = colorTypes[newIndex].id;
            onUpdate(componentType,newId);
        }
    });


    return (
        <img
            src={componentType.imageUrl}
            style={{filter: filter(colorType.hue, colorType.brightness, colorType.saturation)}}
            alt={componentType.name}
            ref={imageRef}
            onDragStart={(event)=>event.preventDefault()}
        />
    )
}
