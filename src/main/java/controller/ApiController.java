package controller;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import model.*;
import hibernate.*;

@Controller
/**
 * kontroler do pobierania danych pogodowych z API IMGW
 */
public class ApiController
{
    /**
     * Pobiera dane pogodowe z api IMGW z wszystkich dostepnych miast
     * i zapisuje do tabeli w bazie
     */
    public void downloadData()
    {
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();

            JSONArray json = readJsonFromUrl("https://danepubliczne.imgw.pl/api/data/synop/format/json");
            for(int i =0; i<json.length(); i++)
            {
                JSONObject obj = (JSONObject) json.get(i);

                //ten if- zeby bledow nie wywalalo gdy cos jest nulem
                if (obj.get("id_stacji") != JSONObject.NULL
                        && obj.get("stacja") != JSONObject.NULL
                        && obj.get("godzina_pomiaru") != JSONObject.NULL
                        && obj.get("data_pomiaru") != JSONObject.NULL)
                {
                    //sprawdzamy czy istnieje juz taki wpis w bazie
                    Query query = session.createQuery("from Measurements where station = :station AND date = :date AND hour = :hour");
                    query.setParameter("station", obj.getString("stacja"));
                    query.setParameter("date", java.sql.Date.valueOf(obj.getString("data_pomiaru")));

                    String s = obj.getString("godzina_pomiaru");
                    SimpleDateFormat sdf = new SimpleDateFormat("H");
                    long ms = sdf.parse(s).getTime();
                    Time t = new Time(ms);
                    query.setParameter("hour", t);

                    List<Measurements> list = query.list();
                    //jesli lista pusta to znaczy ze nie ma jeszcze takiego wpisu w bazie i mozemy go dodac
                    if(list.isEmpty())
                    {
                        //zapis do bazy
                        session.beginTransaction();

                        //Add new object
                        Measurements measurements = new Measurements();
                        measurements.setStationId(obj.getString("id_stacji"));
                        measurements.setStation(obj.getString("stacja"));
                        measurements.setDate(java.sql.Date.valueOf(obj.getString("data_pomiaru")));
                        measurements.setHour(t);

                        if (obj.get("cisnienie") != JSONObject.NULL)
                        {
                            measurements.setPressure(obj.getFloat("cisnienie"));
                        }
                        if (obj.get("wilgotnosc_wzgledna") != JSONObject.NULL)
                        {
                            measurements.setRelativeHumidity(obj.getFloat("wilgotnosc_wzgledna"));
                        }
                        if (obj.get("temperatura") != JSONObject.NULL)
                        {
                            measurements.setTemperature(obj.getFloat("temperatura"));
                        }
                        if (obj.get("suma_opadu") != JSONObject.NULL)
                        {
                            measurements.setTotalRainfall(obj.getDouble("suma_opadu"));
                        }
                        if (obj.get("kierunek_wiatru") != JSONObject.NULL)
                        {
                            measurements.setWindDirection(obj.getInt("kierunek_wiatru"));
                        }
                        if (obj.get("predkosc_wiatru") != JSONObject.NULL)
                        {
                            measurements.setWindSpeed(obj.getInt("predkosc_wiatru"));
                        }

                        //Save in database
                        session.save(measurements);

                        //Commit the transaction
                        session.getTransaction().commit();
                    }
                }
            }
            session.close();
            //HibernateUtil.shutdown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONArray readJsonFromUrl(String url) throws IOException
    {
        // String s = URLEncoder.encode(url, "UTF-8");
        // URL url = new URL(s);
        InputStream is = new URL(url).openStream();
        JSONArray json = null;
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONArray(jsonText);
        } catch (JSONException e)
        {
            e.printStackTrace();
        } finally
        {
            is.close();
        }
        return json;
    }
}
