package uz.boxodir.test2048game.app

import android.app.Application
import uz.boxodir.test2048game.repository.AppRepository
import uz.boxodir.test2048game.settings.Settings

class App:Application() {
    override fun onCreate() {
        Settings.init(this)
        AppRepository.init(this)
        super.onCreate()
    }
}