import React, { useState } from 'react';
import Product from "../models/production/Product";
import useWebSocket from "../services/productionServices";
import {timeout} from "../utils/constants";
import Result from "../models/production/Result";
import InfoModal from "../components/InfoModal";
import Frame from "../components/Production/Frame";
import "../styles/styles.css";
import ProgressBar from "../components/Production/ProgressBar";

export default function ProductionPage() {
    const [product,setProduct] = useState<Product | null>(null);
    const [backgroundClass, setBackgroundClass] = useState('default');
    const [freezeVirtual, setFreezeVirtual] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [isSuccess, setIsSuccess] = useState(true);
    const [messages, setMessages] = useState<string[]>([]);

    function showModal(ms: number){
        setIsOpen(true);
        setTimeout(()=>{
            setIsOpen(false);
        },ms);
    }

    function blink(color: string, mode:'slow'|'fast',  ms: number) {
        setBackgroundClass(`blink-${mode} ${color}`);
        setTimeout(() => {
            setBackgroundClass('');
        },ms);
    }

    function handleSuccess() {
        blink('green','fast', timeout.success);
    }

    function handleFinished() {
        blink('green','slow', timeout.finished);
        setMessages(['Successful assembly.'])
        setIsSuccess(true);
        showModal(timeout.finished);
    }

    function handleError(messages: string[]) {
        blink('red','fast', timeout.error);
        setMessages(messages);
        setIsSuccess(false);
        showModal(timeout.error);
    }

    function handleProductMessage(product: Product) {
        setFreezeVirtual(true);
        setTimeout(() =>{
            setFreezeVirtual(false);
            setProduct(product);
        },1000)
    }

    function handleResultMessage(result: Result) {
        switch(result.type) {
            case 'SUCCESS': handleSuccess();
                break;
            case 'FINISHED': handleFinished();
                break;
            case 'ERROR': handleError(result.messages);
                break;
        }
    }
    let total = product?.componentCount || 1;
    let active = product?.components.length || 0;
    useWebSocket(handleProductMessage,handleResultMessage)
    return (
        <>
            <ProgressBar total={total} active={active -1} />
            <div className={backgroundClass}>
                {product?.components.map( (component,index) =>
                    <Frame freezeVirtual={freezeVirtual} component={component} key={index}/>
                )}
            </div>
            <InfoModal isOpen={isOpen} isSuccess={isSuccess} messages={messages} />
        </>
    );
}
