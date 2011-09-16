package objorient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/*
 * 7.1 Design the data structures for a generic deck of cards. Explain how you would
 *     subclass it to implement particular card games.
 */
public final class Trump {

	public enum Suit {
		HEARTS, SPADES, CLUBS, DIAMONDS
	}

	// Not ideal, but 0 or -1 are too likely to be valid values in some games
	public static final int WILD = Integer.MIN_VALUE;

	public enum Rank {
		ACE(1),
		TWO(2),
		THREE(3),
		FOUR(4),
		FIVE(5),
		SIX(6),
		SEVEN(7),
		EIGHT(8),
		NINE(9),
		TEN(10),
		JACK(11),
		QUEEN(12),
		KING(13),
		
		// NOTE: Having a JOKER requires everyone who doesn't use it
		//       to exclude it when using values()
		JOKER(WILD);
		
		private int value;

		Rank(int value) {
			this.value = value;
		}

		public int getDefault() {
			return value;
		}
	}

	public static final class Card implements Comparable<Card> {

		public final Suit suit;
		public final Rank rank;

		private final int value;

		private Card(Suit suit, Rank rank) {
			this(suit, rank, rank.getDefault());
		}

		private Card(Suit suit, Rank rank, final int value) {
			this.suit = suit;
			this.rank = rank;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Card [suit=" + suit + ", rank=" + rank + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((rank == null) ? 0 : rank.hashCode());
			result = prime * result + ((suit == null) ? 0 : suit.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Card other = (Card) obj;
			if (rank != other.rank)
				return false;
			if (suit != other.suit)
				return false;
			return true;
		}

		public int compareTo(Card other) {
			return value - other.value;
		}
	}

	public static interface Deck {
		public Card pull();
	}
	
	public static final class Utilities {
		
		private Utilities() {}
		
		public static Card drawFrom(List<Card> cards) {
			if (cards.isEmpty())
				throw new EmptyDeckException();
			
			Random seed = new Random();
			synchronized (cards) {
				int toPull = seed.nextInt(cards.size());
				Card deal = cards.get(toPull);
				cards.remove(toPull);
				return deal;
			}
		}
		
		public static boolean isBlack(Suit suit) {
			return suit == Suit.SPADES || suit == Suit.CLUBS;
		}
		
		public static boolean isRed(Suit suit) {
			return suit == Suit.DIAMONDS || suit == Suit.HEARTS;
		}

		public static boolean isSameColor(Suit left, Suit right) {
			return (Utilities.isBlack(left) && Utilities.isBlack(right))
					|| (Utilities.isRed(left) && Utilities.isRed(right));
		}
	}
	
	public static class DeckOf52 implements Deck {
		
		// Having a shared List is dangerous, since you cannot require protection
		private final ArrayList<Card> cards = new ArrayList<Card>(52);

		protected DeckOf52(ValueSetter func) {
			for (Suit suit : Suit.values())
				for (Rank rank : Rank.values()) {
					if (rank == Rank.JOKER)
						continue;
					cards.add(new Card(suit, rank, func.getValue(suit, rank)));
				}
		}
		
		public Card pull() {
			return Utilities.drawFrom(cards);
		}
		
		protected static interface ValueSetter {
			int getValue(Suit suit, Rank rank);
		}
	}
	
	@SuppressWarnings("serial")
	public static final class EmptyDeckException extends RuntimeException { };
	
	@SuppressWarnings("serial")
	public static final class InvalidHandException extends RuntimeException { };

	public static final class BlackjackDeck extends DeckOf52 {

		public static final int BLACKJACK = 21;

		public BlackjackDeck() {
			super(new ValueSetter() {
				public int getValue(Suit suit, Rank rank) {
					switch (rank) {
					case ACE:
						return 11;
					case JACK:
					case QUEEN:
					case KING:
						return 10;

					default:
						return rank.getDefault();

					}
				}
			});
		}
	}

	public static final class HeartsDeck extends DeckOf52 {
		
		public HeartsDeck() {
			super(new ValueSetter() {
				public int getValue(Suit suit, Rank rank) {
					if (suit == Suit.HEARTS)
						return 1;
					if (suit == Suit.SPADES && rank == Rank.QUEEN)
						return 13;
					return 0;
				}
			});
		}
	}
	
	public static final class Hearts {
		
		private Hearts() {}
		
		public static int score(List<Card> cards) {
			int score = 0;
			for (Card card : cards)
				score += card.getValue();
			return score;
		}
		
		public static boolean shootTheMoon(List<Card> cards) {
			return score(cards) == 26;
		}
	}

	public static final class EuchreDeck implements Deck {
		
		public static final Rank[] validRanks = {
			Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE
		};
		
		private final ArrayList<Card> cards = new ArrayList<Card>(24);
		
		public EuchreDeck() {
			for (Suit suit : Suit.values())
				for (Rank rank : validRanks)
					cards.add(new Card(suit, rank, rank == Rank.ACE ? Rank.KING.getDefault() + 1 : rank.getDefault()));
		}

		public Card pull() {
			return Utilities.drawFrom(cards);
		}
	}
	
	public static final class Euchre {
		
		private Euchre() {}

		public static boolean isTrump(Suit trump, Card card) {
			if (card.suit == trump)
				return true;
			return isLeftBower(trump, card);
		}

		public static boolean isLeftBower(Suit trump, Card card) {
			if (card.rank == Rank.JACK
					&& card.suit != trump
					&& Utilities.isSameColor(trump, card.suit))
				return true;
			return false;
		}

		public static Card getWinner(Suit trump, Card lead, Card... cards) {
			if (cards.length < 1)
				throw new InvalidHandException();
			
			Comparator<Card> comparator = getTrumpComparator(trump);
			Card win = lead;
			for (Card card : cards)
				win = Collections.max(Arrays.asList(new Card[] { win, card }), comparator);
			return win;
		}
		
		private static Comparator<Card> getTrumpComparator(final Suit trump) {
			return new Comparator<Card>() {
				public int compare(Card left, Card right) {
					if (isTrump(trump, left)) {
						if (isTrump(trump, right)) {
							if (left.rank == Rank.JACK) {
								if (right.rank == Rank.JACK)
									return isLeftBower(trump, left) ? -1 : 1;
								return 1;
							} else if (isLeftBower(trump, right))
								return -1;
							return left.compareTo(right);
						}
						return 1;
					} else if (isTrump(trump, right))
						return -1;
					return left.compareTo(right);
				}
			};
		}
		
	}
}
