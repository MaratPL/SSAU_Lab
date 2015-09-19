package ssau.lab;

/**
 * Created by stpal on 17.09.2015.
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


    class MapAdapter extends XmlAdapter<MapElements[], Map<String, List<String>>> {
        public MapElements[] marshal(Map<String, List<String>> arg0) throws Exception {
            MapElements[] mapElements = new MapElements[arg0.size()];
            int i = 0;
            for (Map.Entry<String, List<String>> entry : arg0.entrySet())
                mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

            return mapElements;
        }

        public Map<String, List<String>> unmarshal(MapElements[] arg0) throws Exception {
            Map<String, List<String>> r = new HashMap<String, List<String>>();
            for (MapElements mapelement : arg0)
                r.put(mapelement.key, mapelement.value);
            return r;
        }
    }