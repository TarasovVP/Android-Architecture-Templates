package com.kmp.ktlint

import com.android.SdkConstants.ANDROID_URI
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import com.android.tools.lint.detector.api.XmlScanner
import com.android.tools.lint.detector.api.resolveManifestName
import com.intellij.psi.PsiClass
import org.w3c.dom.Element

class UnresolvedComponentDetector : Detector(), XmlScanner {
    override fun getApplicableElements(): Collection<String> = listOf(ACTIVITY, SERVICE, RECEIVER, PROVIDER)

    override fun visitElement(
        context: XmlContext,
        element: Element,
    ) {
        val className = element.getAttributeNS(ANDROID_URI, NAME)
        if (className.isNullOrBlank()) return

        val fqcn = resolveManifestName(element, context.project)

        val parser = context.client.getUastParser(context.project)
        if (!parser.prepared) {
            return
        }
        val evaluator = parser.evaluator
        val psiClass: PsiClass? = evaluator.findClass(fqcn)
        if (psiClass == null) {
            context.report(
                ISSUE,
                element,
                context.getLocation(element),
                "Class $fqcn not found in the project. It may have been removed.",
            )
        }
    }

    companion object {
        private const val ISSUE_PRIORITY = 6
        private const val ACTIVITY = "activity"
        private const val SERVICE = "service"
        private const val RECEIVER = "receiver"
        private const val PROVIDER = "provider"
        private const val NAME = "name"
        private const val ISSUE_ID = "UnresolvedManifestClass"
        private const val ISSUE_BRIEF_DESCRIPTION = "UnresolvedManifestClass"
        private const val ISSUE_EXPLANATION = "UnresolvedManifestClass"

        val ISSUE =
            Issue.create(
                id = ISSUE_ID,
                briefDescription = ISSUE_BRIEF_DESCRIPTION,
                explanation = ISSUE_EXPLANATION,
                category = Category.CORRECTNESS,
                priority = ISSUE_PRIORITY,
                severity = Severity.ERROR,
                implementation =
                    Implementation(
                        UnresolvedComponentDetector::class.java,
                        Scope.MANIFEST_SCOPE,
                    ),
            )
    }
}
