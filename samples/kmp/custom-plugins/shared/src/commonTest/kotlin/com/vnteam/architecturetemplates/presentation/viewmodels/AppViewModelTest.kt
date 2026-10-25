package com.vnteam.architecturetemplates.presentation.viewmodels

import com.vnteam.architecturetemplates.data.APP_LANG_EN
import com.vnteam.architecturetemplates.data.APP_LANG_UK
import com.vnteam.architecturetemplates.domain.usecase.IsDarkThemeUseCase
import com.vnteam.architecturetemplates.domain.usecase.LanguageUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeIsDarkThemeUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeLanguageUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest : BaseViewModelTest() {
    override val overrideModule: Module
        get() =
            module {
                single<IsDarkThemeUseCase> { FakeIsDarkThemeUseCase() }
                single<LanguageUseCase> { FakeLanguageUseCase() }
            }

    private val appViewModel by inject<AppViewModel>()

    private val isDarkThemeUseCase by inject<IsDarkThemeUseCase>()
    private val languageUseCase by inject<LanguageUseCase>()

    @Test
    fun testSetIsDarkThemeTrue() =
        runTest {
            appViewModel.setIsDarkTheme(true)
            runCurrent()
            val actual = isDarkThemeUseCase.get().first() == true
            assertTrue(actual)
        }

    @Test
    fun testSetIsDarkThemeFalse() =
        runTest {
            appViewModel.setIsDarkTheme(false)
            runCurrent()
            val actual = isDarkThemeUseCase.get().first() == true
            assertFalse(actual)
        }

    @Test
    fun testGetIsDarkThemeTrue() =
        runTest {
            isDarkThemeUseCase.set(true)
            appViewModel.getIsDarkTheme()
            runCurrent()
            val actual = appViewModel.isDarkTheme.first() == true
            assertTrue(actual)
        }

    @Test
    fun testGetIsDarkThemeFalse() =
        runTest {
            isDarkThemeUseCase.set(false)
            appViewModel.getIsDarkTheme()
            runCurrent()
            val actual = appViewModel.isDarkTheme.first() == true
            assertFalse(actual)
        }

    @Test
    fun testSetLanguageEn() =
        runTest {
            appViewModel.setLanguage(APP_LANG_EN)
            runCurrent()
            val actual = languageUseCase.get().first()
            assertEquals(APP_LANG_EN, actual)
        }

    @Test
    fun testSetLanguageUk() =
        runTest {
            appViewModel.setLanguage(APP_LANG_UK)
            runCurrent()
            val actual = languageUseCase.get().first()
            assertEquals(APP_LANG_UK, actual)
        }

    @Test
    fun testGetLanguageEn() =
        runTest {
            languageUseCase.set(APP_LANG_EN)
            appViewModel.getLanguage()
            runCurrent()
            val actual = appViewModel.language.first()
            assertEquals(APP_LANG_EN, actual)
        }

    @Test
    fun testGetLanguageUk() =
        runTest {
            languageUseCase.set(APP_LANG_UK)
            appViewModel.getLanguage()
            runCurrent()
            val actual = appViewModel.language.first()
            assertEquals(APP_LANG_UK, actual)
        }
}
