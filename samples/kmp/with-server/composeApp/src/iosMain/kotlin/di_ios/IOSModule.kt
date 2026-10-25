package di_ios

import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import com.vnteam.architecturetemplates.data.local.PreferencesFactory
import com.vnteam.architecturetemplates.presentation.TextToSpeechHelper
import org.koin.dsl.module

val iosModule = module {
    single {
        DatabaseDriverFactory()
    }
    single {
        PreferencesFactory()
    }
    single {
        TextToSpeechHelper()
    }
}