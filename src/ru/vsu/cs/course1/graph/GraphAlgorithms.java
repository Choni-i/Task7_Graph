package ru.vsu.cs.course1.graph;

import java.util.*;
import java.util.function.Consumer;

public class GraphAlgorithms {

    /**
     * Поиск в глубину, реализованный рекурсивно
     * (начальная вершина также включена)
     * @param graph граф
     * @param from Вершина, с которой начинается поиск
     * @param visitor Посетитель
     */
    public static void dfsRecursion(Graph graph, int from, int to, Set<Integer> blockedCity, Consumer<Integer> visitor) {
        boolean[] visited = new boolean[graph.vertexCount()];
        boolean flag = false;

        class Inner {
            void visit(int curr, boolean flag) {
                if(flag){
                    return;
                }
                if (curr == to){
                    flag = true;
                return;
                }
                visitor.accept(curr);
                if (blockedCity.contains(curr)){
                    visited[curr] = true;
                } else {
                visited[curr] = false;
                }
                for (Integer v : graph.adjacencies(curr)) {
                    if (!visited[v]) {
                        visit(v, flag);
                    }
                }

            }
        }
        new Inner().visit(from, flag);
    }

    /**
     * Поиск в глубину, реализованный с помощью стека
     * (не совсем "правильный"/классический, т.к. "в глубину" реализуется только "план" обхода, а не сам обход)
     * @param graph граф
     * @param from Вершина, с которой начинается поиск
     *
     */
    public static Stack<Integer> dfs(Graph graph, int from, int to, Set<Integer> blockedCity) {
        boolean[] visited = new boolean[graph.vertexCount()];
        int [] prev = new int[graph.vertexCount()];
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> result = new Stack<Integer>();
        stack.push(from);
        visited[from] = true;
        //boolean flag = false;
        for(int i = 0; i<prev.length; i++){
            prev[i] = -1;
        }

        while (!stack.empty()) {
            Integer curr = stack.pop();
            if (curr == to ){
                stack.push(curr);
                visited[curr] = true;
                //flag = true;
                break;
            }
            if (blockedCity.contains(curr) && curr != from){
                continue;
            }
            //visitor.accept(curr);
            for (Integer v : graph.adjacencies(curr)) {
                if (!visited[v] && !blockedCity.contains(v) /*&& !flag*/) {
                    prev[v] = curr;
                    stack.push(v);
                    visited[v] = true;
                }
            }
        }
        for(int j = to; j!=from; j = prev[j]){
                result.push(j);}
        result.push(from);

        return result;
    }

    /**
     * Поиск в ширину, реализованный с помощью очереди
     * (начальная вершина также включена)
     * @param graph граф
     * @param from Вершина, с которой начинается поиск
     * @param visitor Посетитель
     */
    public static void bfs(Graph graph, int from, Consumer<Integer> visitor) {
        boolean[] visited = new boolean[graph.vertexCount()];
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(from);
        visited[from] = true;
        while (queue.size() > 0) {
            Integer curr = queue.remove();
            visitor.accept(curr);
            for (Integer v : graph.adjacencies(curr)) {
                if (!visited[v]) {
                    queue.add(v);
                    visited[v] = true;
                }
            }
        }
    }

    /**
     * Поиск в глубину в виде итератора
     * (начальная вершина также включена)
     * @param graph граф
     * @param from Вершина, с которой начинается поиск
     * @return Итератор
     */
    public static Iterable<Integer> dfs(Graph graph, int from) {
        return new Iterable<Integer>() {
            private Stack<Integer> stack = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Integer> iterator() {
                stack = new Stack<>();
                stack.push(from);
                visited = new boolean[graph.vertexCount()];
                visited[from] = true;

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return ! stack.isEmpty();
                    }

                    @Override
                    public Integer next() {
                        Integer result = stack.pop();
                        for (Integer adj : graph.adjacencies(result)) {
                            if (!visited[adj]) {
                                visited[adj] = true;
                                stack.add(adj);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    /**
     * Поиск в ширину в виде итератора
     * (начальная вершина также включена)
     * @param from Вершина, с которой начинается поиск
     * @return Итератор
     */
    public static Iterable<Integer> bfs(Graph graph, int from) {
        return new Iterable<Integer>() {
            private Queue<Integer> queue = null;
            private boolean[] visited = null;

            @Override
            public Iterator<Integer> iterator() {
                queue = new LinkedList<>();
                queue.add(from);
                visited = new boolean[graph.vertexCount()];
                visited[from] = true;

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return ! queue.isEmpty();
                    }

                    @Override
                    public Integer next() {
                        Integer result = queue.remove();
                        for (Integer adj : graph.adjacencies(result)) {
                            if (!visited[adj]) {
                                visited[adj] = true;
                                queue.add(adj);
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }
}
