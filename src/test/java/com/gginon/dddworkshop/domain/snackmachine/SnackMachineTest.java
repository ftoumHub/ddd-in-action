package com.gginon.dddworkshop.domain.snackmachine;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = "src/test/resources/com/gginon/dddworkshop/domain/snackmachine/SnackMachine.feature",
        monochrome = false, plugin = {"pretty", "html:target/cucumber"})
public class SnackMachineTest {

}
