import ProductType from '../models/ProductType';
import PostIdData from '../models/PostIdData';

const url = 'order';

export async function getProductTypes(){
    const response = await fetch(url);
    const data: ProductType[] = await response.json();
    return data;
}

export async function postData(data: PostIdData){
    const response = await fetch(url, { method: 'POST',  headers: {'Content-Type': 'application/json'}, body: JSON.stringify(data)});
    return response.ok;
}
