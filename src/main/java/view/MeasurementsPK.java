package view;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class MeasurementsPK implements Serializable {
    private String stationId;
    private Date date;
    private Time hour;

    @Column(name = "station_id")
    @Id
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Column(name = "date")
    @Id
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "hour")
    @Id
    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementsPK that = (MeasurementsPK) o;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(hour, that.hour);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stationId, date, hour);
    }
}
