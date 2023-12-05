package uz.boxodir.test2048game.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import uz.boxodir.test2048game.R
import uz.boxodir.test2048game.model.SideEnum
import uz.boxodir.test2048game.repository.AppRepository
import uz.boxodir.test2048game.settings.Settings
import uz.boxodir.test2048game.utils.BackgroundUtil
import uz.boxodir.test2048game.utils.MyTouchListener


class PlayActivity : AppCompatActivity() {

    private val items: MutableList<TextView> = ArrayList(16)
    private lateinit var mainView: LinearLayoutCompat
    private var clic: ImageView? = null
    private var clicRestartDialog: AppCompatButton? = null
    private var repository = AppRepository.getInstance()
    private var settings: Settings = Settings.getInstance()
    private val util = BackgroundUtil()
    private lateinit var level: TextView
    private var record = 0
    private var clickBackCount = 0
    private lateinit var home_icon: ImageView


    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clic = null
        clicRestartDialog = null

        setContentView(R.layout.activity_play)
        setContentView(R.layout.activity_play)
        level = findViewById(R.id.txt_score)
        mainView = findViewById(R.id.mainView)
        home_icon = findViewById(R.id.home_icon)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.gamescreen)



        loadViews()
        describeMatrixToViews()
        loadTochLisener()
        funtions()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadTochLisener() {

        if (clickBackCount == 1 || !repository.isPlaying()) {
            findViewById<ImageView>(R.id.back).alpha = 0.2f
            findViewById<ImageView>(R.id.back).isClickable = false
        }


        val myTouchListener = MyTouchListener(this)
        myTouchListener.setMyMovementSideListener {

            findViewById<ImageView>(R.id.back).alpha = 1f
            findViewById<ImageView>(R.id.back).isClickable = true
            clickBackCount = 0


            when (it) {

                SideEnum.DOWN -> {
                    if (!repository.isClickable()) {
                        openGameOverDialog()
                    }
                    repository.setState(true)
                    repository.moveDown()
                    describeMatrixToViews()
                }

                SideEnum.UP -> {
                    if (!repository.isClickable()) {
                        openGameOverDialog()
                    }
                    repository.setState(true)
                    repository.moveUp()
                    describeMatrixToViews()
                }

                SideEnum.RIGHT -> {
                    if (!repository.isClickable()) {
                        openGameOverDialog()
                    }
                    repository.setState(true)
                    repository.moveToRight()
                    describeMatrixToViews()
                }

                SideEnum.LEFT -> {
                    if (!repository.isClickable()) {
                        openGameOverDialog()
                    }

                    repository.setState(true)
                    repository.moveToLeft()
                    describeMatrixToViews()
                }
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }


    fun openGameOverDialog() {
        val gameOverDialog = Dialog(this)
        gameOverDialog.setContentView(R.layout.gameover)


        gameOverDialog.setCancelable(false)

        gameOverDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



        gameOverDialog.findViewById<TextView>(R.id.textView3).text = repository.level.toString()
        gameOverDialog.findViewById<ImageView>(R.id.imageView4).setOnClickListener {
            repository.clear()

            repository = AppRepository.getInstance()
            describeMatrixToViews()
            gameOverDialog.dismiss()
        }

        gameOverDialog.findViewById<ImageView>(R.id.imageView3).setOnClickListener {
            repository.restart()
            repository = AppRepository.getInstance()
            gameOverDialog.dismiss()
            finish()
        }
        gameOverDialog.show()
    }


    private fun loadViews() {


        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until linear.childCount) {
                items.add(linear.getChildAt(j) as TextView)
            }
        }
    }

    private fun funtions() {

        findViewById<ImageView>(R.id.play_activity_restart).setOnClickListener {

            if (clic == null) {
                clic = findViewById<ImageView>(R.id.play_activity_restart)
                findViewById<ImageView>(R.id.play_activity_restart).animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        findViewById<ImageView>(R.id.play_activity_restart).animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clic = null
                                showRestartGameDialog()

                            }.start()
                    }.start()
            }
        }





        findViewById<ImageView>(R.id.back).setOnClickListener {
            if (clic == null) {
                clic = findViewById<ImageView>(R.id.back)
                findViewById<ImageView>(R.id.back).animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        findViewById<ImageView>(R.id.back).animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clic = null
                                clickBackCount++
                                repository.backOldState()
                                describeMatrixToViews()
                            }.start()
                    }.start()
            }

        }
        home_icon.setOnClickListener {
            home_icon.setOnClickListener {
                if (clic == null) {
                    clic = home_icon
                    home_icon.animate()
                        .scaleX(0.7f)
                        .setDuration(200)
                        .scaleY(0.7f)
                        .withEndAction {
                            home_icon.animate()
                                .setDuration(90)
                                .scaleY(1f)
                                .scaleX(1f)
                                .withEndAction {
                                    clic = null
                                    Log.d("TTT","play finish bo'ldi")
                                    finish()
                                }.start()
                        }.start()
                }
            }
        }
    }


    private fun showRestartGameDialog() {

        val restartDialog = Dialog(this)
        restartDialog.setContentView(R.layout.gameover)


        restartDialog.setCancelable(true)

        restartDialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

        restartDialog.setContentView(R.layout.item_restart)

        val yesBtn = restartDialog.findViewById(R.id.button) as AppCompatButton
        val noBtn = restartDialog.findViewById(R.id.button2) as AppCompatButton


        yesBtn.setOnClickListener {

            if (clicRestartDialog == null) {
                clicRestartDialog = yesBtn
                yesBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        yesBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)

                            .withEndAction {
                                clicRestartDialog = null
                                repository.clear()

                                repository = AppRepository.getInstance()
                                describeMatrixToViews()

                                restartDialog.dismiss()
                            }
                            .start()
                    }
                    .start()
            }
        }

        noBtn.setOnClickListener {
            if (clicRestartDialog == null) {
                clicRestartDialog = noBtn
                noBtn.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        noBtn.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                clicRestartDialog = null
                                restartDialog.dismiss()
                            }.start()
                    }.start()
            }
        }
        restartDialog.show()
    }


    @SuppressLint("ResourceAsColor")
    private fun describeMatrixToViews() {
        if (clickBackCount == 1 || !repository.isPlaying()) {
            findViewById<ImageView>(R.id.back).alpha = 0.2f
            findViewById<ImageView>(R.id.back).isClickable = false
        }


        var txt_best = findViewById<TextView>(R.id.txt_bestscore)
        level.text = repository.level.toString()
        record = txt_best.text.toString().toInt()
        record = repository.getRecord()
        if (repository.level > record) {
            record = repository.level
        }
        txt_best.text = record.toString()

        val _matrix = repository.matrix
        for (i in _matrix.indices) {
            for (j in _matrix[i].indices) {
                items[i * _matrix.size + j].apply {
                    text = if (_matrix[i][j] == 0) ""
                    else _matrix[i][j].toString()
                    setBackgroundResource(util.colorByCount(_matrix[i][j]))
                }
                if (_matrix[i][j] == 16384) {
                    openGameOverDialog()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        repository.saveRecord(record)
        repository.saveItems()
    }
}