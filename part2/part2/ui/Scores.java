package part2.ui;

public class Scores {

    private static int oneVsCpu = 0;
    private static int cpuRabbitVsCpuCheetah = 0;
    private static int cpuVsOne = 0;
    private static int cpuCheetahVsCpuRabbit = 0;

    private Scores() {}

    /* getters and setters */

    public static int getOneVsCpu() {
        return oneVsCpu;
    }

    public static void incrementOneVsCpu() {
        ++oneVsCpu;
    }

    public static int getCpuRabbitVsCpuCheetah() {
        return cpuRabbitVsCpuCheetah;
    }

    public static void incrementCpuRabbitVsCpuCheetah() {
        ++cpuRabbitVsCpuCheetah;
    }

    public static int getCpuVsOne() {
        return cpuVsOne;
    }

    public static void incrementCpuVsOne() {
        ++cpuVsOne;
    }

    public static int getCpuCheetahVsCpuRabbit() {
        return cpuCheetahVsCpuRabbit;
    }

    public static void incrementCpuCheetahVsCpuRabbit() {
        ++cpuCheetahVsCpuRabbit;
    }
}
