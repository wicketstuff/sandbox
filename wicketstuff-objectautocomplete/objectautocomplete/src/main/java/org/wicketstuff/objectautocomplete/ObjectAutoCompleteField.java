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

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.Component;
import org.apache.wicket.validation.IValidator;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * @author roland
 * @since May 21, 2008
 */
abstract public class ObjectAutoCompleteField<T,I extends Serializable> extends Panel<I> {

    // Additional lists of components to update when an object has been selected
    private List<Component<?>> componentsToUpdate = new ArrayList<Component<?>>();

    // Remember old id in case a search operation is aborted
    private I backupObject;
    private TextField<String> searchTextField;

    public ObjectAutoCompleteField(String id) {
        this(id,new Model<I>());
    }

    public ObjectAutoCompleteField(String id, IModel<I> iModel) {
        super(id, iModel);
        init();
    }


    private void init() {
        setOutputMarkupId(true);

        // Search Text model contains the text selected
        Model<String> searchTextModel = new Model<String>();

        addSearchTextField(searchTextModel);
        addSelectionPanel(searchTextModel);
    }

    private void addSearchTextField(final Model<String> searchTextModel) {
        searchTextField = new TextField<String>("search",searchTextModel) {
            @Override
            public boolean isVisible() {
                return isSearchMode();
            }
        };
        searchTextField.setOutputMarkupId(true);
        // this disables Firefox autocomplete
        searchTextField.add(new SimpleAttributeModifier("autocomplete", "off"));

        searchTextField.add(
                createObjectAutoCompleteBehaviour(),
                createFormUpdateBehaviour(searchTextField));

        add(searchTextField);
    }

    private boolean isSearchMode() {
        return getModelObject() == null;
    }


    private AjaxFormSubmitBehavior createFormUpdateBehaviour(final TextField<String> searchTextField) {
        return new AjaxFormSubmitBehavior("onchange")
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                searchTextField.updateModel();
                searchTextField.clearInput();
                target.addComponent(ObjectAutoCompleteField.this);
                for (Component comp : componentsToUpdate) {
                    target.addComponent(comp);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
            }
        };
    }

    private IBehavior createObjectAutoCompleteBehaviour() {
        final HiddenField<I> objectField = new HiddenField<I>("hiddenId",getModel());
        objectField.setOutputMarkupId(true);
        add(objectField);
        IBehavior objectAutoSelectBehaviour = new ObjectAutoCompleteBehavior<T>(objectField) {
            @Override
            protected Iterator<T> getChoices(String input) {
                return ObjectAutoCompleteField.this.getChoices(input);
            }
        };
        return objectAutoSelectBehaviour;
    }

    private void addSelectionPanel(final Model<String> searchTextModel) {
        final WebMarkupContainer wac = new WebMarkupContainer("readOnlyPanel") {
            @Override
            public boolean isVisible() {
                return !isSearchMode();
            }
        };
        wac.setOutputMarkupId(true);

        Label<String> selectedLabel = new Label<String>("selectedValue",searchTextModel);
        selectedLabel.setOutputMarkupId(true);

        AjaxFallbackLink deleteLink = new AjaxFallbackLink("deleteLink") {
            public void onClick(AjaxRequestTarget target) {
                backupObject = ObjectAutoCompleteField.this.getModelObject();
                ObjectAutoCompleteField.this.setModelObject(null);
                if (target != null) {
                    target.addComponent(ObjectAutoCompleteField.this);
                    target.appendJavascript(searchTextField.getMarkupId()+".focus();");
                    target.appendJavascript(searchTextField.getMarkupId()+".select();");
                }
            }
        };
        wac.add(selectedLabel);
        wac.add(deleteLink);
        add(wac);
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.setName("span");
    }


    abstract public Iterator<T> getChoices(String input);

    /**
     * Register a component that needs to be update when the model changes, i.e.
     * the use selected an object from the suggestion list
     *
     * @param componentToUpdate the component to update
     */
    public void updateOnModelChange(Component<?> componentToUpdate) {
        componentsToUpdate.add(componentToUpdate);
    }
}
