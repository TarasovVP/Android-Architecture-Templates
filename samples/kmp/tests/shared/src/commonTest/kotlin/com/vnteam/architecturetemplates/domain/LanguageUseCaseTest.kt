package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.data.APP_LANG_EN
import com.vnteam.architecturetemplates.data.APP_LANG_UK
import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.LanguageUseCase
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakePreferencesRepository
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class LanguageUseCaseTest : BaseKoinTest() {

    override val overrideModule: Module
        get() = module {
            single<PreferencesRepository> { FakePreferencesRepository() }
        }

    private val languageUseCase by inject<LanguageUseCase>()
    private val repository by injectAs<PreferencesRepository, FakePreferencesRepository>()

    @Test
    fun testSetLanguageEn() = runTest {
        languageUseCase.set(APP_LANG_EN)
        val actual = repository.getLanguage().first()
        assertEquals(APP_LANG_EN, actual)
    }

    @Test
    fun testSetLanguageUk() = runTest {
        languageUseCase.set(APP_LANG_UK)
        val actual = repository.getLanguage().first()
        assertEquals(APP_LANG_UK, actual)
    }

    @Test
    fun testGetLanguageEn() = runTest {
        repository.setLanguage(APP_LANG_EN)
        val actual = languageUseCase.get().first()
        assertEquals(APP_LANG_EN, actual)
    }

    @Test
    fun testGetLanguageUk() = runTest {
        repository.setLanguage(APP_LANG_UK)
        val actual = languageUseCase.get().first()
        assertEquals(APP_LANG_UK, actual)
    }
}