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
            controller.downloadData();
        }
        catch(Exception e)
        {
            System.out.println();
        }
    }
}