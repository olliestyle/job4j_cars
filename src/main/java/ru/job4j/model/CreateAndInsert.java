package ru.job4j.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

public class CreateAndInsert {
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

            CarBrand bmw = CarBrand.of("BMW");
            CarBrand kia = CarBrand.of("KIA");
            CarBrand audi = CarBrand.of("AUDI");
            CarBrand renault = CarBrand.of("Renault");

            CarModel i320 = CarModel.of("320I");
            CarModel i318 = CarModel.of("318I");
            CarModel i325 = CarModel.of("325I");
            CarModel x5 = CarModel.of("X5");
            CarModel x6 = CarModel.of("X6");
            CarModel soul = CarModel.of("Soul");
            CarModel ceed = CarModel.of("Ceed");
            CarModel cerato = CarModel.of("Cerato");
            CarModel sorento = CarModel.of("Sorento");
            CarModel a4 = CarModel.of("A4");
            CarModel a6 = CarModel.of("A6");
            CarModel a8 = CarModel.of("A8");
            CarModel q7 = CarModel.of("Q7");
            CarModel q8 = CarModel.of("Q8");
            CarModel stepway = CarModel.of("Stepway II");
            CarModel arkana = CarModel.of("Arkana");
            CarModel logan = CarModel.of("Logan");
            Stream.of(i318, i320, i325, x5, x6).forEach(carModel -> {
                bmw.getCarModels().add(carModel);
                carModel.setCarBrand(bmw);
            });
            Stream.of(soul, ceed, cerato, sorento).forEach(carModel -> {
                kia.getCarModels().add(carModel);
                carModel.setCarBrand(kia);
            });
            Stream.of(a4, a6, a8, q7, q8).forEach(carModel -> {
                audi.getCarModels().add(carModel);
                carModel.setCarBrand(audi);
            });
            Stream.of(stepway, arkana, logan).forEach(carModel -> {
                renault.getCarModels().add(carModel);
                carModel.setCarBrand(renault);
            });

            BodyType suv = BodyType.of("Suv");
            BodyType sedan = BodyType.of("Sedan");
            BodyType cabriolet = BodyType.of("Cabriolet");
            BodyType coupe = BodyType.of("Coupe");
            BodyType hatchback = BodyType.of("Hatchback");

            Transmission auto = Transmission.of("Автоматическая");
            Transmission robot = Transmission.of("Робот");
            Transmission variator = Transmission.of("Вариатор");
            Transmission manual = Transmission.of("Механическая");

            Stream.of(i318, i320, i325, x5, x6, soul, ceed, cerato,
                    sorento, a4, a6, a8, q7, q8, stepway, arkana, logan).forEach(carModel -> carModel.getTransmissions().add(auto));
            Stream.of(i318, i320, soul, ceed, cerato,
                    a4, stepway, logan).forEach(carModel -> carModel.getTransmissions().add(robot));
            Stream.of(i325, x5, x6, soul, ceed, cerato,
                    sorento, a4, a6, arkana).forEach(carModel -> carModel.getTransmissions().add(variator));
            Stream.of(i318, soul, ceed, stepway, logan).forEach(carModel -> carModel.getTransmissions().add(manual));

            Stream.of(auto, robot, variator, manual).forEach(trsm -> trsm.setCarModels(Set.of(soul, ceed)));
            Stream.of(auto, robot, manual).forEach(trsm -> trsm.setCarModels(Set.of(i318, stepway)));
            Stream.of(auto, robot, variator).forEach(trsm -> trsm.setCarModels(Set.of(cerato, a4)));
            Stream.of(auto, variator).forEach(trsm -> trsm.setCarModels(Set.of(i325, x5, x6, a6, sorento, arkana)));
            Stream.of(auto, robot).forEach(trsm -> trsm.setCarModels(Set.of(i320)));
            Stream.of(auto, manual).forEach(trsm -> trsm.setCarModels(Set.of(logan)));
            Stream.of(auto).forEach(trsm -> trsm.setCarModels(Set.of(a8, q7, q8)));

            Stream.of(x5, x6, sorento, q7, q8).forEach(carModel -> {
                suv.getCarModels().add(carModel);
                carModel.setBodyType(suv);
            });
            Stream.of(i318, cerato, logan, a4, a6, a8).forEach(carModel -> {
                sedan.getCarModels().add(carModel);
                carModel.setBodyType(sedan);
            });
            Stream.of(i325).forEach(carModel -> {
                cabriolet.getCarModels().add(carModel);
                carModel.setBodyType(cabriolet);
            });
            Stream.of(i320).forEach(carModel -> {
                coupe.getCarModels().add(carModel);
                carModel.setBodyType(coupe);
            });
            Stream.of(soul, arkana, stepway, ceed).forEach(carModel -> {
                hatchback.getCarModels().add(carModel);
                carModel.setBodyType(hatchback);
            });

            User murat = User.of("Murat", "murat@gmail.com", "123");
            User oleg = User.of("Oleg", "oleg@gmail.com", "123");
            User pavel = User.of("Pavel", "pavel@gmail.com", "123");
            Stream.of(auto, robot, variator, manual).forEach(session::save);
            Stream.of(suv, sedan, cabriolet, coupe, hatchback).forEach(session::save);
            Stream.of(i318, i320, i325, x5, x6, soul, ceed, cerato,
                    sorento, a4, a6, a8, q7, q8, stepway, arkana, logan).forEach(session::save);
            Stream.of(bmw, kia, audi, renault).forEach(session::save);
            Stream.of(murat, oleg, pavel).forEach(session::save);

            String description = "Не бита, не крашена";
            BigDecimal price = new BigDecimal(7_500_000);
            String photo = "http://privetphoto.com/1238123";
            short manufactureYear = 2020;
            int mileage = 33333;

            CarAd carAd1 = new CarAd.Builder()
                    .user(murat).carBrand(bmw)
                    .carModel(bmw.getCarModels().get(0))
                    .bodyType(bmw.getCarModels().get(0).getBodyType())
                    .transmission(auto).description(description).price(price)
                    .photo(photo).manufactureYear(manufactureYear).mileage(mileage)
                    .build();
            CarAd carAd2 = new CarAd.Builder()
                    .user(oleg).carBrand(audi)
                    .carModel(audi.getCarModels().get(1))
                    .bodyType(audi.getCarModels().get(1).getBodyType())
                    .transmission(auto).description(description).price(price)
                    .photo(photo).manufactureYear(manufactureYear).mileage(mileage)
                    .build();
            CarAd carAd3 = new CarAd.Builder()
                    .user(pavel).carBrand(kia)
                    .carModel(kia.getCarModels().get(2))
                    .bodyType(kia.getCarModels().get(2).getBodyType())
                    .transmission(auto).description(description).price(price)
                    .photo(photo).manufactureYear(manufactureYear).mileage(mileage)
                    .build();

            session.save(carAd1);
            session.save(carAd2);
            session.save(carAd3);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
