package nl.thewally.templates;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format={ "pretty",
                "html:target/report", "json:target/report/cucumber-json-report.json" },
        features="classpath:features",
        tags = {})

public class Runner {
}