import React, {useState} from 'react';
import {Anchor, Col, Row} from 'antd';
import './SideBar.scss';

export default function SideBar() {
    const [activeKey, setActiveKey] = useState('profile');

    const menuItems = [
        {key: 'profile', href: '#profile', title: 'Thông tin'},
        {key: 'your-order', href: '#your-order', title: 'Đơn hàng'},
    ];

    // Xử lý khi click sẽ lấy key của thẻ đã nhấn lưu vào activeKey
    const handleClick = (e: any, item: any) => {
        setActiveKey(item.key);

    };
    return (
        <div className="w-1/3 mb-4 xl:mb-0 mr-40 whitespace-nowrap siderbar-container ">
            <div className="bg-white shadow-md rounded-lg p-4">
                <h2 className="sidebar-title text-2xl font-semibold mb-6">Điều hướng</h2>
                <Row>
                    <Col span={0}>
                        {menuItems.map((item) => (
                            <div key={item.key} id={item.key} className=""/>
                        ))}
                    </Col>
                    <Col span={20}>
                        <Anchor className="text-decoration-none"
                            showInkInFixed={false}
                            items={menuItems.map((item) => ({
                                ...item,
                                title: (
                                    <span
                                        onClick={(e) => handleClick(e, item)}
                                        // So sánh activeKey với key hiện tại của item, nếu trùng thì sẽ set màu cho chữ
                                        className={`text-decoration-none text-[16px] font-semibold mb-2 ml-[5px] hover:text-[#87C84A] ${activeKey === item.key ? 'text-[#87C84A]' : ''}`}>
                                        {item.title}
                                    </span>
                                ),
                            }))}
                        />
                    </Col>
                </Row>
            </div>
        </div>
    );
}
