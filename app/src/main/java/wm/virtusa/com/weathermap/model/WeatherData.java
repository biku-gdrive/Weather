package wm.virtusa.com.weathermap.model;

/**
 * Created by Bikash on 10/10/2017.
 */

public class WeatherData {

    Coordinates coord;
    Weather[] weather;
    String base;
    Main main;
    int visibility;
    Wind wind;
    Clouds clouds;
    long dt;
    public System sys;
    int id;
    String name;
    int cod;

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDate() {
        return dt;
    }

    public void setDate(long dt) {
        this.dt = dt;
    }

    public System getSys() {
        return sys;
    }

    public void setSys(System sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }


   public class Coordinates {
        Float lon;
        Float lat;

        public Float getLon() {
            return lon;
        }

        public void setLon(Float lon) {
            this.lon = lon;
        }

        public Float getLat() {
            return lat;
        }

        public void setLat(Float lat) {
            this.lat = lat;
        }
    }

    public class Weather {
        int id;
        String main;
        String description;
        String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Main {
        Float temp;
        int pressure;
        int humidity;
        Float temp_min;
        Float temp_max;

        public Float getTemp() {
            return temp;
        }

        public void setTemp(Float temp) {
            this.temp = temp;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public Float getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(Float temp_min) {
            this.temp_min = temp_min;
        }

        public Float getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(Float temp_max) {
            this.temp_max = temp_max;
        }
    }

    public class Wind {
        Float speed;
        Float deg;

        public Float getSpeed() {
            return speed;
        }

        public void setSpeed(Float speed) {
            this.speed = speed;
        }

        public Float getDeg() {
            return deg;
        }

        public void setDeg(Float deg) {
            this.deg = deg;
        }
    }

    public class Clouds {
        int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }
    }
    class System {
        int type;
        int id;
        String message;
        String country;
        long sunrise;
        long sunset;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }
    }
}
