import React, { useState } from 'react';
import Product from '../models/Product';
import Result from '../models/Result';
import Frame from './Frame';
import InfoModal from './InfoModal';
import { timeout} from '../utils/constants';
import useWebSocket from "../services/webSocketService";

export default function App() {
    const [product,setProduct] = useState<Product | null>(null);
    const [backgroundClass, setBackgroundClass] = useState('default');
    const [freezeVirtual, setFreezeVirtual] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [isError, setIsError] = useState(false);
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
            setBackgroundClass('default');
        },ms);
    }

    function handleSuccess() {
        blink('green','fast', timeout.success);
    }

    function handleFinished() {
        blink('green','slow', timeout.finished);
        setMessages(['Successful assembly.'])
        setIsError(false);
        showModal(timeout.finished);
    }

    function handleError(messages: string[]) {
        blink('red','fast', timeout.error);
        setMessages(messages);
        setIsError(true);
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

    useWebSocket(handleProductMessage,handleResultMessage)
    return (
        <>
            <div className={backgroundClass}>
                {product?.components.map( (component,index) =>
                    <Frame freezeVirtual={freezeVirtual} component={component} key={index}/>
                )}
            </div>
            <InfoModal isOpen={isOpen} isError={isError} messages={messages} />
        </>
    );
}
