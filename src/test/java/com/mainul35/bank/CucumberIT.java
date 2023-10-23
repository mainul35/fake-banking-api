package com.mainul35.bank;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        tags = "",
//        dryRun = true,
        features = "src/test/resources/features",
        glue = "com.mainul35.bank.glue"
)
public class CucumberIT {
}
