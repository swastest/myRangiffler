package org.rangiffler.test.api.grpc.geo;


import guru.qa.grpc.rangiffler.grpc.Countries;
import guru.qa.grpc.rangiffler.grpc.Country;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.rangiffler.test.api.BaseGrpcTest;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.rangiffler.utils.testDoc.TestTag.*;

@DisplayName("gRPC: Countries")
public class GeoServiceTest extends BaseGrpcTest {

    @ParameterizedTest(name = "Get country info - find by code {0}")
    @AllureId("5001")
    @Tags({@Tag(API), @Tag(GRPC), @Tag(POSITIVE), @Tag(GEO)})
    @CsvSource(value = {
            "ID | Indonesia ",
            "AR | Argentina",
            "NO | Norway"
    },
            delimiter = '|')
    void getCountryByCodeTest(String code, String countryName) {
        Country response = countryGrpcClient.getCountryByCode(code);
        step("Assertions response", () -> {
            assertEquals(code, response.getCode(), "Verify code");
            assertEquals(countryName, response.getName(), "Verify name");
            assertNotEquals("", response.getUuid(), "Verify that id is not empty");
        });
    }

    @Test
    @AllureId("5002")
    @Tags({@Tag(API), @Tag(GRPC), @Tag(POSITIVE), @Tag(GEO)})
    @DisplayName("Check count countries")
    void getAllCountries() {
        Countries response = countryGrpcClient.getAllCountries();
        step("Assertions response", () -> {
            assertEquals(175, response.getAllCountriesList().size());
        });
    }
}
