import React from 'react';
import Modal from 'react-modal';
import '../styles/styles.css';

export interface InfoModalProps{
    isOpen: boolean,
    isSuccess: boolean
}

export default function InfoModal({isOpen, isSuccess}: InfoModalProps){
    const cls = isSuccess ? 'bg-success' : 'bg-danger';
    return (
        <Modal isOpen={isOpen} className={'InfoModal ' + cls}>
            <b>{isSuccess ? 'Order successful' : 'Order failed'}</b>
        </Modal>
    )
}
