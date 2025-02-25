import React, {useState} from 'react';
import './Account.scss';
import UserForm from '../../components/InforUserForm/UserForm';
import SideBar from '../../components/InforUserForm/SideBar/SideBar';



export default function Account() {

    return (
        <>

            <div className="container" style={{ marginTop: '5%' }}>
                <div className="flex justify-center items-center">
                    <SideBar/>
                    <UserForm/>
                </div>
            </div>
        </>
    );
};

