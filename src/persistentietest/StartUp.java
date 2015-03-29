package persistentietest;

import persistentie.*;
import domein.*;
import java.util.List;

/**
 *
 * @author Jasper De Vrient
 */
public class StartUp {

    public static void main(String[] args) {
        System.out.println("hier");
        GenericDao<Continent, String> dao = new GenericDaoJpa<>(Continent.class);

       
        
        List<Continent> continents = dao.getAll();

        
  
            for (Continent c : continents)
            System.out.println(c.getNaam());

        GenericDaoJpa.closePersistency();
    }
}
