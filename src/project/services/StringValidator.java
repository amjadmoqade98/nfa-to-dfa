package project.services;

import project.model.State;
import project.utils.ColoredText;

import java.util.*;

public enum StringValidator {
    INSTANCE;
    private State startState;
    private Set<String> letters;
    private Map<String, State> states;
    private Set<State> finalStates;

    public static StringValidator createValidator(State startState, Set<String> letters, Map<String, State> states, Set<State> finalStates) {
        INSTANCE.startState = startState;
        INSTANCE.letters = letters;
        INSTANCE.states = states;
        INSTANCE.finalStates = finalStates;

        return INSTANCE;
    }


    public boolean isValid(String input) {
        State currentState = startState;

        System.out.println("current state(start): " + currentState.getState());
        for (char c : input.toCharArray()) {
            System.out.print("letter: (" + c + ")");

            String c1 = "" + c;
            if (!letters.contains(c1)) {
                System.out.println("\n" + c1 + "is not allowed in the language ");
                return false;
            }
            if (!currentState.getTransactions().keySet().contains(c1)) {
                System.out.println("\n" + currentState.getState() + " doesn't have a transaction on " + c);
                return false;
            }
            currentState = currentState.getTransactions().get(c1).iterator().next();
            System.out.println(" --> current state: " + currentState.getState());
        }

        if (finalStates.contains(currentState)) {
            System.out.println(currentState.getState() + " is a final state");
            return true;
        } else {
            System.out.println(currentState.getState() + " is not a valid state");
            return false;
        }
    }

    public void validationLifeCycle() {
        String input;
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("start state = " + startState.getState());
        System.out.print("end states =");
        finalStates.forEach(state -> {
            System.out.print("  " + state.getState());
        });
        System.out.println();
        System.out.println("enter strings to test validity , empty string to end: ");
        System.out.println("**********************************");
        while (true) {
            System.out.print("String: ");
            input = in.nextLine();
            if (input.length() == 0) {
                System.out.println("thanks for using my program\nAmjad Moqade 1160695");
                break;
            }
            if (isValid(input)) {
                System.out.println(input + ColoredText.ToGreen(" -->  Valid"));
            } else {
                System.out.println(input + ColoredText.ToRed(" -->  inValid"));
            }
            System.out.println("----------------------");
        }
    }
}
