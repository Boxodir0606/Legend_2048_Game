package uz.boxodir.test2048game.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import uz.boxodir.test2048game.R

class MainActivity : AppCompatActivity() {

    private lateinit var playgame: AppCompatButton
    private lateinit var infoScreen: AppCompatButton
    private var clickItem: AppCompatButton? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickItem = null

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.start)


        infoScreen = findViewById(R.id.infoScreen)
        playgame = findViewById(R.id.button)


        playgame.setOnClickListener {
            if (clickItem == null) {
                clickItem = playgame.findViewById(R.id.button)
                playgame.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        playgame.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clickItem = null
                                startActivity(Intent(this, PlayActivity::class.java))
                                Log.d("TTT","playga o'tdi")
                            }.start()
                    }.start()
            }
        }

        infoScreen.setOnClickListener {
            if (clickItem == null) {
                clickItem = infoScreen.findViewById(R.id.info)
                infoScreen.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        infoScreen.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clickItem = null
                                startActivity(Intent(this, InfoActivity::class.java))
                            }.start()
                    }.start()
            }

        }
    }
}