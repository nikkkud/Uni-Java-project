package com.online.poker.service;

import com.online.poker.repository.GameState;
import com.online.poker.repository.PlayerInput;
import com.online.poker.repository.Card;
import com.online.poker.repository.User;
import com.online.poker.repository.OutputUserInfo;
import com.online.poker.service.AccessService;

import java.util.*;

public class WhoIsWinner {
    public int getWinner (GameState gamestate) {

        ArrayList<ArrayList<Card>> playersCards = gamestate.getPlayersCards();
        ArrayList<Card> cardsOnTable = gamestate.getCardsOnTable();

        ArrayList<Integer> bestCombinations = new ArrayList<>();
        ArrayList<Integer> cardsSums = new ArrayList<>();

        for (ArrayList<Card> playerHand : playersCards) {
            ArrayList<Card> cardsTogether = new ArrayList<>();
            cardsTogether.addAll(cardsOnTable);
            cardsTogether.addAll(playerHand);
            int result = evaluateHand(cardsTogether);
            bestCombinations.add(result);

            int sum = getSum(cardsTogether);
            cardsSums.add(sum);
        }


        int maxIndex = 0;

        int BestComb = bestCombinations.get(0);
        int MaxSum = cardsSums.get(0);

        for (int i = 1; i < bestCombinations.size(); i++) {
            if (bestCombinations.get(i) > BestComb) {
                maxIndex = i;
                BestComb = bestCombinations.get(i);
                MaxSum = cardsSums.get(0);
            }else if (bestCombinations.get(i) == BestComb && cardsSums.get(i) > MaxSum) {
                maxIndex = i;
                BestComb = bestCombinations.get(i);
                MaxSum = cardsSums.get(i);
            }
        }

        for (int best : bestCombinations) {
            System.out.println(best);
        }
        System.out.println("Winner: " + maxIndex);

        return maxIndex;
    }

    private int getSum(ArrayList<Card> cardsTogether){
        int sum = 0;

        for (Card card : cardsTogether) {
            if (card.Number == 1){
                sum += 14;
            } else {
                sum += card.Number;
            }
        }

        return sum;
    }

    public int evaluateHand(ArrayList<Card> cardsTogether) {
        sortCards(cardsTogether);
        if (isRoyalFlush(cardsTogether)) {
            return 10;
        } else if (isStraightFlush(cardsTogether)) {
            return 9;
        } else if (isFourOfAKind(cardsTogether)) {
            return 8;
        } else if (isFullHouse(cardsTogether)) {
            return 7;
        } else if (isFlush(cardsTogether)) {
            return 6;
        } else if (isStraight(cardsTogether)) {
            return 5;
        } else if (isThreeOfAKind(cardsTogether)) {
            return 4;
        } else if (isTwoPair(cardsTogether)) {
            return 3;
        } else if (isOnePair(cardsTogether)) {
            return 2;
        } else {
            return 1;
        }
    }

    public static void sortCards(ArrayList<Card> cards) {

        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return Character.compare(card1.Suit, card2.Suit);
            }
        });


        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {

                int suitComparison = Character.compare(card1.Suit, card2.Suit);
                if (suitComparison == 0) {

                    return Integer.compare(card1.Number, card2.Number);
                }
                return suitComparison;
            }
        });
    }

    private boolean isRoyalFlush(ArrayList<Card> cardsTogether) {

        boolean has10 = false, hasJ = false, hasQ = false, hasK = false, hasA = false;

        ArrayList<Character> cardSuits = new ArrayList<>();
        cardSuits.add('C');
        cardSuits.add('H');
        cardSuits.add('D');
        cardSuits.add('S');

        for (Character suit : cardSuits){
            for (Card card : cardsTogether) {
                if (card.Suit == suit) {
                    switch (card.Number) {
                        case 10:
                            has10 = true;
                            break;
                        case 11: // Валет
                            hasJ = true;
                            break;
                        case 12: // Дама
                            hasQ = true;
                            break;
                        case 13: // Король
                            hasK = true;
                            break;
                        case 1: // Туз
                            hasA = true;
                            break;
                    }
                }
            }
        }

        return has10 && hasJ && hasQ && hasK && hasA;
    }

    private boolean isStraightFlush(ArrayList<Card> cards) {

        for (int i = 0; i < cards.size() - 4; i++) {
            boolean isStraightFlush = true;
            for (int j = i; j < i + 4; j++) {
                if (cards.get(j).Suit != cards.get(j + 1).Suit || cards.get(j).Number + 1 != cards.get(j + 1).Number) {
                    isStraightFlush = false;
                    break;
                }
            }
            if (isStraightFlush) {
                return true;
            }
        }

        return false;
    }

    private boolean isFourOfAKind(ArrayList<Card> cardsTogether) {

        Map<Integer, Integer> valueCount = new HashMap<>();

        for (Card card : cardsTogether) {
            int value = card.Number;
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }

        for (int count : valueCount.values()) {
            if (count == 4) {
                return true;
            }
        }

        return false;

    }

    private boolean isFullHouse(ArrayList<Card> cardsTogether){

        Map<Integer, Integer> valueCount = new HashMap<>();

        for (Card card : cardsTogether) {
            int value = card.Number;
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }


        boolean threeOfAKind = false;
        boolean pair = false;

        for (int count : valueCount.values()) {
            if (count == 3) {
                threeOfAKind = true;
            } else if (count == 2) {
                pair = true;
            }
        }

        return threeOfAKind && pair;
    }

    private boolean isFlush(ArrayList<Card> cardsTogether) {

        Map<Character, Integer> suitCount = new HashMap<>();

        for (Card card : cardsTogether) {
            char suit = card.Suit;
            suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
        }


        for (int count : suitCount.values()) {
            if (count >= 5) {
                return true;
            }
        }

        return false;
    }

    private boolean isStraight(ArrayList<Card> cardsTogether) {
        HashSet<Integer> uniqueValues = new HashSet<>();

        for (Card card : cardsTogether) {
            uniqueValues.add(card.Number);
        }


        int consecutiveCount = 0;
        for (int i = 1; i <= 13; i++) {
            if (uniqueValues.contains(i)) {
                consecutiveCount++;
                if (consecutiveCount == 5) {
                    return true;
                }
            } else {
                consecutiveCount = 0;
            }
        }

        return false;
    }

    private boolean isThreeOfAKind(ArrayList<Card> cardsTogether) {

        Map<Integer, Integer> valueCount = new HashMap<>();

        for (Card card : cardsTogether) {
            int value = card.Number;
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }


        for (int count : valueCount.values()) {
            if (count == 3) {
                return true;
            }
        }

        return false;
    }


    private boolean isTwoPair(ArrayList<Card> cardsTogether) {
        Map<Integer, Integer> rankCount = new HashMap<>();

        for (Card card : cardsTogether) {
            int rank = card.Number;
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }

        int pairCount = 0;

        for (int count : rankCount.values()) {
            if (count == 2) {
                pairCount++;
            }
            if (pairCount == 2) {
                break;
            }
        }

        return pairCount == 2;
    }

    private boolean isOnePair(ArrayList<Card> cards) {

        Map<Integer, Integer> valueCount = new HashMap<>();

        for (Card card : cards) {
            int value = card.Number;
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
        }


        for (int count : valueCount.values()) {
            if (count == 2) {
                return true;
            }
        }

        return false;
    }

}
