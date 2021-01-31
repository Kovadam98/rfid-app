import React, { ChangeEvent, useEffect, useState } from 'react';
import Order from '../components/Order/Order';
import ProductType from "../models/order/ProductType";
import ComponentTypeData from "../models/order/ComponentTypeData";
import {getProductTypes, postData} from "../services/orderServices";
import Menu from "../components/Order/Menu";
import InfoModal from "../components/InfoModal";

export default function OrderPage() {
    const [productTypes,setProductTypes] = useState<ProductType[]>([]);
    const [selectedProductType,setSelectedProductType] = useState<ProductType | null>(null);
    const [componentTypeDataArray, setComponentTypeDataArray] = useState<ComponentTypeData[] | null>(null);
    const [modalIsOpen,setModalIsOpen] = useState(false);
    const [isSuccess, setIsSuccess] = useState(true);

    function mapComponentTypeData(productType: ProductType){
        const map = productType.componentTypes.map(ct => {
            return {
                componentType: ct,
                colorId: ct.colorTypes[0].id
            }
        });
        setComponentTypeDataArray(map);
    }

    useEffect( () => {
        const fetchData = async () => {
            const data = await getProductTypes();
            setProductTypes(data);
            setSelectedProductType(data[0]);
            mapComponentTypeData(data[0]);
        }
        fetchData().then();
    },[]);

    function handleSelect(event: ChangeEvent<HTMLSelectElement>){
        const productType = productTypes.filter(pt => pt.id.toString() === event.target.value)[0];
        setSelectedProductType(productType);
        mapComponentTypeData(productType);
    }

    async function handleSend(){
        const components = componentTypeDataArray!.map((ctda) => {
            return { componentTypeId: ctda.componentType.id, colorId: ctda.colorId };
        });
        const data = {
            productTypeId: selectedProductType!.id,
            components: components
        };
        const ok = await postData(data);
        setModalIsOpen(true);
        setIsSuccess(ok);
        setTimeout(() => setModalIsOpen(false),1000);
    }

    return (
        <>
            {componentTypeDataArray &&
            <Order
                componentTypeDataArray={componentTypeDataArray}
                setComponentTypeDataArray={setComponentTypeDataArray}
            />
            }
            <Menu
                options={productTypes.map((pt) => { return {value: pt.id.toString(), name: pt.name};})}
                onSelect={handleSelect}
                onSend={handleSend}
            />
            <InfoModal isOpen={modalIsOpen} isSuccess={isSuccess} />
        </>
    );
}
