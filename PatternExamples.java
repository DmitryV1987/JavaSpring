// Singleton
public class Config {

    private static Config instance;

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    // ...
}

// Factory Method
public interface DaoFactory {

    Dao createDao();

}

public class UserDaoFactory implements DaoFactory {

    @Override
    public Dao createDao() {
        return new UserDao();
    }

}

public class OrderDaoFactory implements DaoFactory {

    @Override
    public Dao createDao() {
        return new OrderDao();
    }

}

// ...

// Decorator
public interface Service {

    void doSomething();

}

public class LoggingService implements Service {

    private Service service;

    public LoggingService(Service service) {
        this.service = service;
    }

    @Override
    public void doSomething() {
        System.out.println("Before doSomething()");
        service.doSomething();
        System.out.println("After doSomething()");
    }

}

public class CachingService implements Service {

    private Service service;

    public CachingService(Service service) {
        this.service = service;
    }

    @Override
    public void doSomething() {
    // Кэшируем результат
    String result = cache.get("key");
    if (result == null) {
        result = service.doSomething();
        cache.put("key", result);
    }
    return result;
}

}

// ...

// Observer
public interface Observer {

    void update();

}

public class Observable {

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // ...
}

public class DataChangedObserver implements Observer {

    @Override
    public void update() {
        System.out.println("Data changed!");
    }

}

public class Main {

    public static void main(String[] args) {
        // Singleton
        Config config = Config.getInstance();

        // Factory Method
        DaoFactory daoFactory = new UserDaoFactory();
        Dao dao = daoFactory.createDao();

        // Decorator
        Service service = new LoggingService(new CachingService(new SomeService()));
        service.doSomething();

        // Observer
        Observable observable = new Observable();
        observable.addObserver(new DataChangedObserver());
        observable.notifyObservers();
    }

}