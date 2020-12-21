import React from 'react';
import Modal from 'react-modal';
import '../styles/styles.css';

export interface InfoModalProps{
    isOpen: boolean,
    isError: boolean,
    message: string
}

export default function InfoModal({isOpen, isError, message}: InfoModalProps){
    const cls = isError ? 'red' : 'green';
    return (
        <Modal isOpen={isOpen} className={'InfoModal ' + cls}>
            <b>{message}</b>
        </Modal>
    )
}
