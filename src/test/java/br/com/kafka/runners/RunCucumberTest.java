package br.com.kafka.runners;

import br.com.kafka.adapters.clients.JSONPlaceHolderClient;
import br.com.kafka.config.Config;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;


@EnableFeignClients(clients = JSONPlaceHolderClient.class)
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("br.com.kafka")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ActiveProfiles("test")
public class RunCucumberTest {


}
