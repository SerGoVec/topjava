package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        final List<UserMealWithExceed> filteredWithExceeded = getFilteredMealsWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //forEach появилась в java8
        filteredWithExceeded.forEach(m -> System.out.println(m));
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        return null;
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime,
                                                                               int caloriesPerDay) {
        //объявлять переменные лучше перед их использованием
        //переменные коллекций лучше объявлять интерфейсами
        Map<LocalDate, Integer> caloriesSumPerdate = new HashMap<>(); for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            //если в map уже есть значение по ключу, то получаем его и суммируем с новой итерацией, иначе 0 + итерация
            caloriesSumPerdate.put(mealDate, caloriesSumPerdate.getOrDefault(mealDate, 0) + meal.getCalories());
        }

        List<UserMealWithExceed> mealExeed = new ArrayList<>(); for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealExeed.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        (caloriesSumPerdate.get(meal.getDateTime()) > caloriesPerDay)));
            }
        } return mealExeed;
    }


      //реализовано Alex P, интересно, надо разобраться
//    public static List<UserMealWithExceed> getFilteredWithExceededOptinal2(List<UserMeal> mealList,
//                                                                          LocalTime startTime, LocalTime endTime, int caloriesPerDay){
//        return mealList.stream()
//                .filter(a -> TimeUtil.isBetween(a.getDateTime().toLocalTime(),startTime,endTime))
//                .map(a -> exchangeUMtoUMWE(a,
//                        mealList.stream().filter(b -> b.getDateTime().toLocalDate().equals(a.getDateTime().toLocalDate())).mapToInt(UserMeal::getCalories).sum()>caloriesPerDay
//                )).collect(Collectors.toCollection(ArrayList::new));
//    }
}
