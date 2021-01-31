export default interface PostOrder {
    productTypeId: number;
    components:{ componentTypeId: number; colorId: number; }[];
}
