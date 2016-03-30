package io.protostuff.jetbrains.plugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import io.protostuff.jetbrains.plugin.psi.EnumConstantNode;
import io.protostuff.jetbrains.plugin.psi.KeywordsContainer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ProtoSyntaxKeywordsAnnotator implements Annotator {

    private static void setHighlighting(@NotNull PsiElement element, @NotNull AnnotationHolder holder,
                                        @NotNull TextAttributesKey key) {
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(TextAttributes.ERASE_MARKER);
        holder.createInfoAnnotation(element, null).setEnforcedTextAttributes(
                EditorColorsManager.getInstance().getGlobalScheme().getAttributes(key));
    }

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof KeywordsContainer) {
            KeywordsContainer container = (KeywordsContainer) element;
            for (PsiElement psiElement : container.keywords()) {
                setHighlighting(psiElement, holder, ProtoSyntaxHighlighter.KEYWORD);
            }
        }
        if (element instanceof EnumConstantNode) {
            ASTNode node = element.getNode();
            ASTNode name = node.findChildByType(ProtoParserDefinition.R_NAME);
            if (name != null) {
                // TODO: refactor, should be possible to highlight text with rule w/o finding token
                PsiElement psiElement = (PsiElement) name.getFirstChildNode();
                setHighlighting(psiElement, holder, ProtoSyntaxHighlighter.ENUM_CONSTANT);
            }
        }
    }
}
