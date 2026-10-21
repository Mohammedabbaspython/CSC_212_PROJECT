public class DateTime implements IDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateTime(int year, int month, int day, int hour, int minute) {
        if (month < 1 || month > 12 || day < 1 || day > 31 || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid date/time values.");
        }
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructor takes string "MM/DD/YYYY HH:MM" to make DateTime object.
     */
    public DateTime(String dateTimeStr) {
        if (dateTimeStr == null) {
            throw new IllegalArgumentException("Date/Time string cannot be null.");
        }
        String[] parts = dateTimeStr.split(" ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Expected format: MM/DD/YYYY HH:MM");
        }
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");
        if (dateParts.length != 3 || timeParts.length != 2) {
            throw new IllegalArgumentException("Expected format: MM/DD/YYYY HH:MM");
        }
        int m=Integer.parseInt(dateParts[0]);
        int d=Integer.parseInt(dateParts[1]);
        int y=Integer.parseInt(dateParts[2]);
        int h=Integer.parseInt(timeParts[0]);
        int min=Integer.parseInt(timeParts[1]);
        if (m < 1 || m > 12 || d < 1 || d > 31 || h < 0 || h > 23 || min < 0 || min > 59) {
            throw new IllegalArgumentException("Invalid date/time values.");
        }
        this.month = m;
        this.day = d;
        this.year = y;
        this.hour = h;
        this.minute = min;
        
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    @Override
    public int compareTo(IDateTime other) {
        if (this.year != other.getYear()) {
            return this.year - other.getYear();
        }
        if (this.month != other.getMonth()) {
            return this.month - other.getMonth();
        }
        if (this.day != other.getDay()) {
            return this.day - other.getDay();
        }
        if (this.hour != other.getHour()) {
            return this.hour - other.getHour();
        }
        return this.minute - other.getMinute();
    }

    @Override
    public String toString() {
        return format();
    }
}
