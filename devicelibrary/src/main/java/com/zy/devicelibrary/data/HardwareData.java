package com.zy.devicelibrary.data;

import android.os.Build;

import com.zy.devicelibrary.utils.CpuUtils;

import static com.zy.devicelibrary.utils.OtherUtils.getBaseband_Ver;
import static com.zy.devicelibrary.utils.OtherUtils.getResolutions;
import static com.zy.devicelibrary.utils.OtherUtils.getScreenDensity;
import static com.zy.devicelibrary.utils.OtherUtils.getScreenDensityDpi;
import static com.zy.devicelibrary.utils.OtherUtils.getScreenSizeOfDevice2;
import static com.zy.devicelibrary.utils.OtherUtils.getSerialNumbers;
import static com.zy.devicelibrary.utils.OtherUtils.isTabletDevice;

public class HardwareData {


    /**
     * model : SM-A205F
     * brand : samsung
     * product : a20dd
     * release : 9
     * sdk_version : 28
     * physical_size : 5.91917583812819
     * serial_number : RR8M50LX40M
     * cpu_type : 0
     * cpu_min : 396800
     * cpu_max : 1500000
     * cpu_average : 478000
     * manufacturerName : samsung
     * board : universal7885
     * serial : RR8M50LX40M
     * display : PPR1.180610.011.A205FXXU1ASC8
     * id : PPR1.180610.011
     * bootloader : A205FXXU1ASC8
     * fingerPrint : samsung/a20dd/a20:9/PPR1.180610.011/A205FXXU1ASC8:user/release-keys
     * host : 21HH1E20
     * hardWare : exynos7885
     * device : a20
     * user : dpi
     * radioVersion : A205FDDU1ASC8,A205FDDU1ASC8
     * tags : release-keys
     * time : 1551957941000
     * type : user
     * baseband_ver : 21.258.09.00.030,CBP8.2
     * resolution : 1080*1920
     */

    public String model;
    public String brand;
    public String product;
    public String release;
    public String sdk_version_code;
    public String physical_size;
    public String cpu_type;
    public String cpu_min;
    public String cpu_max;
    public String cpu_cur;
    public String manufacturer_name;
    public String board;
    public String serial_number;
    public String display;
    public String id;
    public String bootloader;
    public String finger_print;
    public String host;
    public String hardware;
    public String device;
    public String user;
    public String radio_version;
    public String tags;
    public String time;
    public String type;
    public String base_os;
    public String baseband_ver;
    public String resolution;
    public String screen_density;
    public String screen_density_dpi;
    public String cpu_abi;
    public String cpu_abi2;
    public String abis = "";
    public int is_tablet;

    {
        model = Build.MODEL;
        brand = Build.BRAND;
        product = Build.PRODUCT;
        release = Build.VERSION.RELEASE;
        sdk_version_code = String.valueOf(Build.VERSION.SDK_INT);
        physical_size = getScreenSizeOfDevice2();
        cpu_type = CpuUtils.getCpuName();
        cpu_min = CpuUtils.getMinCpuFreq();
        cpu_max = CpuUtils.getMaxCpuFreq();
        cpu_cur = CpuUtils.getCurCpuFreq();
        manufacturer_name = Build.MANUFACTURER;
        board = Build.BOARD;
        serial_number = getSerialNumbers();
        display = Build.DISPLAY;
        id = Build.ID;
        bootloader = Build.BOOTLOADER;
        finger_print = Build.FINGERPRINT;
        host = Build.HOST;
        hardware = Build.HARDWARE;
        device = Build.DEVICE;
        user = Build.USER;
        radio_version = Build.RADIO;
        tags = Build.TAGS;
        time = String.valueOf(Build.TIME);
        type = Build.TYPE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            base_os = Build.VERSION.BASE_OS;
        }
        baseband_ver = getBaseband_Ver();
        resolution = getResolutions();
        screen_density = String.valueOf(getScreenDensity());
        screen_density_dpi = String.valueOf(getScreenDensityDpi());
        cpu_abi = Build.CPU_ABI;
        cpu_abi2 = Build.CPU_ABI2;
        is_tablet = isTabletDevice();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] supportedAbis = Build.SUPPORTED_ABIS;
            for (int i = 0; i < supportedAbis.length; i++) {
                if (i == supportedAbis.length - 1) {
                    abis += supportedAbis[i];
                } else {
                    abis += supportedAbis[i] + ",";
                }
            }
        }
    }
}


