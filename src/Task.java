import java.util.Random;

public class Task {
    /*
    Класс задачи
    Поля:
        priority - приоритет задачи (по возрастанию важности: 1, 2, 3)
        estimatedTime - ожидаемое время выполнения задачи (используется процессором)(от 50мс до 200мс)
        waitingTime - время ожидание задачи в очереди
     */
    private int priority;
    private int estimatedTime;
    private int waitingTime;

    public Task () {
        Random rand = new Random();
        int minP = 1, maxP = 3, minT = 50, maxT = 200;
        this.priority = rand.nextInt((maxP - minP) + 1) + minP;
        this.estimatedTime = rand.nextInt((maxT - minT) + 1) + minT;
        this.waitingTime = 0;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
