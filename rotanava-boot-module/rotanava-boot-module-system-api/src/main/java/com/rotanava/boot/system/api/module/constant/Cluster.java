package com.rotanava.boot.system.api.module.constant;

public enum Cluster {

    //

    CLUSTER_DAEMONSET_COUNT("cluster_daemonset_count",""),
    CLUSTER_NET_BYTES_TRANSMITTED("cluster_net_bytes_transmitted",""),
    CLUSTER_CPU_TOTAL("cluster_cpu_total",""),
    CLUSTER_NET_UTILISATION("cluster_net_utilisation",""),
    CLUSTER_CPU_UTILISATION("cluster_cpu_utilisation",""),
    CLUSTER_MEMORY_UTILISATION("cluster_memory_utilisation",""),
    CLUSTER_PVC_COUNT("cluster_pvc_count",""),
    CLUSTER_DISK_SIZE_CAPACITY("cluster_disk_size_capacity",""),
    CLUSTER_JOB_COUNT("cluster_job_count",""),
    CLUSTER_NODE_OFFLINE("cluster_node_offline",""),
    CLUSTER_HPA_COUNT("cluster_hpa_count",""),
    CLUSTER_LOAD15("cluster_load15",""),
    CLUSTER_POD_ABNORMAL_COUNT("cluster_pod_abnormal_count",""),
    CLUSTER_POD_QUOTA("cluster_pod_quota",""),
    CLUSTER_INGRESSES_EXTENSIONS_COUNT("cluster_ingresses_extensions_count",""),
    CLUSTER_POD_RUNNING_COUNT("cluster_pod_running_count",""),
    CLUSTER_NET_BYTES_RECEIVED("cluster_net_bytes_received",""),
    CLUSTER_CRONJOB_COUNT("cluster_cronjob_count",""),
    CLUSTER_MEMORY_USAGE_WO_CACHE("cluster_memory_usage_wo_cache",""),
    CLUSTER_DISK_READ_THROUGHPUT("cluster_disk_read_throughput",""),
    CLUSTER_NODE_OFFLINE_RATIO("cluster_node_offline_ratio",""),
    CLUSTER_POD_ABNORMAL_RATIO("cluster_pod_abnormal_ratio",""),
    CLUSTER_DISK_WRITE_THROUGHPUT("cluster_disk_write_throughput",""),
    CLUSTER_MEMORY_AVAILABLE("cluster_memory_available",""),
    CLUSTER_NODE_TOTAL("cluster_node_total",""),
    CLUSTER_STATEFULSET_COUNT("cluster_statefulset_count",""),
    CLUSTER_POD_COUNT("cluster_pod_count",""),
    CLUSTER_DISK_SIZE_USAGE("cluster_disk_size_usage",""),
    CLUSTER_PV_COUNT("cluster_pv_count",""),
    CLUSTER_DISK_SIZE_UTILISATION("cluster_disk_size_utilisation",""),
    CLUSTER_DEPLOYMENT_COUNT("cluster_deployment_count",""),
    CLUSTER_ENDPOINT_COUNT("cluster_endpoint_count",""),
    CLUSTER_CPU_USAGE("cluster_cpu_usage",""),
    CLUSTER_DISK_INODE_USAGE("cluster_disk_inode_usage",""),
    CLUSTER_NAMESPACE_COUNT("cluster_namespace_count",""),
    CLUSTER_LOAD5("cluster_load5",""),
    CLUSTER_LOAD1("cluster_load1",""),
    CLUSTER_DISK_WRITE_IOPS("cluster_disk_write_iops",""),
    CLUSTER_SECRET_COUNT("cluster_secret_count",""),
    CLUSTER_MEMORY_TOTAL("cluster_memory_total",""),
    CLUSTER_DISK_READ_IOPS("cluster_disk_read_iops",""),
    CLUSTER_POD_UTILISATION("cluster_pod_utilisation",""),
    CLUSTER_DISK_SIZE_AVAILABLE("cluster_disk_size_available",""),
    CLUSTER_DISK_INODE_UTILISATION("cluster_disk_inode_utilisation",""),
    CLUSTER_NODE_ONLINE("cluster_node_online",""),
    CLUSTER_REPLICASET_COUNT("cluster_replicaset_count",""),
    CLUSTER_SERVICE_COUNT("cluster_service_count",""),
    CLUSTER_DISK_INODE_TOTAL("cluster_disk_inode_total",""),
    CLUSTER_POD_SUCCEEDED_COUNT("cluster_pod_succeeded_count",""),

    ;

    private String sign;

    private String description;

    Cluster(String sign, String description) {
        this.sign = sign;
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Cluster getCluster(String sign) {
        for (Cluster cluster : values()) {
            if (cluster.sign.equals(sign)) {
                return cluster;
            }
        }
        return null;
    }
}
