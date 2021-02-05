# DeviceLibray
获取Android设备详细信息

# Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {         ~~~~
			...
			maven { url 'https://jitpack.io' }
		}
	}

# Step 2. Add the dependency

	dependencies {
		implementation 'com.github.z244370114:DeviceLibray:1.0.0'
	}
	

# 3. 使用说明
    (1)需要在Application的onCreate()方法里面调用UtilsApp.init(this)进行初始化上下文
    (2)需要注意的是FileUtils.getSDDeviceTxt(),该方法根据算法得到唯一的device_id,判断设备唯一性


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

![hardware](file:///android_asset/hardware.png)
![hardware1](file:///android_asset/hardware1.png)

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

![general_data](file:///android_asset/general_data.png)
![general_data1](file:///android_asset/general_data1.png)


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

![sim_card](file:///android_asset/sim_card.png)


# storage(存储)
|字段名|类型|说明|
| :----: | :----: | :----: |
|device_id|String|关联设备表下deviceId|
|ram_total_size|long|运行内存总大小(单位byte)|
|ram_usable_size|long|运行内存可用大小(单位byte)|
|internal_storage_total|long|内部存储总空间(单位byte)|
|internal_storage_usable|long|内部存储可用空间(单位byte)|
|memory_card_size|long|sd卡总空间(单位byte)|
|memory_card_size_use|long|sd卡已用空间(单位byte)|

![storage](file:///android_asset/storage.png)


# other_data(其它数据)
|字段名|类型|说明|
| :----: | :----: | :----: |
|root_jailbreak|Int|是否root（0：不是；1：是）|
|simulator|Int|是否为模拟器（0：不是；1：是）|
|keyboard|String|底部是否有物理按键：有：1；无：0|
|dbm|String|手机的信号强度（>-90,越大信号越强；""表示未取到）|
|last_boot_time|Long|最后一次开机时间，以毫秒为单位|
|is_using_vpn|Int|是否使用vpn（0：不是；1：是）|
|vpn_address|String|vpn代理地址|
|is_using_proxy_port|Int|是否使用代理（0：不是；1：是）|
|http_proxy_host_port|String|http代理host:port|
|is_usb_debug|Int|是否开启debug调试（0：不是；1：是）|
|is_mock_location|Int|是否允许位置模拟（0：不是；1：是）|
|ringer_mode|Int|<br>0：RINGER_MODE_SILENT（静音模式）<br>1：RINGER_MODE_VIBRATE（震动模式）<br>2：RINGER_MODE_NORMAL（铃音模式）|
|is_airplane_mode|Int|是否开启飞行模式（0：不是；1：是）|

![other_data](file:///android_asset/other_data.png)

# applist(app安装)
|字段名|类型|说明|
| :----: | :----: | :----: |
|app_name|String|已安装应用：APP名称|
|package_name|String|已安装应用：包名|
|in_time|Long|已安装应用：安装时间|
|up_time|Long|已安装应用：最后更新时间|
|version_name|String|版本名称|
|version_code|Int|版本号|
|flags|Int|应用标签|
|app_type|Int|是否系统应用(1:系统应用；0：非系统应用)|
|special_permisson_list|List<String>|获取app特殊权限项|

![applist](file:///android_asset/applist.png)


# contact(联系人)
|字段名|类型|说明|
| :----: | :----: | :----: |
|contact_display_name|String|联系人名称|
|number|String|联系人列表：电话号码|
|up_time|Long|联系人列表：更新时间||
|last_time_contacted|Long|与联系人最后联系时间|
|times_contacted|int|联系次数|
|starred|int|是否收藏联系人(1:收藏；0：未收藏)|
|email|String|电子邮件地址|

![contact](file:///android_asset/contact.png)


# GPS(定位)-客户端改动
|字段名|类型|说明|
| :----: | :----: | :----: |
|latitude|String|维度|
|longitude|String|经度|
|addressDetails|String|GPS详细地址|

![GPS](file:///android_asset/GPS.png)



# media_files(媒体文件)
|字段名|类型|说明|
| :----: | :----: | :----: |
|audio_internal|Int|音频内部文件个数|
|audio_external|Int|音频外部文件个数|
|images_internal|Int|图片内部文件个数|
|images_external|Int|图片外部文件个数|
|video_internal|Int|视频内部文件个数|
|video_external|Int|视频外部文件个数|
|download_files|Int|下载的文件个数|
|contact_group|int|联系⼈小组个数(基数默认偏大，会算上自带群组名)|

![media_files](file:///android_asset/media_files.png)

# network(网络)
|字段名|类型|说明|
| :----: | :----: | :----: |
|ip|String|路由器IP|
|router_name|String|路由器名字(Server根据当前wifi的bssid查)|
|current_wifi[].bssid|String|当前的wifi BSSID|
|current_wifi[].ssid|String|当前的wifi SSID|
|current_wifi[].name|String|wifi名称|
|current_wifi[].mac|String|当前的wifi mac地址|
|configured_wifi[].bssid|String|BSSID / 配置wifi BSSID|
|configured_wifi[].ssid|String|SSID / 配置wifi SSID|
|configured_wifi[].name|String|wifi名称|

![network](file:///android_asset/network.png)


# battery_status(电池)
|字段名|类型|说明|
| :----: | :----: | :----: |
|is_charging|int|是否正在充电（1：未知状态；2：充电中；3：放电中；4：未充电；5：充满）|
|battery_pct|Double|电池百分⽐(0-100)|
|charge_type|int|1:BATTERY_PLUGGED_AC（充电器）<br>2：BATTERY_PLUGGED_USB（USB充电）<br>4：BATTERY_PLUGGED_ANY（其它）||
|battery_health|int|1:BATTERY_HEALTH_UNKNOWN（未知）<br>2：BATTERY_HEALTH_GOOD（良好）<br>3：BATTERY_HEALTH_OVERHEAT（过热）<br>4：BATTERY_HEALTH_DEAD（没电）<br>5：BATTERY_HEALTH_OVER_VOLTAGE（过电压）<br>6：BATTERY_HEALTH_UNSPECIFIED_FAILURE（未知错误）<br>7：BATTERY_HEALTH_COLD（温度过低）|
|battery_temperature|Double|电池温度(单位0.1，如359表示35.9°C)|
|screen_brightness|Double|屏幕亮度(0-255)|

![battery_status](file:///android_asset/battery_status.png)


# sensor[ ] (传感器)
|字段名|类型|说明|
| :----: | :----: | :----: |
|sensor_lists[].type|int|传感器的类型|
|sensor_lists[].name|String|传感器的名字|
|sensor_lists[].version|int|传感器的版本号|
|sensor_lists[].vendor|String|Vendor名|
|sensor_lists[].max_range|float|传感器的最大值|
|sensor_lists[].min_delay|float|传感器的最小值|
|sensor_lists[].power|float|传感器的功率|
|sensor_lists[].resolution|float|传感器的精度|

![sensor](file:///android_asset/sensor.png)
