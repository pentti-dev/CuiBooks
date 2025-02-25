import React, {useState} from 'react';
import {
    Form,
    Input,
} from 'antd';

export default function ForgotPass() {
    return (

        <>
            <div className="wrapper">

                <Form.Item label="Nhập mật khẩu hiện tại">
                    <Input.Password/>
                </Form.Item>
                <Form.Item label="Nhập mật khẩu mới">
                    <Input.Password/>
                </Form.Item>
                <Form.Item label="Nhập lại mật khẩu mới">
                    <Input.Password/>
                </Form.Item>
            </div>

        </>
    );


}