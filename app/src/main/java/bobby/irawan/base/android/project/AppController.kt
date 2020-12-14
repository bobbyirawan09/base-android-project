package bobby.irawan.base.android.project

import android.app.Application
import bobby.irawan.base.android.project.di.dataModule
import bobby.irawan.base.android.project.di.domainModule
import bobby.irawan.base.android.project.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Bobby Irawan on 14/12/20.
 */
class AppController: Application() {

    override fun onCreate() {
        super.onCreate()
        onInitKoin()
    }

    private fun onInitKoin() {
        startKoin {
            androidContext(this@AppController)
            modules(dataModule, domainModule, presentationModule)
        }
    }
}