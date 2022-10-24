package soupthatisthick.util.logger;

public class Logger {

    public static boolean IS_DEBUG = true;

    public static final StackTraceElement getStackTraceElement() {
        return getStackTraceElement(1); // We want relative to one level higher than this method
    }
    public static final StackTraceElement getStackTraceElement(int depth) {
        try {
            throw new RuntimeException("");
        } catch (Exception e) {
            final StackTraceElement[] st = e.getStackTrace();
            return st[depth+1]; // This is called from somewhere.
        }
    }

    public static final String prefix(StackTraceElement s) {
        return (IS_DEBUG) ? s.getFileName() + "(Line " + getStackTraceElement(2).getLineNumber() + "): " : "";
    }

    public static final void warning(final String message) {
        String prefix = prefix(getStackTraceElement(1));
        if (message != null) {
            System.out.println(prefix + message);
        }
    }

    public static final void warning(final String message, final Throwable t) {
        String prefix = prefix(getStackTraceElement(1));
        if (t != null) {
            if (t.getMessage() != null) {
                System.out.println(prefix = t.getMessage());
            }
            t.printStackTrace(System.out);
        }
    }

    public static final void warning(final Throwable t) {
        String prefix = prefix(getStackTraceElement(1));
        if (t != null) {
            if (t.getMessage() != null) {
                System.out.println(prefix = t.getMessage());
            }
            t.printStackTrace(System.out);
        }
    }

    public static final void error(final Throwable t) {
        String prefix = prefix(getStackTraceElement(1));
        if (t != null) {
            if (t.getMessage() != null) {
                System.err.println(prefix = t.getMessage());
            }
            t.printStackTrace(System.err);
        }
    }
    public static final void error(final String message, final Throwable t) {
        String prefix = prefix(getStackTraceElement(1));
        if (message != null) {
            System.err.println(prefix + message);
        }
        error(t);
    }
    public static final void info(final String message) {
        String prefix = prefix(getStackTraceElement(1));
        System.out.println(prefix + message);
    }
    public static final void header(final String header, final String message) {
        String[] text = header.split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append("\n***");
        for(int i=0; i<text.length; i++) {
            sb.append("\n*** " + text[i]);
        }
        sb.append("\n***\n");
        sb.append(message);
        sb.append("\n");

        info(sb.toString());
    }
}

