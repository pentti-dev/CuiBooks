export interface Order {
  id: number;
  products: {
    id: number;
    name: string;
    image: string;
    price: number;
    type: string | null;
    quantity: number;
  }[];
  totalamount: number;
  status: string;
  address: {
    sonha: string;
    huyen: string;
    xa: string;
    phone: string;
    name: string;
    tinh: string;
  };
  phiship: number;
  thoigian: string;
  userid: number;
}
