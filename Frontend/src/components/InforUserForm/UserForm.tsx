import React, {useEffect, useState} from 'react';
import {Col, DatePicker, Row, Form, Input} from 'antd';
import dayjs from 'dayjs';
import './UserForm.scss'; // Nếu bạn đã có file SCSS này
import Popup from '../Popup/Popup';
import ForgotPass from '../Header/components/ForgotPass/ForgotPass';
import Province from '../Province/Province';

// Định nghĩa kiểu cho prop user
interface User {
    userName: string;
    fullName: string;
    email: string;
    address: string;
    phone: string;
    birthDate: string;
}

// Định nghĩa props cho FillUserForm
interface FillUserFormProps {
    user: User;
}

const FillUserForm: React.FC<FillUserFormProps> = ({user}) => {
    // Chuyển đổi từ chuỗi ngày tháng sang Dayjs
    const formatDate = (dateString: string | null) => {
        return dateString ? dayjs(dateString) : null;
    };

    return (
        <Form layout="vertical">
            <Form.Item label="Tên đăng nhập">
                <Input className="input-custom" defaultValue={user.userName}/>
            </Form.Item>

            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item label="Họ và tên">
                        <Input className="input-custom" defaultValue={user.fullName}/>
                    </Form.Item>
                </Col>
                <Col span={12}>
                    <Form.Item label="Email">
                        <Input className="input-custom" defaultValue={user.email}/>
                    </Form.Item>
                </Col>
            </Row>

            <Form.Item label="Địa chỉ">
                <Province/>
            </Form.Item>

            <Row gutter={16}>
                <Col span={12}>
                    <Form.Item label="Số điện thoại">
                        <Input className="input-custom" defaultValue={user.phone} type="number"/>
                    </Form.Item>
                </Col>
                <Col span={12}>
                    <Form.Item label="Ngày sinh">
                        <DatePicker
                            className="input-custom"
                            defaultValue={formatDate(user.birthDate)}
                            format="YYYY-MM-DD"
                        />
                    </Form.Item>
                </Col>
            </Row>
        </Form>
    );
};

const UserForm: React.FC = () => {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        const storedUser = localStorage.getItem('user') || sessionStorage.getItem('user');
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    return (
        <div className="container mx-auto">
            <div className="flex justify-center items-center">
                <div className="w-[900px]">
                    <div className="bg-white shadow-md rounded-lg p-4 mb-4">
                        <h2 className="text-lg font-semibold mb-2">Thông tin người dùng</h2>
                        <Form layout="vertical">
                            <Form.Item label="Tên đăng nhập">
                                <Input className="input-custom" value={user?.userName || ''} disabled/>
                            </Form.Item>

                            <Row gutter={16}>
                                <Col span={12}>
                                    <Form.Item label="Họ và tên">
                                        <Input className="input-custom" value={user?.fullName || ''} disabled/>
                                    </Form.Item>
                                </Col>
                                <Col span={12}>
                                    <Form.Item label="Email">
                                        <Input className="input-custom" value={user?.email || ''} disabled/>
                                    </Form.Item>
                                </Col>
                            </Row>

                            <Form.Item label="Địa chỉ">
                                <Input className="input-custom" value={user?.address || ''} disabled/>
                            </Form.Item>

                            <Row gutter={16}>
                                <Col span={12}>
                                    <Form.Item label="Số điện thoại">
                                        <Input className="input-custom" value={user?.phone || ''} type="number"
                                               disabled/>
                                    </Form.Item>
                                </Col>
                                <Col span={12}>
                                    <Form.Item label="Ngày sinh">
                                        <DatePicker
                                            className="input-custom"
                                            value={user?.birthDate ? dayjs(user.birthDate) : null}
                                            format="YYYY-MM-DD"
                                            disabled
                                        />
                                    </Form.Item>
                                </Col>
                            </Row>
                        </Form>

                        <div className="mx-64 mt-[20px] grid grid-cols-1 md:grid-cols-2 gap-4">
                            <div className="btn-changeinfor ml-30">
                                <Popup btnText="Thay đổi thông tin" title="Thay đổi thông tin"
                                       content={<FillUserForm user={user as User}/>}/>
                            </div>
                            <div className="ml-20 forgotpass">
                                <Popup btnText="Đổi mật khẩu" title="Đổi mật khẩu" content={<ForgotPass/>}/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserForm;
