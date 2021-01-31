import ColorType from './ColorType';

export default interface ComponentType {
    id: number;
    name: string;
    assemblyOrder: number;
    imageUrl: string;
    colorTypes: ColorType[];
    canvas?: HTMLCanvasElement;
}
