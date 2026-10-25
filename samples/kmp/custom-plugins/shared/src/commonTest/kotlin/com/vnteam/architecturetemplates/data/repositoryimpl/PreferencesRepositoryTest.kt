package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.data.APP_LANGUAGE
import com.vnteam.architecturetemplates.data.APP_LANG_EN
import com.vnteam.architecturetemplates.data.APP_LANG_UK
import com.vnteam.architecturetemplates.data.IS_DARK_THEME
import com.vnteam.architecturetemplates.data.local.Preferences
import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.fake.data.local.FakePreferencesFactory
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PreferencesRepositoryTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<Preferences> { FakePreferencesFactory() }
            }

    private val repository by inject<PreferencesRepository>()
    private val preferencesFactory by injectAs<Preferences, FakePreferencesFactory>()

    @Test
    fun testIsDarkThemeTrue() =
        runTest {
            repository.setIsDarkTheme(true)
            val actual = preferencesFactory.getBoolean(IS_DARK_THEME).first()
            assertTrue(actual)
        }

    @Test
    fun testIsDarkThemeFalse() =
        runTest {
            repository.setIsDarkTheme(false)
            val actual = preferencesFactory.getBoolean(IS_DARK_THEME).first()
            assertFalse(actual)
        }

    @Test
    fun testGetDarkThemeTrue() =
        runTest {
            preferencesFactory.putBoolean(IS_DARK_THEME, true)
            val actual = repository.getIsDarkTheme().first()
            assertTrue(actual)
        }

    @Test
    fun testGetDarkThemeFalse() =
        runTest {
            preferencesFactory.putBoolean(IS_DARK_THEME, false)
            val actual = repository.getIsDarkTheme().first()
            assertFalse(actual)
        }

    @Test
    fun testSetLanguageEn() =
        runTest {
            repository.setLanguage(APP_LANG_EN)
            val actual = preferencesFactory.getString(APP_LANGUAGE).first()
            assertEquals(APP_LANG_EN, actual)
        }

    @Test
    fun testSetLanguageUk() =
        runTest {
            repository.setLanguage(APP_LANG_UK)
            val actual = preferencesFactory.getString(APP_LANGUAGE).first()
            assertEquals(APP_LANG_UK, actual)
        }

    @Test
    fun testGetLanguageEn() =
        runTest {
            preferencesFactory.putString(APP_LANGUAGE, APP_LANG_EN)
            val actual = repository.getLanguage().first()
            assertEquals(APP_LANG_EN, actual)
        }

    @Test
    fun testGetLanguageUk() =
        runTest {
            preferencesFactory.putString(APP_LANGUAGE, APP_LANG_UK)
            val actual = repository.getLanguage().first()
            assertEquals(APP_LANG_UK, actual)
        }
}
