export interface Comment {
  id: number;
  fullName: string;
  content: string;
  date: Date;
}
export interface NewComment {
  userId: number;
  content: string;
}

export interface Product {
  id: string;
  name: string;
  price: number;
  quantity: number;
  image: string;
}

export interface dataProduct {
  id: number;
  name: string;
  image: string;
  price: number;
  type: string;
  comments: any[]; // Use a more specific type if you know the structure of comments
}

export interface listProduct {
  id: number;
  name: string;
  price: number;
  image: string;
  type: string;
  comments: any[];
  quantity: number;
}
export interface formDataProduct {
  id: number;
  name: string;
  price: number;
  image: string;
  type: string;
}
export interface formProduct {
  id: number;
  name: string;
  price: number;
  image: string;
  type: string;
  quantity: number;
}

export type ProductResponse = listProduct[];
