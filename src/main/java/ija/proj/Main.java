/**
 * @author Marek Bitomský - xbitom00
 * @author Ondrěj Darmopil - xdarmo03
 * @author Vladimír Horák - xhorak72
 *
 * Soubor pro implementaci spuštění aplikace.
 */

package ija.proj;

/**
 * Třída, která slouží jako pomocná třída pro spuštění celé aplikace,
 * kvůli problému s generováním některých výsledných souborů.
 */
public class Main {
    /**
     * Hlavní funkce, která spustí celou aplikaci
     * Nutnost pro překlad mít další soubor, který neextenduje Application.
     *
     * @param args Argumenty
     */
    public static void main(String[] args) {
        App.main(args);
    }
}
