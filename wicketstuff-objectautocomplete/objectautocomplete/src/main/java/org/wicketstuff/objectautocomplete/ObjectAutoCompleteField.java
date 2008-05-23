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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * @author roland
 * @since May 21, 2008
 */
abstract public class ObjectAutoCompleteField<T,I extends Serializable> extends Panel<I> {
    private TextField<String> searchTextField;

    private List<Component<?>> componentsToUpdate = new ArrayList<Component<?>>();

    public ObjectAutoCompleteField(String id) {
        this(id,new Model<I>());
    }

    public ObjectAutoCompleteField(String id, IModel<I> iModel) {
        super(id, iModel);
        init();
    }


    private void init() {
        setOutputMarkupId(true);

        Model<String> labelModel = new Model<String>();
        final Label<String> selectedLabel = addSelectionPanel(labelModel);

        searchTextField = new TextField<String>("search",labelModel) {
            @Override
            public boolean isVisible() {
                return ObjectAutoCompleteField.this.getModelObject() == null;
            }
        };
        searchTextField.setOutputMarkupId(true);

        // this disables Firefox autocomplete
        searchTextField.add(new SimpleAttributeModifier("autocomplete", "off"));
        final HiddenField<I> objectField = new HiddenField<I>("hiddenId",getModel());
        objectField.setOutputMarkupId(true);
        add(objectField);

        searchTextField.add(new AjaxFormSubmitBehavior("onchange")
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
        });
        searchTextField.add(new ObjectAutoCompleteBehavior<T>(objectField) {
            @Override
            protected Iterator<T> getChoices(String input) {
                return ObjectAutoCompleteField.this.getChoices(input);
            }
        });

        add(searchTextField);
    }

    private Label<String> addSelectionPanel(final Model<String> descriptionModel) {
        final WebMarkupContainer wac = new WebMarkupContainer("selectedPanel") {
            @Override
            public boolean isVisible() {
                return ObjectAutoCompleteField.this.getModelObject() != null;
            }
        };
        wac.setOutputMarkupId(true);

        final Label<String> selectedLabel = new Label<String>("selectedValue",descriptionModel);
        selectedLabel.setOutputMarkupId(true);

        AjaxFallbackLink deleteLink = new AjaxFallbackLink("deleteLink") {
            public void onClick(AjaxRequestTarget target) {
                ObjectAutoCompleteField.this.setModelObject(null);
                descriptionModel.setObject("");
                if (target != null) {
                    target.addComponent(ObjectAutoCompleteField.this);
                }
            }
        };
        wac.add(selectedLabel);
        wac.add(deleteLink);
        add(wac);
        return selectedLabel;
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
