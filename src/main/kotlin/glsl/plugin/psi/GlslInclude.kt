package glsl.plugin.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.ContributedReferenceHost
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceService
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference

abstract class GlslInclude(node: ASTNode) : ASTWrapperPsiElement(node), ContributedReferenceHost {

    /**
     *
     */
    override fun getReferences(): Array<out PsiReference?> {
        return PsiReferenceService.getService().getContributedReferences(this)
    }

    /**
     *
     */
    override fun getReference(): FileReference? {
        return references.firstOrNull() as FileReference
    }

    /**
     *
     */
    fun getPath(): String? {
        val pathText = text
        if (isValidIncludePath(pathText)) {
            // Takes all between parentheses or brackets
            return pathText.substring(1, pathText.length - 1)
        }
        return null
    }


    companion object {
        /**
         *
         */
        fun isValidIncludePath(includePath: String): Boolean {
            if (includePath.length < 3) return false
            val first = includePath.first()
            val last = includePath.last()
            return (first == '"' && last == '"') || (first == '<' && last == '>')
        }

    }
}