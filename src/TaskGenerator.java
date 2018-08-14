public class TaskGenerator extends Thread {
    /*
    Класс генератора задач
    Поля:
        TASK_QUANTITY - число задач, которое будет сгенерировано за время моделирования
        tm - ссылка на менеджер задач
        isAlive - флаг, управляющий жизнью процесса
    Время генерации задач будет составлять 55мс
    Кол-во генераций: 100
     */
    static final int TASK_QUANTITY = 100;
    private TaskManager tm;
    private boolean isAlive;

    TaskGenerator (TaskManager tm){
        super();
        this.tm = tm;
        this.isAlive = true;
    }

    public Task generateNewTask(){
        return new Task();
    }

    public boolean isDead(){
        return !this.isAlive;
    }

    public void run () {
        while (isAlive){
            for (int i = 0; i < TASK_QUANTITY; i++){
                Task task = generateNewTask();
                System.out.println("$ Сгенерирована задача с приоритетом " + task.getPriority() + " и ОВП " + task.getEstimatedTime());
                this.tm.addNewTaskToQueue(task);
                try{
                    Thread.sleep(30); //80, 55, 30
                }
                catch (Exception e){e.printStackTrace();}
            }
            this.isAlive = false;
        }
        System.out.println("Генератор задач завершает работу...");
    }
}
