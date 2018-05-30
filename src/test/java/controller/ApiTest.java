package controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Autowired
    private ApiController controller;

    @Test
    public void contexLoads() throws Exception
    {
        assertThat(controller).isNotNull();
    }

    @Test
    public void downloadData()
    {
        try
        {
            assertThat(controller.downloadData("krakow")).isNotNull();
        }
        catch(Exception e)
        {
            System.out.println();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void exceptionWhileDownloadData() throws Exception
    {
        try
        {
            controller.downloadData("blednanazwa");
        }
        catch(Exception e)
        {
            System.out.println();
            throw e;
        }
    }
}