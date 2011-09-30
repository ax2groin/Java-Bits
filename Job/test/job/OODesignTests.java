package job;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import job.objorient.CallCenter.Call;
import job.objorient.CallCenter.CallCenterSystem;
import job.objorient.CallCenter.CallHandler;
import job.objorient.CallCenter.CallStatus;
import job.objorient.CallCenter.EmployeeType;
import job.objorient.Trump.BlackjackDeck;
import job.objorient.Trump.Card;
import job.objorient.Trump.Deck;
import job.objorient.Trump.EmptyDeckException;
import job.objorient.Trump.Euchre;
import job.objorient.Trump.EuchreDeck;
import job.objorient.Trump.Hearts;
import job.objorient.Trump.HeartsDeck;
import job.objorient.Trump.Rank;
import job.objorient.Trump.Suit;

import org.junit.Test;

public class OODesignTests {

    // 7.1
    @Test
    public void testDeckOfCards() {
        // Blackjack
        Deck deck = new BlackjackDeck();
        ArrayList<Card> cards = new ArrayList<Card>();

        // No duplicate cards
        for (int i = 0; i < 52; i++) {
            Card card = deck.pull();
            assertFalse(cards.contains(card));
            cards.add(card);
        }

        checkForPullException(deck);
    }

    // 7.1
    @Test
    public void testHeartsDeck() {
        // Hearts, followed by some example functions
        Deck deck = new HeartsDeck();
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 52; i++) {
            Card card = deck.pull();
            assertFalse(cards.contains(card));
            cards.add(card);
        }
        checkForPullException(deck);

        // Total possible points is 26
        assertEquals(Integer.valueOf(26), Integer.valueOf(Hearts.score(cards)));
        assertTrue(Hearts.shootTheMoon(cards));

