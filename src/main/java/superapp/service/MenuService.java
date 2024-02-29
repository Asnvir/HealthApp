package superapp.service;

import reactor.core.publisher.Mono;
import superapp.boundary.object.SuperAppObjectBoundary;
import superapp.entity.object.SuperAppObjectEntity;

public interface MenuService {

    public Mono<Object> addItemToMenu(SuperAppObjectBoundary itemToAdd, SuperAppObjectBoundary menu);
    public Mono<Object> removeItemFromMenu(SuperAppObjectEntity itemToRemove, SuperAppObjectEntity menu);
    public Mono<Object> updateItemInMenu(SuperAppObjectEntity itemToUpdate, SuperAppObjectEntity menu);
    public Mono<Object> getSpecificItemFromMenu(SuperAppObjectEntity itemToGet, SuperAppObjectEntity menu);
    public Mono<Object> removeAllItemsFromMenu(SuperAppObjectEntity menu);
    public Mono<Object> getMenuItems(SuperAppObjectEntity menu);

}
