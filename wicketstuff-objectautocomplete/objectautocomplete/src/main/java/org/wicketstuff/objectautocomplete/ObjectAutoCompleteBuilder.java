package org.wicketstuff.objectautocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for initializing a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteField} and
 * a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteBehavior}
 *
 * The type parameter O specifies the object type of the objects to be selected, I the type of the object's id
 *
 * @author roland
 * @since May 24, 2008
 */
public class ObjectAutoCompleteBuilder<O,I> {

    public static final String SEARCH_LINK_PANEL_ID = "searchLinkPanel";

    // Provider which gives the list of objects matching a
    // particular input string
    AutoCompletionChoicesProvider<O> choicesProvider;

    // A listener notified when a search has been cancelled
    ObjectAutoCompleteCancelListener cancelListener;

    // Renderer for the auto completion list
    ObjectAutoCompleteRenderer<O> objectAutoCompleteRenderer;

    // Renderer to use for the read only view when an object
    // has been already selected
    ObjectReadOnlyRenderer<I> readOnlyObjectRenderer;

    // whether the first match should be preselected
    boolean preselect;

    // maximum height of autocompletion list
    int maxHeightInPx;

    // show the list also on an empty input when users pushes down button
    boolean showListOnEmptyInput;

    // a list of listeners to update on selection change
    List<Component> updateOnSelectionChangeComponents;

    // whether to react on 'onClick' on the read only view itself
    boolean searchOnClick;

    // An own component to use as 'search link' content
    Component searchLinkContent;

    // Text to show for the 'search link' when no image is set
    String searchLinkText;

    // image resource or reference to use for button which switchs back
    // to editing the field
    Resource imageResource;
    ResourceReference imageResourceReference;

    // whether the field cannot be changed after the first selection
    boolean unchangeable;


    public ObjectAutoCompleteBuilder(AutoCompletionChoicesProvider<O> pChoicesProvider) {
        this.choicesProvider = pChoicesProvider;
        cancelListener = null;
        objectAutoCompleteRenderer = new ObjectAutoCompleteRenderer<O>();
        preselect = false;
        searchOnClick = false;
        showListOnEmptyInput = false;
        maxHeightInPx = -1;
        updateOnSelectionChangeComponents = new ArrayList<Component>();
        searchLinkContent = null;
        searchLinkText = "[S]";
        readOnlyObjectRenderer = null;
        unchangeable = false;
    }

    // =======================================================================================================
    // Configuration methods
    // =====================

    // Intentionally package scope, to allow the autocompletion panel to register (but not outside the package)
    ObjectAutoCompleteBuilder<O,I> cancelListener(ObjectAutoCompleteCancelListener pCancelListener) {
        this.cancelListener = pCancelListener;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> autoCompleteRenderer(ObjectAutoCompleteRenderer<O> renderer) {
        this.objectAutoCompleteRenderer = renderer;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> idProperty(String idProperty) {
        if (! (objectAutoCompleteRenderer instanceof ObjectAutoCompleteRenderer)) {
            throw new IllegalArgumentException("Can not set idProperty " + idProperty + " on a renderer of type " +
            objectAutoCompleteRenderer.getClass() + ". Need to operate on a ObjectAutoCompleteRenderer");
        }
        ((ObjectAutoCompleteRenderer) objectAutoCompleteRenderer).setIdProperty(idProperty);
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> readOnlyRenderer(ObjectReadOnlyRenderer<I> pReadOnlyObjectRenderer) {
        this.readOnlyObjectRenderer = pReadOnlyObjectRenderer;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> preselect() {
        this.preselect = true;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> searchOnClick() {
        this.searchOnClick = true;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> searchLinkContent(Component pContent) {
        if (!pContent.getId().equals(SEARCH_LINK_PANEL_ID)) {
            throw new IllegalArgumentException("Panel used for the search link content must have id '"
                    + SEARCH_LINK_PANEL_ID + "' (and not " + pContent.getId() + ")");
        }
        this.searchLinkContent = pContent;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> searchLinkImage(Resource pImageResource) {
        this.imageResource = pImageResource;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> searchLinkImage(ResourceReference pResourceReference) {
        this.imageResourceReference = pResourceReference;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> searchLinkText(String pText) {
        this.searchLinkText = pText;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> maxHeightInPx(int pMaxHeightInPx) {
        this.maxHeightInPx = pMaxHeightInPx;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> showListOnEmptyInput(boolean pShowListOnEmptyInput) {
        this.showListOnEmptyInput = pShowListOnEmptyInput;
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> updateOnSelectionChange(Component ... pComponents) {
        for (Component comp : pComponents) {
            if (comp == null) {
                throw new IllegalArgumentException(
                        "A component to included for an ajax update " +
                                "on selection change cannot be null");
            }
        }
        updateOnSelectionChangeComponents.addAll(Arrays.asList(pComponents));
        return this;
    }

    public ObjectAutoCompleteBuilder<O,I> unchangeable() {
        this.unchangeable = true;
        return this;
    }

    // ==========================================================================================================
    // Builder methods
    // ===============

    public ObjectAutoCompleteBehavior<O> buildBehavior(Component objectIdHolder) {
        return new ObjectAutoCompleteBehavior<O>(objectIdHolder,this);
    }

    public ObjectAutoCompleteField<O,I> build(String id) {
        return build(id,null);
    }

    public ObjectAutoCompleteField<O,I> build(String id, IModel<I> model) {
        return new ObjectAutoCompleteField<O,I>(id,model,this);
    }
}
