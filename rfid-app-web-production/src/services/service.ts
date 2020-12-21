import Product from '../models/Product';

export async function getProduct(){
    let data: Product|null = null;
    try{
        const response = await fetch('production/next');
        data = await response.json();
    } catch(Exception){}
    return data;
}
