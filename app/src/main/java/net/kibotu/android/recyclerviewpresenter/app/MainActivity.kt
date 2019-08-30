package net.kibotu.android.recyclerviewpresenter.app


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.kibotu.android.recyclerviewpresenter.app.java.PresenterActivity
import net.kibotu.android.recyclerviewpresenter.app.nested.NestedActivity
import net.kibotu.android.recyclerviewpresenter.app.pagination.PaginationActivity
import net.kibotu.android.recyclerviewpresenter.app.v2.V2Activity

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        runJavaImplementation()
//        runKotlinImplementation()
//        runPagination()
//        runV2Implementation()
        runNested()
    }

    private fun runPagination() = startActivity(Intent(this, PaginationActivity::class.java))

    private fun runKotlinImplementation() = startActivity(Intent(this, net.kibotu.android.recyclerviewpresenter.app.kotlin.PresenterActivity::class.java))

    private fun runJavaImplementation() = startActivity(Intent(this, PresenterActivity::class.java))

    private fun runV2Implementation() = startActivity(Intent(this, V2Activity::class.java))

    private fun runNested() = startActivity(Intent(this, NestedActivity::class.java))
}