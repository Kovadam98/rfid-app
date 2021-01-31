import React from 'react';
import Modal from 'react-modal';
import '../styles/styles.css';

export interface InfoModalProps{
    isOpen: boolean,
    isSuccess: boolean,
    messages?: string[]
}

export default function InfoModal({isOpen, isSuccess, messages}: InfoModalProps){
    const cls = isSuccess ? 'bg-success' : 'bg-danger';
    if (!messages) {
        messages = isSuccess ? ['Success'] : ['Failure'];
    }
    return (
        <Modal isOpen={isOpen} className={'InfoModal ' + cls}>
            {messages.map((message,index) =>
                <p key={index}>{message}</p>
            )}
        </Modal>
    )
}
