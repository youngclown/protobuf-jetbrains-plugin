package io.protostuff.jetbrains.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.antlr.jetbrains.adapter.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class MapKeyNode extends ANTLRPsiNode implements KeywordsContainer {

    public MapKeyNode(@NotNull ASTNode node) {
        super(node);
    }


    @NotNull
    @Override
    public Collection<PsiElement> keywords() {
        return Util.findKeywords(getNode());
    }
}