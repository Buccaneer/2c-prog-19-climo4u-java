package gui.toets;

import dto.ToetsDto;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.layout.VBox;

public class ToetsenDetailPanelController extends VBox implements InvalidationListener
{

    @Override
    public void invalidated(Observable o)
    {
        if (o == null)
            return;
        ReadOnlyObjectProperty<ToetsDto> rood = (ReadOnlyObjectProperty<ToetsDto>) o;
        if (rood.get() != null)
            setDisable(false);
        else
            setDisable(true);
    }
    
}