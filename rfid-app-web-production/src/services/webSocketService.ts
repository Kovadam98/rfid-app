import { useEffect } from "react";
import SockJS from "sockjs-client";
import { Product, Result} from "../models";
import {CompatClient, Stomp} from "@stomp/stompjs";

export default function useWebSocket(
    itemsCallback: (product: Product) => void,
    resultCallback: (result: Result) => void
) {
    let stompClient: CompatClient;
    useEffect(()=>{
        let socket = new SockJS('http://production-service:8080/rfid');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, ()=>{
            stompClient.subscribe('/notif/items', msg => itemsCallback(JSON.parse(msg.body)));
            stompClient.subscribe('/notif/result', msg => resultCallback(JSON.parse(msg.body)));
        })
    },[]);
}
