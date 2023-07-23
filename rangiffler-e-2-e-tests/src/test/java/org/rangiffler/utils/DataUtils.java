package org.rangiffler.utils;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class DataUtils {

    private static final Faker faker = new Faker();

    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }

    public static String generateRandomName() {
        return faker.name().firstName();
    }

    public static String generateRandomSurname() {
        return faker.name().lastName();
    }

    public static String addImageByClassPath(String imageClasspath) {
        ClassLoader classLoader = DataUtils.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(imageClasspath)) {
            assert is != null;
            String fileExtension = imageClasspath.substring(imageClasspath.lastIndexOf(".") + 1);
            byte[] base64Image = Base64.getEncoder().encode(is.readAllBytes());
            return "data:image/" + fileExtension + ";base64," + new String(base64Image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
