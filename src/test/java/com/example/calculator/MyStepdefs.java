package com.example.calculator;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = TestConfig.class)
public class MyStepdefs {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String SINGLE_REQUEST_TEMPLATE = "{\"firstNumber\":%s, \"secondNumber\": %s}";

    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private String baseUrl;

    private int firstNumber;
    private int secondNumber;
    private int singleResult;

    @Given("^I have two numbers that are (\\d+) and (\\d+)$")
    public void iHaveTwoNumbersThatAreAnd(int firstNumber, int secondNumber) throws Throwable {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    @When("^I ask the service to '(\\w+)' them$")
    public void iAskTheServiceToAddThem(String operation) throws Throwable {
        RequestBody body = RequestBody.create(JSON, String.format(SINGLE_REQUEST_TEMPLATE, firstNumber, secondNumber));
        Request request = new Request.Builder()
                .url(baseUrl + "/" + operation)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        assertNotNull("Response body was null!", response.body());
        singleResult = JsonPath.read(response.body().string(), "$.result");
    }

    @Then("^the result should be (-?\\d+)$")
    public void theResultShouldBe(int expectedResult) throws Throwable {
        assertEquals(expectedResult, singleResult);
    }
}
