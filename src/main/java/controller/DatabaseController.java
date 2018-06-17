package controller;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import model.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * kontroler do pobierania danych z bazy
 */
@Controller
public class DatabaseController
{
    /**
     * pobiera pogode archiwalna z bazy
     * przyklad:
     * http://localhost:8080/pogodaArchiwalna?station=Jelenia%20G%C3%B3ra&date=2018-06-15&hour=17:00:00
     * @param station nazwa stacji
     * @param date data w formacie YYYY-MM-DD
     * @param hour godzina w formacie HH:00:00
     * @return
     */
    @GetMapping("/pogodaArchiwalna")
    public ModelAndView readMeasurements(@RequestParam(name="station", required=false, defaultValue="Kraków") String station, Date date, Time hour)
    {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Measurements where station = :station AND date = :date AND hour = :hour");
        query.setParameter("station", station);
        query.setParameter("date", date);
        query.setParameter("hour", hour);
        List<Measurements> list = query.list();
        session.close();

        if(list.isEmpty())
            return null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("measurements", list.get(0));
        modelAndView.setViewName("/pogodaArchiwalna");
        return modelAndView;

        //HibernateUtil.shutdown();
    }

    /**
     * pobiera pogode aktualna (ostatnia dodana do bazy)
     * przyklad:
     * http://localhost:8080/pogoda?station=Warszawa
     * @param station nazwa stacji
     * @return
     */
    @GetMapping("/pogoda")
    public ModelAndView readMeasurements(@RequestParam(name="station", required=false, defaultValue="Kraków") String station)
    {
        //pobiera nowe dane do bazy jesli sa dostepne
        new ApiController().downloadData();

        //czyta dane z bazy
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Measurements where station = :station");
        query.setParameter("station", station);
        List<Measurements> list = query.list();
        session.close();

        if(list.isEmpty())
            return null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("measurements", list.get(list.size()-1));
        modelAndView.setViewName("/pogoda");
        return modelAndView;
    }

    /**
     * pobiera liste stacji pomiarowych i przechodzi do formularza wyboru archiwalnego pomiaru
     * @return
     */
    @GetMapping("/wybierzArchiwalnyPomiar")
    public ModelAndView chooseArchiwalMeasurements()
    {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select m.station from Measurements m where m.station is not null" );
        List<String> list = query.list();
        session.close();

        ModelAndView modelAndView = new ModelAndView("wybierzArchiwalnyPomiar");
        modelAndView.addObject("measurementsList",list);
        modelAndView.setViewName("/wybierzArchiwalnyPomiar");
        return modelAndView;
    }

    /**
     * pobiera z formularza wybor archiwalnej probki i dostarcza go metodzie ktora pobierze odpowiednie dane
     * @param request
     * @return
     */
    @RequestMapping(value = "/wybierzArchiwalnyPomiar", method = RequestMethod.POST)
    public ModelAndView chooseArchiwalMeasurements(HttpServletRequest request)
    {
        try{

            String station = request.getParameter("station");
            String date = request.getParameter("date");
            String time = request.getParameter("time").replaceAll(":[0-9]{2}", ":00:00");

            ModelAndView modelAndView = new ModelAndView("redirect:/pogodaArchiwalna");
            modelAndView.addObject("station",station);
            modelAndView.addObject("date", date);
            modelAndView.addObject("hour",time);
            return modelAndView;

        } catch(Exception e) {

            ModelAndView modelAndView = new ModelAndView("redirect:/wybierzArchiwalnyPomiar");
            return modelAndView;

        }
    }

    /**
     * pobiera liste stacji pomiarowych i przechodzi do formularza wyboru aktualnego pomiaru
     * @return
     */
    @GetMapping("/wybierzPomiar")
    public ModelAndView chooseMeasurements()
    {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("select m.station from Measurements m where m.station is not null" );
        List<String> list = query.list();
        session.close();

        ModelAndView modelAndView = new ModelAndView("wybierzPomiar");
        modelAndView.addObject("measurementsList",list);
        modelAndView.setViewName("/wybierzPomiar");
        return modelAndView;
    }

    /**
     * pobiera z formularza wybor aktualnej probki i dostarcza go metodzie ktora pobierze odpowiednie dane
     * @param request
     * @return
     */
    @RequestMapping(value = "/wybierzPomiar", method = RequestMethod.POST)
    public ModelAndView chooseMeasurements(HttpServletRequest request)
    {
        String station = request.getParameter("station");

        ModelAndView modelAndView = new ModelAndView("redirect:/pogoda");
        modelAndView.addObject("station",station);
        return modelAndView;
    }

}
