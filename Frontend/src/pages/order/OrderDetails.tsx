import { useEffect, useState } from 'react';
import "./OrderDteails.scss"
import { Link } from 'react-router-dom';
import { useOrderLogic } from './OrderLogic';

const OrderDetailsPage: React.FC = () => {
    const { formatPrice } = useOrderLogic();
    const [orderData, setOrderData] = useState<any>(null);

    useEffect(() => {
        const storedOrder = sessionStorage.getItem('orderData');
        if (storedOrder) {
            const parsedOrder = JSON.parse(storedOrder);
            setOrderData(parsedOrder);
        }
    }, []);

    if (!orderData) {
        return <div>Loading...</div>;
    }

    return (
        <div className="orderDetailContainer">
            <div className="order-details">
                <Link to="/product">
                    <button className="back">X</button>
                </Link>
                <h3 className="info" style={{textAlign: 'center'}}>Thông tin đơn hàng</h3>

                <div className="order-detail-section">
                    <span className="detail-title">Sản phẩm:</span>
                    <ul>
                        {orderData.products.map((product: any, index: number) => (
                            <li key={index}>
                                {product.name}: {product.quantity} x {formatPrice(product.price)}
                            </li>
                        ))}
                    </ul>
                </div>

                <div className="order-detail-section">
                    <span className="detail-title">Trạng thái:</span>
                    <span className="detail-info">{orderData.status}</span>
                </div>
                <div className="order-detail-section">
                    <span className="detail-title">Phí vận chuyển:</span>
                    <span className="detail-info">{formatPrice(orderData.phiship)}</span>
                </div>

                <div className="order-detail-section">
                    <span className="detail-title">Tổng tiền:</span>
                    <span className="detail-info">{formatPrice(orderData.totalamount + orderData.phiship)}</span>
                </div>
                <div className="order-detail-section">
                    <span className="detail-title">Thời gian dự kiến nhận hàng:</span>
                    <span className="detail-info">{orderData.thoiGian}</span>
                </div>
                <div className="order-detail-section">
                    <span className="detail-title">Địa chỉ nhận hàng:</span>
                    <span className="detail-info">
                        {orderData.address.sonha} - {orderData.address.xa} - {orderData.address.huyen} - {orderData.address.tinh}
                    </span>
                </div>
                <div className="order-detail-section">
                    <span className="detail-title">Số điện thoại:</span>
                    <span className="detail-info">{orderData.address.phone}</span>
                </div>
                <div className="order-detail-section">
                    <span className="detail-title">Tên:</span>
                    <span className="detail-info">{orderData.address.name}</span>
                </div>
            </div>
        </div>
    );
};

export default OrderDetailsPage;
