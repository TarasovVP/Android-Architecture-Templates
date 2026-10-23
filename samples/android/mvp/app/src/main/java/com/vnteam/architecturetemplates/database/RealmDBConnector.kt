package com.vnteam.architecturetemplates.database

import com.vnteam.architecturetemplates.models.DemoObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmList

class RealmDBConnector(private val realm: Realm) {

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>): Observable<Unit> {
        return Observable.create<Unit> { emitter ->
            realm.executeTransactionAsync({ realmInstance ->
                val realmList = RealmList<DemoObject>()
                realmList.addAll(demoObjects)
                realmInstance.insertOrUpdate(realmList)
                emitter.onNext(Unit)
                emitter.onComplete()
            }, { error ->
                emitter.onError(error)
                error.printStackTrace()
            })
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    fun getDemoObjectsFromDB(): Observable<List<DemoObject>> {
        return Observable.create<List<DemoObject>> { emitter ->
            realm.runCatching {
                val demoObjects = where(DemoObject::class.java)?.findAll().orEmpty()
                emitter.onNext(demoObjects)
                emitter.onComplete()
            }.onFailure {error ->
                emitter.onError(error)
                error.printStackTrace()
            }
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    fun getDemoObjectById(demoObjectId: Long?): Observable<DemoObject> {
        return Observable.create<DemoObject> { emitter ->
            realm.runCatching {
                val demoObject = where(DemoObject::class.java)?.equalTo("id", demoObjectId)?.findFirst()
                demoObject.takeIf { it != null }?.let { emitter.onNext(it) } ?: emitter.onError(Throwable("DemoObject is not available"))
                emitter.onComplete()
            }.onFailure {error ->
                emitter.onError(error)
                error.printStackTrace()
            }
        }.subscribeOn(AndroidSchedulers.mainThread())
    }
}