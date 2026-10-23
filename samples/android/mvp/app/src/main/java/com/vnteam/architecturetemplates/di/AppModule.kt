package com.vnteam.architecturetemplates.di

import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.vnteam.architecturetemplates.AppApplication
import com.vnteam.architecturetemplates.database.RealmDBConnector
import com.vnteam.architecturetemplates.details.DetailsPresenter
import com.vnteam.architecturetemplates.network.ValleyApiConnector
import com.vnteam.architecturetemplates.list.ListPresenter
import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class AppModule(private val application: AppApplication) {

    @Provides
    fun provideRequestQueue(): RequestQueue {
        return Volley.newRequestQueue(application)
    }

    @Provides
    fun provideValleyApiConnector(requestQueue: RequestQueue): ValleyApiConnector {
        return ValleyApiConnector(requestQueue)
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun provideRealmDBConnector(realm: Realm): RealmDBConnector {
        return RealmDBConnector(realm)
    }

    @Provides
    fun provideListPresenter(valleyApiConnector: ValleyApiConnector, realmDBConnector: RealmDBConnector): ListPresenter {
        return ListPresenter(valleyApiConnector, realmDBConnector)
    }

    @Provides
    fun provideDetailsPresenter(realmDBConnector: RealmDBConnector): DetailsPresenter {
        return DetailsPresenter(realmDBConnector)
    }
}