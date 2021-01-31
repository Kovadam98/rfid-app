import React from 'react';

interface MenuProps{
    onSend: () => void;
    onSelect: (event: any) => void;
    options: {value: string, name: string}[];
}

export default function Menu({onSend, onSelect, options}: MenuProps){
    return (
        <div className="bg-secondary">
            <div className="p-2 row" style={{margin: 0}}>
                <div className="col d-flex justify-content-start">
                    <select className="form-control bg-light" onChange={onSelect}>
                        {options.map((option,index) =>
                            <option className="form-control" value={option.value} key={index}>{option.name}</option>
                        )}
                    </select>
                </div>
                <div className="col d-flex justify-content-end">
                    <button className={"btn btn-light"} onClick={onSend}>Send Order</button>
                </div>
            </div>
        </div>
    );
}
