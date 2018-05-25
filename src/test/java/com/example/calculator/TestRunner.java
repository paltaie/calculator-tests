package com.example.calculator;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", glue = { "com.example.calculator"},format = { "pretty", "html:target/cucumber"})
public class TestRunner {
}
