package com.vnteam.architecturetemplates.list

import android.annotation.SuppressLint
import com.vnteam.architecturetemplates.MainActivity.Companion.DEMO_OBJECT_URL
import com.vnteam.architecturetemplates.database.RealmDBConnector
import com.vnteam.architecturetemplates.models.DemoObject
import com.vnteam.architecturetemplates.network.ValleyApiConnector
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val valleyApiConnector: ValleyApiConnector,
    private val realmDBConnector: RealmDBConnector
){

    private var view: ListViewContract? = null

    fun attachView(view: ListViewContract) {
        this.view = view
    }

    @SuppressLint("CheckResult")
    fun getDemoObjectsFromApi() {
        view?.setProgressVisibility(true)
        valleyApiConnector.makeRequest(DEMO_OBJECT_URL).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { demoObjects ->
                    view?.insertDemoObjectsToDB(demoObjects)
                    view?.setProgressVisibility(false)
                },
                { error ->
                    view?.showError(error?.localizedMessage.toString())
                    view?.setProgressVisibility(false)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        view?.setProgressVisibility(true)
        realmDBConnector.insertDemoObjectsToDB(demoObjects).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.getDemoObjectsFromDB()
                    view?.setProgressVisibility(false)
                },
                { error ->
                    view?.showError(error?.localizedMessage.toString())
                    view?.setProgressVisibility(false)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun getDemoObjectsFromDB() {
        view?.setProgressVisibility(true)
        realmDBConnector.getDemoObjectsFromDB().observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { demoObjects ->
                    view?.setDemoObjectsFromDB(demoObjects)
                    view?.setProgressVisibility(false)
                },
                { error ->
                    view?.showError(error?.localizedMessage.toString())
                    view?.setProgressVisibility(false)
                }
            )
    }

    fun detachView() {
        this.view = null
    }
}