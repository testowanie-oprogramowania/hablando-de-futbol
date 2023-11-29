package com.testowanie.football.scenarios;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        features = "src/test/resources/features",
        glue = "com.testowanie.football.scenarios"
)
public class CucumberTest {

}
