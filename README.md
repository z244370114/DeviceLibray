# DeviceLibray
获取Android设备详细信息

# Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
# Step 2. Add the dependency

	dependencies {
		implementation 'com.github.z244370114:DeviceLibray:1.0.0'
	}
	

# hardware(硬件)
|字段名| 类型| 说明
| :----: | :----: | :----: |
|model|String |设备型号|
|brand|String |设备品牌|
|product|String |产品名称|
|release|String |系统版本|
|sdk_version_code|String |SDK版本号|
|physical_size|String |物理尺寸|
|cpu_type|String |cpu名字|
|cpu_min|String |cpu最小频率|
|cpu_max|String |cpu最大频率|
|cpu_cur|String |cpu当前频率|
|manufacturer_name|String |制造商|
|board|String |主板名称|
|serial_number|String |设备序列号|
|display|String |显示屏参数|
|id|String |修订版本列表|
|bootloader|String |系统引导加载程序版本号|
|finger_print|String |设备指纹|
|host|String |执行代码编译的Host值|
|hardware|String |硬件名|
|device|String |设备参数|
|user|String |执行代码编译的User值|
|radio_version|String |无线电固件版本|
|tags|String |描述Build的标签|
|time|String |编译时间|
|type|String |builder类型|
|base_os|String |build基本操作系统|
|baseband_ver|String |基带版本|
|resolution|String |设备分辨率|
|screen_density|String |屏幕密度（像素比例：0.75/1.0/1.5/2.0）|
|screen_density_dpi|String |屏幕密度（每寸像素：120/160/240/320）|
|cpu_abi|String |获取设备指令集名称|
|cpu_abi2|String |获取第二个指令集名称|
|abis|String |手机cpu架构，支持的指令集|
|is_tablet|int |是否是平板(1:是；0：不是)|

# general_data(通用数据)
|字段名| 类型| 说明
| :----: | :----: | :----: |
|and_id|String| 安卓ID| 
|gaid|String| GAID| 
|network_operator_name|String| 网络运营商名称| 
|network_operator|String| 当前注册运营商的数字名称（MCC + MNC）| 
|network_type|String| 当前网络类型:<br>NETWORK_2G<br>NETWORK_3G<br>NETWORK_4G<br>NETWORK_5G<br>NETWORK_WIFI| 
|phone_type|String| | 
|mcc|String|Mcc / IMSI MCC（移动国家代码） | 
|bluetooth_mac|String|蓝牙mac地址 | 
|mnc|String| Mnc / IMSI MNC（移动网络代码）| 
|locale_iso_3_language|String| 语言环境的三字母缩写| 
|locale_iso_3_country|String| 此地区的国家/地区的缩写| 
|time_zone_id|String| 时区的ID| 
|locale_display_language|String| 用户显示的语言环境语言的名称| 
|cid|String|基站编号 | 
|dns|String| | 
|uuid|String|设备标识符UUID | 
|slot_count|int|支持卡槽数量(sdk_version>=23才可取值，否则为0) | 
|meid|String|移动设备识别码 , 卡槽移动设备身份码1(android 10及以上无法取) | 
|imei2|String|卡槽移动设备身份码2(android 10及以上无法取) | 
|mac|String|mac地址 | 
|language|String| 本地语言| 
|ui_mode_type|String|当前设备支持的UI类型<br>UI_MODE_TYPE_UNDEFINED<br>UI_MODE_TYPE_NORMAL<br>UI_MODE_TYPE_DESK<br>UI_MODE_TYPE_CAR<br>UI_MODE_TYPE_TELEVISION<br>UI_MODE_TYPE_APPLIANCE<br>UI_MODE_TYPE_WATCH<br>UI_MODE_TYPE_VR_HEADSET | 
|security_patch|String| 安全补丁更新日期| 

# sim_card
|字段名| 类型| 说明
| :----: | :----: | :----: |
| sim_count|int|设备当前sim卡数量|
| imsi1|String|sim卡1移动用户身份|
| sim_country_iso1|String|sim卡1ISO国家代码等同于SIM提供商的国家代码|
| sim_serial_number1|String|sim卡1的序列号|
| number1|String|sim卡1对应手机号|
| imsi2|String|sim卡2移动用户身份|
| sim_country_iso2|String|sim卡2ISO国家代码等同于SIM提供商的国家代码|
| sim_serial_number2|String|sim卡2的序列号|
| number2|String|sim卡2对应手机号|

|字段名|类型|说明|
| :----: | :----: | :----: |
|device_id|String|关联设备表下deviceId|
|ram_total_size|long|运行内存总大小(单位byte)|
|ram_usable_size|long|运行内存可用大小(单位byte)|
|internal_storage_total|long|内部存储总空间(单位byte)|
|internal_storage_usable|long|内部存储可用空间(单位byte)|
|memory_card_size|long|sd卡总空间(单位byte)|
|memory_card_size_use|long|sd卡已用空间(单位byte)|