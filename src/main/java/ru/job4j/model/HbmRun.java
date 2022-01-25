package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            SessionFactory factory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            Session session = factory.openSession();
            session.beginTransaction();

            Driver driver1 = Driver.of("Murat", "Baibolatov");
            Driver driver2 = Driver.of("Egor", "Egorov");

            Engine engine1 = Engine.of("V8");
            Engine engine2 = Engine.of("V12");
            Engine engine3 = Engine.of("W12");
            Engine engine4 = Engine.of("W16");

            Car car1 = Car.of("Audi", "A8");
            Car car2 = Car.of("Audi", "RS6");
            Car car3 = Car.of("Audi", "Q7");
            Car car4 = Car.of("BMW", "530i");
            Car car5 = Car.of("BMW", "X5");

            engine1.getCars().add(car1);
            engine1.getCars().add(car2);
            engine2.getCars().add(car3);
            engine2.getCars().add(car4);
            engine3.getCars().add(car4);
            engine4.getCars().add(car5);

            car1.setEngine(engine1);
            car2.setEngine(engine1);
            car3.setEngine(engine2);
            car4.setEngine(engine2);
            car5.setEngine(engine3);
            car5.setEngine(engine4);

            driver1.getCars().add(car1);
            driver1.getCars().add(car2);
            driver2.getCars().add(car3);
            driver2.getCars().add(car4);
            driver2.getCars().add(car5);

            car1.getDrivers().add(driver1);
            car2.getDrivers().add(driver1);
            car3.getDrivers().add(driver2);
            car4.getDrivers().add(driver2);
            car5.getDrivers().add(driver2);

            session.save(driver1);
            session.save(driver2);
            session.save(car1);
            session.save(car2);
            session.save(car3);
            session.save(car4);
            session.save(car5);
            session.save(engine1);
            session.save(engine2);
            session.save(engine3);
            session.save(engine4);

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
