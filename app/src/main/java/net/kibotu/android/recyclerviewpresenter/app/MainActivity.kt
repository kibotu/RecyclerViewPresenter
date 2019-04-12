package net.kibotu.android.recyclerviewpresenter.app


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.kibotu.android.recyclerviewpresenter.app.java.PresenterActivity
import net.kibotu.android.recyclerviewpresenter.app.pagination.PaginationActivity

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        runJavaImplementation()
        runKotlinImplementation()
//        runPagination()
    }

    private fun runPagination() = startActivity(Intent(this, PaginationActivity::class.java))

    private fun runKotlinImplementation() = startActivity(Intent(this, net.kibotu.android.recyclerviewpresenter.app.kotlin.PresenterActivity::class.java))

    private fun runJavaImplementation() = startActivity(Intent(this, PresenterActivity::class.java))
}