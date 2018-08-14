public class MainApplication {
    public static void main (String[] args) throws Exception{
        TaskManager taskManager = new TaskManager();
        TaskGenerator tg = new TaskGenerator(taskManager);
        taskManager.setTaskGenerator(tg);
        Processor processor1 = new Processor(taskManager, 1);
        Processor processor2 = new Processor(taskManager, 2);
        taskManager.addProcessorToList(processor1);
        taskManager.addProcessorToList(processor2);
        processor1.start();
        processor2.start();
        tg.start();
        taskManager.start();
        boolean isReady = false;
        while(!isReady){
            if (processor1.isDead() && processor2.isDead() && taskManager.isDead() && tg.isDead())
                isReady = true;
            Thread.sleep(5);
        }
        System.out.println("--> Результаты <--");
        System.out.println("Дисциплина обслуживания: отбор по приоритетам (с огр. по времени ожидания).");
        System.out.println("Сгенерировано 100 задач.");
        System.out.println("Интенсивность потока задач: " + 1000/30 + " 1/с");
        System.out.println("Процессор 1 >> Задач выполнено: " + processor1.getCompletedTask() + " | Затрачено времени: " + processor1.getUtilizationTime() + "мс.");
        System.out.println("Процессор 2 >> Задач выполнено: " + processor2.getCompletedTask() + " | Затрачено времени: " + processor2.getUtilizationTime() + "мс.");
        System.out.println("Задач в очереди: " + taskManager.getQueueSize());
        System.out.println("Задач удалено из очереди: " + taskManager.killedTasks);
    }
}