        // The value of the Black Lady is 13
        Card blackLady = null;
        for (Card card : cards)
            if (card.suit == Suit.SPADES && card.rank == Rank.QUEEN)
                blackLady = card;
        assertNotNull(blackLady);
        cards.remove(blackLady);
        assertEquals(Integer.valueOf(13), Integer.valueOf(Hearts.score(cards)));
        assertFalse(Hearts.shootTheMoon(cards));
    }

    // 7.1
    @Test
    public void testEuchreDeck() {
        Deck deck = new EuchreDeck();
        List<Rank> validRanks = Arrays.asList(EuchreDeck.validRanks);
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 24; i++) {
            Card card = deck.pull();
            assertFalse(cards.contains(card));
            assertTrue(validRanks.contains(card.rank));
            cards.add(card);
        }
        checkForPullException(deck);

        Suit trump = Suit.CLUBS;

        // For testing how non-trump cards play out
        List<Card> allDiamonds = new ArrayList<Card>();

        // For testing how trump cards play out against one another
        List<Card> allTrump = new ArrayList<Card>();
        Card rightBower = null;
        Card leftBower = null;

        for (Card card : cards) {
            if (card.rank == Rank.JACK) {
                if (card.suit == Suit.SPADES) {
                    assertTrue(Euchre.isTrump(trump, card));
                    assertTrue(Euchre.isLeftBower(trump, card));
                    leftBower = card;
                } else if (card.suit == Suit.DIAMONDS) {
                    assertTrue(Euchre.isTrump(Suit.HEARTS, card));
                    assertTrue(Euchre.isLeftBower(Suit.HEARTS, card));
                    allDiamonds.add(card);
                } else if (card.suit == trump)
                    rightBower = card;
            } else if (card.suit == trump) {
                assertTrue(Euchre.isTrump(trump, card));
                allTrump.add(card);
            } else {
                assertFalse(Euchre.isTrump(trump, card));
                if (Suit.DIAMONDS == card.suit)
                    allDiamonds.add(card);
            }
        }

        // Right Bower always wins
        assertEquals(rightBower, Euchre.getWinner(trump, allTrump.get(0),
                allTrump.get(1), leftBower, rightBower));
        assertEquals(rightBower, Euchre.getWinner(trump, allDiamonds.get(0),
                allDiamonds.get(1), allDiamonds.get(2), allDiamonds.get(3),
                allDiamonds.get(4), allDiamonds.get(5), rightBower));

        // Left Bower always wins in absence of Right Bower
        assertEquals(leftBower, Euchre.getWinner(trump, allTrump.get(0),
                allTrump.get(1), allTrump.get(2), leftBower));
        assertEquals(leftBower, Euchre.getWinner(trump, allDiamonds.get(0),
                allDiamonds.get(1), allDiamonds.get(2), allDiamonds.get(3),
                allDiamonds.get(4), allDiamonds.get(5), leftBower));

        // Ace wins without bowers
        assertEquals(Rank.ACE, Euchre.getWinner(trump, allTrump.get(0),
                allTrump.get(1), allTrump.get(2), allTrump.get(3),
                allTrump.get(4)).rank);

        // Off-color suits rank with Ace high
        assertEquals(Rank.ACE, Euchre.getWinner(trump, allDiamonds.get(0),
                allDiamonds.get(1), allDiamonds.get(2), allDiamonds.get(3),
                allDiamonds.get(4), allDiamonds.get(5)).rank);
    }

    // 7.1, Deck testing utility
    private void checkForPullException(Deck deck) {
        try {
            deck.pull();
            fail();
        } catch (EmptyDeckException ede) {
            // expected result
        }
    }

    // 7.2
    @Test
    public void testCallCenter() {
        CallCenterSystem system = new CallCenterSystem();
        system.start();

        TestHandler agent1 = new TestHandler("agent1", system);
        TestHandler agent2 = new TestHandler("agent2", system);
        TestHandler agent3 = new TestHandler("agent3", system);
        TestHandler tl = new TestHandler("techLead", system);
        TestHandler pm = new TestHandler("prodMgr", system);
        system.login(agent1, EmployeeType.FRESHER);
        system.login(agent2, EmployeeType.FRESHER);
        system.login(agent3, EmployeeType.FRESHER);
        system.login(tl, EmployeeType.TL);
        system.login(pm, EmployeeType.PM);

        assertTrue(system.take(new TestCall()));
        assertTrue(system.take(new TestCall()));
        assertTrue(system.take(new TestCall()));

        // With only three agents, this will escalate to TL
        assertTrue(system.take(new TestCall()));

        // With the TL busy, this should escalate to PM
        assertTrue(system.take(new TestCall()));

        // TODO: Using sleep() in a Test! Sign of bad design.
        // See that finished agent picks up next call.
        agent1.release = true;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            fail();
            e.printStackTrace();
        }
        assertTrue(system.take(new TestCall()));

        agent1.release = true;
        agent2.release = true;
        agent3.release = true;
        tl.release = true;
        pm.release = true;

        // Wait for threads to finish up and stop blocking.
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            fail();
            e.printStackTrace();
        }
        assertEquals(Integer.valueOf(2), Integer.valueOf(agent1.callsHandled));
        assertEquals(Integer.valueOf(1), Integer.valueOf(agent2.callsHandled));
        assertEquals(Integer.valueOf(1), Integer.valueOf(agent3.callsHandled));
        assertEquals(Integer.valueOf(1), Integer.valueOf(tl.callsHandled));
        assertEquals(Integer.valueOf(1), Integer.valueOf(pm.callsHandled));

        // Shutdown
        system.stop();
    }

    private final class TestHandler implements CallHandler {

        private final String uid;
        private final CallCenterSystem system;
        int callsHandled;

        volatile boolean release;

        private volatile boolean handlingCall;

        TestHandler(String uid, CallCenterSystem system) {
            this.uid = uid;
            this.system = system;
        }

        public boolean handle(final Call call) {
            if (handlingCall)
                return false;

            final CallHandler agent = this;
            // Cheap handler for now. A more robust system would queue this off
            // differently
            // in order to avoid the sleep() mechanism.
            Thread t = new Thread(new Runnable() {
                public void run() {
                    handlingCall = true;
                    callsHandled++;
                    try {
                        while (!release)
                            Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        handlingCall = false;
                        system.log(agent, call, CallStatus.FINISHED);
                        release = false;
                    }
                }
            });
            t.start();
            return true;
        }

        public boolean isOpen() {
            return handlingCall;
        }

        public String getUID() {
            return uid;
        }
    }

    private final class TestCall implements Call {

        private final long initTime = System.nanoTime();

        public long getInitTime() {
            return initTime;
        }
    }
}
