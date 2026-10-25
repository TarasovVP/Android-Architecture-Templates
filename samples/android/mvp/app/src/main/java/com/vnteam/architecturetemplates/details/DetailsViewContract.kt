package com.vnteam.architecturetemplates.details

import com.vnteam.architecturetemplates.models.DemoObject

interface DetailsViewContract {

    fun setProgressVisibility(showProgress: Boolean)

    fun setDemoObjectFromDB(demoObject: DemoObject)

    fun showError(errorMessage: String)
}