package model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@IdClass(MeasurementsPK.class)
public class Measurements {
    private String stationId;
    private String station;
    private Date date;
    private Time hour;
    private Float temperature;
    private Integer windSpeed;
    private Integer windDirection;
    private Float relativeHumidity;
    private Double totalRainfall;
    private Float pressure;

    @Id
    @Column(name = "station_id")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Basic
    @Column(name = "station")
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Id
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    @Column(name = "hour")
    public Time getHour() {
        return hour;
    }

    public void setHour(Time hour) {
        this.hour = hour;
    }

    @Basic
    @Column(name = "temperature")
    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    @Basic
    @Column(name = "wind_speed")
    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Basic
    @Column(name = "wind_direction")
    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    @Basic
    @Column(name = "relative_humidity")
    public Float getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(Float relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    @Basic
    @Column(name = "total_rainfall")
    public Double getTotalRainfall() {
        return totalRainfall;
    }

    public void setTotalRainfall(Double totalRainfall) {
        this.totalRainfall = totalRainfall;
    }

    @Basic
    @Column(name = "pressure")
    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurements that = (Measurements) o;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(station, that.station) &&
                Objects.equals(date, that.date) &&
                Objects.equals(hour, that.hour) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(windSpeed, that.windSpeed) &&
                Objects.equals(windDirection, that.windDirection) &&
                Objects.equals(relativeHumidity, that.relativeHumidity) &&
                Objects.equals(totalRainfall, that.totalRainfall) &&
                Objects.equals(pressure, that.pressure);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stationId, station, date, hour, temperature, windSpeed, windDirection, relativeHumidity, totalRainfall, pressure);
    }
}
