export interface Result {
    type: 'ERROR' | 'SUCCESS' | 'FINISHED';
    messages: string[];
}
