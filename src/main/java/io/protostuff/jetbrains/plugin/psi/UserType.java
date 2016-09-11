package io.protostuff.jetbrains.plugin.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.jetbrains.adapter.psi.IdentifierDefSubtree;
import org.antlr.jetbrains.adapter.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class UserType
        extends IdentifierDefSubtree
        implements ScopeNode, KeywordsContainer {

    public UserType(@NotNull ASTNode node, IElementType idElementType) {
        super(node, idElementType);
    }

    /**
     * Returns fully qualified name of this message (starting with dot).
     */
    public String getQualifiedName() {
        PsiElement parent = getParent();
        if (parent instanceof ProtoRootNode) {
            ProtoRootNode proto = (ProtoRootNode) parent;
            String packageName = proto.getPackageName();
            if (packageName.isEmpty()) {
                return "." + getName();
            }
            return "." + packageName + "." + getName();
        }
        if (parent instanceof MessageNode) {
            MessageNode parentMessage = (MessageNode) parent;
            String parentMessageQualifiedName = parentMessage.getQualifiedName();
            return parentMessageQualifiedName + "." + getName();
        }
        throw new IncorrectOperationException("Could not detect qualified name in given context: "
                + parent.getClass().getName());
    }

    /**
     * Returns full name of this type without leading dot.
     */
    public String getFullName() {
        return getQualifiedName().substring(1);
    }

    @Nullable
    @Override
    public PsiElement resolve(PsiNamedElement element) {
        return null;
    }

    @NotNull
    @Override
    public Collection<PsiElement> keywords() {
        return Util.findKeywords(getNode());
    }
}
