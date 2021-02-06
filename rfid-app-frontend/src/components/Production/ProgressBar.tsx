import React from 'react';

interface ProgressBarProps {
    total: number;
    active: number;
}

export default function ProgressBar({ total, active }: ProgressBarProps) {
    let percent = active/total*100;
    percent = percent > 0 ? percent : 1;
    return (
        <div className="bg-secondary progress-bar-container">
            <div className="meter animate">
                <span style={{width: `${percent}%`}}><span></span></span>
            </div>
        </div>

    )
}
