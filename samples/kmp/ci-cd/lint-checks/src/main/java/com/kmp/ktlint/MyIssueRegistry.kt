package com.kmp.ktlint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

private const val MIN_API = 11

class MyIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> =
        listOf(
            UnresolvedComponentDetector.ISSUE,
        )
    override val api = MIN_API
}
