package com.trello.api;

import com.trello.test.TrelloTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static io.restassured.RestAssured.given;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TrelloTest.class);
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "off");

        for (Failure fail : result.getFailures()) {
            System.out.println(fail.toString());
        }
        given().log().all();


        System.out.println(result.wasSuccessful());
    }
}
