import { useEffect } from "react";
import SockJS from "sockjs-client";
import Product from "../models/production/Product";
import Result from "../models/production/Result";
import { Stomp } from "@stomp/stompjs";

export default function useWebSocket(
    itemsCallback: (product: Product) => void,
    resultCallback: (result: Result) => void
) {
    useEffect(() => {
        let socket1 = () => new SockJS('/api/rfid');
        let stompClient1 = Stomp.over(socket1);
        stompClient1.connect({}, ()=>{
            stompClient1.subscribe('/notif/result', msg => resultCallback(JSON.parse(msg.body)));
        })
        let socket2 = () => new SockJS('/api/rfid');
        let stompClient2 = Stomp.over(socket2);
        stompClient2.connect({}, ()=>{
            stompClient2.subscribe('/notif/items', msg => itemsCallback(JSON.parse(msg.body)));
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    },[]);
}
