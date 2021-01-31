import ProductType from '../models/order/ProductType';
import PostOrder from '../models/order/PostOrder';

const url = 'api/order';

export async function getProductTypes(){
    const response = await fetch(url);
    const data: ProductType[] = await response.json();
    return data;
}

export async function postData(data: PostOrder){
    const response = await fetch(url, { method: 'POST',  headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)});
    return response.ok;
}
