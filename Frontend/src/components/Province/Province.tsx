import React, { useState, useEffect } from 'react';
import { Cascader } from 'antd';
import './Province.css'; // Import the CSS file

interface ProvinceData {
    id: number;
    full_name: string;
}

interface CascaderOption {
    value: number;
    label: string;
    isLeaf: boolean;
    loading?: boolean;
    children?: CascaderOption[];
}

function Province() {
    const [options, setOptions] = useState<CascaderOption[]>([]);

    useEffect(() => {
        // Fetch the list of provinces and convert it to Cascader options format
        fetch('https://esgoo.net/api-tinhthanh/1/0.htm')
            .then(res => res.json())
            .then(data => {
                if (data.error === 0) {
                    const provinceOptions = data.data.map((province: ProvinceData) => ({
                        value: province.id,
                        label: province.full_name,
                        isLeaf: false, // Mark province as not the final selection
                    }));
                    setOptions(provinceOptions);
                }
            });
    }, []);

    const loadData = async (selectedOptions: CascaderOption[]) => {
        const targetOption = selectedOptions[selectedOptions.length - 1];
        targetOption.loading = true;

        // Determine the level being selected (province, district, or ward)
        const level = selectedOptions.length;
        const parentId = targetOption.value;

        const res = await fetch(`https://esgoo.net/api-tinhthanh/${level + 1}/${parentId}.htm`);
        const data = await res.json();

        if (data.error === 0) {
            targetOption.loading = false;
            targetOption.children = data.data.map((item: ProvinceData) => ({
                value: item.id,
                label: item.full_name,
                isLeaf: level === 2, // Mark ward as the final selection
            }));
            setOptions([...options]); // Update options to refresh Cascader
        } else {
            targetOption.loading = false;
        }
    };

    const handleChange = (value: any[], selectedOptions?: CascaderOption[]) => {
        if (selectedOptions && selectedOptions.length === 3) { // Close the Cascader when Ward (Xã) is selected
            // Perform any additional actions if needed
        }
    };

    return (
        <Cascader
            options={options}
            loadData={loadData}
            onChange={handleChange}
            changeOnSelect // Allow changing value when selecting each level
            placeholder="Chọn Tỉnh/Thành, Quận/Huyện, Phường/Xã"
        />
    );
}

export default Province;