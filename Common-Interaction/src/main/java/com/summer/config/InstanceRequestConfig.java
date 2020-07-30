package com.summer.config;

public class InstanceRequestConfig {

    public static class InstanceConfig {

        public String instanceName;

        public String instanceDescription;

        public String instanceChargeType;

        public String instanceType;

        public String imageId;

        public String zoneId;

        public String ioOptimized;

    }

    public static class InternetConfig {

        public String internetChargeType;

        public int maxBoundwidthOut;

        public int minBoundwidthIn;

    }

    private static class DiskConfig {

        public int size;

        public String category;

        public String name;

        public String description;

        public int number;
    }

    public static class DataDiskConfig extends DiskConfig {

        public String snapshotId;

        public boolean deleteWithInstance;

    }

    public InstanceConfig instanceConfig;

    public DiskConfig systemDiskConfig;

    public DataDiskConfig dataDiskConfig;

    public InternetConfig internetConfig;

}
