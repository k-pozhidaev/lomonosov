package io.pozhidaev.lomonosov;

import io.pozhidaev.lomonosov.task.Yad2CrawlerScheduledTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LomonosovApplicationTests {


	@MockBean
	Yad2CrawlerScheduledTask yad2CrawlerScheduledTask;


	@Test
	public void contextLoads() {
	}

}
