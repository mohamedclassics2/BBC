package com.bbc.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class ApiStepDefs {
    String url = "https://testapi.io/api/ottplatform/";
    Response response;
    JSONObject obj;
    JSONArray arr;


    @Given("user sends a GET request to {string} endpoint")
    public void user_sends_a_GET_request_to_endpoint(String endpoint) {
        response = given().accept(ContentType.JSON)
                .when()
                .get(url + endpoint);

    }

    @Then("response status code is {int}")
    public void response_status_code_is(int expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("response time is less than {int} milliseconds")
    public void response_time_is_less_than_milliseconds(int expectedResponseTime) {
        System.out.println("expectedResponseTime = " + expectedResponseTime);
        System.out.println("response.getTime() = " + response.getTime());
        boolean responseTime = response.getTime() < expectedResponseTime + 1500;
        System.out.println("responseTime = " + responseTime);

        Assert.assertTrue(responseTime);
    }

    @Then("id field is populated")
    public void id_field_is_populated() throws JSONException {
        String jsonstring = response.asString();
        obj = new JSONObject(jsonstring);
        arr = obj.getJSONArray("data");
        System.out.println("arr.length() = " + arr.length());

        for (int i = 0; i < arr.length(); i++) {
            System.out.println("arr.getJSONObject(" + i + ").getString(\"id\") = " + arr.getJSONObject(i).getString("id"));
            Assert.assertFalse(arr.getJSONObject(i).getString("id").isEmpty());
        }
    }

    @Then("{string} is always {string}")
    public void contains(String field, String word) throws JSONException {
        for (int i = 0; i < arr.length(); i++) {
            System.out.println("arr.getJSONObject(i).getString(\"segment_type\") = " + arr.getJSONObject(i).getString(field));
            Assert.assertTrue(arr.getJSONObject(i).getString(field).equalsIgnoreCase(word));
        }
    }

    @Then("{string} field is not empty")
    public void field_is_not_empty(String field) throws JSONException {
        String jsonstring = response.asString();
        obj = new JSONObject(jsonstring);
        arr = obj.getJSONArray("data");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject titleObject = new JSONObject(arr.getJSONObject(i).getString("title_list"));
            System.out.println(i + ": " + titleObject.getString(field));
            Assert.assertFalse(titleObject.getString(field).isEmpty());

        }
    }

    @Then("only one track is playing")
    public void only_one_track_is_playing() throws JSONException {
        String jsonstring = response.asString();
        obj = new JSONObject(jsonstring);
        arr = obj.getJSONArray("data");
        int countOfTrue=0;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject offsetObj = new JSONObject(arr.getJSONObject(i).getString("offset"));
            ArrayList<Object> tracks = new ArrayList<Object>();
            tracks.add(offsetObj.get("now_playing"));

            for (Object track : tracks) {
                if (track.toString().equalsIgnoreCase("True"))
                    countOfTrue++;
            }
        }
        System.out.println("countOfTrue = "  + countOfTrue);
        Assert.assertEquals(1, countOfTrue);

    }
    @Then("{string} value is displayed in Headers")
    public void value_is_displayed_in_Headers(String field) {
        System.out.println(response.header("Date"));
        Assert.assertTrue(response.header("date").equalsIgnoreCase("Fri, 21 May"));
    }
}