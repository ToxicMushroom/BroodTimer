package me.toxicmushroom.broodtimer.data;

/**
 * Created by Merlijn on 27/12/2017.
 */

public class Broden {


    public Broden() {/*empty*/}

    public Broden(String broodnaam) {
        this._broodnaam = broodnaam;
    }

    private int fase1, fase2, fase3, fase4, fase5, fase6, fase7, fase8, fase9, fase10;
    private String _broodnaam;

    public void set_broodnaam(String _broodnaam) {
        this._broodnaam = _broodnaam;
    }

    public void setFases(int fase1, int fase2, int fase3, int fase4, int fase5, int fase6, int fase7, int fase8, int fase9, int fase10) {
        this.fase1 = fase1;
        this.fase2 = fase2;
        this.fase3 = fase3;
        this.fase4 = fase4;
        this.fase5 = fase5;
        this.fase6 = fase6;
        this.fase7 = fase7;
        this.fase8 = fase8;
        this.fase9 = fase9;
        this.fase10 = fase10;
    }

    public String get_broodnaam() {
        return _broodnaam;
    }

    public int getFase1() {
        return fase1;
    }

    public int getFase2() {
        return fase2;
    }

    public int getFase3() {
        return fase3;
    }

    public int getFase4() {
        return fase4;
    }

    public int getFase5() {
        return fase5;
    }

    public int getFase6() {
        return fase6;
    }

    public int getFase7() {
        return fase7;
    }

    public int getFase8() {
        return fase8;
    }

    public int getFase9() {
        return fase9;
    }

    public int getFase10() {
        return fase10;
    }

    public long getAllFases() {
        return (getFase1() + getFase2() + getFase3() + getFase4() + getFase5() + getFase6() + getFase7() + getFase8() + getFase9() + getFase10());
    }
}
