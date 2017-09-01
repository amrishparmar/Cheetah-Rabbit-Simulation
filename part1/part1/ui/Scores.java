package part1.ui;

public class Scores {

    private static int oneVsCpu = 0;
    private static int oneVsTwo = 0;
    private static int cpuVsOne = 0;
    private static int twoVsOne = 0;

    public static int getOneVsCpu() {
        return oneVsCpu;
    }

    public static void incrementOneVsCpu() {
        ++oneVsCpu;
    }

    public static int getOneVsTwo() {
        return oneVsTwo;
    }

    public static void incrementOneVsTwo() {
        ++oneVsTwo;
    }

    public static int getCpuVsOne() {
        return cpuVsOne;
    }

    public static void incrementCpuVsOne() {
        ++cpuVsOne;
    }

    public static int getTwoVsOne() {
        return twoVsOne;
    }

    public static void incrementTwoVsOne() {
        ++twoVsOne;
    }
}
