import {useEffect, useState} from 'react';
import './OrderHistory.scss';
import {useOrderLogic} from "../order/OrderLogic";
import {useNavigate} from 'react-router-dom';

const OrderHistoryPage: React.FC = () => {
    const [orders, setOrders] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const {formatPrice} = useOrderLogic();

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        const userId = user.id;

        console.log('userId:', userId);

        const fetchOrders = async () => {
            try {
                const response = await fetch('http://localhost:5003/api/Order/' + userId);
                if (!response.ok) {
                    throw new Error('Failed to fetch orders.');
                }
                const data = await response.json();

                console.log('Fetched data:', data);

                // Inspect the structure of each order object
                data.forEach((order: any, index: number) => {
                    console.log(`Order ${index + 1}:`, order);
                });
                console.log('user:', userId);
                // Adjust the filter condition based on the actual structure
                let userOrders = data.filter((order: any) => {
                    console.log(`Order ID: ${order.id}, Order User ID: ${order.user?.id || order.userid}`);
                    return order.user?.id === userId || order.userid === userId;
                });
                console.log('Filtered userOrders:', userOrders);

                userOrders.sort((a: any, b: any) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());

                setOrders(userOrders);
            } catch (error) {
                setError('Không thể lấy thông tin đơn hàng. Vui lòng thử lại sau.');
                console.error('Failed to fetch orders:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, [navigate]);


    const handleViewDetails = (order: any) => {
        sessionStorage.setItem('orderData', JSON.stringify(order));
        navigate('/orderDetail');
    };

    if (loading) {
        return <div>Đang tải dữ liệu...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (orders.length === 0) {
        return <div>Không có đơn hàng nào.</div>;
    }

    return (
        <div className="orderHistoryContainer">
            <h2 style={{textAlign: 'center', color: "#006600", marginBottom: "50px"}}>Lịch sử đơn hàng</h2>
            <table className="orderTable">
                <thead>
                <tr>
                    <th>Hình ảnh</th>
                    <th>Trạng thái</th>
                    <th>Phí vận chuyển</th>
                    <th>Tổng tiền</th>
                    <th>Chi tiết</th>
                </tr>
                </thead>
                <tbody>
                {orders.map((order: any, index: number) => (
                    <tr key={index}>
                        <td>
                            {order.products.length > 0 && (
                                <img
                                    src={order.products[0].image}
                                    alt={order.products[0].name}
                                    className="productImage"
                                />
                            )}
                        </td>
                        <td>{order.status}</td>
                        <td>{formatPrice(order.phiship)}</td>
                        <td>{formatPrice(order.totalamount + order.phiship)}</td>
                        <td>
                            <button className="button" style={{
                                background: "green",
                                width: "140px",
                                padding: "10px",
                                borderRadius: "10px",
                                color: "white"
                            }} onClick={() => handleViewDetails(order)}>
                                Xem chi tiết
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrderHistoryPage;
