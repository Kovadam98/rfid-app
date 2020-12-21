import ComponentType from './ComponentType';

export default interface ProductType{
    id: number;
    name: string;
    componentTypes: ComponentType[];
}
