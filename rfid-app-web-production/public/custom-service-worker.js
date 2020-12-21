const publicKey = 'BN5JR-nxYxfsTJU-5jwXNbwEfK2Yja-rgjkg9_nJUQbCH_j7TrYhaEw2bvADbjlp5EaCVSAMbwI5DD70iMYUqvU';

const urlB64ToUint8Array = base64String => {
    const padding = '='.repeat((4 - (base64String.length % 4)) % 4)
    const base64 = (base64String + padding).replace(/\-/g, '+').replace(/_/g, '/')
    const rawData = atob(base64)
    const outputArray = new Uint8Array(rawData.length)
    for (let i = 0; i < rawData.length; ++i) {
        outputArray[i] = rawData.charCodeAt(i)
    }
    return outputArray
}

function format(subscription) {
    const sub = JSON.parse(JSON.stringify(subscription));
    return {
        endPoint: sub.endpoint,
        publicKey: sub.keys.p256dh,
        auth: sub.keys.auth
    };
}

async function saveSubscription(subscription) {
    const response = await fetch('subscribe', {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(format(subscription))
    })
    return await response.json();
}

self.addEventListener('activate', async () => {
    try {
        const applicationServerKey = urlB64ToUint8Array(publicKey);
        const options = {applicationServerKey, userVisibleOnly: true}
        const subscription = await self.registration.pushManager.subscribe(options);
        await self.clients.claim();
        await saveSubscription(subscription);
        console.log("Service worker was subscribed to notifications.");
    } catch (err) {
        console.log('Error');
    }
})

self.addEventListener('push', function(event) {
    const channel = new BroadcastChannel('sw-messages');
    const message = event.data.json()
    channel.postMessage(message);
})
