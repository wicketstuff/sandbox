package dk.test;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.Component;
import wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import wicket.extensions.markup.html.repeater.data.table.IColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.TextFilteredPropertyColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.GoAndClearFilter;
import wicket.extensions.markup.html.repeater.data.table.filter.FilteredAbstractColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.ChoiceFilteredPropertyColumn;
import wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.model.IModel;
import wicket.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.Serializable;

public class TestPage extends WebPage {

    private Label label_1 = new Label("label_1");
    private static Label label_2;

    static {
        label_2 = new Label("label_2");
    }


    public void TestPage() {
        add(new Label("testid", "some labelname")); 

        add(new Label("someid", "another label"));

        String string = "list";
        add(new Label(string));
    }

}
