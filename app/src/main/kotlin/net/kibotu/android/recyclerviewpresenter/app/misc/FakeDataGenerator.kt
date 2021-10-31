@file:JvmName("FakeDataGenerator")

package net.kibotu.android.recyclerviewpresenter.app.misc

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.text.MessageFormat.format
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * Created by Nyaruhodo on 07.05.2016.
 */

fun createRandomImageUrl(): String {

    val landscape = Random.nextBoolean()
    val endpoint = false // randomBoolean()

    val width = randomRange(300, 400)
    val height = randomRange(200, 300)

    return format(
        if (endpoint)
            "https://lorempixel.com/{0}/{1}/"
        else
            "https://picsum.photos/{0}/{1}/",
        if (landscape) width else height, if (landscape) height else width
    )
}

@JvmOverloads
fun generateRandomColorDrawable(alpha: Int = 255, red: Int = 255, green: Int = 255, blue: Int = 255): ColorDrawable = ColorDrawable(generateRandomColor(alpha, red, green, blue))

@JvmOverloads
fun generateRandomColor(alpha: Int = 255, red: Int = 255, green: Int = 255, blue: Int = 255): Int {

    var r = Random.nextInt(256)
    var g = Random.nextInt(256)
    var b = Random.nextInt(256)

    // mix the color
    r = ((r + red) / 2f).roundToInt()
    g = ((g + green) / 2f).roundToInt()
    b = ((b + blue) / 2f).roundToInt()

    return Color.argb(alpha, r, g, b)
}

fun randomRange(start: Int = 0, end: Int): Int = (Random.nextFloat() * (end - start) + start).roundToInt()