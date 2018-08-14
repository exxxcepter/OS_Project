public class Processor extends Thread{
    /*
    Класс процессора
    Поля:
        currentTask - текущая задача
        isBusy - флаг занятости
        completedTask - число выполненных задач
        isAlive - флаг для завершения работы потока
        utilizationTime - общее время обработки всех задач
        tm - ссылка на менеджер задач
        id - идентификатор процессора
     */
    private Task currentTask;
    private boolean isBusy;
    private int completedTask;
    private int utilizationTime;
    private boolean isAlive;
    private TaskManager tm;
    private int id;

    public Processor (TaskManager tm, int id){
        super();
        this.completedTask = 0;
        this.isBusy = false;
        this.currentTask = null;
        this.isAlive = true;
        this.tm = tm;
        this.utilizationTime = 0;
        this.id = id;
    }

    public int getProcessorId(){
        return this.id;
    }

    public boolean isBusy(){
        return isBusy;
    }

    public boolean isDead(){
        return !this.isAlive;
    }

    public void setNewTask (Task task){
        this.currentTask = task;
    }

    public void setBusy (){
        this.isBusy = true;
    }

    public int getCompletedTask() {
        return completedTask;
    }

    public int getUtilizationTime() {
        return utilizationTime;
    }

    public void run (){
        System.out.println("Processor " + this.getProcessorId() + " is active!");
        while(this.isAlive){
            while(isBusy){
                //if (this.currentTask.getEstimatedTime() <= 100){
                    try{
                        Thread.sleep(this.currentTask.getEstimatedTime());
                        this.utilizationTime += this.currentTask.getEstimatedTime();
                        this.completedTask++;
                    }
                    catch (Exception e) {e.printStackTrace();}
                //}
                /*else{
                    try{
                        Thread.sleep(100);
                        this.currentTask.setEstimatedTime(this.currentTask.getEstimatedTime() - 100);
                        this.utilizationTime += 100;
                        this.currentTask.setWaitingTime(0);
                        tm.addNewTaskToQueue(this.currentTask);
                    }
                    catch (Exception e) {e.printStackTrace();}
                }*/
                this.isBusy = false;
                System.out.println("\t\t#Процессор " + this.getProcessorId() + " теперь свободен!");
            }
            if(this.isAlive)
                try{
                    Thread.sleep(5);
                }
                catch (Exception e) {e.printStackTrace();}
            if (this.tm.isDead())
                this.isAlive = false;
        }
        System.out.println("Процессор " + this.getProcessorId() + " завершает работу...");
    }
}
