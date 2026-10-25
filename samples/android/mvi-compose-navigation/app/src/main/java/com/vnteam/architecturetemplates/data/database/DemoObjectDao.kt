package com.vnteam.architecturetemplates.data.database

import com.vnteam.architecturetemplates.DemoObjectWithOwner

interface DemoObjectDao {

    fun insertDemoObjectWithOwners(demoObjectWithOwners: List<DemoObjectWithOwner>)

    fun getDemoObjects(): List<DemoObjectWithOwner>

    fun getDemoObjectById(id: Long): DemoObjectWithOwner?
}