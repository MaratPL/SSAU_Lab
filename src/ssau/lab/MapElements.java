package ssau.lab;

/**
 * Created by stpal on 17.09.2015.
 */

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

class MapElements {
    @XmlElement
    public String key;
    @XmlElement
    public List<String> value;

    private MapElements() {
    } //Required by JAXB

    public MapElements(String key, List<String> value) {
        this.key = key;
        this.value = value;
    }
}