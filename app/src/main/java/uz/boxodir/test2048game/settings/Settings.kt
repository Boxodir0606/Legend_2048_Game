package uz.boxodir.test2048game.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Settings constructor(context: Context) {

    fun savePos(txt: String) {
        editor.putString("CURRENT_MATRIX", txt).apply()
    }

    fun saveLastMatrix(txt: String) {
        editor.putString("LAST", txt).apply()
    }

    fun saveState(boolean: Boolean) {
        editor.putBoolean("STATE", boolean).apply()
    }

    fun isPlaying() = pref.getBoolean("STATE", false)
    fun getItems(): String? {
        return pref.getString("CURRENT_MATRIX", "")
    }

    fun lastItems(): String? {
        return pref.getString("LAST", "")
    }

    fun saveScore(score: Int) {
        editor.putInt("SCORE", score).apply()
    }

    fun saveLastScore(lastScore: Int) {
        editor.putInt("LAST_SCORE", lastScore).apply()
    }

    fun saveRecord(record: Int) {
        editor.putInt("RECORD", record).apply()
    }

    fun getScore(): Int = pref.getInt("SCORE", 0)
    fun getScoreLast(): Int = pref.getInt("LAST_SCORE", 0)
    fun getRecord(): Int = pref.getInt("RECORD", 0)

    private var pref: SharedPreferences = context.getSharedPreferences("2048", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var instance: Settings

        fun init(context: Context) {
            if (!(Companion::instance.isInitialized)) {
                instance = Settings(context)
            }
        }

        fun getInstance(): Settings {
            return instance
        }
    }
}