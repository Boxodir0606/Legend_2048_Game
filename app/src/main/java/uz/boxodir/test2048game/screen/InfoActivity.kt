package uz.boxodir.test2048game.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import uz.boxodir.test2048game.R

class InfoActivity : AppCompatActivity() {

    private var back: ImageView? = null
    private lateinit var goback:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        back = null

        goback = findViewById(R.id.back_menu)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.info)


        findViewById<View>(R.id.back_menu).setOnClickListener { v: View? ->

            if (back == null) {
                back = goback
                goback.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        goback.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }.start()
                    }.start()
            }
        }
    }
}