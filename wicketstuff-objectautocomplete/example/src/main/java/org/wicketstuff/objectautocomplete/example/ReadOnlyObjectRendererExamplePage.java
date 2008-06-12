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
package org.wicketstuff.objectautocomplete.example;

import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.Component;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteBuilder;
import org.wicketstuff.objectautocomplete.ReadOnlyObjectRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

/**
 * Homepage
 */
public class ReadOnlyObjectRendererExamplePage extends BaseExamplePage<Car,Integer> {

	private static final long serialVersionUID = 1L;

    public ReadOnlyObjectRendererExamplePage() {
        super(new Model<Integer>());
    }

    @Override
    List<Car> getAllChoices() {
        return CarRepository.allCars();
    }

    @Override
    protected void initBuilder(ObjectAutoCompleteBuilder<Car,Integer> pBuilder) {
        super.initBuilder(pBuilder);
        pBuilder.readOnlyObjectRenderer(new ReadOnlyObjectRenderer<Integer>() {
            public Component getRenderedObject(String id, IModel<Integer> pIModel, Model<String> pSearchTextModel) {
                Fragment frag =  new Fragment(id,"readOnlyView", ReadOnlyObjectRendererExamplePage.this);
                frag.add(new Label<String>("search",pSearchTextModel));
                frag.add(new Label<Integer>("id",pIModel));
                return frag;
            }
        });
    }

    @Override
    String getCodeSample() {
        return "ObjectAutoCompleteField<Car,Integer> field =\n" +
                "   new ObjectAutoCompleteBuilder<Car>(\n" +
                "      new AutoCompletionChoicesProvider() {\n" +
                "           Iterator<Car> getChoices(String input) {\n" +
                "                // Get all possible choices for the given \n" +
                "                // input string\n" +
                "                // ...\n" +
                "                return iterator;\n" +
                "           }\n" +
                "      }\n" +
                "   ).build(\"acField\",new Model<Integer>());";
    }

    public Iterator getChoices(String input) {
        List<Car> cars = getAllChoices();
        List<Car> ret = new ArrayList<Car>();
        for (Car car : cars) {
            if (car.getName().toLowerCase().startsWith(input.toLowerCase())) {
                ret.add(car);
            }
        }
        return ret.iterator();
    }
}