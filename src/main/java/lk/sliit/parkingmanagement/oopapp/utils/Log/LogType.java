package lk.sliit.parkingmanagement.oopapp.utils.Log;

public enum LogType {
    NOTE("📝","NOTE", AnsiColor.WHITE),
    INFO("ℹ️","INFO", AnsiColor.CYAN),
    SUCCESS("✅","SUCCESS", AnsiColor.GREEN),
    WARN("⚠️","WARNING", AnsiColor.YELLOW),
    ERROR("⨉","ERROR", AnsiColor.RED);

    public final String icon;
    public final String label;
    public final String color;

    LogType(String icon, String label, String color) {
        this.icon = icon;
        this.label = label;
        this.color = color;
    }
}

