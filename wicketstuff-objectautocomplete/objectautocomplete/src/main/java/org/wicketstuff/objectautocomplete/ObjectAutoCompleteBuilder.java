package org.wicketstuff.objectautocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for initializing a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteField} and
 * a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteBehavior}
 *
 * The type parameter T specifies the object type of the objects to be selected.
 *
 * @author roland
 * @since May 24, 2008
 */
public class ObjectAutoCompleteBuilder<T> {

    public static final String SEARCH_LINK_PANEL_ID = "searchLinkPanel";

    AutoCompletionChoicesProvider<T> choicesProvider;
    ObjectAutoCompleteCancelListener cancelListener;
    ObjectAutoCompleteRenderer<T> objectAutoCompleteRenderer;
    boolean preselect;
    int maxHeightInPx;
    boolean showListOnEmptyInput;
    List<Component<?>> updateOnModelChangeComponents;
    boolean searchOnClick;
    Component searchLinkContent;
    String searchLinkText;

    Resource imageResource;
    ResourceReference imageResourceReference;


    public ObjectAutoCompleteBuilder(AutoCompletionChoicesProvider<T> pChoicesProvider) {
        this.choicesProvider = pChoicesProvider;
        cancelListener = null;
        objectAutoCompleteRenderer = new ObjectAutoCompleteRenderer<T>();
        preselect = false;
        searchOnClick = false;
        showListOnEmptyInput = false;
        maxHeightInPx = -1;
        updateOnModelChangeComponents = new ArrayList<Component<?>>();
        searchLinkContent = null;
        searchLinkText = "[S]";
    }

    // =======================================================================================================
    // Configuration methods
    // =====================

    // Intentionally package scope, to allow the autocompletion panel to register (but not outside the package)
    ObjectAutoCompleteBuilder<T> cancelListener(ObjectAutoCompleteCancelListener pCancelListener) {
        this.cancelListener = pCancelListener;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> autoCompleteRenderer(ObjectAutoCompleteRenderer<T> renderer) {
        this.objectAutoCompleteRenderer = renderer;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> idProperty(String idProperty) {
        if (! (objectAutoCompleteRenderer instanceof ObjectAutoCompleteRenderer)) {
            throw new IllegalArgumentException("Can not set idProperty " + idProperty + " on a renderer of type " +
            objectAutoCompleteRenderer.getClass() + ". Need to operate on a ObjectAutoCompleteRenderer");
        }
        ((ObjectAutoCompleteRenderer) objectAutoCompleteRenderer).setIdProperty(idProperty);
        return this;
    }

    public ObjectAutoCompleteBuilder<T> preselect() {
        this.preselect = true;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> searchOnClick() {
        this.searchOnClick = true;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> searchLinkContent(Component pContent) {
        if (!pContent.getId().equals(SEARCH_LINK_PANEL_ID)) {
            throw new IllegalArgumentException("Panel used for the search link content must have id '"
                    + SEARCH_LINK_PANEL_ID + "' (and not " + pContent.getId() + ")");
        }
        this.searchLinkContent = pContent;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> searchLinkImage(Resource pImageResource) {
        this.imageResource = pImageResource;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> searchLinkImage(ResourceReference pResourceReference) {
        this.imageResourceReference = pResourceReference;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> searchLinkText(String pText) {
        this.searchLinkText = pText;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> maxHeightInPx(int pMaxHeightInPx) {
        this.maxHeightInPx = pMaxHeightInPx;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> showListOnEmptyInput(boolean pShowListOnEmptyInput) {
        this.showListOnEmptyInput = pShowListOnEmptyInput;
        return this;
    }

    public ObjectAutoCompleteBuilder<T> updateOnModelChange(Component<?> ... pComponents) {
        for (Component comp : pComponents) {
            if (comp == null) {
                throw new IllegalArgumentException(
                        "A component to included for an ajax update " +
                                "on model change cannot be null");
            }
        }
        updateOnModelChangeComponents.addAll(Arrays.asList(pComponents));
        return this;
    }

    // ==========================================================================================================
    // Builder methods
    // ===============

    public ObjectAutoCompleteBehavior<T> buildBehavior(Component objectIdHolder) {
        return new ObjectAutoCompleteBehavior<T>(objectIdHolder,this);
    }

    public <I> ObjectAutoCompleteField<T,I> build(String id) {
        return build(id,null);
    }

    public <I> ObjectAutoCompleteField<T,I> build(String id, IModel<I> model) {
        return new ObjectAutoCompleteField<T,I>(id,model,this);
    }
}
