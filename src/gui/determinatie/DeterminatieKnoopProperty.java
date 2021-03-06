package gui.determinatie;

import java.util.ArrayList;
import java.util.List;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Jasper De Vrient
 */
public abstract class DeterminatieKnoopProperty implements PropertySheet.Item {
    protected String categorie;
    protected String naam;
    private boolean moetNotifyDoen = false;
    protected List<PropertyVeranderdListener> listeners = new ArrayList<>();

    public DeterminatieKnoopProperty() {
    }

    public void addListener(PropertyVeranderdListener listener) {
        listeners.add(listener);
    }

    public void nietBemerken() {
        moetNotifyDoen = false;
    }
    
    public void bemerken() {
        moetNotifyDoen = true;
    }
    
    @Override
    public abstract Class<?> getType();

    @Override
    public String getCategory() {
        return categorie;
    }

    @Override
    public String getName() {
        return naam;
    }

    @Override
    public String getDescription() {
        return "Vul in!";
    }

    @Override
    public abstract Object getValue();
    @Override
    public abstract void setValue(Object o);
    protected void gewijzigd() {
        if (moetNotifyDoen)
        listeners.forEach((PropertyVeranderdListener l) -> l.isVeranderd(this));
    }

}
