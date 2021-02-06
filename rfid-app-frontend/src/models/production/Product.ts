import Component from './Component';

export default interface Product {
    id: number;
    name: string;
    components: Component[];
    componentCount: number;
}
