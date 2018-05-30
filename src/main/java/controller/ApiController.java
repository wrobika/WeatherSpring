package controller;

import org.springframework.stereotype.Controller;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.json.*;

@Controller
/**
 * kontroler do pobierania danych pogodowych z API IMGW
 */
public class ApiController
{
    private List<String> dataNames = Arrays.asList(
            "data_pomiaru", "godzina_pomiaru", "temperatura", "predkosc_wiatru", "kierunek_wiatru",
            "wilgotnosc_wzgledna", "suma_opadu", "cisnienie");

    /**
     * Pobiera dane pogodowe z api IMGW z danego miasta
     * @param cityName nazwa miasta, z ktorego chcemy wziac dane (z malej litery, bez polskich liter, dwuczlonowe nazwy pisane razem)
     * @return HashMap z parametrami pogody w danym miescie
     * @throws Exception
     */
    public HashMap downloadData(String cityName) throws Exception
    {
        try
        {
            URL url = new URL("https://danepubliczne.imgw.pl/api/data/synop/station/" + cityName + "/format/json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            InputStream is = con.getInputStream();
            JsonReader jsonReader = Json.createReader(is);
            JsonObject dataObj = jsonReader.readObject();

            HashMap weatherData = new HashMap<String, String>();
            for (String name: dataNames)
            {
                weatherData.put(name, dataObj.getString(name));
                System.out.println(name + ": " + dataObj.getString(name));
            }

            jsonReader.close();
            con.disconnect();
            return weatherData;
        }
        catch(Exception e)
        {
            System.out.println(e.getClass());
            throw e;
        }

        //Airly
        //            URL url = new URL("https://airapi.airly.eu/v1/mapPoint/measurements?latitude=50.06&longitude=19.93");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setRequestProperty("Content-Type", "application/json");
//            con.setRequestProperty("Accept", "application/json");
//            con.setRequestProperty("apikey", "ASvjtvSqUqXBt6cAESJYcdvyN0Ticp5o");
//
//            InputStream is = con.getInputStream();
//            JsonReader jsonReader = Json.createReader(is);
//            JsonObject obj = jsonReader.readObject();
//            JsonObject dataObj = obj.getJsonObject("currentMeasurements");
//
//            HashMap<String, Double> weatherData = new HashMap<>();
//            for (String name: dataNames)
//            {
//                weatherData.put(name, dataObj.getJsonNumber(name).doubleValue());
//                System.out.println(name + ": " + dataObj.getJsonNumber(name));
//            }
//            jsonReader.close();
//            con.disconnect();
//            return weatherData;
    }
}
