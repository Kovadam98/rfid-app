import React from 'react';
import ReactDOM from 'react-dom';
import './styles/styles.css';
import App from './components/App';
import Modal from 'react-modal';

Modal.setAppElement("#root");

navigator.serviceWorker.getRegistrations().then(regs =>
    regs.forEach( reg => reg.unregister())
);

ReactDOM.render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>,
    document.getElementById('root')
);
