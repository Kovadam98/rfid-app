import React from 'react';
import Modal from 'react-modal';
import '../styles/styles.css';

export interface InfoModalProps{
    isOpen: boolean,
    isError: boolean,
    messages: string[]
}

export default function InfoModal({isOpen, isError, messages}: InfoModalProps){
    const cls = isError ? 'red' : 'green';
    return (
        <Modal isOpen={isOpen} className={'InfoModal ' + cls}>
            {messages.map((message,index) =>
                <p key={index}>{message}</p>
            )}
        </Modal>
    )
}
