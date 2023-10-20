package org.example;

import io.reactivex.Observable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;

public class Opt3 {

    private static List<UserFriend> userFriendList = new LinkedList<>();

    public static void main(String[] args) {
        int index = 0;
        while (index != 20) {
            UserFriend userFriend = new UserFriend(
                    new Random().nextInt(10, 20),
                    new Random().nextInt(100, 200)
            );
            userFriendList.add(userFriend);
            out.println(userFriend + " has been added");
            index++;
        }

        Scanner sc = new Scanner(System.in);
        int enter = 0;

        while (enter != -1) {
            out.print("> ");
            enter = sc.nextInt();
            if (enter != -1) {
                getFriends(enter).subscribe(u -> out.println(u.getFriendId()));
            }
        }

    }

    private static Observable<UserFriend> getFriends(int userId) {
        return Observable.fromIterable(userFriendList).filter(u -> u.getUserId() == userId);
    }

}