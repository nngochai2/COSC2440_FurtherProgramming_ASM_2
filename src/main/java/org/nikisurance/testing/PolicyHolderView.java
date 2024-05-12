package org.nikisurance.testing;

import org.nikisurance.entity.PolicyHolder;

import java.util.Scanner;

public class PolicyHolderView {
    private final Scanner scanner = new Scanner(System.in);

    public void menu() {
        System.out.println("Policy Holder View");

    }

    public void authenticate() {
        while (true) {
            System.out.println("Policy Holder View");
            System.out.println("Enter your name:");
            String username = scanner.nextLine();


        }
    }
}
