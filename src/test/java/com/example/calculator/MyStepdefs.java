package com.example.calculator;

import com.jayway.jsonpath.JsonPath;
import cucumber.api.DataTable;
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

import java.util.List;

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

    private RequestBody batchRequest;
    private List<Integer> resultList;

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

    @When("^I ask the service to '(\\w+)' them in batch$")
    public void iAskTheServiceToAddThemInBatch(String operation) throws Throwable {
        Request request = new Request.Builder()
                .url(baseUrl + "/batch/" + operation)
                .post(batchRequest)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        assertNotNull("Response body was null!", response.body());
        String x = response.body().string();
        resultList = JsonPath.read(x, "$..result");
    }

    @Then("^the result should be (-?\\d+)$")
    public void theResultShouldBe(int expectedResult) throws Throwable {
        assertEquals(expectedResult, singleResult);
    }

    @Given("^I have the following pairs of numbers to (add|subtract|divide|multiply):")
    public void iHaveTheFollowingSetOfNumbersToAddTogether(String operation, DataTable dataTable) throws Throwable {
        StringBuilder payload = new StringBuilder("[");
        dataTable.getGherkinRows().forEach(row -> {
            payload.append(String.format("{\"firstNumber\":%s,\"secondNumber\":%s},", row.getCells().get(0), row.getCells().get(1)));
        });
        payload.append("]");
        batchRequest = RequestBody.create(JSON, payload.toString().replaceAll(",]", "]"));
    }

    @Then("^the results should be:$")
    public void theResultsShouldBe(List<Integer> expectedResults) throws Throwable {
        for (int i = 0; i < expectedResults.size(); i++) {
            assertEquals(expectedResults.get(i), resultList.get(i));
        }
    }
}
