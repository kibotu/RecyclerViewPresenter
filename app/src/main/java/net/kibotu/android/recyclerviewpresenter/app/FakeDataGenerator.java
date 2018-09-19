package net.kibotu.android.recyclerviewpresenter.app;

import java.util.Random;

import static java.text.MessageFormat.format;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

class FakeDataGenerator {

    static String createRandomImageUrl() {
        boolean landscape = new Random().nextBoolean();
        return format("https://picsum.photos/{0}/{1}/", landscape ? 400 : 200, landscape ? 200 : 400);
    }
}
