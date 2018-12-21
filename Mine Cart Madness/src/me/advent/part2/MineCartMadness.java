package me.advent.part2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * @author Pandaism
 * @date 12/19/2018 : 5:34 PM
 */
public class MineCartMadness {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = Paths.get(ClassLoader.getSystemResource("PuzzleInput.txt").toURI()).toFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Cart> carts = new ArrayList<>();

        int y = 0;
        while((line = reader.readLine()) != null) {
            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if(c == 'v' || c == '^' || c == '>' || c == '<') {
                    carts.add(new Cart(i, y, c));

                    if(c == 'v' || c == '^') {
                        paths.add(new Path(i, y, '|'));
                    } else {
                        paths.add(new Path(i, y, '-'));
                    }
                } else {
                    paths.add(new Path(i, y, c));
                }
            }
            y++;
        }

        int time = 1;
        while(paths.stream().noneMatch(path -> path.style == 'x')) {

            ArrayList<Cart> collidedCarts = new ArrayList<>();

            for(Cart cart : carts) {
                Path track = paths.stream().filter(path -> path.x == cart.x && path.y == cart.y).findFirst().orElse(null);
                int xInc = 0;
                int yInc = 0;

                if(track != null) {
                    switch (track.style) {
                        case '/':
                            switch (cart.direction) {
                                case '^':
                                    cart.direction = '>';
                                    xInc += 1;
                                    break;
                                case '<':
                                    cart.direction = 'v';
                                    yInc += 1;
                                    break;
                                case '>':
                                    cart.direction = '^';
                                    yInc -= 1;
                                    break;
                                case 'v':
                                    cart.direction = '<';
                                    xInc -= 1;
                                    break;
                            }
                            break;
                        case '\\':
                            switch (cart.direction) {
                                case '^':
                                    cart.direction = '<';
                                    xInc -= 1;
                                    break;
                                case '<':
                                    cart.direction = '^';
                                    yInc -= 1;
                                    break;
                                case '>':
                                    cart.direction = 'v';
                                    yInc += 1;
                                    break;
                                case 'v':
                                    cart.direction = '>';
                                    xInc += 1;
                                    break;
                            }
                            break;
                        case '-':
                            if(cart.direction == '>') {
                                xInc += 1;
                            } else {
                                xInc -= 1;
                            }
                            break;
                        case '|':
                            if(cart.direction == 'v') {
                                yInc += 1;
                            } else {
                                yInc -= 1;
                            }
                            break;
                        case '+':
                            int turn = cart.intersectionTurn % 3;

                            switch (cart.direction) {
                                case '^':
                                    if(turn == 0) {
                                        cart.direction = '>';
                                        xInc += 1;
                                    } else if(turn == 1) {
                                        cart.direction = '<';
                                        xInc -= 1;
                                    } else if(turn == 2) {
                                        yInc -= 1;
                                    }
                                    break;
                                case 'v':
                                    if(turn == 0) {
                                        cart.direction = '<';
                                        xInc -= 1;
                                    } else if(turn == 1) {
                                        cart.direction = '>';
                                        xInc += 1;
                                    } else if(turn == 2) {
                                        yInc += 1;
                                    }
                                    break;
                                case '>':
                                    if(turn == 0) {
                                        cart.direction = 'v';
                                        yInc += 1;
                                    } else if(turn == 1) {
                                        cart.direction = '^';
                                        yInc -= 1;
                                    } else if(turn == 2) {
                                        xInc += 1;
                                    }
                                    break;
                                case '<':
                                    if(turn == 0) {
                                        cart.direction = '^';
                                        yInc -= 1;
                                    } else if(turn == 1) {
                                        cart.direction = 'v';
                                        yInc += 1;
                                    } else if(turn == 2) {
                                        xInc -= 1;
                                    }
                                    break;
                            }

                            cart.incIntersectionTurn();

                            break;
                    }

                }

                cart.x += xInc;
                cart.y += yInc;

                for(int i = 0; i < carts.size(); i++) {
                    for (int j = i + 1; j < carts.size(); j++) {
                        if(carts.get(i).x == carts.get(j).x && carts.get(i).y == carts.get(j).y) {
                            collidedCarts.add(carts.get(i));
                            collidedCarts.add(carts.get(j));
                        }
                    }
                }
            }

            if(collidedCarts.size() > 0) {
                carts.removeIf(collidedCarts::contains);
            }

            if(carts.size() == 1) {
                System.out.printf("Last Cart: (%d,%d) @ %d", carts.get(0).x, carts.get(0).y, time);
                break;
            }
            carts.sort(new CartSort());
            time++;
        }
//        for(Cart cart : carts) {
//            System.out.printf("direction: %s\t(%d,%d)\n", cart.direction, cart.x, cart.y);
//        }
    }

    private static class CartSort implements Comparator<Cart> {
        @Override
        public int compare(Cart o1, Cart o2) {
            int result = Integer.compare(o1.y, o2.y);
            if(result == 0) {
                result = Integer.compare(o1.x, o2.x);
            }
            return result;
        }
    }

    private static class Path {
        private int x;
        private int y;
        private char style;

        Path(int x, int y, char style) {
            this.x = x;
            this.y = y;
            this.style = style;
        }
    }

    private static class Cart {
        private int x;
        private int y;
        private char direction;
        private int intersectionTurn;

        Cart(int x, int y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.intersectionTurn = 1;
        }

        void incIntersectionTurn() {
            this.intersectionTurn += 1;
        }
    }

}
