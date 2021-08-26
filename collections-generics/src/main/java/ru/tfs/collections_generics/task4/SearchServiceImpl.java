package ru.tfs.collections_generics.task4;

import java.util.*;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService {

    @Override
    public List<User> searchForFriendsInWidth(User me, String name) {
        Set<User> checkedFriends = new HashSet<>();
        Deque<User> deq = new ArrayDeque<>();
        List<User> targetUsers = new ArrayList<>();
        deq.add(me);
        while (deq.size() != 0) {
            User user = deq.remove();
            if (checkedFriends.contains(user)) {
                continue;
            }
            checkedFriends.add(user);
            if (user.getName().equals(name)) {
                targetUsers.add(user);
            }
            if (user.getFriends() == null) {
                continue;
            }
            deq.addAll(user.getFriends().stream().filter(newUser -> !checkedFriends.contains(newUser)).collect(Collectors.toList()));

        }
        return targetUsers;
    }


    @Override
    public List<User> searchForFriendsInDepth(User me, String name) {
        Set<User> checkedFriends = new HashSet<>();
        Deque<User> deq = new ArrayDeque<>();
        List<User> targetUsers = new ArrayList<>();
        deq.add(me);
        while (deq.size() != 0) {
            User user = deq.pollLast();
            if (checkedFriends.contains(user)) {
                continue;
            }
            checkedFriends.add(user);
            if (user.getName().equals(name)) {
                targetUsers.add(user);
            }
            if (user.getFriends() == null) {
                continue;
            }
            deq.addAll(user.getFriends().stream().filter(newUser -> !checkedFriends.contains(newUser)).collect(Collectors.toList()));

        }
        return targetUsers;
    }

//    public List<User> searchForFriendsInDepthRecursion(User me, String name) {
//        checkedFriends.clear();
//        List<User> users = startDepthSearchRecursion(me, name);
//        checkedFriends.clear();
//        return users;
//    }
//
//    private List<User> startDepthSearchRecursion(User me, String name) {
//        List<User> targetUsers = new ArrayList<>();
//        checkedFriends.put(me.getId(), me);
//        for (User friend : me.getFriends()) {
//            if (checkedFriends.containsKey(friend.getId())) {
//                continue;
//            }
//            if (friend.getName().equals(name)) {
//                targetUsers.add(friend);
//            }
//            targetUsers.addAll(startDepthSearchRecursion(friend, name).parallelStream()
//                    .filter(user -> user.getName().equals(name))
//                    .collect(toList())
//            );
//            System.out.println(targetUsers.size());
//        }
//        return targetUsers;
//    }

}
