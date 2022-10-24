package soupthatisthick.alpha;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum Alpha {
    SPACE(' '),
    QUESTION('?'),
    PERIOD('.'),
    EXCLAMATION('!'),
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H'),
    I('I'),
    J('J'),
    K('K'),
    L('L'),
    M('M'),
    N('N'),
    O('O'),
    P('P'),
    Q('Q'),
    R('R'),
    S('S'),
    T('T'),
    U('U'),
    V('V'),
    W('W'),
    X('X'),
    Y('Y'),
    Z('Z');

    public final char value;

    private Alpha(final char value) {
        this.value = value;
    }

    public String[] getBlock() {
        switch(this) {
            case PERIOD -> {
                return new String[] {
                    "      ",
                    "      ",
                    "      ",
                    "  xx  ",
                    "  xx  "
                };}
            case QUESTION -> {
                return new String[] {
                    " xxx  ",
                    "x   x ",
                    "   x  ",
                    "  x   ",
                    "  x   "
                };
            }
            case EXCLAMATION -> {
                return new String[] {
                    "  xx  ",
                    "  xx  ",
                    "  xx  ",
                    "      ",
                    "  xx  "
                };}
            case SPACE -> {
                return new String[] {
                    "      ",
                    "      ",
                    "      ",
                    "      ",
                    "      "
                };
            }
            case A -> {
                return new String[] {
                    " xxx  ",
                    "x   x ",
                    "xxxxx ",
                    "x   x ",
                    "x   x "
                };
            }
            case B -> {
                return new String[] {
                    "xxxx  ",
                    "x   x ",
                    "xxxxx ",
                    "x   x ",
                    "xxxx  "
                };
            }
            case C -> {
                return new String[] {
                    " xxxx ",
                    "x     ",
                    "x     ",
                    "x     ",
                    " xxxx "
                };
            }
            case D -> {
                return new String[] {
                    "xxxx  ",
                    "x   x ",
                    "x   x ",
                    "x   x ",
                    "xxxx  "
                };
            }
            case E -> {
                return new String[] {
                    "xxxxx ",
                    "x     ",
                    "xxxxx ",
                    "x     ",
                    "xxxxx "
                };
            }
            case F -> {
                return new String[] {
                    "xxxxx ",
                    "x     ",
                    "xxxxx ",
                    "x     ",
                    "x     "
                };
            }
            case G -> {
                return new String[] {
                    " xxx  ",
                    "x     ",
                    "x  xx ",
                    "x   x ",
                    " xxx  "
                };
            }
            case H -> {
                return new String[] {
                    "x   x ",
                    "x   x ",
                    "xxxxx ",
                    "x   x ",
                    "x   x "
                };
            }
            case I -> {
                return new String[] {
                    "xxxxx ",
                    "  x   ",
                    "  x   ",
                    "  x   ",
                    "xxxxx "
                };
            }
            case J -> {
                return new String[] {
                    "xxxxx ",
                    "   x  ",
                    "   x  ",
                    "x  x  ",
                    " xxx  "
                };
            }
            case K -> {
                return new String[] {
                    "x   x ",
                    "x  x  ",
                    "xxx   ",
                    "x  x  ",
                    "x   x "
                };
            }
            case L -> {
                return new String[] {
                    "x     ",
                    "x     ",
                    "x     ",
                    "x     ",
                    "xxxxx "
                };
            }
            case M -> {
                return new String[] {
                    "x   x ",
                    "xx xx ",
                    "x x x ",
                    "x   x ",
                    "x   x "
                };
            }
            case N -> {
                return new String[] {
                    "x   x ",
                    "xx  x ",
                    "x x x ",
                    "x  xx ",
                    "x   x "
                };
            }
            case O -> {
                return new String[] {
                    " xxx  ",
                    "x   x ",
                    "x   x ",
                    "x   x ",
                    " xxx  "
                };
            }
            case P -> {
                return new String[] {
                    "xxxx  ",
                    "x   x ",
                    "xxxx  ",
                    "x     ",
                    "x     "
                };
            }
            case Q -> {
                return new String[] {
                    " xxx  ",
                    "x   x ",
                    "x   x ",
                    "x  x  ",
                    " xx x "
                };
            }
            case R -> {
                return new String[] {
                    "xxxx  ",
                    "x   x ",
                    "xxxx  ",
                    "x   x ",
                    "x   x "
                };
            }
            case S -> {
                return new String[] {
                    " xxxx ",
                    "x     ",
                    " xxx  ",
                    "    x ",
                    "xxxx  "
                };
            }
            case T -> {
                return new String[] {
                    "xxxxx ",
                    "  x   ",
                    "  x   ",
                    "  x   ",
                    "  x   "
                };
            }
            case U -> {
                return new String[] {
                    "x   x ",
                    "x   x ",
                    "x   x ",
                    "x   x ",
                    " xxx  "
                };
            }
            case V -> {
                return new String[] {
                    "x   x ",
                    "x   x ",
                    "x   x ",
                    " x x  ",
                    "  x   "
                };
            }
            case W -> {
                return new String[] {
                    "x   x ",
                    "x   x ",
                    "x x x ",
                    "xx xx ",
                    "x   x "
                };
            }
            case X -> {
                return new String[] {
                    "x   x ",
                    " x x  ",
                    "  x   ",
                    " x x  ",
                    "x   x "
                };
            }
            case Y -> {
                return new String[] {
                    "x   x ",
                    "x   x ",
                    " x x  ",
                    "  x   ",
                    "  x   "
                };
            }
            case Z -> {
                return new String[] {
                    "xxxxx ",
                    "   x  ",
                    "  x   ",
                    " x    ",
                    "xxxxx "
                };
            }
            default -> {
                return new String[] {
                    "xxxxx ",
                    "xxxxx ",
                    "xxxxx ",
                    "xxxxx ",
                    "xxxxx "
                };
            }
        }
    }


    /**
     * This will attempt to find the alpha that goes with the specified character
     * @param ch
     * @return
     */
    public static Optional<Alpha> find(final Character ch) {
        final char upperCh = Character.toUpperCase(ch);
        for(Alpha alpha : values()) {
            if (alpha.value == upperCh) {
                return Optional.of(alpha);
            }
        }
        return Optional.empty();
    }

    /**
     * This will attempt to search for the Alpha associated with the provided character.
     * If one is not present it will return the default value provided.
     * @param ch is the character we want to map to
     * @param defaultAlpha is the assumed value if no defined mapping exists.
     * @return an {@link Alpha}
     */
    public static Alpha findOrDefault(final Character ch, final Alpha defaultAlpha) {
        return find(ch).orElseGet(() -> defaultAlpha);
    }

    public static List<Alpha> toAlphaList(final String text, final Alpha defaultAlpha) {
        final List<Alpha> list = new ArrayList<>();
        for(int i=0; i<text.length(); i++) {
            final char ch = text.charAt(i);
            list.add(findOrDefault(ch, defaultAlpha));
        }
        return list;
    }

    public static final String buildString(final List<Alpha> characters) {
        final StringBuilder row[] = new StringBuilder[] {
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder(),
                new StringBuilder()
            };

        for(Alpha alpha : characters) {
            final String[] block = alpha.getBlock();
            for(int r=0; r<5; r++) {
                row[r].append(block[r]);
            }
        }

        // Bring all the rows together
        final StringBuilder sb = new StringBuilder();
        for(int r=0; r<5; r++) {
            sb.append(row[r].toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    public static boolean equals(final List<Alpha> expected, final List<Alpha> observed) {
        if (expected==null) return observed==null;
        if (observed==null) return false;
        if (expected.size() != observed.size()) return false;
        for(int i=0; i<expected.size(); i++) {
            if (expected.get(i) != observed.get(i)) {
                return false;
            }
        }
        return true;
    }
}
