/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.determinatie;

import dto.VegetatieTypeDto;

/**
 *
 * @author Jan
 */
public enum Fotos {
    LEEG("","(url ingeven)"),
    TOENDRA("../../Content/001.png", "Toendra"),
    TAIGA("../../Content/002.png", "Taiga"),
    WOESTIJNMIDDELBREEDTEN("../../Content/003.jpg", "Woestijn van de middelbreedten"),
    IJSWOESTIJN("../../Content/004.png", "IJswoestijn"),
    WOESTIJNTROPEN("../../Content/004.png", "Woestijn van de tropen"),
    STEPPE("../../Content/005.png", "Steppe"),
    EMENGDWOUD("../../Content/006.jpg", "Gemengd woud"),
    LOOFBOS("../../Content/007.png", "Loofbos"),
    SUBTROPISCHREGENWOUD("../../Content/008.jpg", "Substropisch regenwoud"),
    HARDBLADIGEMIDDELBREEDTEN("../../Content/009.jpg", "Hardbladige-vegetatie van de centrale middelbreedten"),
    HARDBLADIGESUBTROPEN("../../Content/010.jpg", "Hardbladige-vegetatie van de subtropen"),
    SUBTROPISCHSAVANNE("../../Content/011.jpg", "Subtropische savanne"),
    TROPISCHSAVANNE("../../Content/012.png", "Tropische savanne"),
    TROPISCHREGENWOUD("../../Content/013.png", "Tropisch regenwoud");

    private String waarde;
    private String url;

    private Fotos(String url, String waarde) {
        this.waarde = waarde;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return waarde;
    }

    public static Fotos geefFotos(VegetatieTypeDto foto) {
        for (Fotos param : values()) {
            if (param.getUrl().equalsIgnoreCase(foto.getFoto())) {
                return param;
            }
        }
        return LEEG;
    }
}
