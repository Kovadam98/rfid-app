import Product from '../models/Product';

export function filter(hue: number, brightness: number, saturation: number) {
    return `hue-rotate(${hue}deg) brightness(${brightness}%) saturate(${saturation}%)`;
}

export function equals(productOld: Product | null,productNew: Product | null){
    let found = !!productNew && !!productOld;
    productNew?.components.forEach(nw => {
        const obj = productOld?.components.find(old => old.id === nw.id);
        if(obj === undefined) found = false;
    });
    productOld?.components.forEach(old => {
        const obj = productNew?.components.find(nw => old.id === nw.id );
        if(obj===undefined) found = false;
    });
    return found;
}

