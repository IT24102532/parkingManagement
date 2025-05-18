package lk.sliit.parkingmanagement.oopapp.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class AdminLogs {
    @SerializedName("log_id")
    private String logId;
    @SerializedName("admin")
    private String adminId;
    @SerializedName("action")
    private String action;
    @SerializedName("time")
    private LocalDateTime time;

    public AdminLogs() {}
    public AdminLogs(String adminId, String action) {
        this.logId = UUID.randomUUID().toString();
        this.adminId = adminId;
        this.action = action;
        this.time = LocalDateTime.now();
    }

    public String getLogId() {return logId;}
    public void setLogId(String logId) {this.logId = logId;}

    public String getAdminId() {return adminId;}
    public void setAdminId(String adminId) {this.adminId = adminId;}

    public String getAction() {return action;}
    public void setAction(String action) {this.action = action;}

    public LocalDateTime getTime() {return time;}
    public void setTime(LocalDateTime time) {this.time = time;}

    @Override
    public String toString() {
        return "Log {"
                + "logId='" + logId + '\''
                + ", adminId='" + adminId + '\''
                + ", action='" + action + '\''
                + ", time=" + time
                + "}";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminLogs log = (AdminLogs) o;
        return Objects.equals(logId, log.logId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId);
    }
}
