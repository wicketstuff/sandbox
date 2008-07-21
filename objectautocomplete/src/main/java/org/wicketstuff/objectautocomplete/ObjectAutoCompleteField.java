/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.objectautocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Wicket component for selecting a single object of type T with an identifier of type I via
 * autocompletion. The textfield used for autocompletion is nothing more than a <em>search field</em>
 * where the autocomplete menu presents the search results. Selecting an entry of the search results
 * (either via keyboard or mouse click) "picks" an entity, whose id is stored in the model
 * of this component (So, the model object type of this model is I).
 *
 * A subclass must provide the list of T-objects, which are presented in the autocompletion menu.
 *
 * (TODO: Detailed usage example)
 *
 * @author roland
 * @since May 21, 2008
 */
public class ObjectAutoCompleteField<O,I> extends FormComponentPanel<I>
        implements ObjectAutoCompleteCancelListener {

    // Additional lists of components to update when an object has been selected
    private List<Component> componentsToUpdate = new ArrayList<Component>();

    // Remember old id in case a search operation is aborted
    private I selectedObject;

    // Used for proper state handling
    private I backupObject;
    private String backupText;

    // Remember selected input string
    private String selectedObjectFromChoice;

    // Textfield used to search for the object
    private TextField<String> searchTextField;

    // Hiddenfield carrying the selected object id
    private HiddenField<I> objectField;

    /**
     * Package scoped constructor to be used by the builder to create an auto completion fuild via
     * the builder pattern. I.e. use {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder#build(String)}
     * for creating an object auto completion component
     *
     * @param pId id of the component to add
     * @param pModel the model
     * @param pBuilder builder object used to create this field.
     */
    ObjectAutoCompleteField(String pId, IModel<I> pModel,ObjectAutoCompleteBuilder<O,I> pBuilder) {
        super(pId, pModel);

        // Remember original object. This is the one we are working on.
        selectedObject = getModelObject();

        setOutputMarkupId(true);

        // Register ourself as a cancel listener to restore asn old id
        pBuilder.cancelListener(this);

        // Register all update listener as added to the builder
        for (Component comp : pBuilder.updateOnModelChangeComponents) {
            registerForUpdateOnModelChange(comp);
        }
        // Search Text model contains the text selected
        Model<String> searchTextModel = new Model<String>();
        addSearchTextField(searchTextModel, pBuilder);
        addReadOnlyPanel(searchTextModel,pBuilder);
    }

    /**
     * Register a component that needs to be updated when the model changes, i.e.
     * the use selected an object from the suggestion list. Note, that this component's model
     * will be updated as well (whether this is in a form for validation or not)
     *
     * @param pComponentToUpdate the component to update
     */
    public void registerForUpdateOnModelChange(Component pComponentToUpdate) {
        componentsToUpdate.add(pComponentToUpdate);
    }

    // ==========================================================================================================

    // the 'search part' if in lookup mode
    private void addSearchTextField(final Model<String> pSearchTextModel, ObjectAutoCompleteBuilder<O,I> pBuilder) {
        searchTextField = new TextField<String>("search",pSearchTextModel) {
            @Override
            public boolean isVisible() {
                return isSearchMode();
            }
        };
        searchTextField.setOutputMarkupId(true);
        // this disables Firefox autocomplete
        searchTextField.add(new SimpleAttributeModifier("autocomplete", "off"));

        objectField = new HiddenField<I>("hiddenId",new PropertyModel<I>(this,"selectedObject"));
        objectField.setOutputMarkupId(true);
        add(objectField);

        searchTextField.add(
                pBuilder.buildBehavior(objectField),
                new ObjectUpdateBehavior()
                //newFormUpdateBehaviour(searchTextField)
        );

        add(searchTextField);
    }

    @Override
    protected IModel<I> initModel() {
        IModel model = super.initModel();
        if (model instanceof IWrapModel) {
            IWrapModel iwModel = (IWrapModel) model;
            if (iwModel.getWrappedModel() instanceof CompoundPropertyModel) {
                CompoundPropertyModel<I> cpModel =  (CompoundPropertyModel<I>) iwModel.getWrappedModel();
                objectField.setModel((IModel<I>) cpModel.bind(getId()));
            }
        }
        return model;
    }

    // the 'read only part' if the object has been selected
    private void addReadOnlyPanel(final Model<String> pSearchTextModel, ObjectAutoCompleteBuilder<O,I> pBuilder) {
        final WebMarkupContainer wac = new WebMarkupContainer("readOnlyPanel") {
            @Override
            public boolean isVisible() {
                return !isSearchMode();
            }
        };
        wac.setOutputMarkupId(true);

        ReadOnlyObjectRenderer<I> roRenderer = pBuilder.readOnlyObjectRenderer;

        Component objectReadOnlyComponent;
        if (roRenderer != null) {
            objectReadOnlyComponent =
                    roRenderer.getObjectRenderer(
                            "selectedValue", new PropertyModel<I>(this,"selectedObject"),pSearchTextModel);
        } else {
            objectReadOnlyComponent =
                    new Label("selectedValue",pSearchTextModel);
        }
        objectReadOnlyComponent.setOutputMarkupId(true);

        AjaxFallbackLink deleteLink = new AjaxFallbackLink("searchLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                changeToSearchMode(target);
            }
        };

        Component linkImage = new Image("searchLinkImage").setVisible(false);
        if (pBuilder.imageResource != null || pBuilder.imageResourceReference != null) {
            linkImage = pBuilder.imageResource != null ?
                    new Image("searchLinkImage",pBuilder.imageResource) :
                    new Image("searchLinkImage",pBuilder.imageResourceReference);
            deleteLink.add(new Label(ObjectAutoCompleteBuilder.SEARCH_LINK_PANEL_ID).setVisible(false));
        } else if (pBuilder.searchLinkContent != null) {
            deleteLink.add(pBuilder.searchLinkContent);
        } else {
            deleteLink.add(new Label(ObjectAutoCompleteBuilder.SEARCH_LINK_PANEL_ID,pBuilder.searchLinkText));
        }
        deleteLink.add(linkImage);

        if (pBuilder.searchOnClick) {
            deleteLink.setVisible(false);
            objectReadOnlyComponent.add(new AjaxEventBehavior("onclick") {
                @Override
                protected void onEvent(AjaxRequestTarget target) {
                    changeToSearchMode(target);
                }
            });
        }
        wac.add(objectReadOnlyComponent);
        wac.add(deleteLink);
        add(wac);
    }

    private void changeToSearchMode(AjaxRequestTarget target) {
        backupObject = selectedObject;
        backupText = searchTextField.getModelObject();
        selectedObject = null;
        ObjectAutoCompleteField.this.setModelObject(null);
        if (target != null) {
            target.addComponent(ObjectAutoCompleteField.this);
            String id = searchTextField.getMarkupId();
            target.appendJavascript(
                    "wicketGet('" +id +"').focus();" +
                            "wicketGet('" + id + "').select();");
        }
    }

    /**
     * Callback called in case the user cancels a search via 'escape'
     *
     * @param pTarget target to which the components to update are added
     */
    public void searchCanceled(AjaxRequestTarget pTarget,boolean pForceRestore) {
        if (backupObject != null) {
            if (Strings.isEmpty(searchTextField.getModelObject()) && !pForceRestore) {
                searchTextField.setModelObject(null);
                backupText = null;
                backupObject = null;
            } else if (backupText != null) {
                searchTextField.setModelObject(backupText);
            }
            selectedObject = backupObject;
            pTarget.addComponent(ObjectAutoCompleteField.this);
        } else {
            searchTextField.setModelObject(null);
            selectedObject = null;
            searchTextField.clearInput();
            backupText = null;
            pTarget.addComponent(searchTextField);
        }
        updateDependentComponents(pTarget);
    }

    // mode detection based on the existance of a seleced model
    private boolean isSearchMode() {
        return selectedObject == null;
    }

    /**
     * Called when form is submitted. We are simply store the object remembered internally.
     */
    @Override
    protected void convertInput() {
        setConvertedInput(selectedObject);
    }

    @Override
    // ensure that this component is embedded in a <span> ... </span>
    protected void onComponentTag(ComponentTag pTag) {
        super.onComponentTag(pTag);
        pTag.setName("span");
    }

    // add all registered components to the target for update
    private void updateDependentComponents(AjaxRequestTarget pTarget) {
        for (Component comp : componentsToUpdate) {
            comp.setDefaultModelObject(objectField.getModelObject());
            pTarget.addComponent(comp);
        }
    }


    // =========================================================================================

    //  Behaviour, when user leaves the input field.
    class ObjectUpdateBehavior extends AjaxEventBehavior {

        ObjectUpdateBehavior() {
            super("onchange");
        }

        @Override
        protected void onEvent(AjaxRequestTarget target) {
            objectField.processInput();
            searchTextField.processInput();
            target.addComponent(ObjectAutoCompleteField.this);
            updateDependentComponents(target);
        }

        @Override
        protected CharSequence getEventHandler()
        {
            return generateCallbackScript(new AppendingStringBuffer("wicketAjaxPost('").append(
                    getCallbackUrl(false)).append(
                    "', wicketSerialize(Wicket.$('" + searchTextField.getMarkupId() + "')) + " +
                            "wicketSerialize(Wicket.$('" + objectField.getMarkupId() + "'))"));
        }
    }

}
