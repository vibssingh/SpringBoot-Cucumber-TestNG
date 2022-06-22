package com.example.demo.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/Features"}, glue = {"com.example.demo.definitions"})
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {

}