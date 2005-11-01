/*
 * $Id$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.addons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import wicket.PageParameters;
import wicket.RequestCycle;
import wicket.addons.db.Addon;
import wicket.addons.db.Category;
import wicket.addons.utils.AbstractDataList;
import wicket.addons.utils.AddonListEntry;
import wicket.addons.utils.CategoryDropDownChoice;
import wicket.addons.utils.CategoryDropDownChoice.CategoryOption;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.DropDownChoice;
import wicket.markup.html.form.Form;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.PageableListView;
import wicket.markup.html.navigation.paging.PagingNavigator;
import wicket.model.Model;
import wicket.model.PropertyModel;
import wicket.util.string.StringValueConversionException;

/**
 * @author Juergen Donnerstag
 */
public final class NewAndUpdatedAddons extends BaseHtmlPage /* AuthenticateHtmlPage */
{
    private final PageableListView updatedAddons;
    private final AddonDataList model;
    private Long categoryId = null;
    private int sortId = 3;
    
    /**
     * Constructor
     * @param parameters
     */
    public NewAndUpdatedAddons(final PageParameters parameters)
    {
        super(parameters, "New and updated add-ons");
        
        if ((parameters != null) && (parameters.containsKey("category")))
        {
            try
            {
                this.categoryId = new Long(parameters.getInt("category"));
            }
            catch (StringValueConversionException ex)
            {
                ; // silently ignore
            }
        }
        
        add(new SelectForm("categoryForm"));
        
        this.model = new AddonDataList();
        this.updatedAddons = new PageableListView("updatedAddons", model, 5)
        {
            public void populateItem(final ListItem listItem)
            {
                final Addon addon = (Addon)listItem.getModelObject();
                final AddonListEntry value = new AddonListEntry("info", addon);
                listItem.add(value);
            }
        };

        add(updatedAddons);
        add(new Label("addonCount", new PropertyModel(new Model(model), "size")));
        add(new PagingNavigator("pageTableNav1", updatedAddons));
        add(new PagingNavigator("pageTableNav2", updatedAddons));
    }
    
    public final class SelectForm extends Form
    {
        private final CategoryDropDownChoice categories;
        private final DropDownChoice sortDDC;
        
        public SelectForm(final String componentName)
        {
            super(componentName);
            
            categories = new CategoryDropDownChoice("categories", categoryId);
            add(categories);
            
            final List sortOptions = new ArrayList();
            sortOptions.add(new SortOption("sort on name", 0));
            sortOptions.add(new SortOption("sort on license", 1));
            sortOptions.add(new SortOption("sort on date created", 2));
            sortOptions.add(new SortOption("sort on date modified", 3));
            sortOptions.add(new SortOption("sort on rating", 4));
            sortOptions.add(new SortOption("sort on popularity/clicks", 5));
            sortDDC = new DropDownChoice("sort", new Model((Serializable)sortOptions.get(sortId)), sortOptions);
            add(sortDDC);
        }
        
        /**
         * Show the resulting valid edit
         * @param cycle The request cycle
         */
        public final void onSubmit()
        {
            final CategoryOption category = (CategoryOption)categories.getModelObject();
            final SortOption sortOrder = (SortOption)sortDDC.getModelObject();

            if (category.getCategory() != null)
            {
                NewAndUpdatedAddons.this.categoryId = category.getCategory().getId();
            }
            NewAndUpdatedAddons.this.sortId = sortOrder.getSortOrder();

            NewAndUpdatedAddons.this.modelChanging();
            model.clear();
            NewAndUpdatedAddons.this.modelChanged();
            
            final RequestCycle cycle = getRequestCycle();
            cycle.setResponsePage(NewAndUpdatedAddons.this);
            cycle.setRedirect(false);
        }
    }
    
    private class SortOption implements Serializable
    {
        private final String text;
        private final int sortOrder;
        
        public SortOption(final String text, final int sortOrder)
        {
            this.text = text;
            this.sortOrder = sortOrder;
        }
        
        public int getSortOrder()
        {
            return sortOrder;
        }
        
        public String toString()
        {
            return text;
        }
    }
    
    public class AddonDataList extends AbstractDataList
    {
        private Category category;
        
		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalSize()
		 */
		protected int getInternalSize()
		{
            return getAddonService().getAddonCountByCategory(category).intValue();
		}

		/**
		 * @see wicket.addons.utils.AbstractDataList#getInternalData(int, int)
		 */
		protected List getInternalData(int fromPos, int pageSize)
		{
            this.firstIndex = updatedAddons.getStartIndex();
            this.pageSize = updatedAddons.getRowsPerPage();
            final List data = getAddonService().getUpdatedAddons(firstIndex, pageSize, category, sortId);
            return data;
		}
    }
}
