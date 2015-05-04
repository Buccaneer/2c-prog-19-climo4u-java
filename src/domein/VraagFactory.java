/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import dto.VraagDto;
import java.util.HashSet;
import java.util.Random;
import persistentie.GenericDao;

/**
 *
 * @author Jan
 */
public class VraagFactory {

    public static ToetsVraag maakVraag(VraagDto vraag, GenericDao<Klimatogram, String> klimatogramRepository) {
        if (vraag.isDeterminatieVraag()) {
            DeterminatieVraag v = new DeterminatieVraag();
            v.setId(new Random().nextInt());
            v.setBeschrijving(vraag.getBeschrijving());
            Klimatogram klim = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
            v.setKlimatogram(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            return v;
        }
        if (vraag.isGraadDrieVraag()) {
            LocatieVraag v = new LocatieVraag();
            v.setId(new Random().nextInt());
            v.setBeschrijving(vraag.getBeschrijving());
            HashSet<Klimatogram> klim = new HashSet<>();
            vraag.getKlimatogrammen().stream().forEach(kl -> {
                Klimatogram k = klimatogramRepository.get(kl.getLocatie());
                klim.add(k);
            });
            v.setKlimatogrammen(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            return v;
        }
        if (vraag.isGraadEenVraag()) {
            LosseVraag v = new LosseVraag();
            v.setId(new Random().nextInt());
            v.setSubvragenLijst(vraag.getSubvragen());
            v.setBeschrijving(vraag.getBeschrijving());
            Klimatogram klim = klimatogramRepository.get(vraag.getKlimatogrammen().get(0).getLocatie());
            v.setKlimatogram(klim);
            v.setTeBehalenPunten(vraag.getPuntenTeVerdienen());
            return v;
        }
        return null;
    }
}
