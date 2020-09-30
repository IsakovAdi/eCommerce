package com.example.ecommerce.utils;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.SecondCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<String> getTransportSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Автомобили");
        categories.add("Автозапчасти и аксессуары");
        categories.add("Мотоциклы и мопеды");
        categories.add("Грузовой и с/х транспорт");
        categories.add("Водный транспорт");
        categories.add("Другой транспорт");
        return categories;
    }

    public static List<String> getHomeSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Квартиры");
        categories.add("Новостройка");
        categories.add("Дома");
        categories.add("Земельные участки");
        categories.add("Коммерческая недвижимость");
        categories.add("Комнаты");
        categories.add("Гаражи");
        return categories;
    }

    public static List<String> getGardenSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Мебель");
        categories.add("Ремонт и строительство");
        categories.add("Все для дома и сада");
        categories.add("Продукты питания");
        categories.add("Декор для дома");
        categories.add("Кухонные принадлежности");
        categories.add("Инструменты");
        categories.add("Комнатные растения");
        categories.add("Бытовая химия, хозтовары");
        categories.add("Канцтовары");
        categories.add("Сушилки");
        categories.add("Сейфы");
        categories.add("Гладильные доски");
        categories.add("Другие товары для дома");
        return categories;
    }

    public static List<String> getServicesSubCategory(){
        List<String> categories = new ArrayList<>();
        categories.add("Авто услуги");
        categories.add("Строительство и ремонт");
        categories.add("Мода, красота и здоровье");
        categories.add("Курьерская доставка");
        categories.add("Оборудование для бизнеса");
        categories.add("Ремонт техники");
        categories.add("Мебельные услуги");
        categories.add("Обучение, курсы");
        categories.add("Бизнес услуги");
        categories.add("IT, интернет, телеком");
        categories.add("Реклама и полиграфия");
        categories.add("Клининговые услуги");
        categories.add("Кейтеринг");
        categories.add("Пошив и ремонт одежды");
        categories.add("Рукоделие");
        categories.add("Няни, сиделки");
        categories.add("Юридические услуги");
        categories.add("Ритульаные услуги");
        categories.add("Охранные услуги");
        categories.add("Риэлторские услуги");
        categories.add("Визы и путешествия");
        categories.add("Другие услуги");
        return categories;
    }

    public static List<String> getElectronicSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Мобильные телефоны и аксессуары");
        categories.add("Компьютеры, ноутбуки и планшеты");
        categories.add("Бытовая техника");
        categories.add("Техника для кухни");
        categories.add("Видеоигры и приставки");
        categories.add("ТВ и видео");
        categories.add("Аудиотехника");
        categories.add("Автоэлектроника");
        categories.add("Фото и видеокамеры");
        return categories;
    }

    public static List<String> getWorkSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Поиск сотрудников");
        categories.add("Ищу работу");
        return categories;
    }

    public static List<String> getPrivateSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Женская одежда");
        categories.add("Женская обувь");
        categories.add("Мужская одежда");
        categories.add("Мужская обувь");
        categories.add("Свадебные платья и аксуссуары");
        categories.add("Аксессуары");
        categories.add("Красота и здоровье");
        categories.add("Другое");
        return categories;
    }

    public static List<String> getAnimalSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Зоотовары");
        categories.add("Коты");
        categories.add("Собаки");
        categories.add("Аквариумы");
        categories.add("С/х животные");
        categories.add("Грызуны");
        categories.add("Птицы");
        categories.add("Другие животные");
        return categories;
    }

    public static List<String> getSportSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Велосипеды");
        categories.add("Велоаксессуары");
        categories.add("Спорт и отдых");
        categories.add("Музыкальные инструменты");
        categories.add("Искусство и коллекционирование");
        categories.add("Охота и рыбалка");
        categories.add("Книги, журналы, CD. DVD");
        return categories;
    }
    public static String[] getMedicalSubCategories(){
        String[] medical = new String[14];
        medical[0] = "Маски медицинские";
        medical[1] = "Антисептики";
        medical[2] = "Гразусники, тепловизоры";
        medical[3] = "Кислородные концентраторы";
        medical[4] = "Пульсоксиметры";
        medical[5] = "Кислородные подушки";
        medical[6] = "Алкотестеры";
        medical[7] = "Бандажи";
        medical[8] = "Бахилы";
        medical[9] = "Бинты";
        medical[10] = "Инвалидные коляски";
        medical[11] = "Ингаляторы";
        medical[12] = "Медицинская мебель";
        medical[13] = "Медицинская одежда";
        return medical;
    }

    public static List<String> getKidsSubCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Детская одежда и обувь");
        categories.add("Детская мебель");
        categories.add("Коляски");
        categories.add("Игрушки");
        categories.add("Автокресла");
        categories.add("Другие товары для детей");
        return categories;
    }

    public static String[] getGoodCategorySpinner(){
        String[] categoryModels = new String[12];
        categoryModels[0] = "Транспорт";
        categoryModels[1] = "Недвижимость";
        categoryModels[2] = "Такси";
        categoryModels[3] = "Электроника";
        categoryModels[4] = "Услуги";
        categoryModels[5] = "Работа";
        categoryModels[6] = "Личные вещи";
        categoryModels[7] = "Животные";
        categoryModels[8] = "Спорт и хобби";
        categoryModels[9] = "Медтовары";
        categoryModels[10] = "Детский мир";
        categoryModels[11] = "Отдам даром";
        return categoryModels;
    }

    public static List<CategoryModel> getCategoryModels(){
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel(R.drawable.all, "Все"));
        categoryModels.add(new CategoryModel(R.drawable.transport, "Транспорт"));
        categoryModels.add(new CategoryModel(R.drawable.house, "Недвижимость"));
        categoryModels.add(new CategoryModel(R.drawable.taxi, "Такси"));
        categoryModels.add(new CategoryModel(R.drawable.electronic, "Электроника"));
        categoryModels.add(new CategoryModel(R.drawable.uslugi, "Услуги"));
        categoryModels.add(new CategoryModel(R.drawable.business, "Работа"));
        categoryModels.add(new CategoryModel(R.drawable.private_things, "Личные вещи"));
        categoryModels.add(new CategoryModel(R.drawable.animals, "Животные"));
        categoryModels.add(new CategoryModel(R.drawable.sport, "Спорт и хобби"));
        categoryModels.add(new CategoryModel(R.drawable.medicine, "Медтовары"));
        categoryModels.add(new CategoryModel(R.drawable.kids, "Детский мир"));
        categoryModels.add(new CategoryModel(R.drawable.present, "Отдам даром"));
        return categoryModels;
    }

    public static ArrayList<CategoryModel> getGoodCategory(){
        ArrayList<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel(R.drawable.transport, "Транспорт"));
        categoryModels.add(new CategoryModel(R.drawable.house, "Недвижимость"));
        categoryModels.add(new CategoryModel(R.drawable.taxi, "Такси"));
        categoryModels.add(new CategoryModel(R.drawable.electronic, "Электроника"));
        categoryModels.add(new CategoryModel(R.drawable.uslugi, "Услуги"));
        categoryModels.add(new CategoryModel(R.drawable.business, "Работа"));
        categoryModels.add(new CategoryModel(R.drawable.private_things, "Личные вещи"));
        categoryModels.add(new CategoryModel(R.drawable.animals, "Животные"));
        categoryModels.add(new CategoryModel(R.drawable.sport, "Спорт и хобби"));
        categoryModels.add(new CategoryModel(R.drawable.medicine, "Медтовары"));
        categoryModels.add(new CategoryModel(R.drawable.kids, "Детский мир"));
        categoryModels.add(new CategoryModel(R.drawable.present, "Отдам даром"));
        return categoryModels;
    }

    public static List<SecondCategoryModel> getSecondCategory() {
        List<SecondCategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new SecondCategoryModel(R.drawable.transport, "Транспорт", "Машины Кыргызстана"));
        categoryModels.add(new SecondCategoryModel(R.drawable.house, "Недвижимость", "Купля продажа. Аренда квартир"));
        categoryModels.add(new SecondCategoryModel(R.drawable.taxi, "Такси", "Такси по всем областям Кыргызстана"));
        categoryModels.add(new SecondCategoryModel(R.drawable.electronic, "Электроника", "Гипермаркет электроники"));
        categoryModels.add(new SecondCategoryModel(R.drawable.uslugi, "Услуги", "Ремонт, сервис и прочее"));
        categoryModels.add(new SecondCategoryModel(R.drawable.business, "Работа", "Самые актуальные вакансии"));
        categoryModels.add(new SecondCategoryModel(R.drawable.private_things, "Личные вещи", "Second hand, H & M"));
        categoryModels.add(new SecondCategoryModel(R.drawable.animals, "Животные", "Домашние милашки"));
        categoryModels.add(new SecondCategoryModel(R.drawable.sport, "Спорт и хобби", "Спортивные товары"));
        categoryModels.add(new SecondCategoryModel(R.drawable.medicine, "Медтовары", "Лекарства и прочее"));
        categoryModels.add(new SecondCategoryModel(R.drawable.kids, "Детский мир", "Одежды для детей"));
        categoryModels.add(new SecondCategoryModel(R.drawable.present, "Отдам даром", "Вещи, домашние животные"));
        return categoryModels;
    }
}
