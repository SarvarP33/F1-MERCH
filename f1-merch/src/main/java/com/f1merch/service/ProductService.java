package com.f1merch.service;

import com.f1merch.model.Category;
import com.f1merch.model.Product;
import com.f1merch.model.Team;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/** Каталог товаров. Данные хранятся в памяти — при желании легко заменить на БД (Spring Data JPA). */
@Service
public class ProductService {

    private static final List<String> CLOTHES = List.of("XS", "S", "M", "L", "XL", "XXL");
    private static final List<String> CAPS = List.of("One size");

    private final Map<Long, Product> products = new LinkedHashMap<>();

    public ProductService() {
        add("Командная футболка Ferrari 2026", "Хлопковая футболка в фирменном красном цвете Скудерии. Дышащая ткань, плотный принт.",
                "12990", Category.TSHIRT, Team.FERRARI, "👕", CLOTHES, true);
        add("Кепка Ferrari Driver", "Шестипанельная кепка с регулируемой застёжкой — как у пилотов на пит-лейн.",
                "8490", Category.CAP, Team.FERRARI, "🧢", CAPS, false);
        add("Модель болида Ferrari 1:43", "Детализированная коллекционная модель из литого металла в прозрачном боксе.",
                "34990", Category.MODEL, Team.FERRARI, "🏎️", null, true);
        add("Худи Red Bull Racing", "Тёплое худи с капюшоном и карманом-кенгуру. Вышитая символика команды.",
                "24990", Category.HOODIE, Team.RED_BULL, "🧥", CLOTHES, true);
        add("Куртка Red Bull Team Softshell", "Ветро- и водоотталкивающая куртка, в которой работают механики.",
                "49990", Category.JACKET, Team.RED_BULL, "🧥", CLOTHES, false);
        add("Кепка Red Bull Racing", "Классическая кепка тёмно-синего цвета с изогнутым козырьком.",
                "8990", Category.CAP, Team.RED_BULL, "🧢", CAPS, false);
        add("Футболка Mercedes Team", "Лёгкая футболка из технологичной ткани в бирюзово-чёрной гамме.",
                "11990", Category.TSHIRT, Team.MERCEDES, "👕", CLOTHES, false);
        add("Модель болида Mercedes 1:18", "Крупная модель с открывающимися элементами и высокой детализацией.",
                "89990", Category.MODEL, Team.MERCEDES, "🏎️", null, true);
        add("Худи McLaren Papaya", "Яркое худи в фирменном папайя-оранжевом цвете.",
                "23990", Category.HOODIE, Team.MCLAREN, "🧥", CLOTHES, true);
        add("Футболка McLaren Team", "Хлопковая футболка с логотипом команды на груди.",
                "11490", Category.TSHIRT, Team.MCLAREN, "👕", CLOTHES, false);
        add("Кепка Aston Martin", "Кепка в британском гоночном зелёном с лаймовыми акцентами.",
                "8490", Category.CAP, Team.ASTON_MARTIN, "🧢", CAPS, false);
        add("Куртка Aston Martin Rain", "Лёгкая непромокаемая куртка для гонок под дождём.",
                "44990", Category.JACKET, Team.ASTON_MARTIN, "🧥", CLOTHES, false);
        add("Футболка Williams Racing", "Футболка в сине-голубой расцветке легендарной британской команды.",
                "10990", Category.TSHIRT, Team.WILLIAMS, "👕", CLOTHES, false);
        add("Брелок «Гоночный шлем»", "Металлический брелок в виде шлема пилота.",
                "2990", Category.ACCESSORY, Team.F1, "🔑", null, false);
        add("Кружка «Pit Stop»", "Керамическая кружка 350 мл с картой трассы.",
                "4490", Category.ACCESSORY, Team.F1, "☕", null, false);
        add("Клетчатый флаг финиша", "Большой флаг 90×150 см — для трибуны или стены.",
                "5990", Category.ACCESSORY, Team.F1, "🏁", null, true);
    }

    private void add(String name, String description, String price, Category category,
                     Team team, String icon, List<String> sizes, boolean featured) {
        long id = products.size() + 1L;
        products.put(id, new Product(id, name, description, new BigDecimal(price),
                category, team, icon, sizes, featured));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findFeatured() {
        return products.values().stream().filter(Product::isFeatured).collect(Collectors.toList());
    }

    public List<Product> search(Team team, Category category, String query, String sort) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        Comparator<Product> comparator = switch (sort == null ? "" : sort) {
            case "price_asc" -> Comparator.comparing(Product::getPrice);
            case "price_desc" -> Comparator.comparing(Product::getPrice).reversed();
            case "name" -> Comparator.comparing(Product::getName);
            default -> Comparator.comparing(Product::getId);
        };
        return products.values().stream()
                .filter(p -> team == null || p.getTeam() == team)
                .filter(p -> category == null || p.getCategory() == category)
                .filter(p -> q.isEmpty() || p.getName().toLowerCase(Locale.ROOT).contains(q)
                        || p.getDescription().toLowerCase(Locale.ROOT).contains(q))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Product> findRelated(Product product, int limit) {
        return products.values().stream()
                .filter(p -> !p.getId().equals(product.getId()))
                .filter(p -> p.getTeam() == product.getTeam() || p.getCategory() == product.getCategory())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
