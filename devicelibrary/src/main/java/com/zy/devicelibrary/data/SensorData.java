package com.zy.devicelibrary.data;


import java.util.ArrayList;
import java.util.List;

public class SensorData {

    public List<SensorInfo> sensor_lists = new ArrayList<>();

    public static class SensorInfo {

        public int type;
        public String name;
        public int version;
        public String vendor;
        public float max_range;
        public int min_delay;
        public float power;
        public float resolution;
    }


}
