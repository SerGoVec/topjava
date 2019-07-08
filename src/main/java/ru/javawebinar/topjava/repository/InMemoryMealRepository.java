package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*тестовый класс - добавление и удаление данных
* через память компьютера. Не используем БД.*/
public class InMemoryMealRepository implements MealRepository {
    //thread-safety реализация map для сервлета - ConcurrentHashMap
    private Map<Integer,Meal> repository = new ConcurrentHashMap<>();
    //thread-safety реализация integer - AtomicInteger
    private AtomicInteger counter = new AtomicInteger(0);

    //наполнение локального репозитория данными
    {
     save(new Meal(LocalDateTime.of(2019, Month.JULY,06,10,0),"Завтрак",500));
     save(new Meal(LocalDateTime.of(2019, Month.JULY, 06, 13, 0), "Обед", 1000));
     save(new Meal(LocalDateTime.of(2019, Month.JULY, 06, 20, 0), "Ужин", 500));
     save(new Meal(LocalDateTime.of(2019, Month.JULY, 07, 10, 0), "Завтрак", 1000));
     save(new Meal(LocalDateTime.of(2019, Month.JULY, 07, 13, 0), "Обед", 500));
     save(new Meal(LocalDateTime.of(2019, Month.JULY, 07, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.isNew()){
            meal.setId(counter.incrementAndGet());
        }
        return repository.put(meal.getId(),meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}
