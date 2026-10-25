package com.vnteam.architecturetemplates.list

import com.vnteam.architecturetemplates.models.DemoObject

interface ListViewContract {

    fun setProgressVisibility(showProgress: Boolean)

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>)

    fun getDemoObjectsFromDB()

    fun setDemoObjectsFromDB(demoObjects: List<DemoObject>)

    fun showError(errorMessage: String)
}