package superapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import superapp.boundary.menu.Recipe;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.command.MiniAppCommandEntity;
import superapp.entity.object.SuperAppObjectEntity;
import superapp.repository.ObjectRepository;
import superapp.service.MenuService;
import superapp.service.MiniAppService;

import java.util.List;

@Service("MenuService")
public class MenuServiceImpl implements MiniAppService, MenuService {

    private final ObjectRepository objectRepository;
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    public MenuServiceImpl(
            ObjectRepository objectRepository
    ) {
        this.objectRepository = objectRepository;
    }

    // Метод для добавления элемента в меню
    public Mono<Object> addItemToMenu(SuperAppObjectBoundary itemToAdd, SuperAppObjectBoundary menu) {
//
//       // Check if the item to add or menu is null
//        if (itemToAdd == null || menu == null) {
//            return Mono.error(new IllegalArgumentException("Item to add or menu cannot be null"));
//        }
//
//        // Check if the item to add is a recipe
//        if (!itemToAdd.getType().equals("ingredient")) {
//            return Mono.error(new IllegalArgumentException("Only ingredient can be added to the menu"));
//        }
//
//        Recipe recipe =
//
//
//        // Add the item to the menu
//        if (menu.getObjectDetails().containsKey("ingredients")) {
//            //If the menu has already an ingredient in the menu, do not add the new ingredient
//            return Mono.error(new IllegalArgumentException("Menu already has an ingredient"));
//
//            // If the menu already has a list of items, add the new object to this list
//            List<String> menuItems = (List<String>) menu.getObjectDetails().get("ingredients");
//            menuItems.add(itemToAdd);
//        } else {
//            // Если в меню еще нет списка элементов, создаем новый список и добавляем в него новый объект
//            List<SuperAppObjectEntity> menuItems = new ArrayList<>();
//            menuItems.add(itemToAdd);
//            menu.getObjectDetails().put("menuItems", menuItems);
//        }
//
//        // Возвращаем обновленное меню
//        return Mono.just(menu);
        return null;
    }

    @Override
    public Mono<Object> removeItemFromMenu(SuperAppObjectEntity itemToRemove, SuperAppObjectEntity menu) {
        return null;
    }

    @Override
    public Mono<Object> updateItemInMenu(SuperAppObjectEntity itemToUpdate, SuperAppObjectEntity menu) {
        return null;
    }

    @Override
    public Mono<Object> getSpecificItemFromMenu(SuperAppObjectEntity itemToGet, SuperAppObjectEntity menu) {
        return null;
    }

    @Override
    public Mono<Object> removeAllItemsFromMenu(SuperAppObjectEntity menu) {
        return null;
    }

    @Override
    public Mono<Object> getMenuItems(SuperAppObjectEntity menu) {
        return null;
    }

    @Override
    public Flux<Object> handleCommand(MiniAppCommandEntity command) {
        return null;
    }

    private double calculateCalories(SuperAppObjectEntity object) {
        double height = Double.parseDouble(object.getObjectDetails().getOrDefault("height", "0").toString());
        double weight = Double.parseDouble(object.getObjectDetails().getOrDefault("weight", "0").toString());
        int age = Integer.parseInt(object.getObjectDetails().getOrDefault("age", "0").toString());
        return 0;
    }
}
