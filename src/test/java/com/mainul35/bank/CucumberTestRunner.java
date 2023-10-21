package com.mainul35.bank;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your feature files
        glue = "com.mainul35.bank.test.steps", // Path to your step definition files
        plugin = {"pretty", "html:target/cucumber-report.html"} // Configure reporting
)
public class CucumberTestRunner {
}
