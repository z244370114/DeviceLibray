package com.zy.devicelibrary.data;

import java.util.ArrayList;
import java.util.List;

public class NetWorkData {

    public String ip;
    public NetWorkInfo current_wifi = new NetWorkInfo();
    public List<NetWorkInfo> configured_wifi = new ArrayList<>();

    public static class NetWorkInfo {
        public String bssid;
        public String ssid;
        public String name;
        public String mac;

        public NetWorkInfo() {
        }

        public NetWorkInfo(String bssid, String ssid, String name) {
            this.bssid = bssid;
            this.ssid = ssid;
            this.name = name;
        }
    }

}
