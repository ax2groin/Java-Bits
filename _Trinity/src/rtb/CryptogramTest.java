package rtb;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CryptogramTest {

    @Test
    public void testStringStrip() {
        String prefix = "cataloger::virtualSources";
        String separator = "::";
        String fullString = "cataloger::virtualSources::NewButton::default_status";
        System.out.println(fullString);
        String replace = fullString.replace(prefix + separator, "");
        System.out.println(replace);
        String secondReplace = replace.substring(0, replace.indexOf(separator));
        System.out.println(secondReplace);
    }

    @Test
    @Ignore
    public void testConfirmPhrase() {
        Cryptogram cryptogram = new Cryptogram();
        cryptogram.confirmPhrase("Yrhw, N'u nw rdjy mnck vdg.", "Elan, I'm in love with you.");
        assertEquals("e", cryptogram.valueOf("y"));
        assertEquals("i", cryptogram.valueOf("n"));

        assertEquals("Elan, I'm in love with you. _om_letely in love.", cryptogram.valueOf("Yrhw, N'u nw rdjy mnck vdg. Lduzrycyrv nw rdjy."));
        cryptogram.guessLetter('L', 'C');
        cryptogram.guessLetter('z', 'p');
        assertEquals("Elan, I'm in love with you. Completely in love.", cryptogram.valueOf("Yrhw, N'u nw rdjy mnck vdg. Lduzrycyrv nw rdjy."));
    }

    @Test
    @Ignore
    public void testNewPage() {
        Cryptogram cryptogram = new Cryptogram();
        cryptogram.confirmPhrase("MFGL?? P opvvlu Mfgl?", "NALE?? I kissed Nale?");

        cryptogram.confirmPhrase("Des'ql rew we il", "You've got to be");

        cryptogram.guessLetter('n', 'c');
        cryptogram.guessLetter('y', 'h');
        cryptogram.guessLetter('s', 'u');
        cryptogram.guessLetter('r', 'g');
        cryptogram.guessLetter('a', 'm');
        cryptogram.guessLetter('c', 'f');
        cryptogram.guessLetter('x', 'r');
        cryptogram.guessLetter('k', 'w');
        cryptogram.guessLetter('j', 'p');

        System.out.println(cryptogram.valueOf("Sryl!"));
        System.out.println(cryptogram.valueOf("Keeeeee! Opno ypv fvv, Euugd Lcclnwpql Lgfm!"));
        System.out.println(cryptogram.valueOf("Ysy?"));
        System.out.println(cryptogram.valueOf("Des'ql rew we il obuupmr al. Lgfm, des'xl mew isdpmr wypv, fxl des???"));
        System.out.println(cryptogram.valueOf("Ufam vwxfpryw, yl'v gdpmr!"));
        System.out.println(cryptogram.valueOf("Yl iesryw al wypv uxlvv! Neal em, kesgu FMD keafm NYEEVL we klfx wypv wypmr?"));
        System.out.println(cryptogram.valueOf("Me! P'a mew! P'a vwsjpu! P'a ve vwsjpu!!"));
        System.out.println(cryptogram.valueOf("Ec nesxvl mew! Wypv pv fivsxu! P'a Nyfewpn Reeu! Pvy!"));
        System.out.println(cryptogram.valueOf("Kyd fxl des gpvwlmpnr we ypa?? Yl'v wyl QPGGFPM!"));
        System.out.println(cryptogram.valueOf("Me, P yfqlm'w!"));
        System.out.println(cryptogram.valueOf("Des yfql we ilgplql al! Yl'v gdpmr!!"));
        System.out.println(cryptogram.valueOf("Kyfw?? Me! ME!!"));
        System.out.println(cryptogram.valueOf("P nfm'w... ...wypmo..."));
        System.out.println(cryptogram.valueOf("P nfm'w gevl ypa... P-P wyesryw..."));
    }
}