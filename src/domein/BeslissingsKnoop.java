package domein;

import dto.DeterminatieKnoopDto;
import dto.ParameterDto;
import dto.VergelijkingDto;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "determinatieknopen")
public class BeslissingsKnoop extends DeterminatieKnoop {

    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "JaKnoop_DeterminatieKnoopId")
    private DeterminatieKnoop juistKnoop;
    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "NeeKnoop_DeterminatieKnoopId")
    private DeterminatieKnoop foutKnoop;
    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Vergelijking_VergelijkingId")
    private Vergelijking vergelijking;

    public BeslissingsKnoop() {
        super();
        juistKnoop = new ResultaatBlad();
        foutKnoop = new ResultaatBlad();
        vergelijking = new Vergelijking();
    }

    public BeslissingsKnoop(int id) {
        super(id);
        juistKnoop = new ResultaatBlad();
        foutKnoop = new ResultaatBlad();
        vergelijking = new Vergelijking();
    }

    @Override
    public void wijzigKnoop(DeterminatieKnoopDto knoop) {
        try {
            if (knoop.getId() == getId())
                wijzigAttributen(knoop);
            else {
                int juistOmzetten = moetOmzetten(juistKnoop, knoop);
                int foutOmzetten = moetOmzetten(foutKnoop, knoop);
                if (juistOmzetten != 0)
                    if (juistOmzetten == -1)
                        juistKnoop = new BeslissingsKnoop();
                    else
                        juistKnoop = new ResultaatBlad();
                else if (foutOmzetten != 0)
                    if (foutOmzetten == - 1)
                        foutKnoop = new BeslissingsKnoop();
                    else
                        foutKnoop = new ResultaatBlad();
                if (juistOmzetten == foutOmzetten && juistOmzetten == 0) {
                    juistKnoop.wijzigKnoop(knoop);
                    foutKnoop.wijzigKnoop(knoop);
                }
            }
        } catch (IllegalArgumentException iae) {
// Todo: nested exceptions...
            throw new IllegalArgumentException(iae);
        }
    }

    /**
     * Wijzig de attributen van deze knoop.
     *
     * @param knoop
     */
    private void wijzigAttributen(DeterminatieKnoopDto knoop) {
        VergelijkingDto vergelijkingDto = knoop.getVergelijking();
        vergelijking.setOperator(vergelijkingDto.getOperator());
        boolean linksGevonden = false;
        boolean rechtsGevonden = false;
        for (Parameter par : ParameterFactory.geefParameters()) {
            if (par.getNaam().equals(knoop.getVergelijking().getLinks().getNaam())) {
                linksGevonden = true;
                vergelijking.setLinkerParameter(par);
            }
            if (par.getNaam().equals(knoop.getVergelijking().getRechts().getNaam())) {
                rechtsGevonden = true;
                vergelijking.setRechterParameter(par);
            }
        }
        if (!linksGevonden)
            vergelijking.setLinkerParameter(ParameterFactory.maakConstanteParameter(knoop.getVergelijking().getLinks().getWaarde()));
        if (!rechtsGevonden)
            vergelijking.setRechterParameter(ParameterFactory.maakConstanteParameter(knoop.getVergelijking().getRechts().getWaarde()));

    }

    @Override
    public DeterminatieKnoopDto maakDtoAan() {
        DeterminatieKnoopDto dto = new DeterminatieKnoopDto();
        dto.setId(getId());
        dto.setVergelijking(new VergelijkingDto(new ParameterDto(vergelijking.getLinkerParameter().getNaam(), vergelijking.getLinkerParameter().getWaarde(), vergelijking.getLinkerParameter() instanceof ConstanteParameter), vergelijking.getOperator(), new ParameterDto(vergelijking.getRechterParameter().getNaam(), vergelijking.getRechterParameter().getWaarde(), vergelijking.getRechterParameter() instanceof ConstanteParameter)));
        dto.toBeslissingsKnoop();

        DeterminatieKnoopDto ja = juistKnoop.maakDtoAan();
        DeterminatieKnoopDto nee = foutKnoop.maakDtoAan();
        ja.setOuder(dto);
        nee.setOuder(dto);
        dto.setJa(ja);
        dto.setNee(nee);
        return dto;
    }

    /**
     * <pre>Remark: moet private worden maar momenteel public door test.</pre>
     * Kijkt of de ouder een van zijn kinderen moet omzetten.
     *
     * @return 3 gevallen: <ul><li>
     * -1: ResultaatBlad --> BeslissingsKnoop
     * </li><li>0: niet</li>
     * <li>1: BeslissingsKnoop --> ResultaatBlad</li></ul>
     */
    public int moetOmzetten(DeterminatieKnoop kind, DeterminatieKnoopDto knoop) {
        if (kind.getId() == knoop.getId()) {
            if (knoop.isResultaatBlad() && kind instanceof BeslissingsKnoop)
                return 1;

            if (knoop.isBeslissingsKnoop() && kind instanceof ResultaatBlad)
                return -1;

            return 0;
        }
        return 0;
    }

    /**
     * Valideert of deze knoop en al zijn kinderen in orde zijn. Zijn er null
     * velden die niet null mogen zijn
     */
    @Override
    public void valideer() {
        if (vergelijking == null || vergelijking.getLinkerParameter() == null || vergelijking.getRechterParameter() == null || vergelijking.getLinkerParameter().getNaam() == null || vergelijking.getLinkerParameter().getNaam().isEmpty() || vergelijking.getRechterParameter().getNaam() == null || vergelijking.getRechterParameter().getNaam().isEmpty() || vergelijking.getOperator() == null)
            throw new IllegalArgumentException("Knoop " + getId() + " moet correct ingevuld zijn");
        juistKnoop.valideer();
        foutKnoop.valideer();
    }

    public DeterminatieKnoop getJuistKnoop() {
        return juistKnoop;
    }

    public void setJuistKnoop(DeterminatieKnoop juistKnoop) {
        this.juistKnoop = juistKnoop;
    }

    public DeterminatieKnoop getFoutKnoop() {
        return foutKnoop;
    }

    public void setFoutKnoop(DeterminatieKnoop foutKnoop) {
        this.foutKnoop = foutKnoop;
    }

    public Vergelijking getVergelijking() {
        return vergelijking;
    }

    public void setVergelijking(Vergelijking vergelijking) {
        if (vergelijking == null)
            throw new IllegalArgumentException("Vergelijking mag niet null zijn");
        this.vergelijking = vergelijking;
    }

}
