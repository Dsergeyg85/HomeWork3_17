public class JvmComprehension {
//Классы JvmComprehension, Object, Integer, System загружаются в Metaspace со всеми их методами, константами полями и т.д. вызвав
//Application ClassLoader - > Platform ClassLoader - > Bootstrap ClassLoader
//Bootstrap ClassLoader загрузит базовые классы Object, Integer, System в Metaspace
//Application ClassLoader загрузит JvmComprehension в Metaspace
    public static void main(String[] args) { //создается фрэйм
        int i = 1;                      // 1 - выделяется место в Stack Memory под примитивное поле i и его хначение
        Object o = new Object();        // 2 - выделяется место для new Object() в куче (heap),
                                        // Stack Memory выделяется место для поля o в котором хранится ссылка на new Object() в куче
        Integer ii = 2;                 // 3 - в куче выделяется место для 2,
                                        // в Stack Memory выделяется место для поля ii хранящая ссылку на область памяти в куче содержащее 2
        printAll(o, i, ii);             // 4 - Выделяется место в Stack Memory под создаваемый фрейм printAll,
                                        // в ранее созданном фрейме main, параметры o и ii ссылаются на кучу, параметр i Stack Memory
        System.out.println("finished"); // 7 - в куче выделяется место под значение finished,
                                        // в Stack Memory создается фрейм в параметры которого передается ссылка на выделенное место, где хранится значение finished
                                        // при инициализации классов будет выполнен, после выполнения, ссылка на созданное в куче значение будет удалена, значение в куче будет удалено при сборке мусора
        //Созданные тут объекты не будут удалены до конца работы программы
    }

    private static void printAll(Object o, int i, Integer ii) {
        Integer uselessVar = 700;                   // 5 - в куче выделяется место для значения 700,
                                                    // в Stack Memory выделяется место для поля uselessVar хранящая ссылку на область памяти в куче содержащее 2
        System.out.println(o.toString() + i + ii);  // 6 - в Stack Memory создается фрейм в параметры которого передадутся ссылки на выделенное место,
                                                    // где хранится значение o и ii, а также поле i из Stack Memory.
                                                    // Будет выделено место в куче под string o.toString() + i + ii и в метод передана ссылка на итоговое значение из кучи
                                                    // o.toString() вызовет создание еще одного фрейма по метод toString. После того как метод отработает, фрейм удаляется из Stack Memory
                                                    // Созданный в куче результат выполнения o.toString() + i + ii будет удален из-за недостижимости, так ак ссылка в Stack Memory  будет удалена
        // после окончания выполнения метода, ранее полученные параметры и ссылки (в параметрах метода) будут удалены из Stack Memory.
        // Значение uselessVar в куче будет удалена из-за недостижимости после окончания работы метода, так ак ссылка uselessVar будет удалена в Stack Memory

    }
}