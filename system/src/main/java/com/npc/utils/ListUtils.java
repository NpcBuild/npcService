package com.npc.utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author NPC
 * @description
 * @create 2024/8/31 23:00
 */
public class ListUtils {

    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("apple", "banana", "cherry", "date");
        List<String> list2 = Arrays.asList("banana", "date", "fig", "grape");
        // 交集
        List<String> intersection = findIntersection(list1, list2);
        System.out.println("Intersection: " + intersection);

        // 并集
        List<String> union = findUnion(list1, list2);
        System.out.println("Union: " + union);

        // 差集 (list1 - list2)
        List<String> difference = findDifference(list1, list2);
        System.out.println("Difference (list1 - list2): " + difference);

        // 去重后的并集
        List<String> uniqueUnion = findUniqueUnion(list1, list2);
        System.out.println("Unique Union: " + uniqueUnion);
    }

    /**
     * 交集
     * 使用HashSet快速检查元素是否存在于另一个集合中
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> findIntersection(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        return list2.stream()
                .filter(set1::contains)
                .collect(Collectors.toList());
    }

    /**
     * 并集
     * 将两个List转换为Set以自动去除重复元素，然后将它们合并
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> findUnion(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        set1.addAll(list2);
        return new ArrayList<>(set1);
    }

    /**
     * 差集
     * 从第一个集合中移除第二个集合中的所有元素
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> findDifference(List<String> list1, List<String> list2) {
        Set<String> set1 = new HashSet<>(list1);
        set1.removeAll(new HashSet<>(list2));
        return new ArrayList<>(set1);
    }

    /**
     * 去重后的并集
     * 将两个列表合并成一个流，使用distinct()方法去除重复项
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> findUniqueUnion(List<String> list1, List<String> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
