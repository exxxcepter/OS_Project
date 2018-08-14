import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class TaskManager extends Thread{
    /*
    Класс менеджера задач
    Поля:
        queue - очередь задач
        tg - ссылка на генератор задач
        isAlive - флаг жизни процесса
        processorList - список с ссылками на процессоры
        killedTasks - число задач, удаленных из очереди
     */
    private Vector<Task> q;
    private List<Processor> processorList;
    private boolean isAlive;
    private TaskGenerator tg;
    public int killedTasks;

    public TaskManager (){
        super();
        this.q = new Vector<Task>();
        this.processorList = new LinkedList<Processor>();
        this.isAlive = true;
        this. tg = null;
        this.killedTasks = 0;
    }

    public void setTaskGenerator(TaskGenerator tg){
        this.tg = tg;
    }

    public void addProcessorToList(Processor pr){
        this.processorList.add(pr);
    }

    public boolean isDead(){
        return !this.isAlive;
    }

    private Task selectNewTask(){
        Task tempTask = q.firstElement();
        try{
            for (Task t : q){
                if(t.getPriority() > tempTask.getPriority()){
                    tempTask = t;
                }
            }
        }
        catch(Exception e){}
        q.remove(tempTask);
        return tempTask;
    }

    public void addNewTaskToQueue(Task task){
        q.add(task);
    }

    public int getQueueSize() {
        return q.size();
    }

    public boolean isArrayEmpty(){
        return q.isEmpty();
    }

    public void checkTaskWaitingTime(){
        try{
            for (Task t : q){
                t.setWaitingTime(t.getWaitingTime() + 5);
                if ((t.getPriority() + 1) * t.getEstimatedTime() < t.getWaitingTime()){
                    q.remove(t);
                    this.killedTasks++;
                    System.out.println("\t-> Менеджер задач: Задача с приоритетом " + t.getPriority() + " была удалена из очереди (превышено время ожидания)!");
                }
            }
        }
        catch (Exception e) {}
    }

    public void run (){
        while (this.isAlive){
            for (Processor pr : processorList){
                if (!pr.isBusy()){
                    if(!this.isArrayEmpty()){
                        /*
                        Вызов метода checkTaskWaitingTime() реализует
                        механизм системы с ограниченным временем ожидания
                         */
                        this.checkTaskWaitingTime();
                        Task ts = this.selectNewTask();
                        pr.setNewTask(ts);
                        pr.setBusy();
                        System.out.println("\t-> Менеджер задач: новая задача для Процессора " + pr.getProcessorId() + "|Приоритет: " + ts.getPriority() + "|ОВП: " + ts.getEstimatedTime());
                        System.out.println("\t-> Число задач в очереди: " + this.getQueueSize());
                    }
                    else
                        System.out.println("Нет задач в очереди!");
                }
            }
            try{
                Thread.sleep(5);
            }
            catch (Exception e) {e.printStackTrace();}
            if(this.tg.isDead())
                this.isAlive = false;
        }
        System.out.println("Менеджер задач завершает работу...");
    }
}
