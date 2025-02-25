import { useEffect, useState } from 'react';
import { message } from 'antd';
import { useCart } from '../ProductDetail/CartContext';
import { useNavigate } from 'react-router-dom';

interface FormValues {
    sonha: string;
    huyen: string;
    xa: string;
    phone: string;
    name: string;
    phiShip: number;
    thoiGian: string;
    tinh: string;
}

message.config({
    top: 100,
    duration: 3,
    maxCount: 3,
});

export const useOrderLogic = () => {
    const { products, clearCart, getTotalPrice, sumQuantity } = useCart();
    const selectedDistrict = "TP.Thủ Đức"; // Cố định huyện là Thủ Đức
    const provin = "TP.HCM"; // Cố định huyện là Thủ Đức
    const [shippingInfo, setShippingInfo] = useState({ phiShip: 0, thoiGian: "" });

    const formatPrice = (price: number): string => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
    };

    const getTotalAmount = (): number => {
        return getTotalPrice();
    };
    const getUserId = (): string | null => {
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        return user.id || null; // Lấy ID người dùng từ sessionStorage
    };

    const handleFinish = async (values: FormValues) => {
        const { phiShip, thoiGian } = getShippingAndTime(values.xa);
        const userId = getUserId(); // Lấy ID người dùng
        const orderData = {
            products,
            totalAmount: getTotalAmount(),
            status: 'Đang xử lý',
            address: {
                sonha: values.sonha,
                huyen: values.huyen,
                xa: values.xa,
                phone: values.phone,
                name: values.name,
                tinh: provin,
            },
            phiShip,
            thoiGian,
            userId,
        };

        try {
            await fetch('http://localhost:5003/api/Order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(orderData),
            });

            sessionStorage.setItem('orderData', JSON.stringify(orderData));

            message.success('Đặt hàng thành công!');
            clearCart();
        } catch (error) {
            message.error('Đặt hàng thất bại!');
        }
    };

    const getShippingAndTime = (xa: string): { phiShip: number; thoiGian: string } => {
        const sumQuantityValue = sumQuantity();
        if (sumQuantityValue >= 5) {
            switch (xa) {
                case "Phường Linh Xuân":
                    return { phiShip: 0, thoiGian: "17 phút" };
                case "Phường Linh Trung":
                    return { phiShip: 0, thoiGian: "16 phút" };
                case "Phường Trường Thọ":
                    return { phiShip: 0, thoiGian: "23 phút" };
                case "Phường Tam Phú":
                    return { phiShip: 0, thoiGian: "27 phút" };
                case "Phường Tam Bình":
                    return { phiShip: 0, thoiGian: "25 phút" };
                case "Phường Linh Tây":
                    return { phiShip: 0, thoiGian: "22 phút" };
                case "Phường Linh Đông":
                    return { phiShip: 0, thoiGian: "30 phút" };
                case "Phường Hiệp Bình Phước":
                    return { phiShip: 0, thoiGian: "35 phút" };
                case "Phường Hiệp Bình Chánh":
                    return { phiShip: 0, thoiGian: "36 phút" };
                case "Phường Bình Thọ":
                    return { phiShip: 0, thoiGian: "21 phút" };
                case "Phường Bình Chiểu":
                    return { phiShip: 0, thoiGian: "27 phút" };
                default:
                    return { phiShip: 0, thoiGian: "" };
            }
        } else {
            switch (xa) {
                case "Phường Linh Xuân":
                    return { phiShip: 10000, thoiGian: "17 phút" };
                case "Phường Linh Trung":
                    return { phiShip: 10000, thoiGian: "16 phút" };
                case "Phường Trường Thọ":
                    return { phiShip: 15000, thoiGian: "23 phút" };
                case "Phường Tam Phú":
                    return { phiShip: 20000, thoiGian: "27 phút" };
                case "Phường Tam Bình":
                    return { phiShip: 15000, thoiGian: "25 phút" };
                case "Phường Linh Tây":
                    return { phiShip: 15000, thoiGian: "22 phút" };
                case "Phường Linh Đông":
                    return { phiShip: 20000, thoiGian: "30 phút" };
                case "Phường Hiệp Bình Phước":
                    return { phiShip: 25000, thoiGian: "35 phút" };
                case "Phường Hiệp Bình Chánh":
                    return { phiShip: 20000, thoiGian: "36 phút" };
                case "Phường Bình Thọ":
                    return { phiShip: 15000, thoiGian: "21 phút" };
                case "Phường Bình Chiểu":
                    return { phiShip: 20000, thoiGian: "27 phút" };
                default:
                    return { phiShip: 0, thoiGian: "" };
            }
        }
    };

    return {
        products,
        selectedDistrict,
        formatPrice,
        getTotalAmount,
        handleFinish,
        getShippingAndTime,
        shippingInfo,
        setShippingInfo,
    };
};
