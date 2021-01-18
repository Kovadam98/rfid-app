import React, { useState } from 'react';
import { Product } from '../models';
import { getProduct } from '../services/service';
import { Frame } from './Frame';
import InfoModal from './InfoModal';
import { timeout} from '../utils/constants';
import useWebSocket from "../services/webSocketService";

export default function App() {
    const [product,setProduct] = useState<Product | null>(null);
    const [backgroundClass, setBackgroundClass] = useState('default');
    const [freezeVirtual, setFreezeVirtual] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [isError, setIsError] = useState(false);
    const [message, setMessage] = useState('');

    function showModal(ms: number){
        setIsOpen(true);
        setTimeout(()=>{
            setIsOpen(false);
        },ms);
    }

    function blink(color: string, mode:'slow'|'fast',  ms: number) {
        setBackgroundClass(`blink-${mode} ${color}`);
        setTimeout(() => {
            setBackgroundClass('default');
        },ms);
    }

    function freezeVirtualComponent(ms: number) {
        setFreezeVirtual(true);
        setTimeout(() => {
            setFreezeVirtual(false);
        },ms);
    }

    async function loadAfter(ms: number = 0){
        setTimeout(async () => {
            const data = await getProduct();
            setProduct(data);
        },ms);
    }

    async function success() {
        blink('green','fast', timeout.success);
        freezeVirtualComponent(timeout.success);
        await loadAfter(timeout.success);
    }

    async function finished() {
        blink('green','slow', timeout.finished);
        freezeVirtualComponent(timeout.finished);
        setMessage('Successful assembly.')
        setIsError(false);
        showModal(timeout.finished);
        await loadAfter(timeout.finished);
    }

    function error(message: string) {
        blink('red','fast', timeout.error);
        setMessage(message);
        setIsError(true);
        showModal(timeout.error);
    }
    function noOrder(message: string) {
        setTimeout(() => {
            setMessage(message);
            setIsError(true);
            setIsOpen(true)
        },timeout.finished);
    }

    function onNew() {
        loadAfter(1000).then();
        setIsOpen(false);
    }
/*
    function handlePushNotification(event: any) {
        console.log(event.data);
        switch (event.data) {
            case 'success': success().then();
                break;
            case 'finished': finished().then();
                break;
            case 'new': onNew();
                break;
            case 'No available order.': noOrder(event.data);
                break;
            default: error(event.data);
        }
    }
*/
    useWebSocket(
        product => {
            console.log(product);
        },
        result => {
            console.log(result);
        }
    )
    return (
        <>
            <div className={backgroundClass}>
                {product?.components.map( (component,index) =>
                    <Frame freezeVirtual={freezeVirtual} component={component} key={index}/>
                )}
            </div>
            <InfoModal isOpen={isOpen} isError={isError} message={message} />
        </>
    );
}
