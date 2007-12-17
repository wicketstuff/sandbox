package org.wicketstuff.dojo.examples.yahoomap;

import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.dojo.examples.WicketExamplePage;
import org.wicketstuff.dojo.markup.html.yahoomap.DojoMapData;
import org.wicketstuff.dojo.markup.html.yahoomap.DojoYahooMapPanel;

public class DojoYahooMapPanelSample extends WicketExamplePage {

    public DojoYahooMapPanelSample() {
        
        add(new DojoYahooMapPanel("yahoomap") {
            @Override
            protected List<DojoMapData> getDataMarkerList(String descriptionId) {
                List<DojoMapData> dataList = new ArrayList<DojoMapData>();
                dataList.add(new DojoMapData(48.88256, 2.627097, null, new DojoYahooMapDescriptionPanelSample(descriptionId, "JBQ birth place!")));
                dataList.add(new DojoMapData(44.351842, 2.032454, null, new DojoYahooMapDescriptionPanelSample(descriptionId, "Doume birth place!")));
                dataList.add(new DojoMapData(45.899805, 6.124861, null, new DojoYahooMapDescriptionPanelSample(descriptionId, "Greg birth place!")));
                return dataList;
            }
        });
    }
    
}
