package com.gginon.setup;

import lombok.Value;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldTest {

    @Test
    @Tag("FirstSubmissionTest")
    void helloLabSubmission() {
        HelloWorld helloWorld = new HelloWorld("It works!");

        assertThat(helloWorld.getMessage()).isEqualTo("It works!");
    }

    @Value
    private class HelloWorld {
        String message;
    }

}
