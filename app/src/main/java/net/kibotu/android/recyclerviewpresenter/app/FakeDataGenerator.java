package net.kibotu.android.recyclerviewpresenter.app;

import java.util.Random;

import static java.text.MessageFormat.format;

class FakeDataGenerator {

    static String createRandomImageUrl() {
        boolean landscape = new Random().nextBoolean();
        return format("http://lorempixel.com/{0}/{1}/", landscape ? 400 : 200, landscape ? 200 : 400);
    }
}
