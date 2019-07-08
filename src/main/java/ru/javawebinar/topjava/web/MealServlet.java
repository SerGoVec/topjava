package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;


//mvc здесь: model - то, что кладем в аттрибуты запроса;
//           view - jsp;
//           controler - сервлет.

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private InMemoryMealRepository repository;

    /*своего рода конструктора сервлета, вызывается 1 раз
        начиная с сервлетов 2.3, вэб-контейнер инстанциирует 1 сервлет (от пула сервлетов отказались)
        все запросы идут через него, поэтому все запросы должны быть потокобезопасными*/
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action == null){
            log.debug("getAll");
            request.setAttribute("meals",

                    /*getWithExceeeded - использует метод getFilteredWithExcess,
                      который использует createWithExcess, в котором для тотбражения id
                      необходимо return new MealTo(meal.getId(),meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);*/
                    MealsUtil.getWithExceeded(repository.getAll().stream().collect(Collectors.toList()), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }else if(action.equals("delete")){
            int id = getId(request);
            log.debug("delete {}",id);
            repository.delete(id);
            response.sendRedirect("meals");
        }else{
            final Meal meal = action.equals("create")
                    ?
                    new Meal(LocalDateTime.now(),"",1000)
                    :
                    repository.get(getId(request));
            request.setAttribute("meal",meal);
            //отправка на страницу редактирования
            request.getRequestDispatcher("mealEdit.jsp").forward(request,response);
        }
        //equest.setAttribute("meals", MealsUtil.getWithExceeded(MealsUtil.MEALS,2000));
        //request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //response.sendRedirect("users.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        //?
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                                                    LocalDateTime.parse(request.getParameter("dateTime")),
                                                    request.getParameter("description"),
                                                    Integer.valueOf(request.getParameter("calories")));
        log.info(meal.isNew() ? "Create{}" : "Update {}", meal);
        repository.save(meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
