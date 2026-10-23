package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.details.DetailsFragment
import com.vnteam.architecturetemplates.list.ListFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectListFragment(listFragment: ListFragment)
    fun injectDetailsFragment(detailsFragment: DetailsFragment)
}