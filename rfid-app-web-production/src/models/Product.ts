import { Component } from '../models';

export interface Product{
    id: number;
    name: string;
    components: Component[];
}
