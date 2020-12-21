function checkServiceWorker() {
    if (!('serviceWorker' in navigator)) {
        throw new Error('No ServiceWorker support.');
    }
    else {
        //console.log('ServiceWorker support OK.');
    }
}

function checkPushManager() {
    if(!('PushManager' in window)) {
        throw new Error('No Push API Support.');
    }
    else {
        //console.log('PushManager support OK.');
    }
}

async function registerServiceWorker() {
    try{
        navigator.serviceWorker.register('custom-service-worker.js').then(reg => reg.update());
        //console.log('ServiceWorker was registered.');
    }
    catch (e) {
        throw new Error('Failed to register ServiceWorker.');
    }
}

async function requestNotificationPermission() {
    const permission = await window.Notification.requestPermission(); //granted | default | denied
    if (permission !== 'granted') {
        throw new Error('Permission not granted for showing notification');
    }
    else {
        //console.log("Permission granted for showing notification.");
    }
}

async function registerPushMessageHandler(eventHandler: any) {
    try{
        const channel = new BroadcastChannel('sw-messages');
        channel.addEventListener('message', eventHandler);
        //console.log('Push message handler was registered.');
    }
    catch (e) {
        throw new Error('Failed to register push message handler.');
    }
}

export default async function register(pushMessageHandler: any){
    checkServiceWorker();
    checkPushManager();
    await registerServiceWorker();
    await requestNotificationPermission();
    await registerPushMessageHandler(pushMessageHandler);
}

