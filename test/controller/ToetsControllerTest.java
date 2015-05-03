/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domein.Graad;
import domein.Klas;
import domein.Toets;
import mock.ContinentDaoMockFactory;
import mock.GenericDaoMockGeneric;
import mock.ToetsRepositoryMock;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jasper De Vrient
 */
public class ToetsControllerTest {

    private ToetsController controller;
    private ToetsRepositoryMock trm;
    private GenericDaoMockGeneric<Klas, Integer> klassen = new GenericDaoMockGeneric<>();
    private GenericDaoMockGeneric<Graad, Integer> graden = new GenericDaoMockGeneric<>();

    public ToetsControllerTest() {
    }

    @Before
    public void prepare() {
        graden = new GenericDaoMockGeneric<>();
        klassen = new GenericDaoMockGeneric<>();
        ContinentDaoMockFactory cdmf = new ContinentDaoMockFactory();
        
        Graad g = new Graad(1, 1);
       cdmf.setGraad(g);
      
       
        graden.insert(Integer.SIZE, g);
        Klas k = new Klas(1, "1Hw", 1);
        k.setGraad(g);
        klassen.insert(k);
        controller = new ToetsController();
        trm = ToetsRepositoryMock.creerMetEenToetsErin();
        Toets t = trm.getAll().get(0);
        t.setGraad(g);
        t.voegKlasToe(k);
  
        controller.setToetsrepository(trm);
        controller.setContinentrepository(cdmf.createMock());
        controller.setGraadrepository(graden);
    }

}
